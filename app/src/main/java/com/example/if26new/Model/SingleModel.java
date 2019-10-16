package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.os.Bundle;

@Entity(foreignKeys = {
        @ForeignKey(entity = AlbumModel.class,
                parentColumns = "id",
                childColumns = "albumId"),
        @ForeignKey(entity = ArtistModel.class,
                parentColumns = "id",
                childColumns = "artistId")
})

public class SingleModel extends AppCompatActivity {
    @PrimaryKey
    private int id;
    private int albumId;
    private int artistId;
    private String titleSingle;
    private boolean isNew;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitleSingle() {
        return titleSingle;
    }

    public void setTitleSingle(String titleSingle) {
        this.titleSingle = titleSingle;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
