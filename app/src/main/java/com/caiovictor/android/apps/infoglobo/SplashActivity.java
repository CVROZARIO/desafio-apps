package com.caiovictor.android.apps.infoglobo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // TODO CARREGAR ASSETS E PRESSETS
        // APLICAR CONFIGURAÇÕES DE ABERTURA, INSTANCIA CLASSES...
        // ---------------------------------------------------------------------------------------
        // P) MAS CAIO, PORQUE NÃO COLOCAR O CARREGAMENTO DAS MANTERIAS AQUI?
        // R) ACREDITO QUE A  AUTORIDADE DE CAPTURAR AS MATERIAS E AFINS SEJA DE UM SERVICE EM
        // BACKGROUND PARA TER A REALTIME DA INFORMAÇÕES, COMO NESTE O JSON DE CONSUMO É ESTÁTICO
        // NÃO HÁ A NECESSIDADE DE SE COMPLICAR A VIDA (NESTE MOMENTO)
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();

    }

}
