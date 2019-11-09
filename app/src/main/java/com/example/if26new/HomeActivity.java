package com.example.if26new;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.SingleModel;
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



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        musicTitle=findViewById(R.id.musicTitle);
        mediaControllerAudio=MediaControllerAudio.getInstance();
        db=SaveMyMusicDatabase.getInstance(this);
        musicPicture=findViewById(R.id.imageViewHome);
        playPause=findViewById(R.id.playPauseHome);
        artistTitle=findViewById(R.id.artistTitleHome);

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
                    Intent Album = new Intent(HomeActivity.this, Album_view.class);
                    Album.putExtras(bundle);
                    startActivity(Album);
                }else if ((mediaControllerAudio.getAlbumID()==0)&&(mediaControllerAudio.isArtist()==true)){
                    Bundle bundle=new Bundle();
                    bundle.putString("ARTIST_NAME",artistTitle.getText().toString());
                    bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getImage());
                    bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistTitle.getText().toString()).getBio());
                    Intent Artist = new Intent(HomeActivity.this, ActivityArtist.class);
                    Artist.putExtras(bundle);
                    startActivity(Artist);
                }
            }
        });
        if (mediaControllerAudio.getMediaPlayerAudio()==null){
            playPause.setVisibility(View.INVISIBLE);
        }else{
           playPause.setVisibility(View.VISIBLE);
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
                    mediaControllerAudio.getMediaPlayerAudio().pause();
                }else if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())){
                    playPause.setImageResource(R.drawable.pauselistening);
                    mediaControllerAudio.getMediaPlayerAudio().start();
                }
            }
        });
        artistTitle.setOnClickListener(this);
        musicTitle.setOnClickListener(this);
        bottomNavigationView=findViewById(R.id.bottonView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);
        loadFragment(new MainFragment());
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

            case R.id.profileButtonBottomBar:
                fragment = new UserFragment();
                break;
            case R.id.searchViewBottomBar:
                fragment = new SearchViewFragment();
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
        Intent playListActivity = new Intent(HomeActivity.this, Listening.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }

}
