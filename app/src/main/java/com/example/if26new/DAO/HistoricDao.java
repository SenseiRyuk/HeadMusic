package com.example.if26new.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.if26new.Model.HistoricModel;

@Dao
public interface HistoricDao {
    @Query("SELECT * FROM HistoricModel")
    HistoricModel[] getAllTrackHistoric();

    @Query("DELETE FROM HistoricModel WHERE id = :albumId")
    int deleteHistoric(int albumId);

    @Query("SELECT * FROM PlaylistModel WHERE userId = :userId ")
    HistoricModel[] getHistoricFromUser(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTrackInHistoric(HistoricModel historicModel);
}
