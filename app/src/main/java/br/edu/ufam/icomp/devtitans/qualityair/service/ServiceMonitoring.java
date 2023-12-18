package br.edu.ufam.icomp.devtitans.qualityair.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public class ServiceMonitoring extends Service {
    private static final String TAG = "QUALITY-AIR";
    private ServiceRunnableMonitoring runnable;
    private Thread thread;
    private final String[] channelIDs = {
            "QUALITY_AIR_SERVICE_CHANNEL_ID",
            "QUALITY_AIR_SERVICE_MONITORING_CHANNEL_ID"
    };
    private final String[] channelNames = {"Service", "Monitoring"};
    private final String[] channelDescriptions = {
            "channel for quality air service notifications",
            "channel for quality air monitoring notifications"
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runnable = new ServiceRunnableMonitoring(this, channelIDs[1]);
        thread = new Thread(runnable);
        createNotificationChannel(0);
        createNotificationChannel(1);

        Notification notification =
                new NotificationCompat.Builder(this, channelIDs[0])
                        .setContentTitle("QualityAir Monitoring")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentText("QualityAir is protecting your air.")
                        .build();
        startForeground(Algorithm.randomRange(0,Integer.MAX_VALUE-5), notification);
        thread.start();
        Log.d(TAG, "Service started.");
    }

    public void onDestroy() {
        runnable.stop();
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel(int i) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelIDs[i], channelNames[i], importance);
            channel.setDescription(channelDescriptions[i]);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
