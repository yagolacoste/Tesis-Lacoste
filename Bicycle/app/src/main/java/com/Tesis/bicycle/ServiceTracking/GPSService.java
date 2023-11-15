package com.Tesis.bicycle.ServiceTracking;


import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.Presenter.Notifications;
import com.Tesis.bicycle.Service.Room.RoutesService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import org.jetbrains.annotations.Nullable;
import org.osmdroid.util.GeoPoint;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class GPSService extends Service {

    //Locations service
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    //Binder
    private final IBinder binder = new LocationBinder();


    private Tracking tracking;


    private Notifications notification;

    private boolean lastLocation = false;


    @Override
    public void onCreate() {
        notification = new Notifications(GPSService.this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GPSService.this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);//3 segundos
        locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);//1 segundos
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notification.addNotification("Location service", "Running");
        return super.onStartCommand(intent, flags, startId);
    }

    public void startLocationService() {

        if (tracking == null)
            tracking = new Tracking();

        if (!tracking.isCreated()) {
            //Init and create API Location services.
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult == null) {
                        return;
                    }
                    if (locationResult != null) {
                        for (Location location : locationResult.getLocations()) {
                            tracking.addTracking(location);
                            if (tracking.isDeviation() && tracking.isRepeat()) {
                                notification.addNotification("Alert", "you have gone off the road");
                            }
                        }
                        if (lastLocation){
//                            tracking.addTracking(locationResult.getLastLocation());
                            stopLocationService();
                        }
                    }
                }
            };
            tracking.startTrackingActivity();
            if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                fusedLocationProviderClient
                        .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            }
        }

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        locationCallback = null;
        locationRequest = null;
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }


    private void stopLocationService() {
        //booleano en verdadero para pausar
        if (!lastLocation) {
            lastLocation = true;
        } else {
            tracking.stopTrackingActivity();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            notification.cancel(1);
        }
    }





    public class LocationBinder extends Binder {

        public void startTracking() {
            startLocationService();
        }

        public void stopTracking() {
            stopLocationService();
        }



        public void setTrackingActivity(Tracking repeat) {
            tracking = new Tracking(repeat);
        }

        //Get the tracked session
        public Tracking getTrackedActivity() {
            return tracking;
        }

        //Get all the track points
        public List<Location> getTrkPoints() {
            return tracking.getPoints();
        }


        //Set the title of the tracked session for persistence
        public void setTitle(String title) {
            tracking.setTitle(title);
        }

        //Get the title of the tracked session
        public String getTitle() {
            return tracking.getTitle();
        }

        //Set description for the tracked session for persistence
        public void setDescription(String description) {
            tracking.setDescription(description);
        }

        //Get the description of the tracked session
        public String getDescription() {
            return tracking.getDescription();
        }

        //Get the millisecond time that this was created
        public Date getTimeCreated() {
            return tracking.getTimeCreated();
        }

        //Get the duration of the tracked session
        public String getDuration() {
            return tracking.getDuration();
        }

        //Get the duration as a string ready for printing
        public String getTimeString() {
            return tracking.getTimeString();
        }

        public LocalTime getTimeLocalTime() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tracking.millsToLocalTime();
            }
            return null;
        }

        //Get the distance of the tracked session
        public float getDistance() {
            return tracking.getDistance();
        }

        //Get the distance as a string ready for printing
        public String getDistanceString() {
            return tracking.getDistanceString();
        }

        //Get the average speed of the tracked session
        public float getAvgSpeed() {
            return tracking.getAvgSpeed();
        }

        public float getAvgSpeedCalculated() {
            return tracking.getAvgSpeedFromSUVAT();
        }

        //Get the average speed as a string
        public String getAvgSpeedString() {
            return tracking.getAvgSpeedString();
        }

        public Location getLastLocation() {
            return tracking.getLastPoint();
        }


        public List<GeoPoint> getGeoPoints() {
            return tracking.getGeoPoints();
        }

        public String getId() {
            return tracking.getId();
        }

        public Long getBattleId() {
            return tracking.getBattle();
        }

        public boolean checkingNearestNewPointAndNextPointIndex(Location location) {
            return tracking.checkingNearestNewPointAndNextPointIndex(location);
        }

        public List<Location> getCoordinates() {
            return tracking.getPoints();
        }

        public boolean isRepeat() {
            return tracking.isRepeat();
        }

        public boolean isDeviation() {
            return tracking.isDeviation();
        }

        public void setId(String id) {
            tracking.setId(id);
        }

        public void setBattle(Long id) {
            tracking.setBattle(id);
        }

        public void setRepeat(boolean repeat) {
            tracking.setRepeat(repeat);
        }

        public float getDistancesRoutes() {
            return tracking.getDistancesRoutes();
        }

        public String getAvgSpeedCalcualtedToString() {
            return tracking.getAvgSpeedFromSUVATToString();
        }

        public String getAvgSpeedToString() {
            return tracking.getAvgSpeedString();
        }

        public List<Location> getUnfilteredPoints() {
            return tracking.getUnfilteredPoints();
        }

        public List<Location> getFilteredPoints() {
            return tracking.getFilteredPoints();
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}