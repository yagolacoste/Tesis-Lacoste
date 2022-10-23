package com.Tesis.bicycle;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.Tesis.bicycle.Activity.ShowPointLocationList;
import com.Tesis.bicycle.Dto.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Model.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Model.ApiRest.RouteApiRest;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Model.Room.Route;

import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity<Contex> extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private static final String NAME_DATA_BASE="Bicycle";


    //Google Api for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static TextView tv_lat, tv_lon, tv_sensor;
    private Switch  sw_gps;
    private Button btn_start,btn_turnoff,btn_shared,btn_showpoint;

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
    private AppUserHasRouteDetailsDto appUserHasRouteDetailsDto = null;

    //osm
    private static MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    public static Marker startMarker;

//    //broadcast
//    public static final String MYFILTER="com.my.broadcast.RECEIVER";
//    public static final String MSG="_message";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

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
        btn_showpoint=findViewById(R.id.btn_showpoint);
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

        //capturo coordenadas cada 5segundos
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startLocationUpdates();
//                handler.post(sendData);
                startLocationService();
            }
        });

       //Boton de fenar trackeo y guarda en la room la infomracion
        btn_turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopLocationService();
                BroadcastReceiver myReceiver= new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        Tracking tracking= (Tracking)intent.getExtras().getSerializable(Constants.TRACKING);
                        tracking.setPoints(intent.getParcelableArrayListExtra("points"));
                        addRegisterRoute(tracking);
                        addRegisterStatistics(tracking);



                    }
                };
                registerReceiver(myReceiver,new IntentFilter(Constants.ACCTION_UPDATE_SERVICE));
            }
        });

        //Button update in server
        btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guarda la ruta nueva en el servidor
                Route route=db.routeService().getById(db.routeService().countRoute());
                String pointint=route.getCoordinates();

//                Grabar en el servidor
                RouteApiRest routeApiRest=new RouteApiRest();
                routeApiRest.setId(route.getId());
                routeApiRest.setCoordinates(pointint);
                routeApiRest.setDescription("");
                routeApiRest.setWeather("");
                addRoute(routeApiRest);



                //por defecto hay un usuario despeus se pide al login que usuario seria
                //Pasa la informacion al servidor
                AppUserHasRoute appUserHasRoute=db.appUserHasRouteService().getAppUserHasRouteByID(db.appUserHasRouteService().countAppUserHasRoute());
                AppUserHasRouteApiRest appUserHasRouteApiRest=new AppUserHasRouteApiRest();
                appUserHasRouteApiRest.setAppUser(1L);
                appUserHasRouteApiRest.setRoute(route.getId());
                appUserHasRouteApiRest.setKilometres(appUserHasRoute.getKilometres());
                appUserHasRouteApiRest.setSpeed(appUserHasRoute.getSpeed());
                appUserHasRouteApiRest.setTimeSpeed(appUserHasRoute.getTimeSpeed());
                appUserHasRouteApiRest.setTimesSession(appUserHasRoute.getTimesSession());
                addStatistics(appUserHasRouteApiRest);


            }
        });

        btn_showpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getRoutesByUser();
                Intent i=new Intent(MainActivity.this,ShowPointLocationList.class);
                startActivity(i);

            }
        });

    }

    //Init osm map with position
    private void initLayer(Context ctx) {
        //Seteo de mapa en tandil statico
        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(22);
        mapController.setCenter(tandil);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);

    }

    //Update psotiion ui (PREGUNTAR )
    public static void updatePosition(GeoPoint point){
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        myOpenMapView.getOverlays().add((myOpenMapView.getOverlays().size()-1),startMarker);
        IMapController mapController=myOpenMapView.getController();
        mapController.setCenter(point);
        myOpenMapView.invalidate();

    }


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
            return true;
        }
        return false;
    }

    //Inicia el servicio de localizacion
    private void startLocationService(){
        if(isLocationServiceRunning()){
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
        Route route=db.routeService().getAll().get(db.routeService().countRoute()-1);
        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
        appUserHasRoute.setAppUser(1L);
        appUserHasRoute.setRoute((long) route.getId());
        appUserHasRoute.setKilometres(tracking.getDistance());
        appUserHasRoute.setSpeed(tracking.getSpeed());
        appUserHasRoute.setTimeSpeed(tracking.getTimeSpeed());
        appUserHasRoute.setTimesSession(tracking.getTimeSession());
        db.appUserHasRouteService().addAppUserHasRoute(appUserHasRoute);

    }

    ///add route in room
    private void addRegisterRoute(Tracking tracking)  {
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




    //add statistics in server
    public void addStatistics(AppUserHasRouteApiRest appUserHasRouteApiRest){
        AppUserHasRouteApiRestService appUserHasRouteApiRestService=ApiRestConecction.getServiceAppUserHasRoute();
        Call<Void>call=appUserHasRouteApiRestService.AddStatistics(appUserHasRouteApiRest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"add is success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }


    //add route in server
    public void addRoute(RouteApiRest routeApiRest){
        RouteApiRestService routeApiRestService = ApiRestConecction.getServiceRoute();
        Call<Void>call=routeApiRestService.addRoute(routeApiRest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    Toast.makeText(MainActivity.this,"add is success",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }


//    //metodo runable para alamacenar ubicacion cada 10 segundos.
//    private final Runnable sendData = new Runnable(){
//        public void run(){
//            try {
//                updateGPS();
//                if(currentLocation!=null) {
//                    if ((lastLocation == null) || (currentLocation.getLongitude() != lastLocation.getLongitude()) || (currentLocation.getLatitude() != lastLocation.getLatitude())) {
//                        //preguntar por el tiempo
//                        savedlocations.add(currentLocation);
//                    }
//                }
//                lastLocation=currentLocation;//Agrege lastLocation porque sino me guardaba la misma ubicacion y tengo asincronismo en los timpos
//                handler.postDelayed(this, 1000*5);//delay de 5 segundos
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private void updateGPS(){
//        //get permission from the user to track GPS
//        //get the current location from the fused client
//        //update the UI--set all propitiers in associated with UI
//        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    updateUIValues(location);
//                    currentLocation=location;
//                }
//            });
//        }
//
//    }
//
//    //start location and update callback
//    private void startLocationUpdates() {
//        // VIRU: Aca va negada la condicion!
//        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,  Looper.getMainLooper());//actualizo el request y llamo al callback
//            // VIRU: el updateGps solo obtiene la ubicación una vez
//            updateGPS();
//            //Toast.makeText(this,"se actualizo",Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    //Stop tracking and remove location
//    private void stopLocationUpdates() {
//        tv_lat.setText("Not tracking location");
//        tv_lon.setText("Not tracking location");
//        if (locationCallback != null)
//            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//    }
//
//
//    //update all of the text view objects with new  location
//    public static void updateUIValues(Location location){
//        tv_lat.setText(String.valueOf(location.getLatitude()));
//        tv_lon.setText(String.valueOf(location.getLongitude()));
//
//    }

    //Check permission if enable
    private boolean checkPermissions(boolean request) {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (request)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.REQUEST_PERMISSIONS_REQUEST_CODE);
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
                initLayer(MainActivity.this);
            }
        }
    }


}