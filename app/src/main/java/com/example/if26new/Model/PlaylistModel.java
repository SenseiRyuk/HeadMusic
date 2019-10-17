package com.example.if26new.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=UserModel.class,
parentColumns = "id",
childColumns = "userId"))

public class PlaylistModel {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "userId")
    private int userId;
    private String titles;


    public PlaylistModel(){}
    public PlaylistModel(int id, int userId, String titles) {
        this.id = id;
        this.userId = userId;
        this.titles = titles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }
}