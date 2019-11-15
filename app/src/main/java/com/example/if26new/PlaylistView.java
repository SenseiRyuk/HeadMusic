package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;

public class PlaylistView extends AppCompatActivity implements View.OnClickListener {


    private TextView[] songName;
    private TextView[] artistName;
    private TextView playlistName;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private ImageView mImageView;
    private String playlistNameFromFragment;
    private int sizePlaylistMusic;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);
        //Set ImagePlaylist
        setImagePlaylistFromTheDataBase(getIntent().getExtras().getInt("PLAYLIST_IMAGE_ID"));
        returnButton=findViewById(R.id.returnButtonPlaylist);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        //Retrieve PlayListName
        playlistNameFromFragment=getIntent().getExtras().getString("PLAYLIST_NAME");
        playlistName=findViewById(R.id.playlistNameInPlaylistView);
        playlistName.setText(playlistNameFromFragment);
        //USE DATA BASE TO RETRIEVE ALL SONG FOR THIS PLAYLIST
        getAllSongFromTheDataBase();
    }
    public void setImagePlaylistFromTheDataBase(int idImage){
        mImageView = findViewById(R.id.playlistImageInPlaylistView);
        android.view.ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height=650;
        params.width=650;
        mImageView.setLayoutParams(params);
        mImageView.setImageResource(idImage);
        mImageView.setAdjustViewBounds(false);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
    public void getAllSongFromTheDataBase(){

        //DDB--> retrieve all the song in the dataBase and put them into an Array, Then retrieve in the for loop bellow each song name and artist name
        //retrieve number of song in the playlist
        db=SaveMyMusicDatabase.getInstance(this);
        PlaylistModel playlistToAddSong=db.mPlaylistDao().getPlaylist(playlistName.getText().toString());

        SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistToAddSong.getId());
        sizePlaylistMusic=allSingles.length;
        songName=new TextView[sizePlaylistMusic];
        artistName=new TextView[sizePlaylistMusic];


        linearLayout = findViewById(R.id.linearForPlaylistView);
        ViewGroup.MarginLayoutParams paramsSingle = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsSingle.setMargins(50,25,0,0);
        ViewGroup.MarginLayoutParams paramsArtist = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtist.setMargins(50,0,0,25);
        for (int i = 0; i<sizePlaylistMusic; i++) {
            dynamique = new LinearLayout(this);
            dynamique.setOrientation(LinearLayout.VERTICAL);
            artistName[i] = new TextView(this);
            songName[i] = new TextView(this);
            songName[i].setText(allSingles[i].getSongName());
            songName[i].setTextColor(Color.WHITE);
            songName[i].setTextSize(20);
            songName[i].setSingleLine(true);
            artistName[i].setText(allSingles[i].getArtistName());
            artistName[i].setTextColor(Color.WHITE);
            artistName[i].setTextSize(10);

            artistName[i].setSingleLine(true);
            dynamique.addView(songName[i],paramsSingle);
            dynamique.addView(artistName[i],paramsArtist);

            songName[i].setOnClickListener(this);
            artistName[i].setOnClickListener(this);

            linearLayout.addView(dynamique);
        }
    }
    public void onClick(View v){
        for (int i=0;i<sizePlaylistMusic;i++){
            if (v.equals(songName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",songName[i].getText().toString());
                bundle.putString("ARTIST_NAME",artistName[i].getText().toString());
                bundle.putString("CONTEXT","PlaylistActivity");
                bundle.putString("FRAGMENT_NAME",fragmentName);
                bundle.putString("PLAYLIST_NAME",playlistNameFromFragment);
                bundle.putInt("ALBUM_ID",db.mSingleDao().getSingleFromName(songName[i].getText().toString()).getAlbumId());
                Intent playListActivity = new Intent(PlaylistView.this, Listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(artistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",songName[i].getText().toString());
                bundle.putString("ARTIST_NAME",artistName[i].getText().toString());
                bundle.putString("CONTEXT","PlaylistActivity");
                bundle.putString("FRAGMENT_NAME",fragmentName);
                bundle.putString("PLAYLIST_NAME",playlistNameFromFragment);
                bundle.putInt("ALBUM_ID",db.mSingleDao().getSingleFromName(songName[i].getText().toString()).getAlbumId());
                Intent playListActivity = new Intent(PlaylistView.this, Listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void returnMethod(){
        Bundle bundle = new Bundle();
        bundle.putString("FRAGMENT_NAME",fragmentName);
        bundle.putString("CONTEXT","PlaylistActivity");
        bundle.putString("PLAYLIST_NAME",playlistNameFromFragment);
        Intent playListActivity = new Intent(PlaylistView.this, HomeActivity.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
}
