package com.example.if26new;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.SingleModel;
import com.example.if26new.Model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private TextView musicTitle;
    private MediaControllerAudio mediaControllerAudio;
    private SaveMyMusicDatabase db;
    private ImageView musicPicture;
    private TextView artistTitle;
    private ImageButton playPause;
    private String wichFragment;
    private String context;
    private String playListName;
    private String fragmentForSingleInNew;
    private boolean doubleBackToExitPressedOnce = false;
    private ImageButton settings;
    private ConstraintLayout background;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=SaveMyMusicDatabase.getInstance(this);
        setContentView(R.layout.activity_home);
        DrawableCompat.setTint(getDrawable(R.drawable.home),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(getDrawable(R.drawable.newsong),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(getDrawable(R.drawable.searchview),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(getDrawable(R.drawable.profile),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        musicTitle=findViewById(R.id.musicTitle);
        mediaControllerAudio=MediaControllerAudio.getInstance();

        //Set the the background and button colors
        background=findViewById(R.id.backgroundHomeActivity);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        background.setBackground(gd);

        musicPicture=findViewById(R.id.imageViewHome);

        playPause=findViewById(R.id.playPauseHome);
        artistTitle=findViewById(R.id.artistTitleHome);
        settings=findViewById(R.id.settingBtn);
        DrawableCompat.setTint(settings.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("FRAGMENT_NAME",wichFragment);
                Intent settings = new Intent(HomeActivity.this, SettingActivity.class);
                settings.putExtras(bundle);
                startActivity(settings);
            }
        });
        context=getIntent().getExtras().getString("CONTEXT","null");
        wichFragment=getIntent().getExtras().getString("FRAGMENT_NAME");
        playListName=getIntent().getExtras().getString("PLAYLIST_NAME","null");
        fragmentForSingleInNew=getIntent().getExtras().getString("FRAGMENT","null");
        musicTitle.setText(mediaControllerAudio.getSongName());
        artistTitle.setText(mediaControllerAudio.getArtistName());
        if (mediaControllerAudio.getAlbumID()!=0){
            musicPicture.setVisibility(View.VISIBLE);
            musicPicture.setImageResource(db.mAlbumDao().getAlbumFromId(mediaControllerAudio.getAlbumID()).getImage());
        }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==false)){
            musicPicture.setVisibility(View.INVISIBLE);
        }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==true)){
            musicPicture.setVisibility(View.VISIBLE);
            musicPicture.setImageResource(db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getImage());
        }
        musicPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        if (mediaControllerAudio.getMediaPlayerAudio()==null){
            playPause.setVisibility(View.INVISIBLE);
            musicTitle.setVisibility(View.INVISIBLE);
            artistTitle.setVisibility(View.INVISIBLE);
            musicPicture.setVisibility(View.INVISIBLE);
        }else{
           playPause.setVisibility(View.VISIBLE);
            musicTitle.setVisibility(View.VISIBLE);
            artistTitle.setVisibility(View.VISIBLE);
            musicPicture.setVisibility(View.VISIBLE);
        }
        if (mediaControllerAudio.getMediaPlayerAudio()!=null){
            if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()){
                playPause.setImageResource(R.drawable.pauselistening);
            }else{
                playPause.setImageResource(R.drawable.playlistening);
            }
        }

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())) {
                    playPause.setImageResource(R.drawable.playlistening);
                    DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                    mediaControllerAudio.getMediaPlayerAudio().pause();
                }else if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())){
                    playPause.setImageResource(R.drawable.pauselistening);
                    DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                    mediaControllerAudio.getMediaPlayerAudio().start();
                }
            }
        });
        artistTitle.setOnClickListener(this);
        musicTitle.setOnClickListener(this);
        bottomNavigationView=findViewById(R.id.bottonView);

        bottomNavigationView.setBackgroundColor(db.userDao().getUserFromId(db.getActualUser()).getEndColorGradient());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);

        switch (wichFragment){
            case "MainFragment":
                loadFragment(new MainFragment());
                bottomNavigationView.setSelectedItemId(R.id.homeButtonBottomBar);
                DrawableCompat.setTint( bottomNavigationView.getItemBackground(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                break;
            case "NewsFragment":
                loadFragment(new NewsFragment());
                bottomNavigationView.setSelectedItemId(R.id.newButtonBottomBar);
                DrawableCompat.setTint( bottomNavigationView.getItemBackground(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                break;
            case "SearchViewFragment":
                loadFragment(new SearchViewFragment());
                bottomNavigationView.setSelectedItemId(R.id.searchViewBottomBar);
                DrawableCompat.setTint( bottomNavigationView.getItemBackground(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                break;
            case "UserFragment":
                loadFragment(new UserFragment());
                bottomNavigationView.setSelectedItemId(R.id.profileButtonBottomBar);
                DrawableCompat.setTint( bottomNavigationView.getItemBackground(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                break;
        }
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
                fragment = new MainFragment();
                break;
            case R.id.newButtonBottomBar:
                fragment = new NewsFragment();
                break;
            case R.id.searchViewBottomBar:
                fragment = new SearchViewFragment();
                break;
            case R.id.profileButtonBottomBar:
                fragment = new UserFragment();
                break;
        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainter, fragment).addToBackStack(null).commit();
            return true;
        }
        return false;
    }
    public void onClick(View v){
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
