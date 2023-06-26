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

import com.Tesis.bicycle.Dto.ApiRest.StatisticsApiRest;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.stream.Collectors;

public class TrackingDetailActivity extends AppCompatActivity {

    private TextView tv_distance_value,tv_speed_value,tv_time_value,tv_date_value,tv_session_value;
    private Button btn_save,btn_discard;
    private MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    private GPSService.LocationBinder locationBinder=null;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private StatisticsViewModel statisticsViewModel;

    private OpenStreetMap openStreetMap;



    private ServiceConnection lsc=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            locationBinder= (GPSService.LocationBinder) iBinder;
            openStreetMap.initLayer(TrackingDetailActivity.this,locationBinder.getGeoPoints().get(0));//Probar el delay sino volver a lo viejo
            updateUI();
            if(!locationBinder.isRepeat()){
                inputNameAndDescription();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void updateUI() {
        tv_distance_value.setText(String.valueOf(locationBinder.getDistanceString()));
        tv_speed_value.setText(String.valueOf(locationBinder.getAvgSpeedString()));
        tv_time_value.setText(String.valueOf(locationBinder.getTimeString()));
        tv_date_value.setText(String.valueOf(locationBinder.getTimeCreated()));
        openStreetMap.draw(locationBinder.getGeoPoints());
        if(locationBinder.isRepeat()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                int color = Color.argb(128, 255, 0, 0);
                openStreetMap.setColor(color);
                openStreetMap.draw(locationBinder.getTrkPoints().stream().map(p->new GeoPoint(p.getLatitude(),p.getLongitude())).collect(Collectors.toList()));

            }
        }
//        this.drawRoute(locationBinder.getGeoPoints());
//        tv_session_value.setText(String.valueOf(locationBinder.));//agregar el id session
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
    }

    private void init(){
        //Inicializo los textview
        tv_distance_value=findViewById(R.id.tv_distance_value);
        tv_speed_value=findViewById(R.id.tv_speed_value);
        tv_time_value=findViewById(R.id.tv_time_value);
        tv_date_value=findViewById(R.id.tv_date_value);
        tv_session_value=findViewById(R.id.tv_session_value);
        btn_save=findViewById(R.id.btn_save);
        btn_discard=findViewById(R.id.btn_discard);
        myOpenMapView=findViewById(R.id.v_map);
        openStreetMap=new OpenStreetMap(myOpenMapView);
        Intent intent = new Intent(this, GPSService.class);
        getApplicationContext().bindService(intent, lsc, Context.BIND_ABOVE_CLIENT);
        btn_save.setOnClickListener(view -> {
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
            backToMenuActivity();
        });

        btn_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenuActivity();
            }
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