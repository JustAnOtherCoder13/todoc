package com.cleanup.todoc.ui;

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
    private final Executor executor;

    public TaskViewModel (ProjectRepository projectDataSource, TaskRepository taskDataSource,
                          Executor executor){

        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getAllTasks () { return taskDataSource.getAllTasks();}

    public void createTask (Task task){ executor.execute(()->{taskDataSource.createTask(task);});}

    public void deleteTask (Task task){ executor.execute(()->{taskDataSource.deleteTask(task);});}

    public LiveData<List<Project>> getAllProjects () { return projectDataSource.getAllProjects();}

}
