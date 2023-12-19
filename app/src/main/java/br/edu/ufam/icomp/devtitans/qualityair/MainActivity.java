package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceMonitoring;

public class MainActivity extends AppCompatActivity {

    private void handleNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, ServiceMonitoring.class);
        this.startForegroundService(serviceIntent);

        //OnCLick Listeners (Buttons)
        findViewById(R.id.main_btn_sensors).setOnClickListener(this::onClickBtnMeasurents);
//        findViewById(R.id.main_btn_gas).setOnClickListener(this::onClickBtnGas);
//        findViewById(R.id.main_btn_humidity).setOnClickListener(this::onClickBtnHumidity);
        findViewById(R.id.main_btn_settings).setOnClickListener(this::onClickSettings);

        handleNotificationPermission();
    }

    public void onClickBtnMeasurents(View v){
        Intent intent = new Intent(this, ActivitySensors.class);
        startActivity(intent);
    }

    public void onClickSettings(View v){
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }
}