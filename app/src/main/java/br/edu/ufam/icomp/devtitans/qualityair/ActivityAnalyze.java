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
import br.edu.ufam.icomp.devtitans.qualityair.broadcast.MyBroadcastReceiver;
import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceBroadcastAction;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Sensors;

public class ActivityAnalyze extends AppCompatActivity implements BroadcastListener {
    //View
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;

    // Broadcast
    private boolean automatic;
    private BroadcastReceiver receiver;

    // Auxiliar
    private final int[] gasVals = {700, 1000};
    private final int[] ringIds = {
            R.drawable.ring_g, R.drawable.ring_o, R.drawable.ring_r,
    };
    private final int[] gasStatusIds = {
            R.string.gas_status_low,
            R.string.gas_status_moderate,
            R.string.gas_status_high
    };
    private final int[] gasDescIds = {
        R.string.gas_desc_low,
        R.string.gas_desc_moderate,
        R.string.gas_desc_high
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_gas);
        tvVal = findViewById(R.id.sensor_gas_tv_val);
        imgRing = findViewById(R.id.sensor_gas_img_ring);
        tvStatus = findViewById(R.id.sensor_gas_tv_status);
        tvDesc = findViewById(R.id.sensor_gas_tv_desc);
        automatic = ((CheckBox)findViewById(R.id.sensor_gas_cb_automatic)).isChecked();

        findViewById(R.id.sensor_gas_btn).setOnClickListener(this::onClickBtnMeasure);
        findViewById(R.id.sensor_gas_cb_automatic).setOnClickListener(this::onClickCheckboxAutomatic);

        if(automatic) measure();
    }

    @Override
    protected void onResume() {
        receiver = new MyBroadcastReceiver(this);
        ContextCompat.registerReceiver(
                this, receiver,
                new IntentFilter(ServiceBroadcastAction.QUALITY_AIR_GAS_CHANGED),
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
        int val = Sensors.getGas();
        updateMeasure(val);
    }

    @Override
    public void broadcastListen(Context context, Intent intent) {
        if (automatic && ServiceBroadcastAction.QUALITY_AIR_GAS_CHANGED.equals(intent.getAction())){
            int val = intent.getIntExtra("data", 0);
            updateMeasure(val);
        }
    }

    private void updateMeasure(int val){
        int i = Algorithm.lower_bound(gasVals, val);
        tvVal.setText(String.valueOf(val));
        imgRing.setImageResource(ringIds[i]);
        tvStatus.setText(getResources().getString(gasStatusIds[i]));
        tvDesc.setText(getResources().getString(gasDescIds[i]));
    }
}