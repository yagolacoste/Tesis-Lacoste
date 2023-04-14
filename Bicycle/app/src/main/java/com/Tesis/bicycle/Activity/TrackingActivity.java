package com.Tesis.bicycle.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Model.Room.Route;

import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private TextView tv_distance,tv_avgSpeed,tv_time;
    private Button btn_start,btn_turnoff;

    GPSService.LocationBinder locationBinder=null;
    int activityTypeInd;
    Tracking repeat=null;
    boolean stopped = false,isRepeat=false;
    //retrofit
    private RouteDetailsDto routeDetailsDto;
    //osm
    private MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    public  Marker startMarker;


    Handler updateTimeHandler = new Handler();
    Handler updateStatsHandler = new Handler();

    Runnable updateTimeThread= new Runnable() {
        @Override
        public void run() {
            String timeString=locationBinder.getTimeString();
            tv_time.setText(timeString);
            if (!stopped) updateTimeHandler.postDelayed(this, 0);
        }
    };

    Runnable updateStatsThread=new Runnable() {
        @Override
        public void run() {
            tv_distance.setText(locationBinder.getDistanceString());
            tv_avgSpeed.setText(locationBinder.getAvgSpeedString());
            if(locationBinder.getLastLocation()!=null)
                updatePosition(locationBinder.getLastLocation());
            if (!stopped) updateStatsHandler.postDelayed(this, 1000);
        }
    };

    private BroadcastReceiver locationSettingStateReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            if (!stopped) {
                stopLocationService();
                Toast.makeText(context, "Location must be on to track session", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    private ServiceConnection lsc=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            locationBinder= (GPSService.LocationBinder) iBinder;
            if(repeat!=null){
                locationBinder.setTrackingActivity(repeat);
                repeat=null;
            }
            locationBinder.startTracking();
            locationBinder.setActivityType(activityTypeInd);
            registerReceiver(locationSettingStateReceiver,new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            updateTimeHandler.postDelayed(updateTimeThread, 0);
            updateStatsHandler.postDelayed(updateStatsThread, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            locationBinder=null;

        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_tracking_activity);
        init();
    }


    private void init(){

        tv_distance=findViewById(R.id.tv_distance);
        tv_avgSpeed=findViewById(R.id.tv_avgSpeed);
        tv_time=findViewById(R.id.tv_time);
        myOpenMapView=(MapView)findViewById(R.id.v_map);
        btn_start=findViewById(R.id.btn_start);
        btn_turnoff=findViewById(R.id.btn_turnoff);
        startMarker=new Marker(myOpenMapView);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_bicycle));
        if(checkPermissions(true)) {
            initLayer(this);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertNoGps();
            }

            //capturo coordenadas cada 5segundos
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateLastLocation();
                    btn_start.setEnabled(false);
                }
            });

            //Boton de fenar trackeo y guarda en la room la infomracion
            btn_turnoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopLocationService();
                    Intent i = new Intent(TrackingActivity.this, TrackingDetailActivity.class);

                    if (routeDetailsDto != null) {
                        i.setAction(Constants.REPLAY_MY_ROUTE);
                    }
                    startActivity(i);
                }
            });

            String action = getIntent().getAction();
            if (action != null && action.equals(Constants.REPLAY_MY_ROUTE)) {
                routeDetailsDto = (RouteDetailsDto) getIntent().getSerializableExtra("Route");
                List<GeoPoint> route = getCoordinates();//Transformo la ruta en geopoint
                drawRoute(route);
                isRepeat = true;
                repeat = new Tracking();
                String[] id = routeDetailsDto.getId().split("-");
                repeat.setId(id[1]);
                repeat.setTitle(routeDetailsDto.getName());
                repeat.setDescription(routeDetailsDto.getDescription());
                repeat.setPointsDraw(route);
            }
        }

    }

    private void updateLastLocation() {
       FusedLocationProviderClient fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(TrackingActivity.this);
       LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    startLocationService();
                }
            });
        }
    }

    //This Alert is not gps service activated
    private void AlertNoGps() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Your device setting will be changed")
                .setCancelable(false)
                .setPositiveButton("Turn on", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void drawRoute(List<GeoPoint> routes) {
        Marker startMarker=new Marker(myOpenMapView);
        startMarker.setPosition(routes.get(0));

        myOpenMapView.getOverlays().add(startMarker);
        RoadManager roadManager=new OSRMRoadManager(TrackingActivity.this,"OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) routes);
//        Polyline roadOverlay=new Polyline();
//        roadOverlay.setWidth(20f);
        Polyline roadOverlay=RoadManager.buildRoadOverlay(road, 0x800000FF, 25.0f);


       // Polyline roadOverlay=RoadManager.buildRoadOverlay(road).setWidth(2.0f);
        myOpenMapView.getOverlays().add(roadOverlay);
        Marker endMarker=new Marker(myOpenMapView);
        endMarker.setPosition(routes.get(routes.size()-1));

        myOpenMapView.getOverlays().add(endMarker);

        myOpenMapView.invalidate();
    }

    //Transformation coordinates in GeoPoint
    public List<GeoPoint> getCoordinates(){
        //routeDetailsDto= (RouteDetailsDto) getIntent().getSerializableExtra("Route");
        List<GeoPoint> result=new ArrayList<>();
        String resultCoordinates=routeDetailsDto.getCoordinates();
        try {
            JSONArray coordinates=new JSONArray(resultCoordinates);
            for(int i=0;i<coordinates.length();i++){
                String aux=coordinates.getString(i);
                String[] split=aux.split(",");
                result.add(new GeoPoint(Double.valueOf(split[0]),Double.valueOf(split[1])));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Init osmodroid map with position
    private void initLayer(Context ctx) {
        //Seteo de mapa en tandil statico
        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(18);
        mapController.setCenter(tandil);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);
        myOpenMapView.invalidate();

    }


    public  void updatePosition(Location location){
        GeoPoint point=new GeoPoint(location.getLatitude(),location.getLongitude());
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_RIGHT,Marker.ANCHOR_BOTTOM);
        myOpenMapView.getOverlays().add((myOpenMapView.getOverlays().size()-1),startMarker);
        IMapController mapController=myOpenMapView.getController();
        mapController.setCenter(point);
        myOpenMapView.invalidate();
    }


    //Inicia el servicio de localizacion
    private void startLocationService(){
        Intent intent = new Intent(this, GPSService.class);
        getApplicationContext().bindService(intent, lsc, Context.BIND_ABOVE_CLIENT);
        startService(intent);
    }


    private void stopLocationService(){
            stopped = true;
            locationBinder.stopTracking();
            getApplicationContext().unbindService(lsc);
    }


    ///////////////////////////////////PRUEBA DE SERVICIO GPS///////////////////////////////




//    ///add statistics in room
//    private void addRegisterStatistics(Tracking tracking) {
//        String routeId=null;
//        if(routeDetailsDto!=null){
//            String[] split=routeDetailsDto.getId().split("-");
//            routeId=split[1];
//        }else
//            routeId=db.routeService().getAll().get(db.routeService().countRoute()-1).getId();
//        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
//        appUserHasRoute.setAppUser(1L);
//        appUserHasRoute.setRoute(routeId);
////        appUserHasRoute.setKilometres(tracking.getDistance());
////        appUserHasRoute.setSpeed(tracking.getAvgSpeed());
////        appUserHasRoute.setTimeSpeed(tracking.getT());
////        appUserHasRoute.setTimesSession(tracking.getTimeSession());
//        appUserHasRoute.setWeather("");
//        db.appUserHasRouteService().addAppUserHasRoute(appUserHasRoute);
//    }

//    ///add route in room
//    private void addRegisterRoute(Tracking tracking)  {
//        JSONArray coordinates=new JSONArray();
//        if(routeDetailsDto==null){
//            List<Location> locations =  tracking.getPoints();
//
//        for (int i = 0; i < locations.size(); i++) {
//            GeoPoint p = new GeoPoint(locations.get(i).getLatitude(), locations.get(i).getLongitude());
//            coordinates.put(p);
//        }
//            Route r=new Route();
//            r.setId(RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true));
//            r.setCoordinates(coordinates.toString());
//            db.routeService().addRoute(r);
//        }
//    }






    //Check permission if enable
    private boolean checkPermissions(boolean request) {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                &&((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {
            if (request) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, TrackingActivity.REQUEST_PERMISSIONS_REQUEST_CODE);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TrackingActivity.REQUEST_PERMISSIONS_REQUEST_CODE);
            }
            return false;
        }
        return true;
    }

    //if permission is enable start location
    @Override
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);

        if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (this.checkPermissions(false)) {
                    //startLocationUpdates();
//                    updateGPS();
                startLocationService();
                initLayer(TrackingActivity.this);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        myOpenMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        myOpenMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        //unregisterReceiver(myReceiver);
    }
}