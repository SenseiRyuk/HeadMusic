package com.example.if26new.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.if26new.Model.AlbumModelDeezer;

@Dao
public interface AlbumDeezerDao {


    @Query("SELECT * FROM AlbumModelDeezer")
    AlbumModelDeezer[] getAllAlbum();

    @Query("SELECT * FROM AlbumModelDeezer WHERE titleAlbum =:title")
    AlbumModelDeezer getAlbumFromName(String title);


    @Query("SELECT * FROM AlbumModelDeezer WHERE id =:id")
    AlbumModelDeezer getAlbumFromId(int id);

    @Query("UPDATE AlbumModelDeezer SET isLike = :islike WHERE id =:id")
    void updateLike(boolean islike, int id);

    @Query("DELETE FROM AlbumModelDeezer WHERE id = :albumId")
    int deleteAlbum(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlbum(AlbumModelDeezer albumModelDeezer);
}
