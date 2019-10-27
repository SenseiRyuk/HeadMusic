package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.os.Bundle;

@Entity(foreignKeys = {
        @ForeignKey(entity = UserModel.class,
                parentColumns = "id",
                childColumns = "userId"),
        @ForeignKey(entity = ArtistModel.class,
                parentColumns = "id",
                childColumns = "artistId")
        })

public class AlbumModel{
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "userId")
    private int userId;
    @ColumnInfo(name = "artistId")
    private int artistId;
    private String titleAlbum;
    private int isNew;
    private int image;
    @ColumnInfo(name = "isLike")
    private boolean isLike;

public AlbumModel(){}
    public AlbumModel(int id, int userId, int artistId, String titleAlbum, int isNew, int image, boolean isLike) {
        this.id = id;
        this.userId = userId;
        this.artistId = artistId;
        this.titleAlbum = titleAlbum;
        this.isNew = isNew;
        this.image = image;
        this.isLike=isLike;
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

    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitleAlbum() {
        return titleAlbum;
    }
    public void setTitleAlbum(String titleAlbum) {
        this.titleAlbum = titleAlbum;
    }

    public int getIsNew() {
        return isNew;
    }
    public void setIsNew(int isNew) {
        this.isNew = isNew;
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
}
