package com.Tesis.bicycle.ServiceTracking;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.Nullable;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GPSService extends Service {

    //Locations service
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    //Binder
    private final IBinder binder =new LocationBinder();


    private Tracking tracking;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  startForeground(1,addNotification());
        addNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startLocationService(){
//        addNotification();
        if(tracking==null)
            tracking=new Tracking();

        if(!tracking.isCreated()) {
            //Init and create API Location services.
            fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(GPSService.this);
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
            locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
            locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null ) {
                        for(Location location:locationResult.getLocations())
                            tracking.addTracking(location);
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


    private void addNotification(){
        String channelId="Location_notification_channel";
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent=new Intent();
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );
        builder.setSmallIcon(R.drawable.ic_bicycle);
        builder.setContentTitle("Location service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
            startForeground(Constants.LOCATION_SERVICE_ID, builder.build());
            }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationCallback=null;
        locationRequest=null;
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }


    private void stopLocationService(){
        tracking.stopTrackingActivity();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }



    public class LocationBinder extends Binder {

        public void startTracking(){ startLocationService();}

        public void stopTracking(){ stopLocationService();}


        //Set the activity type
        public void setActivityType(int activityTypeInd){ tracking.setActivityType(activityTypeInd); }

        public void setTrackingActivity(Tracking repeat){tracking=new Tracking(repeat);}

        //Get the tracked session
        public Tracking getTrackedActivity() {
            return tracking;
        }

        //Get all the track points
        public List<Location> getTrkPoints() {
            return tracking.getPoints();
        }

        //Set the tracked session rating for persistence
        public void setRating(int rating) {
            tracking.setRating(rating);
        }

        //Get the tracked session rating
        public int getRating() {
            return tracking.getRating();
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
        public long getDuration() {
            return tracking.getCurrentTimeMillis();
        }

        //Get the duration as a string ready for printing
        public String getTimeString() {
            return tracking.getTimeString();
        }

        public LocalTime getTimeLocalTime(){
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

        //Get the average speed as a string
        public String getAvgSpeedString() {
            return tracking.getAvgSpeedString();
        }

        public Location getLastLocation(){return tracking.getLastPoint();}

        public List<GeoPoint> getGeoPoints(){
                return tracking.getGeoPoints();
            }

        public String getId(){
            return tracking.getId();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}