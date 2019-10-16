package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.if26new.Model.UserModel;

@Dao
public interface UserDao {
    //S'il y a un conflit on ecrase l'ancien.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(UserModel user);

    @Query("SELECT * FROM UserModel WHERE username = :userId")
    UserModel getUser(long userId);

    @Query("SELECT * FROM UserModel")
    public UserModel[] loadAllUsers();
}
