package com.example.hoctienganh.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hoctienganh.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public void insertUser(User... users);

    @Query("select * from user")
    public List<User> getListUser();

    @Query("select * from user where username like :username")
    public List<User> getListUser(String username);

    @Update
    public void updateUser(User... users);
}
