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
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.MainActivity;
import com.Tesis.bicycle.Model.Tracking;
import com.Tesis.bicycle.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import org.jetbrains.annotations.Nullable;
import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GPSService extends Service {

    private Location currentLocation;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Tracking tracking=new Tracking();
    private LocationCallback locationCallback;
    //handle simula un thread
    private Handler handler = new Handler();




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void startLocationService(){
        String channelId="Location_notification_channel";
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent=new Intent();
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            if(notificationManager !=null && notificationManager.getNotificationChannel(channelId)==null){
                NotificationChannel notificationChannel=new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }

            fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(GPSService.this);

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000 * Constants.DEFAULT_UPDATE_INTERVAL);
            locationRequest.setFastestInterval(1000 * Constants.FAST_UPDATE_INTERVAL);
            locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); // Por defecto puse HIGH solo para verificar que andaba

            locationCallback=new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if(locationResult !=null && locationResult.getLastLocation()!=null){
                        //latitud y longitud
                        currentLocation= locationResult.getLastLocation();
                        handler.post(sendData);
//                        tracking.addTracking(currentLocation);
                    }
                }
            };

            if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                fusedLocationProviderClient
                        .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            }

            startForeground(Constants.LOCATION_SERVICE_ID, builder.build());

        }
    }

    private final Runnable sendData = new Runnable(){
        public void run(){
//            try {
////                updateGPS();
                if(currentLocation!=null) {
                    if ((lastLocation == null) || (currentLocation.getLongitude() != lastLocation.getLongitude()) || (currentLocation.getLatitude() != lastLocation.getLatitude())) {
                        //preguntar por el tiempo
                        tracking.addTracking(currentLocation);
                    }
                    else if(lastLocation!=null && currentLocation.getTime()!=lastLocation.getTime()){
                        tracking.addTracking(currentLocation);
                    }
                }
                lastLocation=currentLocation;//Agrege lastLocation porque sino me guardaba la misma ubicacion y tengo asincronismo en los timpos
            //tracking.addTracking(currentLocation);
            GeoPoint aux=new GeoPoint(currentLocation.getLatitude(),currentLocation.getLongitude());
            MainActivity.updatePosition(aux);
            handler.postDelayed(this, 1000*5);//delay de 5 segundos
            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }

    };

    private void stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        handler.removeCallbacks(sendData);
        Intent i = new Intent("location_update");
        i.putExtra("tracking",tracking);
        i.putParcelableArrayListExtra("points", (ArrayList<? extends Parcelable>) tracking.getPoints());
        sendBroadcast(i);
        stopForeground(true);
        stopSelf();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action= intent.getAction();
            if(action!=null){
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    startLocationService();
                }else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class locationBinder extends Binder {
        Tracking getTracking(){
            return tracking;
        }
    }
}