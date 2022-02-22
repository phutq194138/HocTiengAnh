package com.example.hoctienganh.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hoctienganh.WordFavorite;

@Database(entities = {WordFavorite.class}, version = 1, exportSchema = true)
public abstract class WordFavoriteDatabase extends RoomDatabase {

    private static final String WORD_DATABASE_NAME = "wordFavorite";
    private static WordFavoriteDatabase instance;

    public static synchronized WordFavoriteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), WordFavoriteDatabase.class, WORD_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract WordFavoriteDAO wordFavoriteDAO();
}
