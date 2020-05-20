package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import static com.cleanup.todoc.database.Generator.generateProjects;

@androidx.room.Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    private static volatile TaskDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static TaskDatabase getInstance(Context context){
        if (INSTANCE==null){
            synchronized (TaskDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class,"TaskDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                createProject(0, db);
                createProject(1, db);
                createProject(2, db);
            }
        };
    }

    private static void createProject(int index, SupportSQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", generateProjects().get(index).getId());
        contentValues.put("name", generateProjects().get(index).getName());
        contentValues.put("color", generateProjects().get(index).getColor());
        db.insert("project_table", OnConflictStrategy.IGNORE, contentValues);
    }
}
