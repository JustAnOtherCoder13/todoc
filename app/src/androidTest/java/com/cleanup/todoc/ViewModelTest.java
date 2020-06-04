package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.AppViewModel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;

import static com.cleanup.todoc.database.Generator.generateProjects;
import static com.cleanup.todoc.database.Generator.generateTasks;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Observer<List<Project>> projectObserver;
    @Mock
    Observer<List<Task>> taskObserver;
    private AppViewModel appViewModel;
    private final Task TASK = generateTasks().get(0);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("TaskDatabase.db");
    }

    @AfterClass
    public static void afterClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("TaskDatabase.db");
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //Get context and executor to instantiate GlobalViewModel
        Context context = ApplicationProvider.getApplicationContext();
        Executor executor = new CurrentThreadExecutor();
        appViewModel = new AppViewModel(context, executor);
        //Init observers on tasks and projects
        appViewModel.getAllTasks().observeForever(taskObserver);
        appViewModel.getAllProjects().observeForever(projectObserver);
    }

    @Test
    public void a_projectsAndTasksShouldExistAndHaveObservers() {
        //Ensure live data exist
        assertNotNull(appViewModel.getAllProjects());
        assertNotNull(appViewModel.getAllTasks());
        //and have observer
        assertTrue(appViewModel.getAllTasks().hasObservers());
        assertTrue(appViewModel.getAllProjects().hasObservers());
    }

    @Test
    public void b_createTaskShouldAddTaskToList() {
        //Create a new task
        appViewModel.createTask(TASK);
        //Ensure it is add to task's list
        assertEquals(1, appViewModel.getAllTasks().getValue().size());
        Task task = appViewModel.getAllTasks().getValue().get(0);
        //And match with expected values
        assertEquals(TASK.getName(), task.getName());
        assertEquals(TASK.getProjectId(), task.getProjectId());
        assertEquals(TASK.getCreationTimestamp(), task.getCreationTimestamp());
    }

    @Test
    public void c_deleteTaskShouldRemoveTask() {
        //Get the first and only task an delete it
        Task taskToDelete = appViewModel.getAllTasks().getValue().get(0);
        appViewModel.deleteTask(taskToDelete);
        //Ensure task's list is now empty
        assertEquals(0, appViewModel.getAllTasks().getValue().size());
    }

    @Test
    public void d_getAllTasksShouldReturnTaskList() {
        //Add three tasks
        appViewModel.createTask(generateTasks().get(0));
        appViewModel.createTask(generateTasks().get(1));
        appViewModel.createTask(generateTasks().get(2));
        //Ensure list size is now three
        assertEquals(3, appViewModel.getAllTasks().getValue().size());
    }
    @Test
    public void getAllProjectsShouldReturnProjectList(){
        //Ensure the list of project contain 3 project
        assertEquals(3,appViewModel.getAllProjects().getValue().size());
        //And matches the corresponding values
        assertEquals(generateProjects().get(0).getName(),(appViewModel.getAllProjects().getValue().get(0).getName()));
        assertEquals(generateProjects().get(0).getId(),(appViewModel.getAllProjects().getValue().get(0).getId()));
        assertEquals(generateProjects().get(0).getColor(),(appViewModel.getAllProjects().getValue().get(0).getColor()));
        assertEquals(generateProjects().get(1).getName(),(appViewModel.getAllProjects().getValue().get(1).getName()));
        assertEquals(generateProjects().get(1).getId(),(appViewModel.getAllProjects().getValue().get(1).getId()));
        assertEquals(generateProjects().get(1).getColor(),(appViewModel.getAllProjects().getValue().get(1).getColor()));
        assertEquals(generateProjects().get(2).getName(),(appViewModel.getAllProjects().getValue().get(2).getName()));
        assertEquals(generateProjects().get(2).getId(),(appViewModel.getAllProjects().getValue().get(2).getId()));
        assertEquals(generateProjects().get(2).getColor(),(appViewModel.getAllProjects().getValue().get(2).getColor()));

    }
}

class CurrentThreadExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
