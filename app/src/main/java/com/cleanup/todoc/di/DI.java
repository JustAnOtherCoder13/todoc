package com.cleanup.todoc.di;

import android.app.Application;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static TaskRepository provideTaskDataSource(Application application){
        //Database database = Database.getInstance(application);
        return new TaskRepository(application);
    }

    public static ProjectRepository provideProjectDataSource(Application application){
        //Database database = Database.getInstance(application);
        return new ProjectRepository(application);
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideModelFactory(Application application){
        //ProjectRepository projectDataSource = provideProjectDataSource(application);
        //TaskRepository taskDataSource = provideTaskDataSource(application);
        Executor executor = provideExecutor();
        return new ViewModelFactory(application,executor);
    }
}
