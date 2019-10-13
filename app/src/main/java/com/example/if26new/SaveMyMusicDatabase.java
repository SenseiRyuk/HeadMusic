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
                            SaveMyMusicDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
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
                contentValues.put("username", "Philippe");
                contentValues.put("urlPicture", "https://oc-user.imgix.net/users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}



