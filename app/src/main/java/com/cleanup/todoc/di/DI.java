package com.cleanup.todoc.di;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    private static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideModelFactory(Application application){
        Executor executor = provideExecutor();
        return new ViewModelFactory(application,executor);
    }
}
