package br.edu.ufam.icomp.devtitans.qualityair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ActivitySettings extends AppCompatActivity {

    private ImageButton btnEn, btnPt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.settings_btn_en).setOnClickListener(this::onClickEn);
        findViewById(R.id.settings_btn_pt_br).setOnClickListener(this::onClicPt);
    }

    public void onClickEn(View v){
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en");
        AppCompatDelegate.setApplicationLocales(appLocale);
    }

    public void onClicPt(View v){
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("pt-BR");
        AppCompatDelegate.setApplicationLocales(appLocale);
    }
}