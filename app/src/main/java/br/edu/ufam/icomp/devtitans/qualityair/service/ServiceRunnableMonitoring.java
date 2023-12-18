package br.edu.ufam.icomp.devtitans.qualityair.service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Sensors;

public class ServiceRunnableMonitoring implements Runnable {
    private static final int millisSleep = 5000;
    private volatile boolean isRunning;
    private final Context context;
    private final NotificationCompat.Builder builder;
    private final NotificationManagerCompat notificationManager;

    private int pm10, pm25, gas, humidity;

    public ServiceRunnableMonitoring(Context context, String CHANNEL_ID) {
        this.context = context;
        builder =  new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Air Quality")
                .setContentText("Your air is poor!")
                .setSmallIcon(R.drawable.owl0)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."));
        notificationManager = NotificationManagerCompat.from(context);
        pm10 = pm25 = gas = humidity = -1;
    }

    @Override
    public void run() {
        isRunning = true;
        int cnt = 0;

        try {
            while (isRunning) {
                Log.d("QUALITY-AIR", "Running Measures " + String.valueOf(++cnt));
                updatePm10();
                updatePm25();
                updateGas();
                updateHumidity();
                showNotification();
                Thread.sleep(millisSleep);
            }
        } catch (InterruptedException e){
            Log.e("QUALITY-AIR", "Running Measures: broked");
        }
    }

    public void stop() {
        isRunning = false;
    }

    private void updatePm10(){
        int val = Sensors.getPm10();
        if(val!=pm10){
            pm10 = val;
            sendBroadcast(ServiceBroadcastAction.QUALITY_AIR_PM10_CHANGED, val);
        }
    }
    private void updatePm25(){
        int val = Sensors.getPm25();
        if(val!=pm25){
            pm25 = val;
            sendBroadcast(ServiceBroadcastAction.QUALITY_AIR_PM25_CHANGED, val);
        }
    }
    private void updateGas(){
        int val = Sensors.getGas();
        if(val!=gas){
            gas = val;
            sendBroadcast(ServiceBroadcastAction.QUALITY_AIR_GAS_CHANGED, val);
        }
    }
    private void updateHumidity(){
        int val = Sensors.getHumidity();
        if(val!=humidity){
            humidity = val;
            sendBroadcast(ServiceBroadcastAction.QUALITY_AIR_HUMIDITY_CHANGED, val);
        }
    }

    private void sendBroadcast(String action, int val){
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("data", val);
        Log.d("QUALITY-AIR", "new " + action + " : " + val);
        context.sendBroadcast(intent);
    }

    private void showNotification(){
        Log.d("QUALITY-AIR", "TRYING NOTIFICATING");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            Log.d("QUALITY-AIR", "NOTIFICATING");
            notificationManager.notify(Algorithm.randomRange(0, Integer.MAX_VALUE - 5), builder.build());
        }
    }

}
