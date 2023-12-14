package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ActivitySensorParticles extends AppCompatActivity {

    private Button btn25, btn10;
    private TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_particles);
        btn25  = findViewById(R.id.sensor_particles_btn_25);
        btn10  = findViewById(R.id.sensor_particles_btn_10);
        tvDesc = findViewById(R.id.sensor_particles_tv_desc);

        btn25.setOnClickListener(this::run25);
        btn10.setOnClickListener(this::run10);
    }

    public static int randomRange(int mi, int mx) {
        Random random = new Random();
        return mi + random.nextInt(mx-mi);
    }

    public void run25(View v){
        TextView myTextView = findViewById(R.id.sensor_particles_tv_val);
        int val = randomRange(0,250);
        myTextView.setText(String.valueOf(val));
        updateDescription(true, val);
    }

    public void run10(View v){
        TextView myTextView = findViewById(R.id.sensor_particles_tv_val);
        int val = randomRange(0,250);
        myTextView.setText(String.valueOf(val));
        updateDescription(false, val);
    }

    private void updateDescription(boolean is25, int val){
        String text;
        if(is25) { //2.5 measurement
            text = val<=25
                    ? getResources().getString(R.string.mp_1)
                    : val<=50
                    ? getResources().getString(R.string.mp_2)
                    : val<=75
                    ? getResources().getString(R.string.mp_3)
                    : val<=125
                    ? getResources().getString(R.string.mp_4)
                    : getResources().getString(R.string.mp_5);
        }
        else{ //10 measurement
            text = val<=50
                    ? getResources().getString(R.string.mp_1)
                    : val<=100
                    ? getResources().getString(R.string.mp_2)
                    : val<=150
                    ? getResources().getString(R.string.mp_3)
                    : val<=250
                    ? getResources().getString(R.string.mp_4)
                    : getResources().getString(R.string.mp_5);
        }
        tvDesc.setText(text);
    }
}