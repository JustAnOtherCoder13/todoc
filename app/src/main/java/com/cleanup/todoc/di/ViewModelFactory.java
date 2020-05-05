package com.cleanup.todoc.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {


    private final Executor executor;
    private Application application;

    public ViewModelFactory(Application application,
                            Executor executor){
       this.application = application;
        this.executor = executor;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(application,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
