package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;

import java.util.List;

@Dao
public interface AlbumDao {

    /*@Query("SELECT * FROM AlbumModel WHERE userId = :userId")
    AlbumModel[] getAlbumFromUser(int userId);*/

    @Query("SELECT * FROM AlbumModel WHERE artistId = :artistId")
    AlbumModel[] getAlbumFromArtist(int artistId);

    @Query("SELECT * FROM AlbumModel")
    AlbumModel[] getAllAlbum();

    @Query("SELECT * FROM AlbumModel WHERE titleAlbum =:title")
    AlbumModel getAlbumFromName(String title);

    @Query("SELECT * FROM AlbumModel WHERE isNew = 1")
    AlbumModel[] getAlbumFromNew();

    @Query("SELECT * FROM AlbumModel WHERE id =:id")
    AlbumModel getAlbumFromId(int id);

    @Query("UPDATE AlbumModel SET isLike = :islike WHERE id =:id")
    void updateLike(boolean islike, int id);

    @Query("DELETE FROM AlbumModel WHERE id = :albumId")
    int deleteAlbum(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlbum(AlbumModel albumModel);

//    @Query("SELECT * FROM AlbumModel WHERE isLike = :islike")
//    AlbumModel[] getLikedAlbum(boolean islike);
}
