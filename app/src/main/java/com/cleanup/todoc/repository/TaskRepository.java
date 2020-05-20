package com.cleanup.todoc.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository (Context context){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(context);
        taskDao = taskDatabase.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){ return allTasks;}

    public void deleteTask(Task task){this.taskDao.deleteTask(task);}

    public void createTask(Task task){this.taskDao.insertTask(task);}
}
