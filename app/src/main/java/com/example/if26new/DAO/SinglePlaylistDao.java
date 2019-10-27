package com.example.if26new.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.if26new.Model.SinglePlaylistModel;

@Dao
public interface SinglePlaylistDao {
    @Query("SELECT * FROM SinglePlaylistModel WHERE playlistId = :playlistId")
    SinglePlaylistModel[] getSinglesFromPlaylist(int playlistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSingle(SinglePlaylistModel single);

    @Update
    int updateSingle(SinglePlaylistModel single);

    @Query("DELETE FROM SinglePlaylistModel WHERE id = :singleId")
    int deleteSingle(int singleId);
}
