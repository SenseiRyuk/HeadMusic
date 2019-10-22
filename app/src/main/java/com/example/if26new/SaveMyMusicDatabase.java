package com.example.if26new;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.if26new.Converter.Converters;
import com.example.if26new.DAO.AlbumDao;
import com.example.if26new.DAO.ArtistDao;
import com.example.if26new.DAO.ConcertDao;
import com.example.if26new.DAO.PlaylistDao;
import com.example.if26new.DAO.SingleDao;
import com.example.if26new.DAO.UserDao;
import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.ConcertModel;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SingleModel;
import com.example.if26new.Model.UserModel;

@Database(entities = {UserModel.class, PlaylistModel.class,ArtistModel.class , ConcertModel.class, AlbumModel.class, SingleModel.class}, version = 1, exportSchema = false)
public abstract class SaveMyMusicDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyMusicDatabase INSTANCE;

    SaveMyMusicDatabase db;
    // --- DAO ---
    public abstract PlaylistDao mPlaylistDao();
    public abstract UserDao userDao();
   public abstract AlbumDao mAlbumDao();
    public abstract ConcertDao mConcertDao();
    public abstract ArtistDao mArtistDao();
    public abstract SingleDao mSingleDao();

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
                //contentValues.put("id", 1);
                contentValues.put("mailAdress", "root@gmail.com");
                contentValues.put("username", "root");
                contentValues.put("password", "root");

                db.insert("UserModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //PLAYLIST
                //contentValues.put("id",1);
                contentValues.put("userID",0);
                contentValues.put("titles","Favorite");
                contentValues.put("image",R.drawable.like);
                db.insert("PlaylistModel",OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //ALBUM
                contentValues.put("id",1);
                contentValues.put("userId",0);
                contentValues.put("artistId",1);
                contentValues.put("titleAlbum","Hollywood's Bleeding");
                contentValues.put("isNew",1);
                contentValues.put("image",R.drawable.postmalone_album_hollywood);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("userId",0);
                contentValues.put("artistId",1);
                contentValues.put("titleAlbum","Beerpongs & Bentleys");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.postmalone_album_beerpong);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("userId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleAlbum","Thank U, Next");
                contentValues.put("isNew",1);
                contentValues.put("image",R.drawable.arianagrande_album_thank);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("userId",0);
                contentValues.put("artistId",2);
                contentValues.put("titleAlbum","Sweetener");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.arianagrande_album_sweetener);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("userId",0);
                contentValues.put("artistId",6);
                contentValues.put("titleAlbum","Greatest Hits");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.queen_album_greatest);
                db.insert("AlbumModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("userId",0);
                contentValues.put("artistId",5);
                contentValues.put("titleAlbum","Greatest Hits");
                contentValues.put("isNew",0);
                contentValues.put("image",R.drawable.ewf_album_greatest);
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
                db.insert("ConcertModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();



                //SINGLE

                //ARTIST
                contentValues.put("id",1);
                contentValues.put("userId",0);
                contentValues.put("name","Post Malone");
                contentValues.put("topArtist",1);
                contentValues.put("bio",R.raw.postmalone_bio);
                contentValues.put("image",R.drawable.postmalone);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("userId",0);
                contentValues.put("name","Ariana Grande");
                contentValues.put("topArtist",2);
                contentValues.put("bio",R.raw.arianagrande_bio);
                contentValues.put("image",R.drawable.arianagrande);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("userId",0);
                contentValues.put("name","Vegedream");
                contentValues.put("topArtist",3);
                contentValues.put("bio",R.raw.vegedream_bio);
                contentValues.put("image",R.drawable.vegedream);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("userId",0);
                contentValues.put("name","Bakermat");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.bakermat_bio);
                contentValues.put("image",R.drawable.bakermat);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("userId",0);
                contentValues.put("name","Earth Wind and Fire");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.ewf_bio);
                contentValues.put("image",R.drawable.ewf);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("userId",0);
                contentValues.put("name","Queen");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.queen_bio);
                contentValues.put("image",R.drawable.queen);
                db.insert("ArtistModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",7);
                contentValues.put("userId",0);
                contentValues.put("name","Hazy");
                contentValues.put("topArtist",0);
                contentValues.put("bio",R.raw.hazy_bio);
                contentValues.put("image",R.drawable.hazy1);
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
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",2);
                contentValues.put("albumId",1);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Circles");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.postmalone_circles);
                contentValues.put("video",R.raw.postmalone_circles_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",3);
                contentValues.put("albumId",2);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Psycho");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.postmalone_psycho);
                contentValues.put("video",R.raw.postmalone_psycho_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",4);
                contentValues.put("albumId",2);
                contentValues.put("artistId",1);
                contentValues.put("titleSingle","Rockstar");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.postmalone_rockstar);
                contentValues.put("video",R.raw.postmalone_rockstar_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",5);
                contentValues.put("albumId",3);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","Thank U, Next");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.arianagrande_thank);
                contentValues.put("video",R.raw.arianagrande_thank_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",6);
                contentValues.put("albumId",3);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","7 rings");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.arianagrande_sevenrings);
                contentValues.put("video",R.raw.arianagrande_sevenrings_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",7);
                contentValues.put("albumId",4);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","God is a woman");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_god);
                contentValues.put("video",R.raw.arianagrande_god_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",8);
                contentValues.put("albumId",4);
                contentValues.put("artistId",2);
                contentValues.put("titleSingle","No tears left to cry");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.arianagrande_notears);
                contentValues.put("video",R.raw.arianagrande_notears_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",9);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Mama he");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_mamahe);
                contentValues.put("video",R.raw.vegedream_mamahe_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",10);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Marchand de sable");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_marchand);
                contentValues.put("video",R.raw.vegedream_marchand_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",11);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","La fuite");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.vegedream_lafuite);
                contentValues.put("video",R.raw.vegedreamlafuite_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",12);
                contentValues.put("albumId",0);
                contentValues.put("artistId",3);
                contentValues.put("titleSingle","Elle est bonne sa mère");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.vegedream_bonne);
                contentValues.put("video",R.raw.vegedream_bonne_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",13);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","One day");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.bakermat_oneday);
                contentValues.put("video",R.raw.bakermat_oneday_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",14);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","Baiana");
                contentValues.put("isNew",true);
                contentValues.put("music",R.raw.bakermat_baiana);
                contentValues.put("video",R.raw.bakermat_baiana_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",15);
                contentValues.put("albumId",0);
                contentValues.put("artistId",4);
                contentValues.put("titleSingle","Baby");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.bakermat_baby);
                contentValues.put("video",R.raw.bakermat_baby_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",16);
                contentValues.put("albumId",6);
                contentValues.put("artistId",5);
                contentValues.put("titleSingle","September");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.ewf_septemner);
                contentValues.put("video",R.raw.ewf_september_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",17);
                contentValues.put("albumId",6);
                contentValues.put("artistId",5);
                contentValues.put("titleSingle","Let's groove");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.ewf_groove);
                contentValues.put("video",R.raw.ewf_groove_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",18);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Bohemian rhapsody");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_bohemian);
                contentValues.put("video",R.raw.queen_bohemian_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",19);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Another bites in the dust");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_bites);
                contentValues.put("video",R.raw.queen_bites_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",20);
                contentValues.put("albumId",5);
                contentValues.put("artistId",6);
                contentValues.put("titleSingle","Don't stop me now");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.queen_stop);
                contentValues.put("video",R.raw.queen_stop_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",21);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Universe");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_universe);
                contentValues.put("video",R.raw.hazy_universe_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",22);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Cosmos");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_cosmos);
                contentValues.put("video",R.raw.hazy_cosmos_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                contentValues.put("id",23);
                contentValues.put("albumId",0);
                contentValues.put("artistId",7);
                contentValues.put("titleSingle","Ocean");
                contentValues.put("isNew",false);
                contentValues.put("music",R.raw.hazy_ocean);
                contentValues.put("video",R.raw.hazy_ocean_video);
                db.insert("SingleModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

            }
        };
    }
    /*public void InsertPlaylist(int id, String title,int image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",db.mPlaylistDao().loadAllPlaylist().length+1);
        contentValues.put("userID",0);
        contentValues.put("titles",text);
        contentValues.put("image",(Integer)buttonWithImage.getTag());
        db.insert("PlaylistModel",OnConflictStrategy.IGNORE, contentValues);
        contentValues.clear();
    }*/
}



