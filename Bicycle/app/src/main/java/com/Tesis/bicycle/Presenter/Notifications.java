package com.Tesis.bicycle.Presenter;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notifications {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private Context context;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "Location_notification_channel";

    private static int notificationCounter = 1;
    public Notifications(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear el canal de notificación (solo es necesario una vez)
        createNotificationChannel();

        // Crear el NotificationCompat.Builder (solo una vez)
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bicycle)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Service",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("This channel is used by location service");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void addNotification(String title, String description) {
        // Incrementar el contador para IDs de notificación únicos
        int uniqueNotificationId = NOTIFICATION_ID + notificationCounter;

        // Actualizar el contenido de la notificación usando el Builder existente
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bicycle)
                .setContentTitle(title)
                .setContentText(description)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        // Crear un intent vacío (puede que desees proporcionar un intent real)
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context.getApplicationContext(),
                0,
                resultIntent,
                PendingIntent.FLAG_MUTABLE
        );

        // Actualizar la acción al hacer clic en la notificación
        notificationBuilder.setContentIntent(pendingIntent);

        // Mostrar la notificación actualizada con un ID único
        notificationManager.notify(uniqueNotificationId, notificationBuilder.build());

        // Incrementar el contador para la próxima notificación
        notificationCounter++;
    }

    public void cancel(int i) {
        notificationManager.cancel(i);
    }

    public void successMessage(String title,String message) {
        new SweetAlertDialog(context,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText(title)
                .setContentText(message).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();  // Cierra el diálogo
                ((Activity) context).finish();  // Cierra la actividad actual
            }
        }).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(context,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                .setContentText(message).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();  // Cierra el diálogo
                        Intent intent = new Intent(context, NavInitActivity.class);

                        // Agrega banderas para limpiar la pila de actividades y hacer que la nueva actividad sea la principal
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        // Inicia la nueva actividad
                        context.startActivity(intent);

                        // Cierra la actividad actual
                        ((Activity) context).finish();
//                        ((Activity) context).finish(); // Cierra la actividad actual

                    }
                }).show();
//        new SweetAlertDialog(context,
//                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(context,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}
