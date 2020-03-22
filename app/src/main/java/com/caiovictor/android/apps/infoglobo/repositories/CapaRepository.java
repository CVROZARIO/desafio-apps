package com.caiovictor.android.apps.infoglobo.repositories;

import android.util.Log;

import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.service.apis.capa.CapaApiClient;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class CapaRepository {

    private static final String TAG = "CapaRepository";

    private static CapaRepository instance;
    private CapaApiClient mCapaApiClient;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<Capa[]> mCapas = new MediatorLiveData<>();

    public static CapaRepository getInstance(){
        if(instance == null){
            instance = new CapaRepository();
        }
        return instance;
    }

    private CapaRepository(){
        mCapaApiClient = CapaApiClient.getInstance();
        initMediators();
    }

    private void initMediators(){
        LiveData<Capa[]> recipeListApiSource = mCapaApiClient.getCapas();
        mCapas.addSource(recipeListApiSource, new Observer<Capa[]>() {
            @Override
            public void onChanged(@Nullable Capa[] capas) {
                Log.d(TAG, "onChanged");
                if(capas != null){
                    mCapas.setValue(capas);
                    doneQuery(capas);
                }
                else{
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(Capa[] capas){
        if(capas == null || capas.length <= 0){
            mIsQueryExhausted.setValue(true);
        }
    }

    public void loadLiveData(){
        mIsQueryExhausted.setValue(false);
        mCapaApiClient.loadLiveDataApi();
    }

    public LiveData<Capa[]> getCapas(){
        return mCapas;
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

}
