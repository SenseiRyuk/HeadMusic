package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Query("SELECT * FROM PlaylistModel WHERE titles = :name")
    PlaylistModel getPlaylist(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPlaylist(PlaylistModel playlist);

    @Update
    int updatePlaylist(PlaylistModel playlist);

    @Query("DELETE FROM PlaylistModel WHERE id = :playlistId")
    int deletePlaylist(int playlistId);

    @Query("SELECT * FROM PlaylistModel")
    public PlaylistModel[] loadAllPlaylist();
}
