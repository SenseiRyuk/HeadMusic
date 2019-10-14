package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlaylistView extends AppCompatActivity implements View.OnClickListener {


    private TextView[] songName;
    private TextView[] artistName;
    private TextView playlistName;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private ImageView mImageView;
    private String playlistNameFromFragment;
    private int sizePlaylistMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);
        //Set ImagePlaylist
        setImagePlaylistFromTheDataBase();


        //retrieve number of song in the playlist
        sizePlaylistMusic=10;
        songName=new TextView[sizePlaylistMusic];
        artistName=new TextView[sizePlaylistMusic];

        //Retrieve PlayListName
        playlistNameFromFragment=getIntent().getExtras().getString("PLAYLIST_NAME");
        playlistName=findViewById(R.id.playlistNameInPlaylistView);
        System.out.println("nom de la playlist " + playlistNameFromFragment);
        playlistName.setText(playlistNameFromFragment);
        //USE DATA BASE TO RETRIEVE ALL SONG FOR THIS PLAYLIST
        getAllSongFromTheDataBase();
    }
    public void setImagePlaylistFromTheDataBase(){
        mImageView = findViewById(R.id.playlistImageInPlaylistView);
        android.view.ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height=700;
        params.width=700;
        mImageView.setLayoutParams(params);
        //Exemple pour récupérer l'image ressource suivant un string (le nom de la photo de la playlist --> rap
        Context context=mImageView.getContext();
        int id=context.getResources().getIdentifier("rap","drawable",context.getPackageName());
        mImageView.setImageResource(id);
        mImageView.setAdjustViewBounds(false);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
    public void getAllSongFromTheDataBase(){
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
            songName[i].setText("Universer");
            songName[i].setTextColor(Color.WHITE);
            songName[i].setTextSize(20);
            songName[i].setSingleLine(true);
            artistName[i].setText("Hazy - centrifuge");
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
                Intent playListActivity = new Intent(PlaylistView.this, listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(artistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",songName[i].getText().toString());
                bundle.putString("ARTIST_NAME",artistName[i].getText().toString());
                Intent playListActivity = new Intent(PlaylistView.this, listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
}
