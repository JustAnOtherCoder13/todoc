package com.cleanup.todoc.di;

import android.content.Context;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static TaskRepository provideTaskDataSource(Context context){
        Database database = Database.getInstance(context);
        return new TaskRepository(database.taskDao);
    }

    public static ProjectRepository provideProjectDataSource(Context context){
        Database database = Database.getInstance(context);
        return new ProjectRepository(database.projectDao);
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideModelFactory(Context context){
        ProjectRepository projectDataSource = provideProjectDataSource(context);
        TaskRepository taskDataSource = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectDataSource,taskDataSource,executor);
    }
}
