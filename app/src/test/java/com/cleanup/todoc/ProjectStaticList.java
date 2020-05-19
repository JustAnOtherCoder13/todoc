package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ProjectStaticList {

    private static List<Project> PROJECTS = Arrays.asList(
    new Project(1L,"Projet Tartampion",0xFFEADAD1),
    new Project(2L,"Projet Lucidia",0xFFB4CDBA),
    new Project(3L,"Projet Circus",0xFFA3CED2)
    );
    public static List<Project> generateProjects(){return new ArrayList<>(PROJECTS);
    }
}
