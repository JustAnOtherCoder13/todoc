package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.GlobalViewModel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.cleanup.todoc.database.Generator.generateTasks;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
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
    private List<Task> taskList;

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
        globalViewModel.getAllTasks().observeForever(this::updateTask);
        globalViewModel.getAllProjects().observeForever(projectObserver);


    }
private void updateTask(List<Task> tasks){
        this.taskList = tasks;
}
    @Test
    public void projectsAndTasksShouldExistAndHaveObservers(){
        globalViewModel.getAllTasks().observeForever(taskObserver);
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
        globalViewModel.createTask(TASK);
        final int size = taskList.size();
        //globalViewModel.getAllTasks().observeForever(taskObserver);
        //verify(taskObserver).onChanged(taskList);
        do{
            globalViewModel.getAllTasks().getValue();
        }while (taskList.size() == size);

        assertEquals(1,globalViewModel.getAllTasks().getValue().size());

    }





}
