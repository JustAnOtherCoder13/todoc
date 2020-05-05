package com.cleanup.todoc.di;

import android.app.Application;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    private static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideModelFactory(Application application){
        Executor executor = provideExecutor();
        return new ViewModelFactory(application,executor);
    }
}
