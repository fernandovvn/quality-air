package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public class ActivitySensorGas extends AppCompatActivity {
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;

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

        findViewById(R.id.sensor_gas_btn).setOnClickListener(this::measure);
    }

    private void measure(View v){
        int val = Algorithm.randomRange(0,2000);
        int i = Algorithm.lower_bound(gasVals, val);
        tvVal.setText(String.valueOf(val));
        updateStatus(i);
    }

    private void updateStatus(int i){
        imgRing.setImageResource(ringIds[i]);
        tvStatus.setText(getResources().getString(gasStatusIds[i]));
        tvDesc.setText(getResources().getString(gasDescIds[i]));
    }
}