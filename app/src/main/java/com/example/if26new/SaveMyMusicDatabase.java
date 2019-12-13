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
import com.example.if26new.DAO.ArtistDao;
import com.example.if26new.DAO.ConcertDao;
import com.example.if26new.DAO.LikeAlbumDao;
import com.example.if26new.DAO.LikeArtistDao;
import com.example.if26new.DAO.PlaylistDao;
import com.example.if26new.DAO.SingleDao;
import com.example.if26new.DAO.SinglePlaylistDao;
import com.example.if26new.DAO.UserDao;
import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.ConcertModel;
import com.example.if26new.Model.LikeAlbumModel;
import com.example.if26new.Model.LikeArtistModel;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SingleModel;
import com.example.if26new.Model.UserModel;
import com.example.if26new.Model.SinglePlaylistModel;

@Database(entities = {UserModel.class, PlaylistModel.class,ArtistModel.class , ConcertModel.class, AlbumModel.class, SingleModel.class, SinglePlaylistModel.class, LikeAlbumModel.class, LikeArtistModel.class}, version = 1, exportSchema = false)
public abstract class SaveMyMusicDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyMusicDatabase INSTANCE;
    private int actualUser;

    SaveMyMusicDatabase db;
    // --- DAO ---
    public abstract PlaylistDao mPlaylistDao();
    public abstract UserDao userDao();
    public abstract AlbumDao mAlbumDao();
    public abstract ConcertDao mConcertDao();
    public abstract ArtistDao mArtistDao();
    public abstract SingleDao mSingleDao();
    public abstract SinglePlaylistDao mSinglePlaylistDao();
    public abstract LikeAlbumDao mLikeAlbumDao();
    public abstract LikeArtistDao mLikeArtistDao();

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
                contentValues.put("startColorGradient",0xFF482834);
                contentValues.put("endColorGradient",0xFF1C3766);
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
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.postmalone_album_beerpong);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("userId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleAlbum","Thank U, Next");
                contentValues.put("isNew",1);
                contentValues.put("image",R.drawable.arianagrande_album_thank);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("userId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleAlbum","Sweetener");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.arianagrande_album_sweetener);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("userId",0);
                contentValues.put("artistId",6);
                contentValues.put("titleAlbum","Greatest Hits");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.queen_album_greatest);
                contentValues.put("isLike",false);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("userId",0);
                contentValues.put("artistId",5);
                contentValues.put("titleAlbum","Greatest Hits");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.ewf_album_greatest);
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

                contentValues.put("id",2);
                contentValues.put("artistId",2);
                contentValues.put("date","22/02/2020");
                contentValues.put("location","The Forum");
                contentValues.put("locationCity","Los Angeles");
                contentValues.put("locationLat",33.958638);
                contentValues.put("locationLgn",-118.341987);
                contentValues.put("titleConcert","Sweetener World Tour");
                contentValues.put("isNew",false);
                contentValues.put("image",R.drawable.arianagrande_concert);
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("artistId",2);
                contentValues.put("date","12/11/2019");
                contentValues.put("location","Barclays Center");
                contentValues.put("locationCity","New Yrok");
                contentValues.put("locationLat",40.682853);
                contentValues.put("locationLgn",-73.975392);
                contentValues.put("titleConcert","Sweetener World Tour");
                contentValues.put("isNew",false);
                contentValues.put("image",R.drawable.arianagrande_concert);
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("artistId",3);
                contentValues.put("date","28/03/2020");
                contentValues.put("location","Zenith De Paris La Villette");
                contentValues.put("locationCity","Paris");
                contentValues.put("locationLat",48.894317);
                contentValues.put("locationLgn",2.393186);
                contentValues.put("titleConcert","Game over");
                contentValues.put("isNew",true);
                contentValues.put("image",R.drawable.vegedream_paris_concert);
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("artistId",3);
                contentValues.put("date","28/04/2020");
                contentValues.put("location","L'amphithéâtre cité internationale");
                contentValues.put("locationCity","Lyon");
                contentValues.put("locationLat",45.785234);
                contentValues.put("locationLgn",4.858123);
                contentValues.put("titleConcert","Game over");
                contentValues.put("isNew",true);
                contentValues.put("image",R.drawable.vegedream_lyon_concert);
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("artistId",4);
                contentValues.put("date","28/02/2020");
                contentValues.put("location","RAI Amsterdam");
                contentValues.put("locationCity","Amsterdam");
                contentValues.put("locationLat",52.341711);
                contentValues.put("locationLgn",4.888407);
                contentValues.put("titleConcert","Valhalla Festival");
                contentValues.put("isNew",true);
                contentValues.put("image",R.drawable.valhalla_sound_cricus);
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
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("userId",0);
                contentValues.put("name","Ariana Grande");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.arianagrande_bio);
                contentValues.put("image",R.drawable.arianagrande);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("userId",0);
                contentValues.put("name","Vegedream");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.vegedream_bio);
                contentValues.put("image",R.drawable.vegedream);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("userId",0);
                contentValues.put("name","Bakermat");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.bakermat_bio);
                contentValues.put("image",R.drawable.bakermat);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("userId",0);
                contentValues.put("name","Earth Wind and Fire");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.ewf_bio);
                contentValues.put("image",R.drawable.ewf);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("userId",0);
                contentValues.put("name","Queen");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.queen_bio);
                contentValues.put("image",R.drawable.queen);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",7);
                contentValues.put("userId",0);
                contentValues.put("name","Hazy");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.hazy_bio);
                contentValues.put("image",R.drawable.hazy1);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",8);
                contentValues.put("userId",0);
                contentValues.put("name","Coldplay");
                contentValues.put("topArtist",2);
                contentValues.put("bio",R.raw.coldplay_bio);
                contentValues.put("image",R.drawable.coldplay);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",9);
                contentValues.put("userId",0);
                contentValues.put("name","Linkin Park");
                contentValues.put("topArtist",3);
                contentValues.put("bio",R.raw.linkinpark_bio);
                contentValues.put("image",R.drawable.linkinpark);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",10);
                contentValues.put("userId",0);
                contentValues.put("name","Oliver Francis");
                contentValues.put("topArtist",1);
                contentValues.put("bio",R.raw.olivierfrancis_bio);
                contentValues.put("image",R.drawable.olivierfrancis);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

               /* contentValues.put("id",11);
                contentValues.put("userId",0);
                contentValues.put("name","VSO");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.vso_bio);
                contentValues.put("image",R.drawable.vsojpg);
                contentValues.put("isLike",false);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();*/


                //SINGLES
                contentValues.put("id",1);
                contentValues.put("albumId",1);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Sunflower");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.postmalone_sunflower);
                contentValues.put("video",R.raw.postmalone_sunflower_video);
                contentValues.put("lyrics",R.raw.sunflower_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("albumId",1);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Circles");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.postmalone_circles);
                contentValues.put("video",R.raw.postmalone_circles_video);
                contentValues.put("lyrics",R.raw.circles_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("albumId",2);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Psycho");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.postmalone_psycho);
                contentValues.put("video",R.raw.postmalone_psycho_video);
                contentValues.put("lyrics",R.raw.psycho_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("albumId",2);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Rockstar");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.postmalone_rockstar);
                contentValues.put("video",R.raw.postmalone_rockstar_video);
                contentValues.put("lyrics",R.raw.rockstar_postmalone_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("albumId",3);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","Thank U, Next");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_thank);
                contentValues.put("video",R.raw.arianagrande_thank_video);
                contentValues.put("lyrics",R.raw.thanku_ariana_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("albumId",3);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","7 rings");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_sevenrings);
                contentValues.put("video",R.raw.arianagrande_sevenrings_video);
                contentValues.put("lyrics",R.raw.sevenrings_ariana_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",7);
                contentValues.put("albumId",4);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","God is a woman");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_god);
                contentValues.put("video",R.raw.arianagrande_god_video);
                contentValues.put("lyrics",R.raw.godwoman_ariana_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",8);
                contentValues.put("albumId",4);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","No tears left to cry");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_notears);
                contentValues.put("video",R.raw.arianagrande_notears_video);
                contentValues.put("lyrics",R.raw.notears_ariana_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                /*contentValues.put("id",9);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Mama he");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_mamahe);
                contentValues.put("video",R.raw.vegedream_mamahe_video);
                contentValues.put("lyrics",R.raw.mamahe_vegedream_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",10);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Marchand de sable");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_marchand);
                contentValues.put("video",R.raw.vegedream_marchand_video);
                contentValues.put("lyrics",R.raw.marchanddesable_vegedream_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",11);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","La fuite");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_lafuite);
                contentValues.put("video",R.raw.vegedreamlafuite_video);
                contentValues.put("lyrics",R.raw.lafuite_vegedream_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",12);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Elle est bonne sa mère");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.vegedream_bonne);
                contentValues.put("video",R.raw.vegedream_bonne_video);
                contentValues.put("lyrics",R.raw.elleestbonnesamere_vegedream_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();*/

                contentValues.put("id",13);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","One day");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.bakermat_oneday);
                contentValues.put("video",R.raw.bakermat_oneday_video);
                contentValues.put("lyrics",R.raw.oneday_bakermat_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",14);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","Baiana");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.bakermat_baiana);
                contentValues.put("video",R.raw.bakermat_baiana_video);
                contentValues.put("lyrics",R.raw.baiana_bakermat_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",15);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","Baby");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.bakermat_baby);
                contentValues.put("video",R.raw.bakermat_baby_video);
                contentValues.put("lyrics",R.raw.baby_bakermat_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",16);
                contentValues.put("albumId",6);
                contentValues.put("artistId",5);
                contentValues.put("titleSingle","September");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.ewf_septemner);
                contentValues.put("video",R.raw.ewf_september_video);
                contentValues.put("lyrics",R.raw.september_ewf_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",17);
                contentValues.put("albumId",6);
                contentValues.put("artistId",5);
                contentValues.put("titleSingle","Let's groove");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.ewf_groove);
                contentValues.put("video",R.raw.ewf_groove_video);
                contentValues.put("lyrics",R.raw.letsgroove_ewf_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",18);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Bohemian rhapsody");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_bohemian);
                contentValues.put("video",R.raw.queen_bohemian_video);
                contentValues.put("lyrics",R.raw.bohemian_queen_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",19);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Another bites in the dust");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_bites);
                contentValues.put("video",R.raw.queen_bites_video);
                contentValues.put("lyrics",R.raw.anotherbites_queen_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",20);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Don't stop me now");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_stop);
                contentValues.put("video",R.raw.queen_stop_video);
                contentValues.put("lyrics",R.raw.dontstop_queen_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",21);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Universe");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_universe);
                contentValues.put("video",R.raw.hazy_universe_video);
                contentValues.put("lyrics",R.raw.nolyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",22);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Cosmos");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_cosmos);
                contentValues.put("video",R.raw.hazy_cosmos_video);
                contentValues.put("lyrics",R.raw.nolyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",23);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Ocean");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_ocean);
                contentValues.put("video",R.raw.hazy_ocean_video);
                contentValues.put("lyrics",R.raw.nolyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",24);
                contentValues.put("albumId",0);
                contentValues.put("artistId",9);
                contentValues.put("titleSingle","Powerless");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.linkinpark_powerless);
                contentValues.put("video",R.raw.linkinpark_powerless_video);
                contentValues.put("lyrics",R.raw.linkinpark_powerless_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",25);
                contentValues.put("albumId",0);
                contentValues.put("artistId",9);
                contentValues.put("titleSingle","Lost in the echo");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.linkinpark_lostintheecho);
                contentValues.put("video",R.raw.linkinpark_lostintheecho_video);
                contentValues.put("lyrics",R.raw.linkinpark_lostintheecho_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",26);
                contentValues.put("albumId",0);
                contentValues.put("artistId",9);
                contentValues.put("titleSingle","New Divide");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.linkinpark_newdivide);
                contentValues.put("video",R.raw.linkinpark_newdivide_video);
                contentValues.put("lyrics",R.raw.linkinpark_newdivide_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",27);
                contentValues.put("albumId",0);
                contentValues.put("artistId",9);
                contentValues.put("titleSingle","One more ligth");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.linkinpark_onemoreligth);
                contentValues.put("video",R.raw.linkinpark_onemoreligth_video);
                contentValues.put("lyrics",R.raw.linkinpark_onemoreligth_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",28);
                contentValues.put("albumId",0);
                contentValues.put("artistId",9);
                contentValues.put("titleSingle","Catalyst");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.linkinpark_catalyst);
                contentValues.put("video",R.raw.linkinpark_catalyst_video);
                contentValues.put("lyrics",R.raw.linkinpark_catalyst_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",29);
                contentValues.put("albumId",0);
                contentValues.put("artistId",8);
                contentValues.put("titleSingle","Adventure of a lifetime");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_adventureofalifetime);
                contentValues.put("video",R.raw.coldplay_adventureofalifetime_video);
                contentValues.put("lyrics",R.raw.coldplay_adventureofalifetime_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",30);
                contentValues.put("albumId",0);
                contentValues.put("artistId",8);
                contentValues.put("titleSingle","The scientist");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_thescientist);
                contentValues.put("video",R.raw.coldplay_thescientist_video);
                contentValues.put("lyrics",R.raw.coldplay_thescientist_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",31);
                contentValues.put("albumId",0);
                contentValues.put("artistId",8);
                contentValues.put("titleSingle","A sky full of stars");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_askyfullofstars);
                contentValues.put("video",R.raw.coldplay_askyfullofstars_video);
                contentValues.put("lyrics",R.raw.coldplay_askyfullofstars_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",32);
                contentValues.put("albumId",0);
                contentValues.put("artistId",8);
                contentValues.put("titleSingle","Hymn for the weekend");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.coldplay_hymnfortheweekend);
                contentValues.put("video",R.raw.coldplay_hymnfortheweekend_video);
                contentValues.put("lyrics",R.raw.coldplay_hymnfortheweekend_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",33);
                contentValues.put("albumId",0);
                contentValues.put("artistId",10);
                contentValues.put("titleSingle","Aeou");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.oliver_aeou);
                contentValues.put("video",R.raw.oliver_aeou_video);
                contentValues.put("lyrics",R.raw.olivierfrancis_aeou_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",34);
                contentValues.put("albumId",0);
                contentValues.put("artistId",10);
                contentValues.put("titleSingle","Anti-Grav");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.oliver_antigrav);
                contentValues.put("video",R.raw.oliver_antigrav_video);
                contentValues.put("lyrics",R.raw.olivierfrancis_antigrav_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",35);
                contentValues.put("albumId",0);
                contentValues.put("artistId",10);
                contentValues.put("titleSingle","Gemini");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.oliver_gemini);
                contentValues.put("video",R.raw.oliver_gemini_video);
                contentValues.put("lyrics",R.raw.olivierfrancis_gemini_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                /*contentValues.put("id",36);
                contentValues.put("albumId",0);
                contentValues.put("artistId",11);
                contentValues.put("titleSingle","Bordeaux-Lilas");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.vso_bordeaulilas);
                contentValues.put("video",R.raw.vso_bordeauxlilas_video);
                contentValues.put("lyrics",R.raw.vso_bordeauxlilas_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",37);
                contentValues.put("albumId",0);
                contentValues.put("artistId",11);
                contentValues.put("titleSingle","Puzzle");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.vso_puzzle);
                contentValues.put("video",R.raw.vso_puzzle_video);
                contentValues.put("lyrics",R.raw.vso_puzzle_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",38);
                contentValues.put("albumId",0);
                contentValues.put("artistId",11);
                contentValues.put("titleSingle","Encore un soir");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.vso_encoreunsoir);
                contentValues.put("video",R.raw.vso_encoreunsoir_video);
                contentValues.put("lyrics",R.raw.vso_encoreunsoir_lyrics);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();*/

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




