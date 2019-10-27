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

public class SingleModel {
    @PrimaryKey
    private int id;
    private int albumId;
    private int artistId;
    private String titleSingle;
    private boolean isNew;
    private int music;
    private int video;

    public SingleModel(){};

    public SingleModel(int id, int albumId, int artistId, String titleSingle, boolean isNew, int music, int video) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.titleSingle = titleSingle;
        this.isNew = isNew;
        this.music = music;
        this.video = video;
    }

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

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

}
