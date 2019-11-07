package com.example.if26new;

import android.media.MediaPlayer;

public class MediaControllerAudio {
    private static MediaControllerAudio INSTANCE;
    public static MediaControllerAudio getInstance()
    {   if (INSTANCE == null) {
            INSTANCE = new MediaControllerAudio();
    }
        return INSTANCE;
    }

    private MediaPlayer mediaPlayerAudio;
    private String songName;
    private String ArtistName;
    private int AlbumID;
    private boolean isArtist=false;

    public boolean isArtist() {
        return isArtist;
    }

    public void setArtist(boolean artist) {
        isArtist = artist;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public MediaPlayer getMediaPlayerAudio() {
        return mediaPlayerAudio;
    }

    public void setMediaPlayerAudio(MediaPlayer mediaPlayerAudio) {
        this.mediaPlayerAudio = mediaPlayerAudio;
    }
    public void freeMediaCOntroller(){
        this.mediaPlayerAudio.stop();
    }

}
