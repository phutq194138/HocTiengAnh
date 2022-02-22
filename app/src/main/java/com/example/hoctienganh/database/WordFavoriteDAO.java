package com.example.hoctienganh.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hoctienganh.WordFavorite;

import java.util.List;

@Dao
public interface WordFavoriteDAO {
    @Insert
    public void insertWord(WordFavorite wordFavorite);

    @Query("select * from wordFavorite where username like :username")
    public List<WordFavorite> getListWord(String username);

    @Delete
    public void deleteWord(WordFavorite wordFavorite);
}
