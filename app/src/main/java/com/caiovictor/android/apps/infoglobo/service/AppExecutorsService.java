package com.caiovictor.android.apps.infoglobo.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutorsService {

    private static AppExecutorsService instance;

    public static AppExecutorsService getInstance(){
        if(instance == null){
            instance = new AppExecutorsService();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}
