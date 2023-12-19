package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorReading;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorType;
import br.edu.ufam.icomp.devtitans.qualityair.sensorview.SensorsAdapter;
import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceData;
import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceMonitoring;

public class ActivitySensors extends AppCompatActivity implements SensorEventListener {

    private RecyclerView rv;
    private SensorsAdapter adapter;
    private ServiceData data;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        data = new ServiceData(this);
        rv = findViewById(R.id.sensors_rv);

        ArrayList<SensorReading> ls = data.getAllData();
        adapter = new SensorsAdapter(ls,this);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_DEVICE_PRIVATE_BASE);
        sensorManager.registerListener(this, sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // The value of the first subscript in the values array is the current light intensity
        switch((int)event.values[0]){
            case ServiceMonitoring.PARTICLES:{
                adapter.setItemUpdate(SensorType.PM_10.getValue(), data.getReading(SensorType.PM_10));
                adapter.setItemUpdate(SensorType.PM_25.getValue(), data.getReading(SensorType.PM_25));
                break;
            }
            case ServiceMonitoring.GAS:{
                adapter.setItemUpdate(SensorType.GAS_LPG.getValue(), data.getReading(SensorType.GAS_LPG));
                adapter.setItemUpdate(SensorType.GAS_CO.getValue(), data.getReading(SensorType.GAS_CO));
                adapter.setItemUpdate(SensorType.GAS_SMOKE.getValue(), data.getReading(SensorType.GAS_SMOKE));
                break;
            }
            case ServiceMonitoring.TMP:{
                int humidity = (int) event.values[1], tmp = (int) event.values[2];
                Log.d("LIGHT_TEST", "HUMIDITY: " + String.valueOf(humidity));
                Log.d("LIGHT_TEST", "TEMPERATURE: " + String.valueOf(tmp));

                adapter.setItemUpdate(SensorType.DHT_TMP.getValue(), data.getReading(SensorType.DHT_TMP));
                adapter.setItemUpdate(SensorType.DHT_HUM.getValue(), data.getReading(SensorType.DHT_HUM));

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