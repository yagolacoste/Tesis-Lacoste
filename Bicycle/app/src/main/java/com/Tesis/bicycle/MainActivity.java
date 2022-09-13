package com.Tesis.bicycle;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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


import com.Tesis.bicycle.Model.RouteApiRest;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Model.Route;
import com.Tesis.bicycle.Service.RouteApiRestService;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private static final int DEFAULT_UPDATE_INTERVAL = 10; // Puse un valor mas bajo solo para verificar que andaba
    private static final int FAST_UPDATE_INTERVAL = 5;
    private static final String NAME_DATA_BASE="Bicycle";


    //Google Api for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView tv_lat, tv_lon, tv_sensor, tv_update;
    private Switch sw_locationupdates, sw_gps;
    private Button btn_start,btn_turnoff,btn_shared;

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


   //Servicios y objetos de retorfit

    private RouteApiRestService routeApiRestService;
    private List<RouteApiRest> routes=new ArrayList<>();


    //Base de datos local utilizando room
    private AppDataBase db;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkPermissions(true);

        ///////////////////////Inicializacion y configuracion/////////////////////


        // Mantengo una única instancia de client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //event that is triggered whever the update internal is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                    if (location != null)
                        updateUIValues(location);

            }
        };

        //Obtener ubicacion con fused location options
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_update = findViewById(R.id.tv_update);
        sw_gps = findViewById(R.id.sw_gps);
        sw_locationupdates = findViewById(R.id.sw_locationupdates);
        btn_start=findViewById(R.id.btn_start);
        btn_turnoff=findViewById(R.id.btn_turnoff);
        btn_shared=findViewById(R.id.btn_shared);

        // set all properties of locationRequest
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba
        tv_sensor.setText("using GPS");

        savedlocations=new ArrayList<>();

        //initial data base
        db= Room.databaseBuilder(getApplicationContext(),AppDataBase.class,NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        //Initial service with retrofit
        routeApiRestService=ApiRestConecction.getService();


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


        //capturo coordenadas cada 15segundos
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
//                            points.add(p);
                        }
//                        coordinates.put("point",points);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                Route r=new Route(coordinates.toString());
                db.routeService().insertCoordinates(r);

               String pointint=db.routeService().getAll().get(db.routeService().countRoute()-1).getCoordinates();//obtengo el ultimo registro ingresado
                Toast.makeText(MainActivity.this,"Finalizo", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, pointint,Toast.LENGTH_LONG).show();


            }
        });

        btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String pointint=db.routeService().getAll().get(db.routeService().countRoute()-1).getCoordinates();
//                Ggrabar en el servidor
                RouteApiRest aux=new RouteApiRest();
//                aux.setId(db.routeService().countRoute()-1);
                aux.setCoordinates(pointint);
                aux.setDescription("");
                aux.setWeather("");
                addRoute(aux);

            }
        });


    }

    //Agregar rutas
    public void addRoute(RouteApiRest routeApiRest){
//        OkHttpClient httpClient = new  OkHttpClient.Builder()
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .build();
//
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(ApiRestConecction.URL)
//                .addConverterFactory(GsonConverterFactory.create()).client(httpClient)
//                .build();

//        routeApiRestService= retrofit.create(RouteApiRestService.class);

        Call<Void>call=routeApiRestService.addRoute(routeApiRest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    Toast.makeText(MainActivity.this,"se agrego con exito",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    ///Lista las rutas
    public void listarRoute(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiRestConecction.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routeApiRestService= retrofit.create(RouteApiRestService.class);
        Call<List<RouteApiRest>> call =routeApiRestService.getRoutes();
        call.enqueue(new Callback<List<RouteApiRest>>() {
            @Override
            public void onResponse(Call<List<RouteApiRest>> call, Response<List<RouteApiRest>> response) {
                routes=response.body();
//                tv_coordinates.setText(routes.toString());
            }

            @Override
            public void onFailure(Call<List<RouteApiRest>> call, Throwable t) {
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
            tv_update.setText("Location is being tracked");
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