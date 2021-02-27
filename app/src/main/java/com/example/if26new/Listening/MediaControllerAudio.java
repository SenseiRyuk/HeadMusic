package com.example.if26new.Listening;

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
    private String songID;
    private String ArtistName;
    private int AlbumID;
    private boolean isArtist=false;
    private boolean isDeezerMusic=false;
    private String artistIdDeezer;
    private String musicMP3URL;
    private String[] urlMP3AllMusic;
    private String[] songsInAlbum;
    private String albumImage;
    private String albumIDForDeezer;
    private String albumName;

    public void setIsDeezerMusic(boolean isDeezerMusic){
        this.isDeezerMusic=isDeezerMusic;
    }
    public boolean getIsDeezerMusic(){
        return this.isDeezerMusic;
    }
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
    public String getArtistIdDeezer() {
        return artistIdDeezer;
    }
    public void setArtistIdDeezer(String artistIdDeezer) {
        this.artistIdDeezer = artistIdDeezer;
    }
    public String getMusicMP3URL() {
        return musicMP3URL;
    }
    public void setMusicMP3URL(String musicMP3URL) {
        this.musicMP3URL = musicMP3URL;
    }
    public String[] getUrlMP3AllMusic() {
        return urlMP3AllMusic;
    }
    public void setUrlMP3AllMusic(String[] urlMP3AllMusic) {
        this.urlMP3AllMusic = urlMP3AllMusic;
    }
    public String[] getSongsInAlbum() {
        return songsInAlbum;
    }
    public void setSongsInAlbum(String[] songsInAlbum) {
        this.songsInAlbum = songsInAlbum;
    }
    public String getAlbumImage() {
        return albumImage;
    }
    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }
    public String getAlbumIDForDeezer() {
        return albumIDForDeezer;
    }
    public void setAlbumIDForDeezer(String albumIDForDeezer) {
        this.albumIDForDeezer = albumIDForDeezer;
    }
    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

}
