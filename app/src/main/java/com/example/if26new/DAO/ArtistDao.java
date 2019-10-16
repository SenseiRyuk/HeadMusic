package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.ArtistModel;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM ArtistModel")
    List<ArtistModel> getAllArtist();

    @Query("SELECT * FROM ArtistModel WHERE userId = :userId")
    List<ArtistModel> getArtistFromUser(int userId);

    @Query("SELECT * FROM ArtistModel WHERE topArtist = :topArtist")
    ArtistModel getTop(int topArtist);

    @Insert
    long insertArtist(ArtistModel artist);

    @Update
    int updateArtist(ArtistModel artist);

    @Query("DELETE FROM ArtistModel WHERE id = :artistId")
    int deleteArtist(int artistId);
}
