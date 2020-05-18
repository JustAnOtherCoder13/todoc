package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.cleanup.todoc.ProjectStaticList.generateProjects;
import static com.cleanup.todoc.ui.MainActivity.allProjects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
//TODO use mockito here?

public class TaskUnitTest {
    private List<Project> projects;
    private long projectId1;
    private long projectId2;
    private long projectId3;

//TODO How to do else
    @Before
    public void initDb(){projects = generateProjects();
    projectId1 = projects.get(0).getId();
    projectId2 = projects.get(1).getId();
    projectId3 = projects.get(2).getId();
    }
    @Test
    public void test_projects() {
        final Task task1 = new Task( projectId1, "task 1", new Date().getTime());
        final Task task2 = new Task( projectId2, "task 2", new Date().getTime());
        final Task task3 = new Task( projectId3, "task 3", new Date().getTime());
        final Task task4 = new Task( 4, "task 4", new Date().getTime());

        assertEquals(projectId1, task1.getProjectId());
        assertEquals(projectId2, task2.getProjectId());
        assertEquals(projectId3, task3.getProjectId());
        assertFalse(task4.getProjectId()==projectId3||task4.getProjectId()==projectId2||
                            task4.getProjectId()==projectId1);
        //assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}