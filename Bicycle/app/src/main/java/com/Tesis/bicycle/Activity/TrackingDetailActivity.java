package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsApiRest;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import org.apache.commons.lang3.RandomStringUtils;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.stream.Collectors;

public class TrackingDetailActivity extends AppCompatActivity {

    private TextView tvTitleTrackingDetail,tvDescriptionTrackingDetail,tvDistanceTrackingDetail,tvSpeedTrackingDetail,tvTimeTrackingDetail,tvDateTrackingDetail;
    private Button btnSave,btnDiscard;
    private MapView myOpenMapView;
    private GPSService.LocationBinder locationBinder=null;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private StatisticsViewModel statisticsViewModel;
    private OpenStreetMap openStreetMap;



    private ServiceConnection lsc=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            locationBinder= (GPSService.LocationBinder) iBinder;
            filterData();//filtro la data para ver si se hizo un camino o estan correcto los datos
            updateUI();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void filterData() {
//        if(!locationBinder.isRepeat()){
//            locationBinder.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
//            inputNameAndDescription();
//        }else if(!locationBinder.getEqualsRoutes()){
//            locationBinder.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
//            inputNameAndDescription();
//            }
        if(!locationBinder.isRepeat() || !locationBinder.getEqualsRoutes()){
            locationBinder.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
            inputNameAndDescription();
        }
    }

    private void updateUI() {
        openStreetMap.initLayer(TrackingDetailActivity.this,locationBinder.getGeoPoints().get(0));
        tvTitleTrackingDetail.setText(String.valueOf(locationBinder.getTitle()));
        tvDescriptionTrackingDetail.setText(String.valueOf(locationBinder.getDescription()));
        tvDistanceTrackingDetail.setText(String.valueOf(locationBinder.getDistanceString()));
        tvSpeedTrackingDetail.setText(String.valueOf(locationBinder.getAvgSpeedString()));
        tvTimeTrackingDetail.setText(String.valueOf(locationBinder.getTimeString()));
        tvDateTrackingDetail.setText(String.valueOf(locationBinder.getTimeCreated()));
        openStreetMap.draw(locationBinder.getGeoPoints());
         if(locationBinder.isRepeat()){
            //updateUI();
//            openStreetMap.draw(locationBinder.getGeoPoints());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                int color = Color.argb(128, 255, 0, 0);
                openStreetMap.setColor(color);
                openStreetMap.draw(locationBinder.getTrkPoints().stream().map(p->new GeoPoint(p.getLatitude(),p.getLongitude())).collect(Collectors.toList()));
            }
        }else if(locationBinder.isRepeat() && !locationBinder.getEqualsRoutes()){

            // updateUI();
//            openStreetMap.draw(locationBinder.getGeoPoints());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                int color = Color.argb(128, 255, 0, 0);
                openStreetMap.setColor(color);
                openStreetMap.draw(locationBinder.getTrkPoints().stream().map(p->new GeoPoint(p.getLatitude(),p.getLongitude())).collect(Collectors.toList()));
            }
        }

    }
        //       openStreetMap.draw(locationBinder.getGeoPoints());
//        if(locationBinder.isRepeat()){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                int color = Color.argb(128, 255, 0, 0);
//                openStreetMap.setColor(color);
//                openStreetMap.draw(locationBinder.getTrkPoints().stream().map(p->new GeoPoint(p.getLatitude(),p.getLongitude())).collect(Collectors.toList()));
//
//            }
//        }
//        this.drawRoute(locationBinder.getGeoPoints());
//        tv_session_value.setText(String.valueOf(locationBinder.));//agregar el id session

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
        Intent intent = new Intent(this, GPSService.class);
        getApplicationContext().bindService(intent, lsc, Context.BIND_ABOVE_CLIENT);
        btnSave.setOnClickListener(view -> {
            storeData();
            backToMenuActivity();
        });

        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenuActivity();
            }
        });
    }

    private void storeData() {
        this.accessTokenRoomViewModel.getFirst().observe(this,response->{
            RefreshTokenDto refreshTokenDto=response;
            StatisticsApiRest statisticsApiRest=new StatisticsApiRest();
            statisticsApiRest.setAppUser(refreshTokenDto.getId());
            statisticsApiRest.setDistance(locationBinder.getDistance());
            statisticsApiRest.setAvgSpeed(locationBinder.getAvgSpeed());
            statisticsApiRest.setTime(locationBinder.getTimeLocalTime());
            statisticsApiRest.setTimeCreated(locationBinder.getTimeCreated());
            statisticsApiRest.setWeather("");
            statisticsApiRest.setDescription(locationBinder.getDescription());
            statisticsApiRest.setTitle(locationBinder.getTitle());
            if(!locationBinder.getId().contains("-"))
                statisticsApiRest.setRoute(refreshTokenDto.getId()+"-"+locationBinder.getId());
            else
                statisticsApiRest.setRoute(locationBinder.getId());
            statisticsApiRest.setCoordinates(locationBinder.getGeoPoints());
            statisticsApiRest.setBattleId(locationBinder.getBattleId());
            statisticsViewModel.addStatistic(statisticsApiRest).observe(this, resp->{
            });
        });
    }


    private void backToMenuActivity() {
        Intent i=new Intent(TrackingDetailActivity.this, MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stopService(new Intent(this,GPSService.class));///Esto puedo ver si ponerlo antes
        startActivity(i);
    }

    private void inputNameAndDescription() {
        androidx.appcompat.app.AlertDialog.Builder myDialog=new androidx.appcompat.app.AlertDialog.Builder(TrackingDetailActivity.this);
        myDialog.setTitle("Input name and description for the new route");
        LinearLayout layout=new LinearLayout(this);
        EditText name=new EditText(TrackingDetailActivity.this);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        name.setHint("Name");
        layout.addView(name);
        EditText description=new EditText(TrackingDetailActivity.this);
        description.setInputType(InputType.TYPE_CLASS_TEXT);
        description.setHint("Description");
        layout.addView(description);

        myDialog.setView(layout);

        myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locationBinder.setTitle(name.getText().toString());
                locationBinder.setDescription(description.getText().toString());
            }
        });
        myDialog.show();
    }


}