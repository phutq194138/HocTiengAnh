package com.example.hoctienganh.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hoctienganh.User;

@Database(entities = {User.class}, version = 1, exportSchema = true)
public abstract class UserDatabase extends RoomDatabase {

    private static final String USER_DATABASE_NAME = "user";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, USER_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
