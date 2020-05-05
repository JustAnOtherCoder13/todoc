package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT*FROM task_table")
    LiveData<List<Task>> getAllTasks();

   @Delete
   void deleteTask (Task task);

    @Insert
    void insertTask(Task task);
}
