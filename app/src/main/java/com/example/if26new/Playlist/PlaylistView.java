package com.example.if26new.Playlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.if26new.Artists.Deezer.Track.RV_Adapter_Artist_Track;
import com.example.if26new.HomeActivity;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class PlaylistView extends AppCompatActivity {

    private TextView[] songName;
    private TextView[] artistName;
    private TextView playlistName;
    private ImageView mImageView;
    private String playlistNameFromFragment;
    private int sizePlaylistMusic;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;
    private ConstraintLayout background;
    private PlaylistModel playlistToAddSong;
    private SinglePlaylistModel [] allTracks;
    private TextView numberofTracks;

    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Artist_Track adapteur;
    private RecyclerView recyclerView;
    private int imagePlaylistID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        //Set ImagePlaylist
        imagePlaylistID=0;
        db=SaveMyMusicDatabase.getInstance(this);
        retrieveBundle();
        initPlaylistInfo();
        initView();
        initRecyclerView();
        getAllSongFromTheDataBase();
    }
    public void retrieveBundle(){
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        playlistNameFromFragment=getIntent().getExtras().getString("PLAYLIST_NAME");
        imagePlaylistID=getIntent().getExtras().getInt("PLAYLIST_IMAGE_ID");
    }
    public void initRecyclerView(){
        recyclerView=findViewById(R.id.RV_Playlist_Activity);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_Artist_Track(this.informationForAdapteur,this);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    public void initView(){
        background=findViewById(R.id.LayoutPlaylist);

        playlistName=findViewById(R.id.playlistNameInPlaylistView);
        playlistName.setText(playlistNameFromFragment);

        numberofTracks=findViewById(R.id.numberOfTrackInPlaylistView);
        numberofTracks.setText(sizePlaylistMusic+" Tracks");

        mImageView = findViewById(R.id.playlistImageInPlaylistView);
        mImageView.setAdjustViewBounds(false);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (playlistNameFromFragment.equals("Favorite") || playlistNameFromFragment.equals("Recognition") && imagePlaylistID!=0){
            mImageView.setImageResource(imagePlaylistID);
        }else if (playlistToAddSong.getImageDeezer()!=null){
            initializeImageAlbum(playlistToAddSong.getImageDeezer());
        }
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playlistNameFromFragment.equals("Favorite")&& !playlistNameFromFragment.equals("Recognition")){
                    Random r = new Random();
                    int low = 0;
                    int high = sizePlaylistMusic;
                    int result = r.nextInt(high-low) + low;
                    initializeImageAlbum(allTracks[result].getAlbumImage());
                    playlistToAddSong.setImageDeezer(allTracks[result].getAlbumImage());
                    db.mPlaylistDao().updatePlaylist(playlistToAddSong);
                }
            }
        });
        returnButton=findViewById(R.id.returnButtonPlaylist);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });
    }
    public void initPlaylistInfo(){
        playlistToAddSong=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),playlistNameFromFragment);
        allTracks=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistToAddSong.getId());
        sizePlaylistMusic=allTracks.length;
    }
    public void initializeImageAlbum(final String urlArtistImage){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImageView.setImageBitmap(ICON);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public void getAllSongFromTheDataBase(){
        for (int i=0; i<sizePlaylistMusic; i++){
            ArrayList<String> arrayListTrack=new ArrayList<>();
            arrayListTrack.add(allTracks[i].getArtistName());
            arrayListTrack.add(allTracks[i].getSongName());
            arrayListTrack.add(allTracks[i].getSongID());
            arrayListTrack.add("UserFragment");
            arrayListTrack.add(allTracks[i].getTrackDuration());
            arrayListTrack.add("Call_From_Playlist_Local_Activity");
            arrayListTrack.add(playlistNameFromFragment);
            informationForAdapteur.add(arrayListTrack);
            adapteur.notifyItemChanged(i);
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
