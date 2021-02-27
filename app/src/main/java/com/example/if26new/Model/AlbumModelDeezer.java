package com.example.if26new.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys =
@ForeignKey(entity = UserModel.class,
        parentColumns = "id",
        childColumns = "userId"))

public class AlbumModelDeezer {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "userId")
    private int userId;
    private String titleAlbum;
    private String albumImageDeezer;
    private String artistName;
    @ColumnInfo(name = "isLike")
    private boolean isLike;

    public AlbumModelDeezer(){}
    public AlbumModelDeezer(int id, int userId, String titleAlbum, String albumImageDeezer,String artistName) {
        this.id = id;
        this.userId = userId;
        this.titleAlbum = titleAlbum;
        this.albumImageDeezer=albumImageDeezer;
        this.artistName=artistName;
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

    public String getTitleAlbum() {
        return titleAlbum;
    }
    public void setTitleAlbum(String titleAlbum) {
        this.titleAlbum = titleAlbum;
    }


    public boolean isLike() {
        return isLike;
    }
    public void setLike(boolean like) {
        isLike = like;
    }

    public String getAlbumImageDeezer() {
        return albumImageDeezer;
    }

    public void setAlbumImageDeezer(String albumImageDeezer) {
        this.albumImageDeezer = albumImageDeezer;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

}
