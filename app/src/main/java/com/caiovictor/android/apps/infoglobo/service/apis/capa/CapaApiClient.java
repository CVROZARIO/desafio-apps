package com.caiovictor.android.apps.infoglobo.service.apis.capa;

import android.util.Log;

import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.service.AppExecutorsService;
import com.caiovictor.android.apps.infoglobo.service.ServiceGenerator;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

import static com.caiovictor.android.apps.infoglobo.util.Constants.NETWORK_TIMEOUT;

public class CapaApiClient {

    private static final String TAG = "CapaApiClient";

    private static CapaApiClient instance;
    private MutableLiveData<Capa[]> mCapas;
    private RetrieveCapaRunnable mRetrieveCapaRunnable;
    private MutableLiveData<Boolean> mCapaRequestTimeout = new MutableLiveData<>();

    public static CapaApiClient getInstance(){
        if(instance == null){
            instance = new CapaApiClient();
        }
        return instance;
    }

    private CapaApiClient(){
        mCapas = new MutableLiveData<>();
    }

    public LiveData<Capa[]> getCapas(){
        return mCapas;
    }

    public LiveData<Boolean> isCapaRequestTimedOut(){
        return mCapaRequestTimeout;
    }

    public void loadLiveDataApi(){
        if(mRetrieveCapaRunnable != null){
            mRetrieveCapaRunnable = null;
        }
        mRetrieveCapaRunnable = new RetrieveCapaRunnable();
        final Future handler = AppExecutorsService.getInstance().networkIO().submit(mRetrieveCapaRunnable);

        AppExecutorsService.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    private class RetrieveCapaRunnable implements Runnable{

        boolean cancelRequest;

        public RetrieveCapaRunnable() {
            Log.d(TAG, "RetrieveCapaRunnable");
            cancelRequest = false;
        }

        @Override
        public void run() {
            Log.d(TAG, "run started");
            try {
                Response response = getCapa().execute();
                if(cancelRequest){
                    Log.d(TAG, "run canceled");
                    return;
                }
                Log.d(TAG, "run code " + response.code());
                if(response.code() == 200){
                    mCapas.postValue((Capa[])response.body());
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run error " + error );
                    mCapas.postValue(null);
                }
            } catch (IOException e) {
                Log.e(TAG, "run ioexception " + e.getMessage() );
                e.printStackTrace();
                mCapas.postValue(null);
            } catch (Exception e){
                Log.e(TAG, "run exception " + e.getMessage() );
                e.printStackTrace();
                mCapas.postValue(null);
            }

        }

        private Call<Capa[]> getCapa(){
            return ServiceGenerator.getCapaApi().getCapa();
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest");
            cancelRequest = true;
        }
    }

}
