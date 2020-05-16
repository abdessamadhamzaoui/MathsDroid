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
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceNotif extends Service {
    private Timer t = new Timer();
    private int i = 2;
    private final String CHANNEL_ID = "Notifications";
    public int nbrpremier = 2;
    public Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int period = intent.getIntExtra("argument",2);

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isPrime(i)){
                updatenotif(i);}
                i++;
            }
        },0,period*1000);


        return super.onStartCommand(intent, flags, startId);}
        public void updatenotif(int nbre){
            Intent NotificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, NotificationIntent, 0);
            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Plus Grand Premier")
                    .setContentText(String.valueOf(nbre))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String description = "Nombres premiers";
                CharSequence name = "channel";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);}
        }
    @Override
    public void onDestroy() {
        t.cancel();
        super.onDestroy();
    }

    }

