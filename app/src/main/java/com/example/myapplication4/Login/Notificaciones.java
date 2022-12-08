package com.example.myapplication4.Login;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication4.R;

public class Notificaciones {

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private Context myContext;

    public Notificaciones(Context myContext) {
        this.myContext = myContext;
    }

    public void notificacion1(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_add_comment_24);
        builder.setContentTitle("Notificacion registro");
        builder.setContentText("Usted se a registrado");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(myContext);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }

}
