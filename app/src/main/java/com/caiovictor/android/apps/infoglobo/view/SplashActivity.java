package com.caiovictor.android.apps.infoglobo.view;

import android.content.Intent;
import android.os.Bundle;

import com.caiovictor.android.apps.infoglobo.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // TODO CARREGAR ASSETS E PRESSETS
        // APLICAR CONFIGURAÇÕES DE ABERTURA, INSTANCIA CLASSES...
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();

    }

}
