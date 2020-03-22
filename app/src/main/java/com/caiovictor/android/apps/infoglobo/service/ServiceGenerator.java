package com.caiovictor.android.apps.infoglobo.service;

import com.caiovictor.android.apps.infoglobo.service.apis.capa.CapaApi;
import com.caiovictor.android.apps.infoglobo.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder mRetrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit mRetrofit = mRetrofitBuilder.build();

    private static CapaApi mCapaApi = mRetrofit.create(CapaApi.class);

    public static CapaApi getCapaApi(){
        return mCapaApi;
    }

}
