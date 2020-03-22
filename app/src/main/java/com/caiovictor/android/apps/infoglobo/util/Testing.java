package com.caiovictor.android.apps.infoglobo.util;

import android.util.Log;

import com.caiovictor.android.apps.infoglobo.models.Capa;

import java.util.List;

public class Testing {

    public static void printCapa(Capa[] capas, String tag){
        for(Capa capa: capas){
            Log.d(tag, "onChanged " + capa.getProduto());
        }
    }
}

