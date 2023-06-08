package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;

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
            if(locationBinder.getTitle().equals("")){
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
                AppUserHasRouteApiRest appUserHasRouteApiRest=new AppUserHasRouteApiRest();
                appUserHasRouteApiRest.setAppUser(refreshTokenDto.getId());
                appUserHasRouteApiRest.setDistance(locationBinder.getDistance());
                appUserHasRouteApiRest.setAvgSpeed(locationBinder.getAvgSpeed());
                appUserHasRouteApiRest.setTime(locationBinder.getTimeLocalTime());
                appUserHasRouteApiRest.setTimeCreated(locationBinder.getTimeCreated());
                appUserHasRouteApiRest.setWeather("");
                appUserHasRouteApiRest.setDescription(locationBinder.getDescription());
                appUserHasRouteApiRest.setTitle(locationBinder.getTitle());
                if(!locationBinder.getId().contains("-"))
                    appUserHasRouteApiRest.setRoute(refreshTokenDto.getId()+"-"+locationBinder.getId());
                else
                    appUserHasRouteApiRest.setRoute(locationBinder.getId());
                appUserHasRouteApiRest.setCoordinates(locationBinder.getGeoPoints());
                statisticsViewModel.addStatistic(appUserHasRouteApiRest).observe(this, resp->{
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
        stopService(new Intent(this,GPSService.class));
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

//    private void drawRoute(List<GeoPoint> routes) {
//        Marker startMarker=new Marker(myOpenMapView);
//        startMarker.setPosition(routes.get(0));
//
//        myOpenMapView.getOverlays().add(startMarker);
//        RoadManager roadManager=new OSRMRoadManager(TrackingDetailActivity.this,"OBP_Tuto/1.0");
//        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
//        Road road=roadManager.getRoad((ArrayList<GeoPoint>) routes);
////        Polyline roadOverlay=new Polyline();
////        roadOverlay.setWidth(20f);
//        Polyline roadOverlay=RoadManager.buildRoadOverlay(road, 0x800000FF, 25.0f);
//
//
//        // Polyline roadOverlay=RoadManager.buildRoadOverlay(road).setWidth(2.0f);
//        myOpenMapView.getOverlays().add(roadOverlay);
//        Marker endMarker=new Marker(myOpenMapView);
//        endMarker.setPosition(routes.get(routes.size()-1));
//
//        myOpenMapView.getOverlays().add(endMarker);
//
//        myOpenMapView.invalidate();
//    }
//
//    private void initLayer(Context ctx) {
//        //Seteo de mapa en tandil statico
//        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);
//        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
//        myOpenMapView.setBuiltInZoomControls(true);
//        myOpenMapView.setMultiTouchControls(true);
//        IMapController mapController=myOpenMapView.getController();
//        mapController.setZoom(15);
//        mapController.setCenter(tandil);
//        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
//        mRotationGestureOverlay.setEnabled(true);
//        myOpenMapView.setMultiTouchControls(true);
//        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
//        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), myOpenMapView);
//        this.mCompassOverlay.enableCompass();
//        myOpenMapView.getOverlays().add(this.mCompassOverlay);
//        myOpenMapView.invalidate();
//
//    }


}