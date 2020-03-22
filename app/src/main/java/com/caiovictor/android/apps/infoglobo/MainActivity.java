package com.caiovictor.android.apps.infoglobo;

import android.os.Build;
import android.os.Bundle;

import com.caiovictor.android.apps.infoglobo.util.Commons;
import com.caiovictor.android.apps.infoglobo.viewmodels.CapaViewModel;

import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawer;
    private AppBarConfiguration mAppBarConfiguration;
    private CapaViewModel mCapaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fade.excludeTarget(android.R.id.statusBarBackground, true);
                fade.excludeTarget(android.R.id.navigationBarBackground, true);
                getWindow().setEnterTransition(fade);
                getWindow().setExitTransition(fade);
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_capa)
                .setDrawerLayout(mDrawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mCapaViewModel = new ViewModelProvider(this).get(CapaViewModel.class);

        subscribeObservers();
        // SEGURAR A AUTONOMIA DE OBTENÇÃO DE DADOS DA CAPA PARA A MAINACTIVITY AFIM DE POSSIBILITAR
        // FUTURAS IMPLEMENTAÇÕES (NOTIFICAÇÕES, ALERTAS E AFINS) ENQUANO O USUÁRIO NAVEGA PELO APP
        // TODO IMPLEMENTAR MÉTRICA PARA LOAD PERIÓDICO DA CAPA (SERVICE, SOCKET, ...?)
        mCapaViewModel.loadLiveData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.nav_share:
                    mDrawer.closeDrawers();
                    Commons.doShareOut(MainActivity.this, R.string.share_app_subject, R.string.share_app_text);
                    return true;

                case R.id.nav_goto_site:
                    mDrawer.closeDrawers();
                    Commons.doOpenUrl(MainActivity.this, R.string.web_site);
                    return true;

                case R.id.nav_about:
                    //mDrawer.closeDrawers();// TODO CAUSANDO THROTTLE - AJUSTAR
                    Commons.doSimleStartActivity(MainActivity.this, AboutActivity.class);
                    return true;

            }

            return false;

        }
    };

    private void subscribeObservers(){

        mCapaViewModel.getCapas().observe(this, capas -> {
            if(capas.length > 0){
                setFrameOverlayContent(-1);
            }
        });

        mCapaViewModel.isQueryExhausted().observe(this, aBoolean -> {
            if(aBoolean) {

                // TODO QUANDO HOUVER API PARA REALTIME DATA, EXECUTAR CARREGAMENTO DE DADOS
                //  EM REALTIME (DELIBERAR ESTRATÉGIA)
                setFrameOverlayContent(R.id.foc_no_data);

            }
        });
    }

    private void setFrameOverlayContent(int resIdToShow){

        Log.d(TAG, "setFrameOverlayContent");

        boolean hasAny = false;

        for(int i : new int[]{R.id.foc_loading, R.id.foc_no_data}){
            if(i == resIdToShow){
                hasAny = true;
                findViewById(i).setVisibility(View.VISIBLE);
            }else{
                findViewById(i).setVisibility(View.GONE);
            }
        }

        if(findViewById(R.id.frame_overlay_content).getVisibility() != (hasAny ? View.VISIBLE : View.GONE)) {
            findViewById(R.id.frame_overlay_content).startAnimation(AnimationUtils.loadAnimation(this, hasAny ? android.R.anim.fade_in : android.R.anim.fade_out));
            findViewById(R.id.frame_overlay_content).setVisibility(hasAny ? View.VISIBLE : View.GONE);
        }

    }
}
