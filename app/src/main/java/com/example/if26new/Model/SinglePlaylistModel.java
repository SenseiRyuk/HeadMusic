package com.example.if26new.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(foreignKeys = @ForeignKey(entity=PlaylistModel.class, parentColumns = "id", childColumns = "playlistId", onDelete = ForeignKey.CASCADE))
public class SinglePlaylistModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int playlistId;
    private String songName;
    private String artistName;
    private String songID;
    private String TrackDuration;
    private String albumImage;

    @ColumnInfo(name = "nameAllMusicInAlbumDeezer", index = true) //just add index = true
    private boolean isForDeezer=false;

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


    public boolean isForDeezer() {
        return isForDeezer;
    }

    public void setForDeezer(boolean forDeezer) {
        isForDeezer = forDeezer;
    }

    public String getTrackDuration() {
        return TrackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        TrackDuration = trackDuration;
    }



    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }


    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

}
