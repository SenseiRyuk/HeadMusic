package com.example.if26new.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.LikeArtistModel;


@Dao
public interface LikeArtistDao {
    @Query("SELECT * FROM LikeArtistModel WHERE userId = :userId")
    LikeArtistModel[] getLikeFromUser(int userId);

    @Query("SELECT * FROM LikeArtistModel")
    LikeArtistModel[] getAllLike();

    @Query("SELECT * FROM LikeArtistModel WHERE id = :id")
    LikeArtistModel getLikeFromId(int id);

    @Query("SELECT * FROM LikeArtistModel WHERE artistId = :artistId")
    LikeArtistModel[] getLikeFromArtist(int artistId);

    @Query("SELECT * FROM LikeArtistModel WHERE userId = :userId AND artistId = :artistId")
    LikeArtistModel getLikeFromArtistAndUser(int userId, int artistId);

    @Query("SELECT * FROM LikeArtistModel WHERE userId = :userId AND artistId = :artistId")
    boolean getLikeFromArtistAndUserExist(int userId, int artistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertLike(LikeArtistModel like);

    @Update
    int updateLike(LikeArtistModel like);

    @Query("DELETE FROM LikeArtistModel WHERE id = :likeId")
    int deleteLike(int likeId);
}
