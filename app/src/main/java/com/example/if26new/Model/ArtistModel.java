package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.os.Bundle;

@Entity(foreignKeys =
        @ForeignKey(entity = UserModel.class,
                parentColumns = "id",
                childColumns = "userID"))

public class ArtistModel extends AppCompatActivity {
    @PrimaryKey
    private int id;
    private int userId;
    private String name;
    private int topArtist;
    private int bio;
    private int image;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTopArtist() {
        return topArtist;
    }

    public void setTopArtist(int topArtist) {
        this.topArtist = topArtist;
    }

    public int getBio() {
        return bio;
    }

    public void setBio(int bio) {
        this.bio = bio;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
