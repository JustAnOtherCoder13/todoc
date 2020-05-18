package com.cleanup.todoc.di;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {


    private final Executor executor;
    private Context context;

    ViewModelFactory(Context context,
                     Executor executor){
       this.context = context;
        this.executor = executor;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(context,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
