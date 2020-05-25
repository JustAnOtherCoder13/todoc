package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.cleanup.todoc.database.Generator.generateProjects;
import static com.cleanup.todoc.database.Generator.generateTasks;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TaskDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static long TASK_PROJECT_ID = generateTasks().get(0).getProjectId();
    private static long CREATION_TIMESTAMP = generateTasks().get(0).getCreationTimestamp();
    private static String NAME = generateTasks().get(0).getName();
    private static String PROJECT_NAME = generateProjects().get(0).getName();
    private static int PROJECT_COLOR = generateProjects().get(0).getColor();
    private static long PROJECT_ID = generateProjects().get(0).getId();

    //to create project demo and task demo in database
    private void createTaskAndProjectInDb() {
        this.database.projectDao().insertProject(generateProjects().get(0));
        this.database.taskDao().insertTask(generateTasks().get(0));
    }

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        this.database = Room.inMemoryDatabaseBuilder(context, TaskDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        //Assert that tasks list is empty in the database
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void getProjectsWhenNoProjectInserted() throws InterruptedException {
        //Assert that projects list is empty in the database
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }

    @Test
    public void createAndGetProject() throws InterruptedException {
        //Create project and task
        createTaskAndProjectInDb();
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        //Get the first project in project's list
        Project projectAdded = projects.get(0);
        //Assert project added match to static values
        assertTrue(projectAdded.getId() == TASK_PROJECT_ID
                && projectAdded.getName().equals(PROJECT_NAME)
                && projectAdded.getColor() == PROJECT_COLOR);
    }

    @Test
    public void createAndGetTask() throws InterruptedException {
        //Create project and task
        createTaskAndProjectInDb();
        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getAllTasks());
        //Get the first task in task's list
        Task taskAdded = tasks.get(0);
        //Assert task added match to static values
        assertTrue(taskAdded.getProjectId() == PROJECT_ID && taskAdded.getName().equals(NAME)
                && taskAdded.getCreationTimestamp() == CREATION_TIMESTAMP);
    }

    @Test
    public void createAndDeleteTask() throws InterruptedException {
        //Create project and task
        createTaskAndProjectInDb();
        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getAllTasks());
        //Assert task's list is not empty
        assertFalse(tasks.isEmpty());
        //Get the first task and delete it
        Task taskAdded = tasks.get(0);
        this.database.taskDao().deleteTask(taskAdded);
        tasks = LiveDataTestUtil.getValue(database.taskDao().getAllTasks());
        //Assert task's list is now empty
        assertTrue(tasks.isEmpty());
    }

}
