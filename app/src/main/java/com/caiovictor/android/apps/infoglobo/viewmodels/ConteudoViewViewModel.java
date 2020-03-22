package com.caiovictor.android.apps.infoglobo.viewmodels;

import android.app.Application;

import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.models.ConteudoViewUser;
import com.caiovictor.android.apps.infoglobo.repositories.CapaRepository;
import com.caiovictor.android.apps.infoglobo.repositories.ConteudoViewUserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ConteudoViewViewModel extends ViewModel implements ConteudoViewUserRepository.ConteudoViewUserRepositoryCallback {

    private ConteudoViewUserRepository mConteudoViewUserRepository;
    private CapaRepository mCapaRepository;

    public ConteudoViewViewModel(@NonNull Application application) {
        mConteudoViewUserRepository = ConteudoViewUserRepository.getInstance(application, this);
        mCapaRepository = CapaRepository.getInstance();
    }

    public LiveData<Capa[]> getCapas(){
        return mCapaRepository.getCapas();
    }

    public void insert(ConteudoViewUser conteudoViewUser){
        mConteudoViewUserRepository.insert(conteudoViewUser);
    }

    public LiveData<Long> getCountViewsByConteudoId(long id){
        return mConteudoViewUserRepository.getCountViewsByConteudoId(id);
    }

    @Override
    public void onInserted(long id) {
        // TODO PREPARADO PARA FUTURAS NECESSIDADES UTEIS
    }
}
