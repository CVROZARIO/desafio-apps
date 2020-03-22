package com.caiovictor.android.apps.infoglobo.util;

import android.app.Application;

import com.caiovictor.android.apps.infoglobo.viewmodels.ConteudoViewViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private Object[] mParams;

    public ViewModelFactory(Application application, Object... params) {
        this.mApplication = application;
        this.mParams = params;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ConteudoViewViewModel.class) {
            return (T) new ConteudoViewViewModel(mApplication);
        } else {
            return super.create(modelClass);
        }
    }
}
