package com.example.hoctienganh.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hoctienganh.Test;

@Database(entities = {Test.class}, version = 1, exportSchema = true)
public abstract class TestDatabase extends RoomDatabase{

    private static final String TEST_DATABASE_NAME = "test";
    private static TestDatabase instance;

    public static synchronized TestDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TestDatabase.class, TEST_DATABASE_NAME)
            .allowMainThreadQueries()
            .build();
        }
        return instance;
    }

    public abstract TestDAO testDAO();

}
