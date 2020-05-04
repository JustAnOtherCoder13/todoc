package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

public interface ProjectDao {

    @Query("SELECT*FROM project_table")
    LiveData<List<Project>> getAllProjects();
}
