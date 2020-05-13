package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Application application){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        projectDao = taskDatabase.projectDao();
        allProjects = projectDao.getAllProjects();
    }
    public LiveData<List<Project>> getAllProjects(){return allProjects;}
}
