package com.cleanup.todoc.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class GlobalViewModel extends ViewModel {

    private final ProjectRepository projectDataSource;
    private TaskRepository taskDataSource;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> allProjects;
    private final Executor executor;

    public GlobalViewModel(@NonNull Context context, Executor executor) {
        taskDataSource = new TaskRepository(context);
        projectDataSource = new ProjectRepository(context);
        allTasks = taskDataSource.getAllTasks();
        allProjects = projectDataSource.getAllProjects();
        this.executor = executor;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskDataSource.deleteTask(task));
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public TaskRepository getTaskDataSource() {
        return taskDataSource;
    }

    public void setTaskDataSource(TaskRepository taskDataSource) {
        this.taskDataSource = taskDataSource;
    }

    public void createTask() {
        Task task = new Task(1L,"Laver le linge",360);
        executor.execute(() -> taskDataSource.createTask(task));
    }
}
