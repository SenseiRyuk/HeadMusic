package com.example.if26new.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=PlaylistModel.class, parentColumns = "id", childColumns = "playlistId", onDelete = ForeignKey.CASCADE))
public class SinglePlaylistModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int playlistId;
    private String songName;
    private String artistName;

    public SinglePlaylistModel(){}
    public SinglePlaylistModel(int playlistId,String songName, String artistName){
        this.playlistId=playlistId;
        this.songName=songName;
        this.artistName=artistName;
    }

    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}
