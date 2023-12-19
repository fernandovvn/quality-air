package br.edu.ufam.icomp.devtitans.qualityair.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.notification.NotificationFactory;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorReading;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorType;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public class ServiceMonitoring extends Service implements SensorEventListener{
    private static final String TAG = "QUALITY-AIR";
    public static final int PARTICLES = 0, GAS = 1, TMP = 2;
    private SensorManager sensorManager;
    private NotificationFactory notificationFactory;
    private ServiceData data;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        data = new ServiceData(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_DEVICE_PRIVATE_BASE);
        sensorManager.registerListener(this, sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);

        notificationFactory = new NotificationFactory(this);
        startForeground(
                Algorithm.randomRange(0,Integer.MAX_VALUE-5),
                notificationFactory.buildNotification(
                        "QualityAir Monitoring",
                        "QualityAir is protecting your air.",
                        "QualityAir is protecting your air."));
        Log.d(TAG, "Service: onCreated.");
    }

    @Override
    public void onDestroy() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            Log.d(TAG, "Service: onDestroy.");
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Service: onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // The value of the first subscript in the values array is the current light intensity
        switch((int)event.values[0]){
            case PARTICLES:{
                int pm10 = (int) event.values[1], pm25 = (int) event.values[2];
                Log.d("LIGHT_TEST", "PM10: " + String.valueOf(pm10));
                Log.d("LIGHT_TEST", "PM25: " + String.valueOf(pm25));

                data.saveReading(new SensorReading(SensorType.PM_10, pm10, System.currentTimeMillis()));
                data.saveReading(new SensorReading(SensorType.PM_25, pm25, System.currentTimeMillis()));

                //PM10
                if(pm10>150) notificationFactory.showAlert(SensorType.PM_10);
                else notificationFactory.cancelAlert(SensorType.PM_10);

                //PM25
                if(pm25>150) notificationFactory.showAlert(SensorType.PM_25);
                else notificationFactory.cancelAlert(SensorType.PM_25);

                break;
            }
            case GAS:{
                int lpg = (int) event.values[1], co = (int) event.values[2], smoke = (int) event.values[3];
                Log.d("LIGHT_TEST", "LPG: " + String.valueOf(lpg));
                Log.d("LIGHT_TEST", "CO: " + String.valueOf(co));
                Log.d("LIGHT_TEST", "SMOKE: " + String.valueOf(smoke));

                data.saveReading(new SensorReading(SensorType.GAS_LPG, lpg, System.currentTimeMillis()));
                data.saveReading(new SensorReading(SensorType.GAS_CO, co, System.currentTimeMillis()));
                data.saveReading(new SensorReading(SensorType.GAS_SMOKE, smoke, System.currentTimeMillis()));

                //LPG
                if(lpg>700) notificationFactory.showAlert(SensorType.GAS_LPG);
                else notificationFactory.cancelAlert(SensorType.GAS_LPG);

                //CO
                if(co>200) notificationFactory.showAlert(SensorType.PM_25);
                else notificationFactory.cancelAlert(SensorType.PM_25);

                //SMOKE
                if(smoke>200) notificationFactory.showAlert(SensorType.GAS_SMOKE);
                else notificationFactory.cancelAlert(SensorType.GAS_SMOKE);

                break;
            }
            case TMP:{
                int humidity = (int) event.values[1], tmp = (int) event.values[2];
                Log.d("LIGHT_TEST", "HUMIDITY: " + String.valueOf(humidity));
                Log.d("LIGHT_TEST", "TEMPERATURE: " + String.valueOf(tmp));

                data.saveReading(new SensorReading(SensorType.DHT_TMP, tmp, System.currentTimeMillis()));
                data.saveReading(new SensorReading(SensorType.DHT_HUM, humidity, System.currentTimeMillis()));

                //LPG
                if(tmp>38) notificationFactory.showAlert(SensorType.DHT_TMP);
                else notificationFactory.cancelAlert(SensorType.DHT_TMP);

                //HUMIDITY
                if(tmp>38) notificationFactory.showAlert(SensorType.DHT_HUM);
                else notificationFactory.cancelAlert(SensorType.DHT_HUM);

                break;
            }
            default:
                Log.d("LIGHT_TEST", "Invalid values[0]");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
