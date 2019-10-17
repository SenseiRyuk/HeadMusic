package com.example.if26new.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.SingleModel;

import java.util.List;

@Dao
public interface SingleDao {
    @Query("SELECT * FROM SingleModel WHERE albumId = :albumId")
    SingleModel[] getSingleFromAlbum(int albumId);

    @Query("SELECT * FROM SingleModel WHERE artistId = :artistId")
    SingleModel[] getSingleFromArtist(int artistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSingle(SingleModel single);

    @Update
    int updateSingle(SingleModel single);

    @Query("DELETE FROM SingleModel WHERE id = :singleId")
    int deleteSingle(int singleId);

}


