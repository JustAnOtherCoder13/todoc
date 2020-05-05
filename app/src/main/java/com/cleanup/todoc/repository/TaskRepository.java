package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository (Application application){
        Database database = Database.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){ return allTasks;}

    public void deleteTask(Task task){this.taskDao.deleteTask(task);}

    public void createTask(Task task){this.taskDao.insertTask(task);}
}
