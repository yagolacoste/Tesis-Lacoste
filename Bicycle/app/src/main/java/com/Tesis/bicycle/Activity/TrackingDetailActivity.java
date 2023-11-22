package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsApiRest;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.Routes;
import com.Tesis.bicycle.Model.Room.Tracked;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Presenter.Notifications;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.Service.Room.RoutesService;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.RouteViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.TrackedRoomViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.RandomStringUtils;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingDetailActivity extends AppCompatActivity {

    private TextView tvDistanceTrackingDetail,tvSpeedTrackingDetail,tvTimeTrackingDetail,tvDateTrackingDetail;

    private TextInputLayout tvTitleTrackingDetail,tvDescriptionTrackingDetail;
    private EditText edtTitle,edtDescription;
    private Button btnSave,btnDiscard;
    private MapView myOpenMapView;
    private GPSService.LocationBinder locationBinder=null;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private StatisticsViewModel statisticsViewModel;

    private TrackedRoomViewModel trackedRoomViewModel;

    private OpenStreetMap openStreetMap;

    private Notifications notifications;

    private RoutesService db;




    private ServiceConnection lsc=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            locationBinder= (GPSService.LocationBinder) iBinder;

//            openStreetMap.initLayer(TrackingDetailActivity.this,locationBinder.getGeoPoints().get(0));
//            filterData();//filtro la data para ver si se hizo un camino o estan correcto los datos
            updateUI();

            //////////////////ROOM/////////////////////////
            Routes route=new Routes();
            route.setId(locationBinder.getId());
            route.setUnfilteredPoints(locationBinder.getUnfilteredPoints());
            route.setFilteredPoints(locationBinder.getFilteredPoints());
            route.setFilteredBuffer_points(locationBinder.getCoordinates());
            db.add(route);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

//    private void filterData() {//revisar esto de cuando se hace una ruta pero no termino de hacerla
//        if(checkConditionRoutes()){
//            locationBinder.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
////            inputNameAndDescription();
//            locationBinder.setBattle(null);
//        }
//    }

    private void updateUI() {
        //Pone los datos capturados
        openStreetMap.initLayer(TrackingDetailActivity.this,locationBinder.getGeoPoints().get(0));
        tvDistanceTrackingDetail.setText(String.valueOf(locationBinder.getDistanceString()));
        //tvSpeedTrackingDetail.setText(locationBinder.getAvgSpeedToString());
        tvSpeedTrackingDetail.setText(locationBinder.getAvgSpeedCalcualtedToString());
        tvTimeTrackingDetail.setText(locationBinder.getDuration());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        tvDateTrackingDetail.setText(formatter.format(locationBinder.getTimeCreated()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(locationBinder.isRepeat()){
                if(locationBinder.isDeviation() || !locationBinder.equalsBetweenNewRouteAndReplay()) {//reviso si se desvio y si ambas son iguales
                    openStreetMap.draw(locationBinder.getRouteReplay());//pone la ruta original
                    int color = Color.argb(128, 255, 0, 0);
                    openStreetMap.setColor(color);
                    openStreetMap.draw(locationBinder.getGeoPoints().stream().map(p -> new GeoPoint(p.getLatitude(), p.getLongitude())).collect(Collectors.toList()));//ruta grabada nueva con otro color
                    notifications.addNotification("Oops...", "You didn't complete the route or divert but you have new statistics ;) ");
                    //set battle y repeat para que sea almacenada una nueva ruta
                    locationBinder.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
                    locationBinder.setBattle(null);
                    locationBinder.setRepeat(false);
                }else {
                    openStreetMap.draw(locationBinder.getRouteReplay());//pone la ruta original
                    edtTitle.setText(String.valueOf(locationBinder.getTitle()));
                    edtDescription.setText(String.valueOf(locationBinder.getDescription()));

                }
            }else{
                openStreetMap.draw(locationBinder.getGeoPoints().stream().map(p -> new GeoPoint(p.getLatitude(), p.getLongitude())).collect(Collectors.toList()));
            }
        }
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_detail);
        InitViewModel();
        init();
    }

    private void InitViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.accessTokenRoomViewModel=vmp.get(AccessTokenRoomViewModel.class);
        this.statisticsViewModel =vmp.get(StatisticsViewModel.class);
        this.trackedRoomViewModel=vmp.get(TrackedRoomViewModel.class);
        this.db = Room.databaseBuilder(TrackingDetailActivity.this, AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build().routesService();
    }

    private void init(){
        //Inicializo los textview
        tvTitleTrackingDetail=findViewById(R.id.tvTitleTrackingDetail);
        tvDescriptionTrackingDetail=findViewById(R.id.tvDescriptionTrackingDetail);
        tvDistanceTrackingDetail=findViewById(R.id.tvDistanceTrackingDetail);
        tvSpeedTrackingDetail=findViewById(R.id.tvSpeedTrackingDetail);
        tvTimeTrackingDetail=findViewById(R.id.tvTimeTrackingDetail);
        tvDateTrackingDetail=findViewById(R.id.tvDateTrackingDetail);
        myOpenMapView=findViewById(R.id.v_map);
        btnSave=findViewById(R.id.btnSave);
        btnDiscard=findViewById(R.id.btnDiscard);
        openStreetMap=new OpenStreetMap(myOpenMapView);
        edtTitle=findViewById(R.id.edtTitle);
        edtDescription=findViewById(R.id.edtDescription);
        notifications=new Notifications(TrackingDetailActivity.this);
        Intent intent = new Intent(this, GPSService.class);
        getApplicationContext().bindService(intent, lsc, Context.BIND_ABOVE_CLIENT);

        btnSave.setOnClickListener(view -> {
            if(validate()) {
                storeData();
            }
        });
        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenuActivity();
            }
        });

        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTitleTrackingDetail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvDescriptionTrackingDetail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    private void storeData() {
            this.accessTokenRoomViewModel.getFirst().observe(this, response -> {
                RefreshTokenDto refreshTokenDto = response;
                StatisticsApiRest statisticsApiRest = new StatisticsApiRest();
                statisticsApiRest.setAppUser(refreshTokenDto.getId());
                statisticsApiRest.setDistance(locationBinder.getDistance());
                statisticsApiRest.setAvgSpeed(locationBinder.getAvgSpeedCalculated());
                statisticsApiRest.setTime(locationBinder.getTimeLocalTime());
                statisticsApiRest.setTimeCreated(locationBinder.getTimeCreated());
                statisticsApiRest.setWeather("");
                statisticsApiRest.setDescription(locationBinder.getDescription());
                statisticsApiRest.setTitle(locationBinder.getTitle());
                statisticsApiRest.setCoordinates(locationBinder.getGeoPoints());
                statisticsApiRest.setBattleId(locationBinder.getBattleId());
                if (!locationBinder.getId().contains("-")) {
                    statisticsApiRest.setRoute(refreshTokenDto.getId() + "-" + locationBinder.getId());
                } else
                    statisticsApiRest.setRoute(locationBinder.getId());
                statisticsViewModel.addStatistic(statisticsApiRest).observe(this, resp -> {
                    if (resp) {
                        if (statisticsApiRest.getBattleId() != null) {
                            notifications.addNotification("Good Job!", "You completed the battle wait the result");
                        } else if (locationBinder.isRepeat()) {
                            notifications.addNotification("Good Job!", "You completed the route");
                        } else
                            notifications.addNotification("Congratulation!", "You save new route ! ");
                        backToMenuActivity();
                    } else {
                        Tracked tracked=new Tracked(statisticsApiRest);
                        trackedRoomViewModel.add(tracked);
                        notifications.errorMessage("There isn't connection,You statistics is wait for update");
                        Toast.makeText(this, "There isn't conextion, ", Toast.LENGTH_LONG).show();
                    }
                });
            });

    }

    private boolean validate() {
        boolean result = true;
        String title, description;
        title = edtTitle.getText().toString();
        description = edtDescription.getText().toString();
        if (title.isEmpty()) {
            tvTitleTrackingDetail.setError("Insert Title");
            result = false;
        } else {
            tvTitleTrackingDetail.setErrorEnabled(false);
            locationBinder.setTitle(title);
        }
        if (description.isEmpty()) {
            tvDescriptionTrackingDetail.setError("Insert Description");
            result = false;
        } else {
            tvDescriptionTrackingDetail.setErrorEnabled(false);
            locationBinder.setDescription(description);
        }
        return result;
    }



    private void backToMenuActivity() {

        Intent i=new Intent(TrackingDetailActivity.this, NavInitActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stopService(new Intent(this,GPSService.class));///Esto puedo ver si ponerlo antes
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Desvincular el servicio si está vinculado
        if (locationBinder != null) {
            getApplicationContext().unbindService(lsc);
        }
    }



}