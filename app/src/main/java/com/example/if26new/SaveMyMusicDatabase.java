package com.example.if26new;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.if26new.DAO.AlbumDao;
import com.example.if26new.DAO.AlbumDeezerDao;
import com.example.if26new.DAO.ArtistDao;
import com.example.if26new.DAO.ConcertDao;
import com.example.if26new.DAO.HistoricDao;
import com.example.if26new.DAO.LikeAlbumDao;
import com.example.if26new.DAO.LikeArtistDao;
import com.example.if26new.DAO.PlaylistDao;
import com.example.if26new.DAO.SingleDao;
import com.example.if26new.DAO.SinglePlaylistDao;
import com.example.if26new.DAO.UserDao;
import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.AlbumModelDeezer;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.ConcertModel;
import com.example.if26new.Model.HistoricModel;
import com.example.if26new.Model.LikeAlbumModel;
import com.example.if26new.Model.LikeArtistModel;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SingleModel;
import com.example.if26new.Model.UserModel;
import com.example.if26new.Model.SinglePlaylistModel;

@Database(entities = {UserModel.class, PlaylistModel.class,ArtistModel.class , ConcertModel.class, AlbumModel.class, SingleModel.class, SinglePlaylistModel.class, LikeAlbumModel.class, LikeArtistModel.class, AlbumModelDeezer.class, HistoricModel.class}, version = 1, exportSchema = false)
public abstract class SaveMyMusicDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyMusicDatabase INSTANCE;
    private int actualUser;

    SaveMyMusicDatabase db;
    // --- DAO ---
    public abstract PlaylistDao mPlaylistDao();
    public abstract UserDao userDao();
    public abstract AlbumDao mAlbumDao();
    public abstract AlbumDeezerDao mAlbumDeezerDao();
    public abstract ConcertDao mConcertDao();
    public abstract ArtistDao mArtistDao();
    public abstract SingleDao mSingleDao();
    public abstract SinglePlaylistDao mSinglePlaylistDao();
    public abstract LikeAlbumDao mLikeAlbumDao();
    public abstract LikeArtistDao mLikeArtistDao();
    public abstract HistoricDao mHistoricDao();

    // --- INSTANCE ---
    public static SaveMyMusicDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyMusicDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyMusicDatabase.class, "MyDatabase.db").allowMainThreadQueries().addCallback(prepopulateDatabase()).build();
                }
            }
        }
        return INSTANCE;
    }
    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                //USER
                ContentValues contentValues = new ContentValues();
                contentValues.put("mailAdress", "root@gmail.com");
                contentValues.put("username", "root");
                contentValues.put("password", "root");
                contentValues.put("avatar",R.drawable.default_avatar);
                contentValues.put("startColorGradient",R.color.StartColor);
                contentValues.put("endColorGradient",R.color.EndColor);
                contentValues.put("buttonColor",0XFF4A86E8);
                contentValues.put("isFingerPrint",false);
                db.insert("UserModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //ALBUM
                contentValues.put("id",1);
                contentValues.put("userId",0);
                contentValues.put("artistId",1);
                contentValues.put("titleAlbum","Hollywood's Bleeding");
                contentValues.put("isNew",1);
                contentValues.put("image",R.drawable.postmalone_album_hollywood);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("userId",0);
                contentValues.put("artistId",1);
                contentValues.put("titleAlbum","Beerpongs & Bentleys");
                contentValues.put("isNew",1);
                contentValues.put("image",R.drawable.postmalone_album_beerpong);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //CONCERT
                contentValues.put("id",1);
                contentValues.put("artistId",1);
                contentValues.put("date","22/02/2020");
                contentValues.put("location","Accordhotels arena");
                contentValues.put("locationCity","Paris");
                contentValues.put("locationLat",48.838599);
                contentValues.put("locationLgn",2.378591 );
                contentValues.put("titleConcert","2019EuroTour");
                contentValues.put("isNew",true);
                contentValues.put("image",R.drawable.postmalone_paris_concert);
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //ARTIST
                contentValues.put("id",1);
                contentValues.put("userId",0);
                contentValues.put("name","Post Malone");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.postmalone_bio);
                contentValues.put("image",R.drawable.postmalone);
                contentValues.put("isLike",false);
                contentValues.put("isDeezer",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("userId",0);
                contentValues.put("name","Coldplay");
                contentValues.put("topArtist",2);
                contentValues.put("bio",R.raw.coldplay_bio);
                contentValues.put("image",R.drawable.coldplay);
                contentValues.put("isLike",false);
                contentValues.put("isDeezer",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("userId",0);
                contentValues.put("name","Linkin Park");
                contentValues.put("topArtist",3);
                contentValues.put("bio",R.raw.linkinpark_bio);
                contentValues.put("image",R.drawable.linkinpark);
                contentValues.put("isLike",false);
                contentValues.put("isDeezer",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //SINGLES
                contentValues.put("id",1);
                contentValues.put("albumId",1);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Sunflower");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.postmalone_sunflower);
                contentValues.put("video",R.raw.postmalone_sunflower_video);
                contentValues.put("lyrics",R.raw.sunflower_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("albumId",2);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Rockstar");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.postmalone_rockstar);
                contentValues.put("video",R.raw.postmalone_rockstar_video);
                contentValues.put("lyrics",R.raw.rockstar_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Lost in the echo");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.linkinpark_lostintheecho);
                contentValues.put("video",R.raw.linkinpark_lostintheecho_video);
                contentValues.put("lyrics",R.raw.linkinpark_lostintheecho_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","One more ligth");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.linkinpark_onemoreligth);
                contentValues.put("video",R.raw.linkinpark_onemoreligth_video);
                contentValues.put("lyrics",R.raw.linkinpark_onemoreligth_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();


                contentValues.put("id",5);
                contentValues.put("albumId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","Adventure of a lifetime");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_adventureofalifetime);
                contentValues.put("video",R.raw.coldplay_adventureofalifetime_video);
                contentValues.put("lyrics",R.raw.coldplay_adventureofalifetime_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("albumId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","A sky full of stars");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_askyfullofstars);
                contentValues.put("video",R.raw.coldplay_askyfullofstars_video);
                contentValues.put("lyrics",R.raw.coldplay_askyfullofstars_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",7);
                contentValues.put("albumId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","Hymn for the weekend");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_hymnfortheweekend);
                contentValues.put("video",R.raw.coldplay_hymnfortheweekend_video);
                contentValues.put("lyrics",R.raw.coldplay_hymnfortheweekend_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

            }
        };
    }
    public int getActualUser() {
        return actualUser;
    }

    public void setActualUser(int actualUser) {
        this.actualUser = actualUser;
    }
}




