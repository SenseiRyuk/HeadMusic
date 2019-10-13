package com.example.if26new;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Query("SELECT * FROM PlaylistModel WHERE userID = :userId")
    LiveData<List<PlaylistModel>> getPlaylist(int userId);

    @Insert
    long insertPlaylist(PlaylistModel playlist);

    @Update
    int updatePlaylist(PlaylistModel playlist);

    @Query("DELETE FROM PlaylistModel WHERE id = :playlistId")
    int deletePlaylist(int playlistId);
}
