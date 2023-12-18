package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufam.icomp.devtitans.qualityair.broadcast.BroadcastListener;
import br.edu.ufam.icomp.devtitans.qualityair.broadcast.MyBroadcastReceiver;
import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceBroadcastAction;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Sensors;

public class ActivitySensorParticles extends AppCompatActivity implements BroadcastListener {

    // Views
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;
    private Button btn10, btn25;

    // Auxiliar
    private enum PmType {
        NONE,
        MP_2_5,
        MP_10
    };
    private final int[] mp10vals = {50, 100, 150, 250}, mp25vals = {25, 50, 75, 125};
    private final int[] ringIds = {
            R.drawable.ring_g, R.drawable.ring_y,
            R.drawable.ring_o, R.drawable.ring_r,
            R.drawable.ring_p
    };
    private final int[] mpStatusIds = {
            R.string.status_good, R.string.status_moderate,
            R.string.status_bad, R.string.status_very_bad,
            R.string.status_terrible
    };
    private final int[] mpDescriptionIds = {
            R.string.mp_description_1,R.string.mp_description_2,
            R.string.mp_description_3, R.string.mp_description_4,
            R.string.mp_description_5
    };

    // Broadcast & Measure
    private boolean automatic = false;
    private BroadcastReceiver receiver = null;
    private PmType selected = PmType.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_particles);
        tvVal = findViewById(R.id.sensor_particles_tv_val);
        imgRing = findViewById(R.id.sensor_particles_img_ring);
        tvStatus = findViewById(R.id.sensor_particles_tv_status);
        tvDesc = findViewById(R.id.sensor_particles_tv_desc);
        btn10 = findViewById(R.id.sensor_particles_btn_10);
        btn25 = findViewById(R.id.sensor_particles_btn_25);

        btn10.setOnClickListener(this::onClickBtn10);
        btn25.setOnClickListener(this::onClickBtn25);
        findViewById(R.id.sensor_particles_cb_automatic).setOnClickListener(this::onClickCheckboxAutomatic);
    }

    @Override
    protected void onResume() {
        receiver = new MyBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ServiceBroadcastAction.QUALITY_AIR_PM10_CHANGED);
        intentFilter.addAction(ServiceBroadcastAction.QUALITY_AIR_PM25_CHANGED);
        ContextCompat.registerReceiver(
                this, receiver,
                intentFilter,
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
        if(automatic && selected!=PmType.NONE) measure(selected);
    }

    private void select(PmType tp){
        switch(tp){
            case MP_10:{
                btn10.setAlpha(1);
                btn25.setAlpha(0.5F);
                break;
            }
            case MP_2_5:{
                btn25.setAlpha(1);
                btn10.setAlpha(0.5F);
                break;
            }
            default:{
                btn10.setAlpha(1);
                btn25.setAlpha(1);
            }
        }
        selected=tp;
    }

    public void onClickBtn10(View v){
        select(PmType.MP_10);
        measure(selected);
    }

    public void onClickBtn25(View v){
        select(PmType.MP_2_5);
        measure(selected);
    }

    private void measure(PmType tp){
        updateMeasure(tp, tp== PmType.MP_10 ? Sensors.getPm10() : Sensors.getPm25());
    }

    @Override
    public void broadcastListen(Context context, Intent intent) {
        if(!automatic) return;
        if (selected== PmType.MP_10 && ServiceBroadcastAction.QUALITY_AIR_PM10_CHANGED.equals(intent.getAction())){
            int val = intent.getIntExtra("data", 0);
            updateMeasure(PmType.MP_10, val);
        }
        else if (selected== PmType.MP_2_5 && ServiceBroadcastAction.QUALITY_AIR_PM25_CHANGED.equals(intent.getAction())){
            int val = intent.getIntExtra("data", 0);
            updateMeasure(PmType.MP_2_5, val);
        }
    }

    private void updateMeasure(PmType tp, int val){
        int i = Algorithm.lower_bound(tp == PmType.MP_10 ? mp10vals : mp25vals, val);
        String status=getResources().getString(mpStatusIds[i]);
        String desc=getResources().getString(mpDescriptionIds[i]);
        tvVal.setText(String.valueOf(val));
        tvStatus.setText(status);
        tvDesc.setText(desc);
        imgRing.setImageResource(ringIds[i]);
    }
}