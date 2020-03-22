package com.caiovictor.android.apps.infoglobo.viewmodels;

import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.repositories.CapaRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CapaViewModel extends ViewModel {

    private CapaRepository mCapaRepository;

    public CapaViewModel() {
        mCapaRepository = CapaRepository.getInstance();
    }

    public LiveData<Capa[]> getCapas(){
        return mCapaRepository.getCapas();
    }

    public void loadLiveData(){
        mCapaRepository.loadLiveData();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mCapaRepository.isQueryExhausted();
    }

}