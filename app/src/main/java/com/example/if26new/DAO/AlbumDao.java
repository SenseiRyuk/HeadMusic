package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.if26new.Model.AlbumModel;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM AlbumModel WHERE userID = :userId")
    List<AlbumModel> getAlbumFromUser(int userId);

    @Query("SELECT * FROM AlbumModel WHERE artistId = :artistId")
    List<AlbumModel> getAlbumFromArtist(int artistId);

    @Insert
    long insertAlbum(AlbumModel album);

    @Update
    int updateAlbum(AlbumModel album);

    @Query("DELETE FROM AlbumModel WHERE id = :albumId")
    int deleteAlbum(int albumId);
}
