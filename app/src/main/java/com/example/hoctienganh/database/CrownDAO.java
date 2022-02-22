package com.example.hoctienganh.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hoctienganh.Crown;

import java.util.List;

@Dao
public interface CrownDAO {

    @Insert
    public void insertCrown(Crown crown);

    @Query("select * from crown where username like :username")
    public List<Crown> getListCrown(String username);
}
