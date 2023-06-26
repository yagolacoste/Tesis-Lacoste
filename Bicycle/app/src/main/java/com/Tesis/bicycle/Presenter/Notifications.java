package com.Tesis.bicycle.Presenter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.R;

public class Notifications {

    private NotificationManager notificationManager;

    private NotificationChannel notificationChannel;

    private Context context;
    private static final int NOTIFICATION_ID = 1;

    public Notifications( Context context) {
        notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
    }

    public void addNotification(String title,String description){
        String channelId="Location_notification_channel";

        Intent resultIntent=new Intent();
        PendingIntent pendingIntent=PendingIntent.getActivity(context.getApplicationContext(),0,resultIntent,PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(
                context.getApplicationContext(),
                channelId
        );
        builder.setSmallIcon(R.drawable.ic_bicycle);
//        builder.setContentTitle("Location service");
        builder.setContentTitle(title);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
//        builder.setContentText("Running");
        builder.setContentText(description);
        builder.setContentIntent(pendingIntent);
        //builder.setAutoCancel(false);
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
        notificationManager.notify(NOTIFICATION_ID,builder.build());
        //startForeground(Constants.LOCATION_SERVICE_ID, builder.build());
    }

    public void cancel(int i) {
        notificationManager.cancel(i);
    }
}