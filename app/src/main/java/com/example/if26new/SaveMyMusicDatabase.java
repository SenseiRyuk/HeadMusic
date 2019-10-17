package com.example.if26new;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

@Database(entities = {UserModel.class, PlaylistModel.class, AlbumModel.class, ConcertModel.class, ArtistModel.class, SingleModel.class}, version = 1, exportSchema = false)
public abstract class SaveMyMusicDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyMusicDatabase INSTANCE;

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
                contentValues.put("id", 1);
                contentValues.put("mailAdress", "root@gmail.com");
                contentValues.put("username", "root");
                contentValues.put("password", "root");

                db.insert("UserModel", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();

                //PLAYLIST
                contentValues.put("id",1);
                contentValues.put("userID",0);
                contentValues.put("titles","Favorite");
                contentValues.put("imageButton",R.drawable.like);
                db.insert("PlaylistModel",OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                //ALBUM

                //CONCERT

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
                contentValues.put("image",R.drawable.postmalone);
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

            }
        };
    }
}



