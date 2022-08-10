package com.Tesis.bicycle;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends Activity  {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private static final int DEFAULT_UPDATE_INTERVAL = 10; // Puse un valor mas bajo solo para verificar que andaba
    private static final int FAST_UPDATE_INTERVAL = 5;

    //Google Api for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView tv_lat, tv_lon, tv_sensor, tv_update;
    private Switch sw_locationupdates, sw_gps;

    //Location request is config for all setting related to fusedLocationProviderClient
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Mantengo una única instancia de client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Obtener ubicacion con fused location options
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_update = findViewById(R.id.tv_update);
        sw_gps = findViewById(R.id.sw_gps);
        sw_locationupdates = findViewById(R.id.sw_locationupdates);

        // set all properties of locationRequest
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba

        sw_gps.setOnCheckedChangeListener((CompoundButton compoundButton, boolean value) -> {
            // Viru: Para revisar, creo que no alcanza solo con cambiar el valor
            // Hay que volver a llamar al requestLocationUpdates o algo asi
             if (value) {
                locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                tv_sensor.setText("Using gps");
             } else {
                locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                tv_sensor.setText("using wifi");
            }
        });

        sw_locationupdates.setChecked(false);
        sw_locationupdates.setOnCheckedChangeListener((CompoundButton compoundButton, boolean value) -> {
            if (value) {
                startLocationupdates();
            } else {
                stopLocationUpdates();
            }
        });

        checkPermissions(true);

    }

    private void startLocationupdates() {
        //event that is triggeredwheevr the update internal is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null)
                    updateUIValues(location);
            }
        };

        // VIRU: Aca va negada la condicion!
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            tv_update.setText("Location is being tracked");
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,  Looper.getMainLooper());
            // VIRU: el updateGps solo obtiene la ubicación una vez
            // updateGPS();
        }

    }

    private void stopLocationUpdates() {
        tv_lat.setText("Not tracking location");
        tv_lon.setText("Not tracking location");
        if (locationCallback != null)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void updateUIValues(Location location){
        //update all of the text view objects with new  location
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));

    }

    private boolean checkPermissions(boolean request) {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (request)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.REQUEST_PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);

        if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (this.checkPermissions(false)) {
                if (this.sw_locationupdates.isChecked())
                    startLocationupdates();
            }
        }
    }

}