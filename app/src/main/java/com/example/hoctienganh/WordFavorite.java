package com.example.hoctienganh;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wordFavorite")
public class WordFavorite {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String word;
    private String mean;
    private int idResource;
    private boolean isLike;

    public WordFavorite(String username, String word, String mean, int idResource, boolean isLike) {
        this.username = username;
        this.word = word;
        this.mean = mean;
        this.idResource = idResource;
        this.isLike = isLike;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
