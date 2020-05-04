package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository (TaskDao taskDao){ this.taskDao = taskDao; }

    public LiveData<List<Task>> getAllTasks(){ return this.taskDao.getAllTasks();}

    public void deleteTask(Task task){this.taskDao.deleteTask(task);}

    public void createTask(Task task){this.taskDao.insertTask(task);}
}
