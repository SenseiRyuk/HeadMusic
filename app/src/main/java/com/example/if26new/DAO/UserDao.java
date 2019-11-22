package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.UserModel;

@Dao
public interface UserDao {
    //S'il y a un conflit on ecrase l'ancien.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(UserModel user);

    @Query("SELECT * FROM UserModel WHERE id = :userId")
    UserModel getUserFromId(int userId);

    @Query("SELECT * FROM UserModel WHERE username = :username")
    UserModel getUserFromUsername(String username);

    @Query("SELECT * FROM UserModel")
    UserModel[] loadAllUsers();

    @Query("UPDATE UserModel SET avatar=:avatar  WHERE id=:id")
    int UpdateUser(int avatar, int id);

    @Query("UPDATE UserModel SET startColorGradient=:startColorGradient, endColorGradient=:endColorGradient, buttonColor=:buttonColor  WHERE id=:id")
    int UpdateUserColor(int startColorGradient,int endColorGradient,int buttonColor, int id);

}
