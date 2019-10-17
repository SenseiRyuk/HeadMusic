package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.PlaylistModel;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Query("SELECT * FROM PlaylistModel WHERE userID = :userId")
    PlaylistModel[] getPlaylist(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPlaylist(PlaylistModel playlist);

    @Update
    int updatePlaylist(PlaylistModel playlist);

    @Query("DELETE FROM PlaylistModel WHERE id = :playlistId")
    int deletePlaylist(int playlistId);
}
