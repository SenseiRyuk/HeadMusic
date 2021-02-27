package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.os.Bundle;

@Entity(foreignKeys =
        @ForeignKey(entity = UserModel.class,
                parentColumns = "id",
                childColumns = "userId"))

public class ArtistModel {
    @PrimaryKey
    private int id;
    private int userId;
    private String name;
    private int topArtist;
    private int bio;
    private int image;
    private boolean isLike;
    private String deezerID;
    private boolean isDeezer=false;
    private String imageDeezer;
    private String numberOfFans="50";

public ArtistModel(){}
    public ArtistModel(int id, int userId, String name, int topArtist, int bio, int image, boolean isLike) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.topArtist = topArtist;
        this.bio = bio;
        this.image = image;
        this.isLike=isLike;
        this.isDeezer=false;
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

    public boolean isLike() {
        return isLike;
    }
    public void setLike(boolean like) {
        isLike = like;
    }


    public String getDeezerID() {
        return deezerID;
    }

    public void setDeezerID(String deezerID) {
        this.deezerID = deezerID;
    }

    public boolean getDeezer() {
        return isDeezer;
    }

    public void setDeezer(Boolean deezer) {
        isDeezer = deezer;
    }

    public String getImageDeezer() {
        return imageDeezer;
    }

    public void setImageDeezer(String imageDeezer) {
        this.imageDeezer = imageDeezer;
    }


    public String getNumberOfFans() {
        return numberOfFans;
    }

    public void setNumberOfFans(String numberOfFans) {
        this.numberOfFans = numberOfFans;
    }


}
