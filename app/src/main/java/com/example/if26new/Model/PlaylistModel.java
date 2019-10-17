package com.example.if26new.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=UserModel.class,
parentColumns = "id",
childColumns = "userId"))

public class PlaylistModel {
    @PrimaryKey
    private int id;
    private int userId;
    private String titles;
    private int imageButton;

    public PlaylistModel(){}
    public PlaylistModel(int id, int userId,String title, int imageButton){
        this.id=id;
        this.userId=userId;
        this.titles=title;
        this.imageButton=imageButton;
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

    public int getImageButton() {
        return imageButton;
    }

    public void setImageButton(int imageButton) {
        imageButton = imageButton;
    }
}