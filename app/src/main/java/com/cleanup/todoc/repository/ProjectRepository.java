package com.cleanup.todoc.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Context context) {
        TaskDatabase taskDatabase = TaskDatabase.getInstance(context);
        ProjectDao projectDao = taskDatabase.projectDao();
        allProjects = projectDao.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}
