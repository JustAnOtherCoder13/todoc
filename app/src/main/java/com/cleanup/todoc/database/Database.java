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

@androidx.room.Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public TaskDao taskDao;
    public ProjectDao projectDao;

    public static Database getInstance(Context context){
        if (INSTANCE==null){
            synchronized (Database.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,"TaskDatabase.db")
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

                ContentValues contentValues = new ContentValues();
                contentValues.put("id",1L);
                contentValues.put("name","Projet Tartampion");
                contentValues.put("color",0xFFEADAD1);
                db.insert("project_table", OnConflictStrategy.IGNORE,contentValues);

                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id",0);
                contentValues1.put("projectId",1L);
                contentValues1.put("name","laver les vitres");
                contentValues1.put("creationTimestamp",100);
                db.insert("task_table", OnConflictStrategy.IGNORE,contentValues1);
            }
        };
    }
}
