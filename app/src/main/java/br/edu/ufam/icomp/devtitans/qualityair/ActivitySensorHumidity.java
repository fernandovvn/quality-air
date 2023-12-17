package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public class ActivitySensorHumidity extends AppCompatActivity {
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;

    // Auxiliar
    private final int[] humidityVals = {30, 50, 70};
    private final int[] ringIds = {
            R.drawable.ring_r, R.drawable.ring_g,
            R.drawable.ring_y, R.drawable.ring_p
    };
    private final int[] gasStatusIds = {
            R.string.status_bad,
            R.string.status_good,
            R.string.status_moderate,
            R.string.status_very_bad
    };
    private final int[] gasDescIds = {
            R.string.humidity_desc_bad,
            R.string.humidity_desc_good,
            R.string.gas_desc_moderate,
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

        findViewById(R.id.sensor_humidity_btn).setOnClickListener(this::measure);
    }

    private void measure(View v){
        int val = Algorithm.randomRange(0,100);
        int i = Algorithm.lower_bound(humidityVals, val);
        tvVal.setText(String.valueOf(val));
        updateStatus(i);
    }

    private void updateStatus(int i){
        imgRing.setImageResource(ringIds[i]);
        tvStatus.setText(getResources().getString(gasStatusIds[i]));
        tvDesc.setText(getResources().getString(gasDescIds[i]));
    }
}