package com.example.if26new.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.LikeAlbumModel;


public interface LikeAlbumDao {
    @Query("SELECT * FROM LikeAlbumModel WHERE userId = :userId")
    LikeAlbumModel[] getLikeFromUser(int userId);

    @Query("SELECT * FROM LikeAlbumModel WHERE id = :id")
    LikeAlbumModel getLikeFromId(int id);

    @Query("SELECT * FROM LikeAlbumModel WHERE albumId = :albumId")
    LikeAlbumModel[] getLikeFromAlbum(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertLike(LikeAlbumModel like);

    @Update
    int updateLike(LikeAlbumModel like);

    @Query("DELETE FROM LikeAlbumModel WHERE id = :likeId")
    int deleteLike(int likeId);
}
