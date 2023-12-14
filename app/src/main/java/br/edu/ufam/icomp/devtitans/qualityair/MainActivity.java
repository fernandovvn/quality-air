package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnParticle, btnGas, btnHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParticle = findViewById(R.id.main_btn_particles);
        btnGas = findViewById(R.id.main_btn_gas);
        btnHumidity = findViewById(R.id.main_btn_humidity);

        //OnCLick Listeners (Buttons)
        btnParticle.setOnClickListener(this::onClickBtnParticles);
        btnGas.setOnClickListener(this::onClickBtnGas);
        btnHumidity.setOnClickListener(this::onClickBtnHumidity);
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
}