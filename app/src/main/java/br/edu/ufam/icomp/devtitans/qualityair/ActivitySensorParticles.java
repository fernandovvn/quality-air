package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public class ActivitySensorParticles extends AppCompatActivity {

    // Views
    private TextView tvVal, tvStatus, tvDesc;
    private ImageView imgRing;

    // Auxiliar
    private enum MpType {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_particles);
        tvVal = findViewById(R.id.sensor_particles_tv_val);
        imgRing = findViewById(R.id.sensor_particles_img_ring);
        tvStatus = findViewById(R.id.sensor_particles_tv_status);
        tvDesc = findViewById(R.id.sensor_particles_tv_desc);

        findViewById(R.id.sensor_particles_btn_25).setOnClickListener(this::run25);
        findViewById(R.id.sensor_particles_btn_10).setOnClickListener(this::run10);
    }

    public void run25(View v){
        int val = Algorithm.randomRange(0,300);
        tvVal.setText(String.valueOf(val));
        updateStatus(MpType.MP_2_5, val);
    }

    public void run10(View v){
        int val = Algorithm.randomRange(0,300);
        tvVal.setText(String.valueOf(val));
        updateStatus(MpType.MP_10, val);
    }

    private void updateStatus(MpType tp, int val){
        int i = Algorithm.lower_bound(tp == MpType.MP_10 ? mp10vals : mp25vals, val);
        setStatus(i);
    }

    private void setStatus(int i){
        String status=getResources().getString(mpStatusIds[i]);
        String desc=getResources().getString(mpDescriptionIds[i]);
        tvStatus.setText(status);
        tvDesc.setText(desc);
        imgRing.setImageResource(ringIds[i]);
    }
}