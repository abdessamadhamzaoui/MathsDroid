package com.example.mathsdroid;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceNotif extends Service {
    private Timer t = new Timer();
    private final String CHANNEL_ID = "Notifications";
    public Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //code

        TimerTask calcul = new TimerTask() {
            @Override
            public void run() {
                updatenotif(2);
            }
        };
        t.scheduleAtFixedRate(calcul,0,2000);
        return super.onStartCommand(intent, flags, startId);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updatenotif(int nbrpremier){
        Intent NotificationIntent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,NotificationIntent,0);
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Plus Grand Premier")
                .setContentText(String.valueOf(nbrpremier))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"nbre premiers", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(notificationChannel);

    }
}
