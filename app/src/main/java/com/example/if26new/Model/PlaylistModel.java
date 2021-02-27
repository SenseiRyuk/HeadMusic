package com.example.if26new.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=UserModel.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))

public class PlaylistModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String titles;
    private int image;
    private String imageDeezer;

    public PlaylistModel(){}
    public PlaylistModel(int userId,String title, int imageButton){
        this.userId=userId;
        this.titles=title;
        this.image=imageButton;
        imageDeezer=null;
    }

    public String getImageDeezer() {
        return imageDeezer;
    }
    public void setImageDeezer(String imageDeezer) {
        this.imageDeezer = imageDeezer;
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

    public int getImage() {
        return image;
    }
    public void setImage(int imageButton) {
        image = imageButton;
    }


}