package com.example.if26new;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.if26new.Albums.Album_view;
import com.example.if26new.Albums.Album_view_For_Deezer;
import com.example.if26new.Artists.Local.ActivityArtist;
import com.example.if26new.Listening.Listening;
import com.example.if26new.Listening.ListeningForDeezer;
import com.example.if26new.Home.HomeFragment;
import com.example.if26new.Listening.MediaControllerAudio;
import com.example.if26new.Model.UserModel;
import com.example.if26new.Recognition.Recognize;
import com.example.if26new.Search.SearchViewFragment;
import com.example.if26new.SignIn_LogIn.MainActivity;
import com.example.if26new.Setting.SettingActivity;
import com.example.if26new.Library.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final static int heigthOfMusicPlayer=150;
    private BottomNavigationView bottomNavigationView;
    private MediaControllerAudio mediaControllerAudio;
    private SaveMyMusicDatabase db;

    private String wichFragment;
    private String context;
    private String playListName;
    private String fragmentForSingleInNew;
    private boolean doubleBackToExitPressedOnce = false;
    private ConstraintLayout background;
    private Bitmap mIcon_val;
    private ImageButton setting;
    private LinearLayout mainLinearLayout;

    private TextView musicTitle;
    private TextView artistTitle;
    private ImageView musicPicture;
    private ImageButton playPause;
    private ConstraintLayout musicContraintLayout;
    private ImageButton IB_Recognition;
    private String currentFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=SaveMyMusicDatabase.getInstance(this);
        setContentView(R.layout.activity_home);
        initView();
        //Set the the background and button colors
        retrieveBundle();
        initAudioPlayer();
        initBottomView();
    }

    public void initAudioPlayer(){
        mediaControllerAudio=MediaControllerAudio.getInstance();
        if (mediaControllerAudio.getMediaPlayerAudio()!=null){
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            musicContraintLayout = (ConstraintLayout) inflater.inflate(R.layout.music_headband, null, false);
            musicContraintLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heigthOfMusicPlayer));

            musicTitle=musicContraintLayout.findViewById(R.id.musicTitle);
            musicTitle.setText(mediaControllerAudio.getSongName());
            musicTitle.setOnClickListener(this);

            artistTitle=musicContraintLayout.findViewById(R.id.artistTitleHome);
            artistTitle.setOnClickListener(this);
            artistTitle.setText(mediaControllerAudio.getArtistName());

            musicPicture=musicContraintLayout.findViewById(R.id.imageViewHome);
            if (mediaControllerAudio.getIsDeezerMusic()){
                musicPicture.setImageBitmap(getImageFromDeezerURL(mediaControllerAudio.getAlbumImage()));
            }else{
                if (mediaControllerAudio.getAlbumID()!=0){
                    musicPicture.setVisibility(View.VISIBLE);
                    musicPicture.setImageResource(db.mAlbumDao().getAlbumFromId(mediaControllerAudio.getAlbumID()).getImage());
                }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==false)){
                    musicPicture.setVisibility(View.INVISIBLE);
                }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==true)){
                    musicPicture.setVisibility(View.VISIBLE);
                    musicPicture.setImageResource(db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getImage());
                }
            }
            musicPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaControllerAudio.getIsDeezerMusic()){
                        setAlbumClickDeezer();
                    }else{
                        setAlbumClick();
                    }
                }
            });

            playPause=musicContraintLayout.findViewById(R.id.playPauseHome);
            if (mediaControllerAudio.getMediaPlayerAudio()!=null){
                if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()){
                    playPause.setImageResource(R.drawable.pauselistening);
                    DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                }else{
                    playPause.setImageResource(R.drawable.playlistening);
                }
            }

            if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()) {
                playPause.setImageResource(R.drawable.pauselistening);
                DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
            }else{
                playPause.setImageResource(R.drawable.playlistening);
                DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
            }

            playPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()){
                        playPause.setImageResource(R.drawable.playlistening);
                        DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                        mediaControllerAudio.getMediaPlayerAudio().pause();
                    }else{
                        playPause.setImageResource(R.drawable.pauselistening);
                        DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                        mediaControllerAudio.getMediaPlayerAudio().start();
                    }
                }
            });
            //SET CONSTRAINT
            if (mediaControllerAudio.getMediaPlayerAudio()!=null){
                mainLinearLayout.addView(musicContraintLayout,0);
            }
        }
    }
    public void initBottomView(){
        bottomNavigationView=findViewById(R.id.bottonView);
        bottomNavigationView.setBackgroundColor(db.userDao().getUserFromId(db.getActualUser()).getEndColorGradient());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        switch (wichFragment){
            case "HomeFragment":
                loadFragment(new HomeFragment());
                bottomNavigationView.setSelectedItemId(R.id.homeButtonBottomBar);
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                break;
            case "SearchViewFragment":
                loadFragment(new SearchViewFragment());
                bottomNavigationView.setSelectedItemId(R.id.searchViewBottomBar);
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                break;
            case "UserFragment":
                loadFragment(new UserFragment());
                bottomNavigationView.setSelectedItemId(R.id.profileButtonBottomBar);
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                break;
        }
        bottomNavigationView.setItemIconTintList(null);
    }
    public void retrieveBundle(){
        context=getIntent().getExtras().getString("CONTEXT","null");
        wichFragment=getIntent().getExtras().getString("FRAGMENT_NAME");
        playListName=getIntent().getExtras().getString("PLAYLIST_NAME","null");
        fragmentForSingleInNew=getIntent().getExtras().getString("FRAGMENT","null");
    }
    public void initView(){
        IB_Recognition=findViewById(R.id.IB_Recognition);
        IB_Recognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Recognize());
            }
        });
        background=findViewById(R.id.backgroundHomeActivity);
        setting=findViewById(R.id.settingBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("FRAGMENT_NAME","HomeFragment");
                Intent settings = new Intent(getApplicationContext(), SettingActivity.class);
                settings.putExtras(bundle);
                startActivity(settings);
            }
        });
        //SetBackgroundColor
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        background.setBackground(gd);
        mainLinearLayout=findViewById(R.id.LL_Navigation_View);
    }
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent connexionActivity =new Intent(HomeActivity.this, MainActivity.class);
            startActivity(connexionActivity);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.homeButtonBottomBar:
                fragment = new HomeFragment();
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.searchViewBottomBar:
                fragment = new SearchViewFragment();
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.profileButtonBottomBar:
                fragment = new UserFragment();
                bottomNavigationView.getMenu().getItem(0).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(1).getIcon().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.SRC_ATOP);
                bottomNavigationView.getMenu().getItem(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                break;
        }

        if(currentFragment!=null){
            if(!item.getTitle().toString().equals(currentFragment)){
                currentFragment=item.getTitle().toString();
                return loadFragment(fragment);
            }else{
                return false;
            }
        }else{
            currentFragment=item.getTitle().toString();
            return false;
        }
    }
    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainter, fragment).addToBackStack(null).commit();
            return true;
        }
        return false;
    }
    public void setAlbumClick(){
        if (mediaControllerAudio.getAlbumID()!=0){
            Bundle bundle=new Bundle();
            bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromId(mediaControllerAudio.getAlbumID()).getTitleAlbum());
            bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromId(mediaControllerAudio.getAlbumID()).getImage());
            bundle.putInt("ALBUM_ID",mediaControllerAudio.getAlbumID());
            bundle.putString("ARTIST_NAME",artistTitle.getText().toString());
            bundle.putString("FRAGMENT_NAME",wichFragment);
            Intent Album = new Intent(HomeActivity.this, Album_view.class);
            Album.putExtras(bundle);
            startActivity(Album);
        }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==true)){
            Bundle bundle=new Bundle();
            bundle.putString("ARTIST_NAME",artistTitle.getText().toString());
            bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getImage());
            bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getBio());
            bundle.putString("FRAGMENT_NAME",wichFragment);
            Intent Artist = new Intent(HomeActivity.this, ActivityArtist.class);
            Artist.putExtras(bundle);
            startActivity(Artist);
        }
    }
    public void setAlbumClickDeezer(){
        Bundle bundle=new Bundle();
        bundle.putString("ALBUM_IMAGE_ID",mediaControllerAudio.getAlbumImage());
        bundle.putString("ALBUM_NAME",mediaControllerAudio.getAlbumName());
        bundle.putString("ALBUM_ID",mediaControllerAudio.getAlbumIDForDeezer());
        bundle.putString("FRAGMENT_NAME",wichFragment);
        bundle.putInt("IS_FROM_ARTIST_VIEW",0);
        bundle.putString("ARTIST_NAME",mediaControllerAudio.getArtistName());
        bundle.putString("ARTIST_ID",mediaControllerAudio.getArtistIdDeezer());
        Intent Album = new Intent(HomeActivity.this, Album_view_For_Deezer.class);
        Album.putExtras(bundle);
        startActivity(Album);
    }
    public void onClick(View v){
        //DEEZER MUSIC
        if(mediaControllerAudio.getIsDeezerMusic()){
            Bundle bundle=new Bundle();
            bundle.putString("SONG_ID",mediaControllerAudio.getSongID());
            bundle.putString("SONG_NAME",mediaControllerAudio.getSongName());
            bundle.putString("ARTIST_NAME",mediaControllerAudio.getArtistName());
            bundle.putString("ARTIST_ID",mediaControllerAudio.getArtistIdDeezer());
            bundle.putString("URL_MUSIC", mediaControllerAudio.getMusicMP3URL());
            bundle.putString("ALBUM_IMAGE_ID",mediaControllerAudio.getAlbumImage());
            bundle.putString("ALBUM_ID",mediaControllerAudio.getAlbumIDForDeezer());
            bundle.putString("ALBUM_NAME",mediaControllerAudio.getAlbumName());
            bundle.putStringArray("URL_ALL_MUSIC",mediaControllerAudio.getUrlMP3AllMusic());
            bundle.putStringArray("SONGS_IN_ALBUM",mediaControllerAudio.getSongsInAlbum());
            bundle.putString("CONTEXT","HomeActivity");
            bundle.putString("FRAGMENT_NAME","HomeFragment");
            Intent playListActivity = new Intent(HomeActivity.this, ListeningForDeezer.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }else{
            Bundle bundle=new Bundle();
            bundle.putString("SONG_NAME",musicTitle.getText().toString());
            bundle.putString("ARTIST_NAME",artistTitle.getText().toString());
            bundle.putInt("ALBUM_ID",mediaControllerAudio.getAlbumID());
            bundle.putString("CONTEXT", context);
            bundle.putString("FRAGMENT_NAME",wichFragment);
            bundle.putString("PLAYLIST_NAME",playListName);
            bundle.putString("FRAGMENT",fragmentForSingleInNew);
            Intent playListActivity = new Intent(HomeActivity.this, Listening.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }

    }
    public Bitmap getImageFromDeezerURL(final String imageURL){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                try {
                    if(!imageURL.equals("null")){
                        newurl = new URL(imageURL);
                        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mIcon_val;
    }

}
