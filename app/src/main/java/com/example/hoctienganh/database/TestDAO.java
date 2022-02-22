package com.example.hoctienganh.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hoctienganh.Test;

import java.util.List;

@Dao
public interface TestDAO {

    @Insert
    public void insertTest(Test test);

    @Query("select * from test where username like :username")
    public List<Test> getListTest(String username);
}
