package com.cleanup.todoc.ui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectRepository projectDataSource;
    private final TaskRepository taskDataSource;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> allProjects;
    private final Executor executor;

    public TaskViewModel(@NonNull Context context, Executor executor) {
        taskDataSource = new TaskRepository(context);
        projectDataSource= new ProjectRepository(context);
        allTasks = taskDataSource.getAllTasks();
        allProjects = projectDataSource.getAllProjects();
        this.executor=executor;
    }

    LiveData<List<Task>> getAllTasks() { return allTasks;}

    void createTask(Task task){ executor.execute(()-> taskDataSource.createTask(task));}

    void deleteTask(Task task){ executor.execute(()-> taskDataSource.deleteTask(task));}

    LiveData<List<Project>>getAllProjects() { return allProjects;}

}
