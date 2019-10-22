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
                childColumns = "artistId")
})
public class LikeArtistModel {
    @PrimaryKey
    int id;
    int userId;
    int artistId;

    public LikeArtistModel(){}

    public LikeArtistModel(int id, int userId, int artistId) {
        this.id = id;
        this.userId = userId;
        this.artistId = artistId;
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
}
