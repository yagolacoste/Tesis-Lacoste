package com.Tesis.bicycle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.Tesis.bicycle.Activity.ShowPointLocationList;
import com.Tesis.bicycle.Dto.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.InfomationDto;
import com.Tesis.bicycle.Dto.RouteDetailsDto;
import com.Tesis.bicycle.Model.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Model.ApiRest.RouteApiRest;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Model.Room.Route;

import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiRestService;
import com.Tesis.bicycle.Service.Room.AppUserHasRouteService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private static final String NAME_DATA_BASE="Bicycle";


    //Google Api for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView tv_lat, tv_lon, tv_sensor;
    private Switch  sw_gps;
    private Button btn_start,btn_turnoff,btn_shared,btn_showpoint;

    //Location request is config for all setting related to fusedLocationProviderClient
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    //para obtener las coordenadas cuando se actualiza
    private Location currentLocation;
    private Location lastLocation=null;//Es para ver si se actualizo la ubicacion

    private List<Location> savedlocations;

    //handle simula un thread
   private  Handler handler = new Handler();

    //JsonCoordenates
   private  JSONObject coordinates=new JSONObject();



    //Base de datos local utilizando room
    private AppDataBase db;

    //retrofit
    private AppUserHasRouteDetailsDto appUserHasRouteDetailsDto = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkPermissions(true);

        ///////////////////////Inicializacion y configuracion/////////////////////

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);

        //event that is triggered whever the update internal is met
         locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                if (location != null){
                updateUIValues(location);

                }
            }
        };




        //Obtener ubicacion con fused location options
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_sensor = findViewById(R.id.tv_sensor);

        sw_gps = findViewById(R.id.sw_gps);
        btn_start=findViewById(R.id.btn_start);
        btn_turnoff=findViewById(R.id.btn_turnoff);
        btn_shared=findViewById(R.id.btn_shared);
        btn_showpoint=findViewById(R.id.btn_showpoint);


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba


        savedlocations=new ArrayList<>();

        //initial data base
        db= Room.databaseBuilder(getApplicationContext(),AppDataBase.class,NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();





        ///////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////Botones y acciones/////////////////////

        //change gps to Wifi and update coordenates
        sw_gps.setOnCheckedChangeListener((CompoundButton compoundButton, boolean value) -> {
            // Viru: Para revisar, creo que no alcanza solo con cambiar el valor
            // Hay que volver a llamar al requestLocationUpdates o algo asi
            //YAGO:cada vez que se cambia de antena se actualiza las coordenadas.
             if (value) {
                locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                tv_sensor.setText("Using gps");
                 startLocationUpdates();
             } else {
                locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                tv_sensor.setText("using wifi");
                startLocationUpdates();
            }
        });


        //capturo coordenadas cada 5segundos
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationUpdates();
                handler.post(sendData);
            }
        });

        btn_turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(sendData);

                List<GeoPoint>points=new ArrayList<>();

                    try {
                        for(int i=0 ; i<savedlocations.size();i++) {
                            GeoPoint p = new GeoPoint(savedlocations.get(i).getLatitude(), savedlocations.get(i).getLongitude());
                            coordinates.put(String.valueOf(i),p);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                Route r=new Route(coordinates.toString());
                db.routeService().insertCoordinates(r);
                addInformation();

//               String pointint=db.routeService().getAll().get(db.routeService().countRoute()-1).getCoordinates();//obtengo el ultimo registro ingresado
//                Toast.makeText(MainActivity.this,"Finalizo", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, pointint,Toast.LENGTH_LONG).show();


            }
        });

        btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route=db.routeService().getAll().get(db.routeService().countRoute()-1);
               String pointint=route.getCoordinates();
//                Grabar en el servidor
                RouteApiRest aux=new RouteApiRest();
//                aux.setId(db.routeService().countRoute()-1);
                aux.setCoordinates(pointint);
                aux.setDescription("");
                aux.setWeather("");
                addRoute(aux);
                //por defecto hay un usuario
                //Pasa la informacion al servidor
                InfomationDto informationDto=db.appUserHasRouteService().getInformation((long)1,(long)route.getId());
                AppUserHasRouteApiRest appUserHasRouteApiRest=new AppUserHasRouteApiRest();
                appUserHasRouteApiRest.setAppUser(1L);
                appUserHasRouteApiRest.setRoute((long)route.getId());
                appUserHasRouteApiRest.setKilometres(informationDto.getKilometres());
                appUserHasRouteApiRest.setSpeed(informationDto.getSpeed());
                appUserHasRouteApiRest.setTimeSpeed(informationDto.getTimeSpeed());
                appUserHasRouteApiRest.setTimesSession(informationDto.getTimesSession());
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

    private void addInformation() {
        double distance=getDistance();
        double speed=getSpeed();
        double timeSpeed=getTimeSpeed();
        Date fecha=new Date();
        Route route=db.routeService().getAll().get(db.routeService().countRoute()-1);
        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
        appUserHasRoute.setAppUser(1L);
        appUserHasRoute.setRoute((long) route.getId());
        appUserHasRoute.setKilometres(distance);
        appUserHasRoute.setSpeed(speed);
        appUserHasRoute.setTimeSpeed(timeSpeed);
        appUserHasRoute.setTimesSession(fecha);
        db.appUserHasRouteService().addInformation(appUserHasRoute);

    }

    private double getTimeSpeed() {//acomodar esto size-getTime(0); para calcular el tiempo que tardo en hacerlo
        double time=0;
        for(int i=0;i<savedlocations.size();i++){
            time=time+savedlocations.get(i).getTime();
        }
        return time;
    }

    //obtengo la velocidad promedio(metros por segundo)----->getSpeedAcuraccyMetersPerSecond()
    private double getSpeed(){
        double speed=0;
        int j=0;
        //
        for(int i=0;i<savedlocations.size();i++){

            Location p1=savedlocations.get(i);
            if(p1.hasSpeed()){
            speed=speed+p1.getSpeed();
            j++;//--->sumo j para contar los puntos que tuvieorn velocidad
            }
        }
        return speed/j;
    }

    //Obtengo la distancia entre los puntos
    private double getDistance() {
        double distance=0;
        int i=0;
        Location p1=null;
        while(i<savedlocations.size()){
            p1=savedlocations.get(i);
            if(i+1<savedlocations.size()){
                Location p2=savedlocations.get(i+1);
                distance=distance+p1.distanceTo(p2);
            }
            else
                break;
            i++;
        }
        return distance/1000;//lo da en metros
    }


    //agregar estadisticas
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


    //Agregar rutas
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


    //metodo runable para alamacenar ubicacion cada 10 segundos.
    private final Runnable sendData = new Runnable(){
        public void run(){
            try {
                updateGPS();
                if(currentLocation!=null) {
                    if ((lastLocation == null) || (currentLocation.getLongitude() != lastLocation.getLongitude()) || (currentLocation.getLatitude() != lastLocation.getLatitude())) {
                        //preguntar por el tiempo
                        savedlocations.add(currentLocation);
                    }
                }
                lastLocation=currentLocation;//Agrege lastLocation porque sino me guardaba la misma ubicacion y tengo asincronismo en los timpos
                handler.postDelayed(this, 1000*5);//delay de 5 segundos
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void updateGPS(){
        //get permission from the user to track GPS
        //get the current location from the fused client
        //update the UI--set all propitiers in associated with UI
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                    currentLocation=location;
                }
            });
        }

    }

    //start location and update callback
    private void startLocationUpdates() {
        // VIRU: Aca va negada la condicion!
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,  Looper.getMainLooper());//actualizo el request y llamo al callback
            // VIRU: el updateGps solo obtiene la ubicación una vez
            updateGPS();
            //Toast.makeText(this,"se actualizo",Toast.LENGTH_LONG).show();
        }

    }

    //Stop tracking and remove location
    private void stopLocationUpdates() {
        tv_lat.setText("Not tracking location");
        tv_lon.setText("Not tracking location");
        if (locationCallback != null)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    //update all of the text view objects with new  location
    private void updateUIValues(Location location){
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));

    }

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
                    updateGPS();
            }
        }
    }


}