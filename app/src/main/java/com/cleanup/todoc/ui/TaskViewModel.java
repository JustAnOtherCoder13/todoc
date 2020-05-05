package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends AndroidViewModel {

    private final ProjectRepository projectDataSource;
    private final TaskRepository taskDataSource;
    private LiveData<List<Task>> allTasks;
    private Project[] allProjects;
    private final Executor executor;

    public TaskViewModel(@NonNull Application application, Executor executor) {
        super(application);
        taskDataSource = new TaskRepository(application);
        projectDataSource= new ProjectRepository(application);
        allTasks = taskDataSource.getAllTasks();
        allProjects = projectDataSource.getAllProjects();
        this.executor=executor;
    }

    public LiveData<List<Task>> getAllTasks () { return allTasks;}

    public void createTask (Task task){ executor.execute(()->{taskDataSource.createTask(task);});}

    public void deleteTask (Task task){ executor.execute(()->{taskDataSource.deleteTask(task);});}

    public Project[] getAllProjects () { return allProjects;}

}
