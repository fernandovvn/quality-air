package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufam.icomp.devtitans.qualityair.broadcast.BroadcastListener;
import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceBroadcastAction;
import br.edu.ufam.icomp.devtitans.qualityair.broadcast.MyBroadcastReceiver;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Sensors;

public class ActivitySensorHumidity extends AppCompatActivity implements BroadcastListener {
    //View
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;

    // Broadcast
    private boolean automatic = false;
    private BroadcastReceiver receiver = null;

    // Auxiliar
    private final int[] humidityVals = {30, 50, 70};
    private final int[] ringIds = {
            R.drawable.ring_r, R.drawable.ring_g,
            R.drawable.ring_y, R.drawable.ring_p
    };
    private final int[] humidityStatusIds = {
            R.string.status_bad,
            R.string.status_good,
            R.string.status_moderate,
            R.string.status_very_bad
    };
    private final int[] humidityDescIds = {
            R.string.humidity_desc_bad,
            R.string.humidity_desc_good,
            R.string.humidity_desc_moderate,
            R.string.humidity_desc_very_bad
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_humidity);
        tvVal = findViewById(R.id.sensor_humidity_tv_val);
        imgRing = findViewById(R.id.sensor_humidity_img_ring);
        tvStatus = findViewById(R.id.sensor_humidity_tv_status);
        tvDesc = findViewById(R.id.sensor_humidity_tv_desc);
        automatic = ((CheckBox)findViewById(R.id.sensor_humidity_cb_automatic)).isChecked();

        findViewById(R.id.sensor_humidity_btn).setOnClickListener(this::onClickBtnMeasure);
        findViewById(R.id.sensor_humidity_cb_automatic).setOnClickListener(this::onClickCheckboxAutomatic);

        if(automatic) measure();
    }

    @Override
    protected void onResume() {
        receiver = new MyBroadcastReceiver(this);
        ContextCompat.registerReceiver(
                this, receiver,
                new IntentFilter(ServiceBroadcastAction.QUALITY_AIR_HUMIDITY_CHANGED),
                ContextCompat.RECEIVER_EXPORTED);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onPause();
    }

    private void onClickCheckboxAutomatic(View v){
        CheckBox cb = (CheckBox) v;
        automatic = cb.isChecked();
        if(automatic) measure();
    }

    private void onClickBtnMeasure(View v){
        measure();
    }

    private void measure(){
        int val = Sensors.getHumidity();
        updateMeasure(val);
    }

    @Override
    public void broadcastListen(Context context, Intent intent) {
        if (automatic && ServiceBroadcastAction.QUALITY_AIR_HUMIDITY_CHANGED.equals(intent.getAction())){
            int val = intent.getIntExtra("data", 0);
            updateMeasure(val);
        }
    }

    private void updateMeasure(int val){
        int i = Algorithm.lower_bound(humidityVals, val);
        tvVal.setText(String.valueOf(val));
        imgRing.setImageResource(ringIds[i]);
        tvStatus.setText(getResources().getString(humidityStatusIds[i]));
        tvDesc.setText(getResources().getString(humidityDescIds[i]));
    }

}