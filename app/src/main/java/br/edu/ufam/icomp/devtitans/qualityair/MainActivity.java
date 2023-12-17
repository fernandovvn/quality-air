package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnCLick Listeners (Buttons)
        findViewById(R.id.main_btn_particles).setOnClickListener(this::onClickBtnParticles);
        findViewById(R.id.main_btn_gas).setOnClickListener(this::onClickBtnGas);
        findViewById(R.id.main_btn_humidity).setOnClickListener(this::onClickBtnHumidity);
        findViewById(R.id.main_btn_settings).setOnClickListener(this::onClickSettings);
    }

    public void onClickBtnParticles(View v){
        Intent intent = new Intent(this, ActivitySensorParticles.class);
        startActivity(intent);
    }

    public void onClickBtnGas(View v){
        Intent intent = new Intent(this, ActivitySensorGas.class);
        startActivity(intent);
    }

    public void onClickBtnHumidity(View v){
        Intent intent = new Intent(this, ActivitySensorHumidity.class);
        startActivity(intent);
    }

    public void onClickSettings(View v){
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }
}