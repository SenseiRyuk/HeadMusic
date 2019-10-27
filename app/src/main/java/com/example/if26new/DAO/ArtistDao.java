package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.ArtistModel;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM ArtistModel")
    ArtistModel[] getAllArtist();

    @Query("SELECT * FROM ArtistModel WHERE userId = :userId")
    ArtistModel[] getArtistFromUser(int userId);

    @Query("SELECT * FROM ArtistModel WHERE topArtist = :topArtist")
    ArtistModel getTop(int topArtist);

    @Query("SELECT * FROM ArtistModel WHERE id = :id")
    ArtistModel getArtistFromId(int id);

    @Query("SELECT * FROM ArtistModel WHERE name = :name")
    ArtistModel getArtistFromName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertArtist(ArtistModel artist);

    @Update
    int updateArtist(ArtistModel artist);

    @Query("DELETE FROM ArtistModel WHERE id = :artistId")
    int deleteArtist(int artistId);

    @Query("UPDATE ArtistModel SET isLike = :islike WHERE id =:id")
    void updateLike(boolean islike, int id);

    @Query("SELECT * FROM ArtistModel WHERE isLike = :islike")
    ArtistModel[] getLikedArtist(boolean islike);
}
