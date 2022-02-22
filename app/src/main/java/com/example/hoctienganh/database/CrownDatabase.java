package com.example.hoctienganh.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hoctienganh.Crown;

@Database(entities = {Crown.class}, version = 1, exportSchema = true)
public abstract class CrownDatabase extends RoomDatabase {

    private static final String CROWN_DATABASE_NAME = "crown";
    private static CrownDatabase instance;

    public static synchronized CrownDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CrownDatabase.class, CROWN_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CrownDAO crownDAO();
}
