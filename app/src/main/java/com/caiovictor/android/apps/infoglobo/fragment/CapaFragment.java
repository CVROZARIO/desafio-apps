package com.caiovictor.android.apps.infoglobo.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caiovictor.android.apps.infoglobo.R;
import com.caiovictor.android.apps.infoglobo.adapters.ConteudoAdapter;
import com.caiovictor.android.apps.infoglobo.adapters.OnCapaConteudoListener;
import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.models.Conteudo.Conteudo;
import com.caiovictor.android.apps.infoglobo.util.Commons;
import com.caiovictor.android.apps.infoglobo.ConteudoViewActivity;
import com.caiovictor.android.apps.infoglobo.viewmodels.CapaViewModel;

public class CapaFragment extends Fragment implements OnCapaConteudoListener {

    private static final String TAG = "CapaFragment";

    private ConteudoAdapter mConteudoAdapter;
    private RecyclerView mRecyclerView;
    private CapaViewModel mCapaViewModel;
    private Capa mCapa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        mCapaViewModel = new ViewModelProvider(this).get(CapaViewModel.class);

        View root = inflater.inflate(R.layout.fragment_capa, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);

        initRecyclerView();
        subscribeObservers();

        return root;
    }

    private void initRecyclerView(){
        mConteudoAdapter = new ConteudoAdapter(this);
        RecyclerView.LayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mConteudoAdapter);
    }

    private void subscribeObservers(){
        mCapaViewModel.getCapas().observe(getViewLifecycleOwner(), capas -> {
            if(capas.length > 0){

                // PEGAR O PRIMEIRO REGISTRO DO ARRAY POIS O JSON EXEMPLO
                // NÃO TEM MAIS REGISTROS - FUTURO - CRIAR DINAMICA PARA ACEITAS MULTIPLOS
                // REGISTROS DE CAPA (PRODUTOS)
                mCapa = capas[0];
                mConteudoAdapter.setConteudos(mCapa.getConteudos());

            }
            // TODO NESTE CASO O ELSE ESTÁ SENDO TRATADO PELA ACTIVITY PRINCIPAL ATRAVEZ DO OBSERVE
            // ELA IRÁ APLICAR AS DEVIDAS AÇÕES
        });
    }

    @Override
    public void onCapaItemClick(int position, View view) {
        final Conteudo conteudo;
        if(mCapa != null && mCapa.getConteudos().size() > 0 && (conteudo = mCapa.getConteudos().get(position)) != null) {

            if(conteudo.getTypeMode() == Conteudo.TypeMode.TYPE_LINKEXTERNO){
                Commons.doOpenUrl(getActivity(), conteudo.getUrl());
            }else {

                try {

                    Intent intent = new Intent(getActivity(), ConteudoViewActivity.class);
                    intent.putExtra(ConteudoViewActivity.BUNDLE_DATA_PRODUTO, mCapa.getProduto());
                    intent.putExtra(ConteudoViewActivity.BUNDLE_DATA_CONTEUDO_ID, conteudo.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && conteudo.getImagens() != null && conteudo.getImagens().length > 0) {
                        final View thumbImage = view.findViewById(R.id.thumb);
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(getActivity(), thumbImage, ViewCompat.getTransitionName(thumbImage));
                        startActivity(intent, activityOptionsCompat.toBundle());
                    } else {
                        startActivity(intent);
                    }

                } catch (Exception exception) {
                    Toast.makeText(getActivity(), R.string.error_opening_conteudo, Toast.LENGTH_LONG).show();
                }

            }

        }
    }

    @Override
    public void onCapaShareClick(int position){
        final Conteudo conteudo;
        if(mCapa != null && mCapa.getConteudos().size() > 0 && (conteudo = mCapa.getConteudos().get(position)) != null) {
            Commons.doShareOut(getActivity(), conteudo.getTitulo(), conteudo.getUrl());
        }
    }
}