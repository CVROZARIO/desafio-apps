package com.caiovictor.android.apps.infoglobo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.models.Conteudo.Conteudo;
import com.caiovictor.android.apps.infoglobo.models.ConteudoViewUser;
import com.caiovictor.android.apps.infoglobo.util.Commons;
import com.caiovictor.android.apps.infoglobo.util.ViewModelFactory;
import com.caiovictor.android.apps.infoglobo.viewmodels.CapaViewModel;
import com.caiovictor.android.apps.infoglobo.viewmodels.ConteudoViewViewModel;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class ConteudoViewActivity extends AppCompatActivity {

    private static final String TAG = "ConteudoViewActivity";

    // PREPARAR PARA MULTIPLOS PRODUTOS - O PRODUTO DEVERIA POSSUIR ID PARA CONTROLE INTERNO
    public final static String BUNDLE_DATA_PRODUTO = "BUNDLE_DATA_PRODUTO";
    public final static String BUNDLE_DATA_CONTEUDO_ID = "BUNDLE_DATA_CONTEUDO_ID";

    ConteudoViewViewModel mConteudoViewViewModel;

    private String mExtraProduto ="";
    private int mExtraConteudoId = -1;
    private Conteudo mConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_conteudo_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // TODO NÃO NECESSÁRIO PARA O MOMENTO
        //if (savedInstanceState == null) {}

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mExtraProduto = bundle.getString(BUNDLE_DATA_PRODUTO);
            if(mExtraProduto != null){
                mExtraProduto = mExtraProduto.toUpperCase();
            }
            mExtraConteudoId = bundle.getInt(BUNDLE_DATA_CONTEUDO_ID);
        }

        if(bundle == null || mExtraProduto.equals("") || mExtraConteudoId < 0){
            Toast.makeText(this, R.string.content_notfound, Toast.LENGTH_LONG).show();
            finishMe();
        }

        // TODO CAPTURAR O CONTEÚDO DA ROOM DATABASE TEMP, MAS COMO NÃO HÁ NECESSIDADE DE SE
        //  IMPLEMENTAR AINDA...
        initObservers();

    }

    private void initObservers(){

        mConteudoViewViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(ConteudoViewViewModel.class);

        // PROVAVELMENTE MATERIAS NÃO RECEBEM ATUALIZAÇÃOES MODIFICAÇÕES DE CONTEÚDO OU DADOS
        // TALVEZ SEJA INTERESSANTE MANTER LIVEDATA PARA RECEBER ATUALIZAÇÕES/MODIFICAÇÕES
        // A TODOS TEMPO E ATUALIZAR A UI CONFORME
        // TODO CRIAR OBESERVER PARA O CONTEÚDO ESPECÍFICO
        LiveData liveDataGetCapas = mConteudoViewViewModel.getCapas();
        liveDataGetCapas.observe(this, (Observer<Capa[]>) capas -> {

            if(capas != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Arrays.stream(capas)
                            .filter(c -> c.getProduto().toUpperCase().equals(mExtraProduto))
                            .findFirst().ifPresent(capa -> mConteudo = capa.getConteudos().stream()
                            .filter(p -> p.getId() == mExtraConteudoId)
                            .findFirst().orElse(null));
                } else {
                    for (Capa capa : capas) {
                        if (capa.getProduto().toUpperCase().equals(mExtraProduto)) {
                            for (Conteudo conteudo : capa.getConteudos()) {
                                if (conteudo.getId() == mExtraConteudoId) {
                                    mConteudo = conteudo;
                                    break;
                                }
                            }
                        }
                        if (mConteudo != null) {
                            break;
                        }
                    }
                }

                if(mConteudo == null){
                    Toast.makeText(ConteudoViewActivity.this, R.string.content_notfound, Toast.LENGTH_LONG).show();
                    finishMe();
                }

                mConteudoViewViewModel.insert(new ConteudoViewUser(mConteudo.getId(), new Date()));
                observeConteudoViewCount(mConteudoViewViewModel.getCountViewsByConteudoId(mConteudo.getId()));

                fillData();

            }

            // NÃO É REALTIME DATA - SEM NECESSIDADE DE MANTER O OBERSER VIVO
            //liveDataGetCapas.removeObserver(this);
        });

    }

    private void observeConteudoViewCount(LiveData<Long> aLong){
        // TODO SUPUNDO QUE SEJA UMA CONSULTA A BASE EXTERNA, OU MELHOR, MICRO SERVIÇO, OS DADOS
        //  SERÃO VIVOS, E COM UM ATUALIZAÇÃO COMO ESTA QUE CONSUMIRÁ QUASE NADA RECURSOS DO
        //  DISPOSITIVO É INTERESSANTE DEXAR O OBESERVER VIVO - O USUÁRIO IRÁ ACOMPNAHAR A
        //  "REPERCUSSÃO" DA MATÉRIA AO VIVO
        aLong.observe(this, aLong1 -> {
            if(aLong1 != null) {
                ((TextView) findViewById(R.id.count_views)).setText(String.valueOf(aLong1));
            }
        });
    }

    private void fillData(){

        // INSERT SIMPLES SEM FIRULA

        if(mConteudo.getImagens() != null && mConteudo.getImagens().length > 0) {

            // TODO CONCLUIR CONFIGURAÇÃO DO SLIDER, EMBORA NÃO HÁ MATÉRIA COM MAS DE UMA IMAGEM
            /*if (mConteudo.getImagens().length > 1) {
                SliderView sliderView = findViewById(R.id.image_slider);
                ConteudoImageSliderAdapter conteudoImageSliderAdapter = new ConteudoImageSliderAdapter(this);
                conteudoImageSliderAdapter.renewItems(Arrays.asList(mConteudo.getImagens()));
                sliderView.setSliderAdapter(conteudoImageSliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setScrollTimeInSec(4);
                sliderView.startAutoCycle();
            }*/

            Glide
                    .with(this)
                    .setDefaultRequestOptions(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.conteudo_thumb_placeholder)
                            .centerCrop())
                    .load(mConteudo.getImagens()[0].getUrl())
                    .into((ImageView) findViewById(R.id.thumb));

            ((TextView)findViewById(R.id.thumb_subtitle)).setText(
                    String.format(this.getString(R.string.conteudo_view_thumb_subtitle_format)
                            , mConteudo.getImagens()[0].getLegenda(), mConteudo.getImagens()[0].getFonte())
            );

        }else{
            findViewById(R.id.thumb_wrapper).setVisibility(View.GONE);
        }

        final TextView secao = findViewById(R.id.secao);
        secao.setText(mConteudo.getSecao().getNome());
        secao.setOnClickListener(view -> Commons.doOpenUrl(ConteudoViewActivity.this, mConteudo.getSecao().getUrl()));

        Date date = Commons.getDateFromString(mConteudo.getAtualizadoEm());
        if(date != null) {
            ((TextView)findViewById(R.id.last_update)).setText(Commons.getHumanElapsedTimeFrom(this, date));
            ((TextView)findViewById(R.id.last_update_date)).setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date));
        }else{
            ((TextView)findViewById(R.id.last_update)).setText(R.string.human_elapsed_time_unknow);
        }

        if(mConteudo.getAutores() != null && mConteudo.getAutores().length > 0) {
            StringBuilder stringBuilderAutores = new StringBuilder();
            final String autorSeparator = getString(R.string.conteudo_view_autor_separator);
            for (String autor : mConteudo.getAutores()) {
                if (stringBuilderAutores.length() > 0) {
                    stringBuilderAutores.append(autorSeparator);
                }
                stringBuilderAutores.append(autor);
            }
            ((TextView) findViewById(R.id.autores)).setText(stringBuilderAutores.toString());
        }else{
            ((TextView) findViewById(R.id.autores)).setText(R.string.conteudo_view_autor_none);
        }

        ((TextView)findViewById(R.id.title)).setText(mConteudo.getTitulo());
        ((TextView)findViewById(R.id.subtitle)).setText(mConteudo.getSubTitulo());
        ((TextView)findViewById(R.id.text)).setText(mConteudo.getTexto());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.conteudo_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishMe();
                return true;
            case R.id.action_share:
                Commons.doShareOut(this, mConteudo.getTitulo(), mConteudo.getUrl());
                return true;
            case R.id.action_webpage:
                Commons.doOpenUrl(this, mConteudo.getUrl());
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void finishMe(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("message", "This is my message to be reloaded");
        super.onSaveInstanceState(outState);
    }

}
