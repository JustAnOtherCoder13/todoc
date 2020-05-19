package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.GlobalViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Mock
    Observer<List<Task>> taskObserver;
    @Mock
    Observer<List<Project>> projectObserver;

    private GlobalViewModel globalViewModel;

    private static List<Project> PROJECTS = Arrays.asList(
            new Project(1L,"Projet Tartampion",0xFFEADAD1),
            new Project(2L,"Projet Lucidia",0xFFB4CDBA),
            new Project(3L,"Projet Circus",0xFFA3CED2)
    );
    public static List<Project> generateProjects(){return new ArrayList<>(PROJECTS);
    }

    @Before
    public  void setUp(){
        MockitoAnnotations.initMocks(this);
        globalViewModel = mock(GlobalViewModel.class);
        when(globalViewModel.getAllProjects()).thenReturn((LiveData<List<Project>>) generateProjects());
       // globalViewModel.getAllTasks().observeForever(taskObserver);
        globalViewModel.getAllProjects();
    }



    @Test
    public void testMockito(){
        assertNotNull (globalViewModel.getAllProjects());
        assertTrue(globalViewModel.getAllTasks().hasObservers());
        assertTrue(globalViewModel.getAllProjects().hasObservers());

    }




}
