package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.ConcertModel;

import java.util.List;

@Dao
public interface ConcertDao {

    @Query("SELECT * FROM ConcertModel WHERE artistId = :artistId")
    List<ConcertModel> getConcertFromArtist(int artistId);

    @Insert
    long insertConcert(ConcertModel concert);

    @Update
    int updateConcert(ConcertModel concert);

    @Query("DELETE FROM ConcertModel WHERE id = :concertId")
    int deleteConcert(int concertId);
}
