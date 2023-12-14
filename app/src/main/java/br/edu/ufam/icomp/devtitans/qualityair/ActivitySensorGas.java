package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class ActivitySensorGas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_gas);
    }

    public static int gerarNumeroAleatorio() {
        // Crie uma instância da classe Random
        Random random = new Random();

        // Gere um número aleatório entre 0 (inclusive) e 100 (exclusive)
        int numeroAleatorio = random.nextInt(101);

        return numeroAleatorio;
    }

    public void mostraVal2(View v){
        TextView myTextView = findViewById(R.id.textview_valor_s1);
        int val = gerarNumeroAleatorio();
        myTextView.setText(String.valueOf(val));
    }
}