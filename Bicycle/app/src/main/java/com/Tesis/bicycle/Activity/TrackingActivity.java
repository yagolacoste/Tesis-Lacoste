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
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Model.Tracking;

import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

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

import cn.pedant.SweetAlert.SweetAlertDialog;


public class TrackingActivity extends Activity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private TextView tv_distance, tv_avgSpeed, tv_time;
    private Button btn_start, btn_turnoff;

    GPSService.LocationBinder locationBinder = null;
    int activityTypeInd;
    Tracking repeat = null;
    boolean stopped = false, isRepeat = false;
    //retrofit
    private RouteDetailsDto routeDetailsDto;

    //osm init layer and position user
    private MapView myOpenMapView;
    private OpenStreetMap openStreetMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationCallback locationCallback;

    private Location currentLocation;

    private String action = null;


    Handler updateTimeHandler = new Handler();
    Handler updateStatsHandler = new Handler();

    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            String timeString = locationBinder.getTimeString();
            tv_time.setText(timeString);
            if (!stopped) updateTimeHandler.postDelayed(this, 0);
        }
    };

    Runnable updateStatsThread = new Runnable() {
        @Override
        public void run() {
            tv_distance.setText(locationBinder.getDistanceString());
            tv_avgSpeed.setText(locationBinder.getAvgSpeedString());
            if (locationBinder.getLastLocation() != null)
                openStreetMap.updatePosition(locationBinder.getLastLocation());
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


    private ServiceConnection lsc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            locationBinder = (GPSService.LocationBinder) iBinder;
            if (repeat != null) {
                locationBinder.setTrackingActivity(repeat);
                repeat = null;
            }
            locationBinder.startTracking();
            locationBinder.setActivityType(activityTypeInd);
            registerReceiver(locationSettingStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            updateTimeHandler.postDelayed(updateTimeThread, 0);
            updateStatsHandler.postDelayed(updateStatsThread, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            locationBinder = null;

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


    private void init() {

        tv_distance = findViewById(R.id.tv_distance);
        tv_avgSpeed = findViewById(R.id.tv_avgSpeed);
        tv_time = findViewById(R.id.tv_time);
        myOpenMapView = (MapView) findViewById(R.id.v_map);
        btn_start = findViewById(R.id.btn_start);
        btn_turnoff = findViewById(R.id.btn_turnoff);
        if (checkPermissions(true)) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertNoGps();
            }
            openStreetMap = new OpenStreetMap(myOpenMapView);
            openStreetMap.initLayer(TrackingActivity.this, Constants.LOCATION_INITIAL);
            updateLastLocation();
            action = getIntent().getAction();
            if (action != null) {
                setRouteByAction(action);
                }


            //capturo coordenadas cada 5segundos
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(action!=null){
                        if (equalsPosition()) {
                            warningMessage("Pro favor esta en el inicio de la carrera");
                        }
                        else{
                            stopLocationUpdates();
                            openStreetMap.removeMarker();
                            startLocationService();
                            btn_start.setEnabled(false);
                        }
                    }else{
                        stopLocationUpdates();
                        openStreetMap.removeMarker();
                        startLocationService();
                        btn_start.setEnabled(false);
                    }
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
                    // btn_turnoff.setEnabled(false);
                }
            });


        }
    }

    private boolean equalsPosition() {
            GeoPoint Compare = repeat.getRouteReplay().get(0);
            Location location = new Location("");
            location.setLatitude(Compare.getLatitudeE6() / 1E6);
            location.setLongitude(Compare.getLongitudeE6() / 1E6);
            if (currentLocation.distanceTo(location) > 10.0f) {//toma la distancia a 10 metros
                return true;
            }
            return false;
    }

    private void setRouteByAction(String action) {
        repeat = new Tracking();
        if (action.equals(Constants.REPLAY_MY_ROUTE)) {
            routeDetailsDto = (RouteDetailsDto) getIntent().getSerializableExtra("Route");
            repeat.setBattle(null);
        } else if (action.equals(Constants.REPLAY_BATTLE)) {
            BattleDto battleDto = (BattleDto) getIntent().getSerializableExtra(Constants.BATTLE_ITEM);
            routeDetailsDto = battleDto.getRoute();
            repeat.setBattle(battleDto.getIdBattle());
        }
        openStreetMap.draw(routeDetailsDto.getCoordinates());
        isRepeat = true;
        repeat.setId(routeDetailsDto.getId());
        repeat.setTitle(routeDetailsDto.getName());
        repeat.setDescription(routeDetailsDto.getDescription());
        repeat.setRouteReplay(routeDetailsDto.getCoordinates());

    }

    private void updateLastLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(TrackingActivity.this);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    openStreetMap.updatePosition(location);
                    currentLocation = location;
                    return;
                }
            }
        };

        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    // Método para cancelar las actualizaciones de ubicación
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    //This Alert is not gps service activated
    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    //Inicia el servicio de localizacion
    private void startLocationService() {
        Intent intent = new Intent(this, GPSService.class);
        getApplicationContext().bindService(intent, lsc, Context.BIND_ABOVE_CLIENT);
        startService(intent);
    }


    private void stopLocationService() {
        stopped = true;
        locationBinder.stopTracking();
        getApplicationContext().unbindService(lsc);
    }


    //Check permission if enable
    private boolean checkPermissions(boolean request) {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {
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
                updateLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // updateLastLocation();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
//        ope.onResume(); //needed for compass, my location overlays, v6.0.0 and up
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
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        btn_turnoff.setEnabled(false);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Good Job!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}