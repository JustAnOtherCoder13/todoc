package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.GlobalViewModel;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.cleanup.todoc.database.Generator.generateTasks;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Mock
    Observer<List<Task>> taskObserver;

    @Mock
    Observer<List<Project>> projectObserver;

    private GlobalViewModel globalViewModel;
    private final Task TASK = generateTasks().get(0);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("TaskDatabase.db");
    }
    @AfterClass
    public static void afterClass(){
        ApplicationProvider.getApplicationContext().deleteDatabase("TaskDatabase.db");
    }

    @Before
    public  void setUp(){
        MockitoAnnotations.initMocks(this);
        //get context and executor to instantiate GlobalViewModel
        Context context = ApplicationProvider.getApplicationContext();
        Executor executor = Executors.newSingleThreadExecutor();
        globalViewModel = new GlobalViewModel(context,executor);
        //init observers on tasks and projects
        globalViewModel.getAllTasks().observeForever(taskObserver);
        globalViewModel.getAllProjects().observeForever(projectObserver);
    }

    @Test
    public void projectsAndTasksShouldExistAndHaveObservers(){
        //ensure live data exist
        assertNotNull (globalViewModel.getAllProjects());
        assertNotNull (globalViewModel.getAllTasks());
        //have observer and correspond to the expected size
        assertTrue(globalViewModel.getAllTasks().hasObservers());
        assertEquals(0, globalViewModel.getAllTasks().getValue().size());
        assertTrue(globalViewModel.getAllProjects().hasObservers());
        assertEquals(3, globalViewModel.getAllProjects().getValue().size());
    }
    @Test
    public void testDb() throws InterruptedException {
        globalViewModel.getAllTasks().observeForever(taskObserver);
        globalViewModel.createTask(TASK);
        List<Task> tasks = LiveDataTestUtil.getValue(globalViewModel.getAllTasks());
        verify(globalViewModel).createTask(TASK);
        //assertEquals(1,globalViewModel.getAllTasks().getValue().size());

    }




}
