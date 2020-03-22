package com.caiovictor.android.apps.infoglobo.service.apis.capa;

import com.caiovictor.android.apps.infoglobo.models.Capa;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CapaApi {

    @GET("Infoglobo/desafio-apps/master/capa.json")
    Call<Capa[]> getCapa(
            //@Query("x") String x // PARA FUTUROS PARAMETROS
    );

}
