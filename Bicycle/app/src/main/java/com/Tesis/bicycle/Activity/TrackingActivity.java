package com.Tesis.bicycle.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.RouteDetailsDto;
import com.Tesis.bicycle.Model.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Model.ApiRest.RouteApiRest;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Model.Room.Route;

import com.Tesis.bicycle.R;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingActivity extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private static final String NAME_DATA_BASE="Bicycle";


    //Google Api for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static TextView tv_lat, tv_lon, tv_sensor;
    private Switch  sw_gps;
    private Button btn_start,btn_turnoff,btn_shared;

    //Location request is config for all setting related to fusedLocationProviderClient
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
//
//    //para obtener las coordenadas cuando se actualiza
//    private Location currentLocation;
//    private Location lastLocation=null;//Es para ver si se actualizo la ubicacion
//
//
//    //handle simula un thread
//   private  Handler handler = new Handler();

    //JsonCoordenates
   private JSONArray coordinates=new JSONArray();



    //Base de datos local utilizando room
    private AppDataBase db;

    //retrofit
    private RouteDetailsDto routeDetailsDto;

    //osm
    private static MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    public static Marker startMarker;

//    //broadcast
//    public static final String MYFILTER="com.my.broadcast.RECEIVER";
//    public static final String MSG="_message";


    BroadcastReceiver myReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Tracking tracking= (Tracking)intent.getExtras().getSerializable(Constants.TRACKING);
            tracking.setPoints(intent.getParcelableArrayListExtra("points"));
            addRegisterRoute(tracking);
            addRegisterStatistics(tracking);

        }

    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_tracking_activity);

        checkPermissions(true);

        ///////////////////////Inicializacion y configuracion/////////////////////

//        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
//
//        //event that is triggered whever the update internal is met
//         locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                Location location=locationResult.getLastLocation();
//                if (location != null){
//                updateUIValues(location);
//
//                }
//            }
//        };




        //Obtener ubicacion con fused location options
//        tv_lat = findViewById(R.id.tv_lat);
//        tv_lon = findViewById(R.id.tv_lon);
//        tv_sensor = findViewById(R.id.tv_sensor);
//
//        sw_gps = findViewById(R.id.sw_gps);
        myOpenMapView=(MapView)findViewById(R.id.v_map);
        btn_start=findViewById(R.id.btn_start);
        btn_turnoff=findViewById(R.id.btn_turnoff);
        btn_shared=findViewById(R.id.btn_shared);
        startMarker=new Marker(myOpenMapView);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_bicycle));


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba


//        savedlocations=new ArrayList<>();

        //initial data base
        db= Room.databaseBuilder(getApplicationContext(),AppDataBase.class,NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        //Inicio de la osm


        if ( checkPermissions(true)) {
          initLayer(ctx);
            String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }



        ///////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////Botones y acciones/////////////////////

        //change gps to Wifi and update coordenates
//        sw_gps.setOnCheckedChangeListener((CompoundButton compoundButton, boolean value) -> {
//            // Viru: Para revisar, creo que no alcanza solo con cambiar el valor
//            // Hay que volver a llamar al requestLocationUpdates o algo asi
//            //YAGO:cada vez que se cambia de antena se actualiza las coordenadas.
//             if (value) {
//                locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
//                tv_sensor.setText("Using gps");
////                 startLocationUpdates();
//             } else {
//                locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
//                tv_sensor.setText("using wifi");
////                startLocationUpdates();
//            }
//        });
//
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
        }

        //capturo coordenadas cada 5segundos
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });

       //Boton de fenar trackeo y guarda en la room la infomracion
        btn_turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopLocationService();
                registerReceiver(myReceiver,new IntentFilter(Constants.ACTION_UPDATE_SERVICE));
            }
        });

        //Button update in server
        btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //por defecto hay un usuario despeus se pide al login que usuario seria
                //Pasa la informacion al servidor
                AppUserHasRoute appUserHasRoute=db.appUserHasRouteService().getAppUserHasRouteByID(db.appUserHasRouteService().countAppUserHasRoute());//me traigo el ultimo registro del apphasroute
                AppUserHasRouteApiRest appUserHasRouteApiRest=new AppUserHasRouteApiRest();
                appUserHasRouteApiRest.setAppUser(1L);
                appUserHasRouteApiRest.setKilometres(appUserHasRoute.getKilometres());
                appUserHasRouteApiRest.setSpeed(appUserHasRoute.getSpeed());
                appUserHasRouteApiRest.setTimeSpeed(appUserHasRoute.getTimeSpeed());
                appUserHasRouteApiRest.setTimeSession(appUserHasRoute.getTimesSession());
                appUserHasRouteApiRest.setDescription("");
                appUserHasRouteApiRest.setWeather("");

                //Guarda la ruta nueva en el servidor
                if(routeDetailsDto!=null){
                    appUserHasRouteApiRest.setRoute(String.valueOf(appUserHasRoute.getRoute()));
                }
                else{
                    Route route=db.routeService().getById(db.routeService().countRoute());
                    String pointint=route.getCoordinates();
                    appUserHasRouteApiRest.setRoute(1+String.valueOf(route.getId()));//Agrego por defecto el usuario 1 para probar + adelante cambiar
                    appUserHasRouteApiRest.setCoordinates(pointint);
                }
                sendData(appUserHasRouteApiRest);


            }
        });

        String result=getIntent().getAction();
        if(result!=null)
            if(result.equals(Constants.ACTION_DRAW_MAP)){
                List<GeoPoint>route=getCoordinates();//Transformo la ruta en geopoint
                drawRoute(route);
        }
    }

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
        routeDetailsDto= (RouteDetailsDto) getIntent().getSerializableExtra("Route");
        List<GeoPoint> result=new ArrayList<>();
        String resultCoordinates=routeDetailsDto.getCoordinates();
        try {
            JSONArray coordinates=new JSONArray(resultCoordinates);
            for(int i=0;i<coordinates.length();i++){
//                JSONObject point=coordinates.getJSONObject(i);
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

    //Update psotiion ui (PREGUNTAR )
    public static void updatePosition(GeoPoint point){
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_RIGHT,Marker.ANCHOR_BOTTOM);
        myOpenMapView.getOverlays().add((myOpenMapView.getOverlays().size()-1),startMarker);
        IMapController mapController=myOpenMapView.getController();
        mapController.setCenter(point);
        myOpenMapView.invalidate();

    }

    //public




    /////////////////////////PRUEBA DE SERVICIO GPS///////////////////////////////
    private boolean isLocationServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager!=null){
            for(ActivityManager.RunningServiceInfo service:
            activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(GPSService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    //Inicia el servicio de localizacion
    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent intent =new Intent(getApplicationContext(),GPSService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"service is running",Toast.LENGTH_LONG).show();
        }
    }


    private void stopLocationService(){
        if(isLocationServiceRunning()){
            Intent intent =new Intent(getApplicationContext(),GPSService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"service is not running",Toast.LENGTH_LONG).show();
        }
    }


    ///////////////////////////////////PRUEBA DE SERVICIO GPS///////////////////////////////




    ///add statistics in room
    private void addRegisterStatistics(Tracking tracking) {

//        Date fecha=new Date();
        Long routeId=db.routeService().getAll().get(db.routeService().countRoute()-1).getId();
        if(routeDetailsDto!=null){
            routeId=routeDetailsDto.getId();
        }
        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
        appUserHasRoute.setAppUser(1L);
        appUserHasRoute.setRoute(routeId);
        appUserHasRoute.setKilometres(tracking.getDistance());
        appUserHasRoute.setSpeed(tracking.getSpeed());
        appUserHasRoute.setTimeSpeed(tracking.getTimeSpeed());
        appUserHasRoute.setTimesSession(tracking.getTimeSession());
        db.appUserHasRouteService().addAppUserHasRoute(appUserHasRoute);

    }

    ///add route in room
    private void addRegisterRoute(Tracking tracking)  {
        if(routeDetailsDto==null){
        List<Location> locations =  tracking.getPoints();

        for (int i = 0; i < locations.size(); i++) {
            GeoPoint p = new GeoPoint(locations.get(i).getLatitude(), locations.get(i).getLongitude());
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put(String.valueOf(i), p);
            coordinates.put(p);
        }
        Route r=new Route(coordinates.toString());
        db.routeService().addRoute(r);
        }


    }




    //add statistics in server
    public void sendData(AppUserHasRouteApiRest appUserHasRouteApiRest){
        AppUserHasRouteApiRestService appUserHasRouteApiRestService=ApiRestConecction.getServiceAppUserHasRoute();
        Call<Void>call=appUserHasRouteApiRestService.AddStatistics(appUserHasRouteApiRest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TrackingActivity.this,"add is success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }





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
        routeDetailsDto=null;
        //unregisterReceiver(myReceiver);
    }
}