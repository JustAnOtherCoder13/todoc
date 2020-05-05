package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Application application){
        Database database = Database.getInstance(application);
        projectDao = database.projectDao();
        allProjects = projectDao.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects(){return allProjects;}
}
