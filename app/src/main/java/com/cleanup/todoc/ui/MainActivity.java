package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.di.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    //TODO is it right way to do?
    public static List<Project> allProjects = Collections.emptyList();
    private ArrayList<Task> mTasks = new ArrayList<>();
    private  TasksAdapter adapter;
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;
    @Nullable
    public AlertDialog dialog = null;
    @Nullable
    private EditText dialogEditText = null;
    @Nullable
    private Spinner dialogSpinner = null;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;
    private TaskViewModel taskViewModel;
    private int tasksSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        this.configureViewModel();
        this.initRecyclerView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());
    }

    private void initRecyclerView() {
        adapter = new TasksAdapter(mTasks, this);
        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
        updateTasks();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        taskViewModel.deleteTask(task);
        updateTasks();
    }

    private void configureViewModel(){
        ViewModelFactory viewModelFactory = DI.provideModelFactory(this.getApplication());
        this.taskViewModel = new ViewModelProvider(this,viewModelFactory).get(TaskViewModel.class);
        this.taskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.updateTasks(tasks);
            mTasks =(ArrayList<Task>) taskViewModel.getAllTasks().getValue();
            tasksSize = tasks.size();
            assert mTasks != null;
            updateTasks();
        });
        this.taskViewModel.getAllProjects().observe(this, projects -> allProjects = taskViewModel.getAllProjects().getValue());
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {

        if (dialogEditText != null && dialogSpinner != null) {
            String taskName = dialogEditText.getText().toString();
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            else if (taskProject != null) {
                Task task = new Task(
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );
                addTask(task);
                dialogInterface.dismiss();
            }
            else{
                dialogInterface.dismiss();
            }
        }
        else {
            dialogInterface.dismiss();
        }
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();
        dialog.show();
        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);
        populateDialogSpinner();
    }

    private void addTask(@NonNull Task task) {
        taskViewModel.createTask(task);
        updateTasks();
    }

    private void updateTasks() {
        if (tasksSize == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(mTasks, new Task.TaskAZComparator());
                    adapter.updateTasks(mTasks);
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(mTasks, new Task.TaskZAComparator());
                    adapter.updateTasks(mTasks);
                    break;
                case RECENT_FIRST:
                    Collections.sort(mTasks, new Task.TaskRecentComparator());
                    adapter.updateTasks(mTasks);
                    break;
                case OLD_FIRST:
                    Collections.sort(mTasks, new Task.TaskOldComparator());
                    adapter.updateTasks(mTasks);
                    break;
            }
        }
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });
        return dialog;
    }

    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    private enum SortMethod {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        RECENT_FIRST,
        OLD_FIRST,
        NONE
    }
}
