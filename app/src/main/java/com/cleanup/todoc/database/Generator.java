package com.cleanup.todoc.database;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Generator {

    private static List<Project> PROJECTS = Arrays.asList(
            new Project(1L,"Projet Tartampion",0xFFEADAD1),
            new Project(2L,"Projet Lucidia",0xFFB4CDBA),
            new Project(3L,"Projet Circus",0xFFA3CED2)
    );
    public static List<Project> generateProjects(){return new ArrayList<>(PROJECTS);
    }

    private static List<Task> TASKS = Arrays.asList(
            new Task(1L,"Laver le linge",360),
            new Task(2L,"Faire la vaisselle",340),
            new Task(3L,"Faire les lits",1234)
    );
    public static List<Task> generateTasks(){return new ArrayList<>(TASKS);
    }

}
