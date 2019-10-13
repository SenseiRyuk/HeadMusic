package com.example.if26new;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserModel.class, PlaylistModel.class}, version = 1, exportSchema = false)
public abstract class SaveMyMusicDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyMusicDatabase INSTANCE;

    // --- DAO ---
    public abstract PlaylistDao mPlaylistDao();
    public abstract UserDao userDao();

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

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("mailAdress", "root@gmail.com");
                contentValues.put("username", "root");
                contentValues.put("password", "root");

                db.insert("UserModel", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}



