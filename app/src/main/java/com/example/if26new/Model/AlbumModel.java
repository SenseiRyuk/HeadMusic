package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
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

public class AlbumModel extends AppCompatActivity {
    @PrimaryKey
    private int id;
    private int userId;
    private int artistId;
    private String titleAlbum;
    private boolean isNew;


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

    public void setTitleAlbum(String title) {
        this.titleAlbum = title;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
