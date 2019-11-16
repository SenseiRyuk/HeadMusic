package com.example.if26new.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = UserModel.class,
                parentColumns = "id",
                childColumns = "userId"),
        @ForeignKey(entity = ArtistModel.class,
                parentColumns = "id",
                childColumns = "albumId")
})
public class LikeAlbumModel {
    @PrimaryKey (autoGenerate = true)
    int id;
    int userId;
    int albumId;

    public LikeAlbumModel(){}

    public LikeAlbumModel(int userId, int albumId) {
        this.userId = userId;
        this.albumId = albumId;
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

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
