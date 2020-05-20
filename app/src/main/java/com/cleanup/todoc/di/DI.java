package com.cleanup.todoc.di;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    private static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideModelFactory(Context context){
        Executor executor = provideExecutor();
        return new ViewModelFactory(context,executor);
    }
}
