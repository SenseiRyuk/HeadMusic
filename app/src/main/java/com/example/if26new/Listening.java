package com.example.if26new;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SingleModel;
import com.example.if26new.Model.SinglePlaylistModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
public class Listening extends AppCompatActivity implements View.OnClickListener {
    private ImageButton like;
    private ImageButton previousMusic;
    private ImageButton nextMusic;
    private ImageButton playPause;
    private ImageButton switchVideoAudio;
    private ImageButton replaySong;
    private ImageButton addPlaylist;
    private ImageButton lyrics;
    private ImageView photoAlbum;
    private VideoView clipVideo;
    private SeekBar seekBar;
    private TextView totalDurationText;
    private TextView currentDurationText;
    private TextView lyricsText;
    private TextView single;
    private TextView album;
    private boolean weWantUpdate=true;
    private Handler threadHandler = new Handler();
    private UpdateSeekBarThread updateSeekBarThread;
    private int currentPosition;
    private boolean isVideoPlayingWhenLyricsOn=false;
    private Dialog playlistDialog;
    private String songName;
    private String artistName;
    private SaveMyMusicDatabase db;
    private ImageButton[] imageButtonPlaylist;
    private TextView[] playlistTitle;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private int sizePlaylist;
    private int AlbumID;
    private MediaControllerAudio mediaControllerAudio;
    private MediaPlayer mediaPlayerAudio;
    private ImageButton returnButton;
    private String context;
    private String fragmentForSingleInNew;
    private String fragmentName;
    private String PlaylistName;
    private boolean nextMusicBolean;
    private boolean previousMusicBolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        //Init of the android view
        initView();

        //Retrieve the bundles
        retrieveBundle();

        //Set the previous method for the button
        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMusicBolean=true;
            }
        });

        //Set the next method for the button
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusicBolean=true;
            }
        });

        //Set the returnButton méthod
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              returnMethod();
            }
        });

        //Set the lyrics
        setLyricsText();

        //Set photo Music Artist
        setPhotoInListeningView();

        //Set Single and ArtistName
        setSongNameArtistName();

        //Test if the Current Song is like or not
        isLiked();

        //Set playlist
        setPlaylist();

        //Set replaySong;
        setReplaySong();

        //Switch video to audio and audio to video
        switchVideoToAudio();

        //Set the method for the button play-pause
        setPlayPause();

        //Set the seekbar
        setSeekBar();

        //Init of the video
        initVideo();

        //Init of the audio
        initAudio();

        //Init with launch of the music
        intiLaunch();
    }
    public void ShowPopup(){
        playlistDialog.setContentView(R.layout.playlist_pop_up);
        playlistDialog.show();
        linearLayout = playlistDialog.findViewById(R.id.linearForSingle);

        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsPlaylistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsPlaylistName.setMargins(10,60,0,0);

        db=SaveMyMusicDatabase.getInstance(this);
        PlaylistModel[] allPlaylist = db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
        sizePlaylist=allPlaylist.length;
        playlistTitle=new TextView[sizePlaylist];
        imageButtonPlaylist=new ImageButton[sizePlaylist];

        for (int i=0; i<sizePlaylist; i++){
            dynamique = new LinearLayout(this);
            dynamique.setOrientation(LinearLayout.HORIZONTAL);
            if (!allPlaylist[i].getTitles().equals("Favorite")){
                imageButtonPlaylist[i]=new ImageButton(this);
                dynamique.addView(imageButtonPlaylist[i],paramsImageButton);
                imageButtonPlaylist[i].setBackground(null);

                int id = allPlaylist[i].getImage();
                imageButtonPlaylist[i].setImageResource(id);
                imageButtonPlaylist[i].setTag(id);
                android.view.ViewGroup.LayoutParams params = imageButtonPlaylist[i].getLayoutParams();
                params.height=200;
                params.width=200;
                imageButtonPlaylist[i].setLayoutParams(params);
                imageButtonPlaylist[i].setAdjustViewBounds(true);
                imageButtonPlaylist[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButtonPlaylist[i].requestLayout();
                imageButtonPlaylist[i].setOnClickListener(this);

                playlistTitle[i]=new TextView(this);
                playlistTitle[i].setText(allPlaylist[i].getTitles());
                playlistTitle[i].setTextColor(Color.WHITE);
                playlistTitle[i].setTextSize(20);
                playlistTitle[i].setSingleLine(true);
                playlistTitle[i].setOnClickListener(this);

                dynamique.addView(playlistTitle[i],paramsPlaylistName);
                linearLayout.addView(dynamique);
            }else{
                playlistTitle[i]=new TextView(this);
                playlistTitle[i].setText("null");
            }
        }
    }
    public void onClick(View v){
        db=SaveMyMusicDatabase.getInstance(this);
        boolean isAlreadyExist=false;
        boolean playlistEnter=false;
        for (int i=0;i<sizePlaylist;i++){
            if (!playlistTitle[i].getText().toString().equals("null")){
                PlaylistModel playlistToAddSong=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),playlistTitle[i].getText().toString());
                SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistToAddSong.getId());
                if (v.equals(playlistTitle[i])){
                    for (int j=0;j<allSingles.length;j++){
                        if ((allSingles[j].getSongName().equals(songName))&&(allSingles[j].getArtistName().equals(artistName))){
                            isAlreadyExist=true;
                        }
                    }
                    if (isAlreadyExist==false){
                        SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistToAddSong.getId(),songName.toString(),artistName.toString());
                        db.mSinglePlaylistDao().insertSingle(singleToAdd);
                    }
                    unShowPopUp(playlistTitle[i].getText().toString(),isAlreadyExist);
                    playlistEnter=true;
                }else if (v.equals(imageButtonPlaylist[i])){
                    for (int j=0;j<allSingles.length;j++){
                        if ((allSingles[j].getSongName().equals(songName))&&(allSingles[j].getArtistName().equals(artistName))){
                            isAlreadyExist=true;
                        }
                    }
                    if (isAlreadyExist==false){
                        SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistToAddSong.getId(),songName.toString(),artistName.toString());
                        db.mSinglePlaylistDao().insertSingle(singleToAdd);
                    }
                    unShowPopUp(playlistTitle[i].getText().toString(),isAlreadyExist);
                    playlistEnter=true;
                }
            }
        }
        if (playlistEnter==false){
            Toast toast;
            PlaylistModel playlistLike=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
            if (like.getDrawable().getConstantState().equals(getDrawable(R.drawable.likenoclick).getConstantState())){
                like.setImageResource(R.drawable.likeonclick);
                SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistLike.getId(),songName.toString(),artistName.toString());
                db.mSinglePlaylistDao().insertSingle(singleToAdd);
                toast = Toast.makeText(getApplicationContext(), "Music add in playlist : Favorite ", Toast.LENGTH_SHORT);
                toast.show();
            }else if (like.getDrawable().getConstantState().equals(getDrawable(R.drawable.likeonclick).getConstantState())){
                SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistLike.getId());
                for (int j=0;j<allSingles.length;j++){
                    if ((allSingles[j].getSongName().equals(songName))&&(allSingles[j].getArtistName().equals(artistName))){
                        db.mSinglePlaylistDao().deleteSingle(allSingles[j].getId());
                        toast = Toast.makeText(getApplicationContext(), "Music delete in playlist : Favorite ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                like.setImageResource(R.drawable.likenoclick);
            }
        }
    }
    public void unShowPopUp(String playlistName,boolean isAlreadyExist){
        playlistDialog.dismiss();
        Toast toast;
        if (isAlreadyExist==false){
            toast = Toast.makeText(getApplicationContext(), "Music add in playlist : "+playlistName, Toast.LENGTH_SHORT);
            toast.show();
        }else{
            toast = Toast.makeText(getApplicationContext(), "This Song is already in playlist : "+playlistName, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void  returnMethod(){
        Bundle bundle=new Bundle();
        switch (context){
            case "HomeActivity":
                bundle.putString("FRAGMENT_NAME",fragmentForSingleInNew);
                bundle.putString("FRAGMENT",fragmentForSingleInNew);
                bundle.putString("CONTEXT",context);
                Intent returnhome= new Intent(Listening.this,HomeActivity.class);
                returnhome.putExtras(bundle);
                startActivity(returnhome);
                break;
            case "AlbumActivity":
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromId(AlbumID).getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromId(AlbumID).getImage());
                bundle.putInt("ALBUM_ID",AlbumID);
                bundle.putString("ARTIST_NAME",artistName);
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent Album = new Intent(Listening.this, Album_view.class);
                Album.putExtras(bundle);
                startActivity(Album);
                break;
            case "ArtistActivity":
                bundle.putString("ARTIST_NAME",artistName);
                bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(artistName).getImage());
                bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistName).getBio());
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent Artist = new Intent(Listening.this, ActivityArtist.class);
                Artist.putExtras(bundle);
                startActivity(Artist);
                break;
            case "PlaylistActivity":
                PlaylistModel currentPlaylist=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),PlaylistName);
                bundle.putString("PLAYLIST_NAME",currentPlaylist.getTitles());
                bundle.putInt("PLAYLIST_IMAGE_ID",currentPlaylist.getImage());
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent playListActivity = new Intent(Listening.this, PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
                break;
            case "SearchViewActivity":
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent search = new Intent(Listening.this, HomeActivity.class);
                search.putExtras(bundle);
                startActivity(search);
                break;
        }
    }

    public void goPreviousMusic(){
        Bundle bundle=new Bundle();
        int position=0;
        Intent switchListening;
        switch (context) {
            case "AlbumActivity":
                SingleModel[] allSingleInAlbum=db.mSingleDao().getSingleFromAlbum(AlbumID);
                for (int i=0;i<allSingleInAlbum.length;i++){
                    if (allSingleInAlbum[i].getTitleSingle().equals(songName)){
                        position=i;
                    }
                }
                if (position==0){
                    bundle.putString("SONG_NAME", allSingleInAlbum[allSingleInAlbum.length-1].getTitleSingle());
                }else{
                    bundle.putString("SONG_NAME", allSingleInAlbum[position-1].getTitleSingle());
                }
                bundle.putString("ARTIST_NAME", artistName);
                bundle.putInt("ALBUM_ID", AlbumID);
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "PlaylistActivity":
                SinglePlaylistModel[] allSinglePlaylist=db.mSinglePlaylistDao().getSinglesFromPlaylist(db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),PlaylistName).getId());
                for (int i=0;i<allSinglePlaylist.length;i++){
                    if (allSinglePlaylist[i].getSongName().equals(songName)){
                        position=i;
                    }
                }
                SingleModel[] singleInAlbum=db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(allSinglePlaylist[0].getArtistName()).getId());
                if (position==0){
                    bundle.putString("SONG_NAME", allSinglePlaylist[allSinglePlaylist.length-1].getSongName());
                    bundle.putString("ARTIST_NAME",allSinglePlaylist[allSinglePlaylist.length-1].getArtistName());
                    for (int i=0;i<singleInAlbum.length;i++){
                        if (singleInAlbum[i].getTitleSingle().equals(allSinglePlaylist[allSinglePlaylist.length-1].getSongName())){
                            bundle.putInt("ALBUM_ID",singleInAlbum[i].getAlbumId() );
                        }
                    }
                }else{
                    bundle.putString("SONG_NAME", allSinglePlaylist[position-1].getSongName());
                    bundle.putString("ARTIST_NAME",allSinglePlaylist[position-1].getArtistName());
                    for (int i=0;i<singleInAlbum.length;i++){
                        if (singleInAlbum[i].getTitleSingle().equals(allSinglePlaylist[position-1].getSongName())){
                            bundle.putInt("ALBUM_ID",singleInAlbum[i].getAlbumId() );
                        }
                    }
                }
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME",PlaylistName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "ArtistActivity":
                SingleModel [] allArtistSingle=db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(artistName).getId());
                for (int i=0;i<allArtistSingle.length;i++){
                    if (allArtistSingle[i].getTitleSingle().equals(songName)){
                        position=i;
                    }
                }
                if (position==0){
                    bundle.putString("SONG_NAME", allArtistSingle[allArtistSingle.length-1].getTitleSingle());
                    bundle.putInt("ALBUM_ID", allArtistSingle[allArtistSingle.length-1].getAlbumId());
                }else{
                    bundle.putString("SONG_NAME", allArtistSingle[position-1].getTitleSingle());
                    bundle.putInt("ALBUM_ID", allArtistSingle[position-1].getAlbumId());
                }
                bundle.putString("ARTIST_NAME", artistName);
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;


        }
        if (fragmentForSingleInNew.equals("NewsFragment")){
            SingleModel [] allNewSingle=db.mSingleDao().getSingleFromNew(true);
            for (int i=0;i<allNewSingle.length;i++){
                if (allNewSingle[i].getTitleSingle().equals(songName)){
                    position=i;
                }
            }
            if (position==0){
                bundle.putString("SONG_NAME", allNewSingle[allNewSingle.length-1].getTitleSingle());
                bundle.putInt("ALBUM_ID", allNewSingle[allNewSingle.length-1].getAlbumId());
                bundle.putString("ARTIST_NAME", db.mArtistDao().getArtistFromId(allNewSingle[allNewSingle.length-1].getArtistId()).getName());
            }else{
                bundle.putString("SONG_NAME", allNewSingle[position-1].getTitleSingle());
                bundle.putInt("ALBUM_ID", allNewSingle[position-1].getAlbumId());
                bundle.putString("ARTIST_NAME", db.mArtistDao().getArtistFromId(allNewSingle[position-1].getArtistId()).getName());
            }
            bundle.putString("CONTEXT", context);
            bundle.putString("FRAGMENT_NAME", fragmentName);
            switchListening = new Intent(Listening.this, Listening.class);
            switchListening.putExtras(bundle);
            startActivity(switchListening);
        }
    }
    public void goNextMusic(){
        int position=0;
        Bundle bundle=new Bundle();
        Intent switchListening;
        switch (context) {
            case "AlbumActivity":
                SingleModel[] allSingleInAlbum=db.mSingleDao().getSingleFromAlbum(AlbumID);
                for (int i=0;i<allSingleInAlbum.length;i++){
                    if (allSingleInAlbum[i].getTitleSingle().equals(songName)){
                        position=i;
                    }
                }
                if (position==allSingleInAlbum.length-1){
                    bundle.putString("SONG_NAME", allSingleInAlbum[0].getTitleSingle());
                }else{
                    bundle.putString("SONG_NAME", allSingleInAlbum[position+1].getTitleSingle());
                }
                bundle.putString("ARTIST_NAME", artistName);
                bundle.putInt("ALBUM_ID", AlbumID);
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "PlaylistActivity":
                SinglePlaylistModel[] allSinglePlaylist=db.mSinglePlaylistDao().getSinglesFromPlaylist(db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),PlaylistName).getId());
                for (int i=0;i<allSinglePlaylist.length;i++){
                    if (allSinglePlaylist[i].getSongName().equals(songName)){
                        position=i;
                    }
                }
                SingleModel[] singleInAlbum=db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(allSinglePlaylist[0].getArtistName()).getId());
                if (position==allSinglePlaylist.length-1){
                    bundle.putString("SONG_NAME", allSinglePlaylist[0].getSongName());
                    bundle.putString("ARTIST_NAME",allSinglePlaylist[0].getArtistName());
                    for (int i=0;i<singleInAlbum.length;i++){
                        if (singleInAlbum[i].getTitleSingle().equals(allSinglePlaylist[0].getSongName())){
                            bundle.putInt("ALBUM_ID",singleInAlbum[i].getAlbumId() );
                        }
                    }
                }else{
                    bundle.putString("SONG_NAME", allSinglePlaylist[position+1].getSongName());
                    bundle.putString("ARTIST_NAME",allSinglePlaylist[position+1].getArtistName());
                    for (int i=0;i<singleInAlbum.length;i++){
                        if (singleInAlbum[i].getTitleSingle().equals(allSinglePlaylist[position+1].getSongName())){
                            bundle.putInt("ALBUM_ID",singleInAlbum[i].getAlbumId() );
                        }
                    }
                }
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME",PlaylistName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "ArtistActivity":
                SingleModel [] allArtistSingle=db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(artistName).getId());
                for (int i=0;i<allArtistSingle.length;i++){
                    if (allArtistSingle[i].getTitleSingle().equals(songName)){
                        position=i;
                    }
                }
                if (position==allArtistSingle.length-1){
                    bundle.putString("SONG_NAME", allArtistSingle[0].getTitleSingle());
                    bundle.putInt("ALBUM_ID", allArtistSingle[0].getAlbumId());
                }else{
                    bundle.putString("SONG_NAME", allArtistSingle[position+1].getTitleSingle());
                    bundle.putInt("ALBUM_ID", allArtistSingle[position+1].getAlbumId());
                }
                bundle.putString("ARTIST_NAME", artistName);
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                switchListening = new Intent(Listening.this, Listening.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
        }
        if (fragmentForSingleInNew.equals("NewsFragment")){
            SingleModel [] allNewSingle=db.mSingleDao().getSingleFromNew(true);
            for (int i=0;i<allNewSingle.length;i++){
                if (allNewSingle[i].getTitleSingle().equals(songName)){
                    position=i;
                }
            }
            if (position==allNewSingle.length-1){
                bundle.putString("SONG_NAME", allNewSingle[0].getTitleSingle());
                bundle.putInt("ALBUM_ID", allNewSingle[0].getAlbumId());
                bundle.putString("ARTIST_NAME", db.mArtistDao().getArtistFromId(allNewSingle[0].getArtistId()).getName());
            }else{
                bundle.putString("SONG_NAME", allNewSingle[position+1].getTitleSingle());
                bundle.putInt("ALBUM_ID", allNewSingle[position+1].getAlbumId());
                bundle.putString("ARTIST_NAME", db.mArtistDao().getArtistFromId(allNewSingle[position+1].getArtistId()).getName());


            }
            bundle.putString("CONTEXT", context);
            bundle.putString("FRAGMENT_NAME", fragmentName);
            bundle.putString("FRAGMENT",fragmentForSingleInNew);
            switchListening = new Intent(Listening.this, Listening.class);
            switchListening.putExtras(bundle);
            startActivity(switchListening);
        }
    }
    public void initView(){
        db=SaveMyMusicDatabase.getInstance(this);
        playlistDialog=new Dialog(this);
        like=findViewById(R.id.likeButton);
        previousMusic=findViewById(R.id.previousMusic);
        playPause=findViewById(R.id.playButton);
        switchVideoAudio=findViewById(R.id.switchVideoAudio);
        replaySong=findViewById(R.id.replayButton);
        addPlaylist=findViewById(R.id.addPlaylistButton);
        photoAlbum=findViewById(R.id.imageAlbum);
        clipVideo=findViewById(R.id.videoView);
        seekBar=findViewById(R.id.seekBarMusic);
        totalDurationText=findViewById(R.id.totalDuration);
        currentDurationText=findViewById(R.id.currentDuration);
        lyrics=findViewById(R.id.lyricsButton);
        lyricsText=findViewById(R.id.lyricsText);
        single=findViewById(R.id.ArtistIDinListening);
        album=findViewById(R.id.AlbumIDinListening);
        returnButton=findViewById(R.id.retunrListening);
        previousMusic=findViewById(R.id.previousMusic);
        nextMusic=findViewById(R.id.nextMusic);
        like.setOnClickListener(this);
    }
    // Find ID corresponding to the name of the resource (in the directory raw).
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds)-(minutes*60) ;
        if ((seconds<10)&& (seconds>=0)){
            return minutes+":0"+ seconds;

        }else{
            return minutes+":"+ seconds;
        }
    }
    public void doStart()  {
        // The duration in milliseconds
        if(this.millisecondsToString(this.mediaPlayerAudio.getCurrentPosition()).equals(this.millisecondsToString(this.mediaPlayerAudio.getDuration())))  {
            // Resets the MediaPlayer to its uninitialized state.
            this.mediaPlayerAudio.reset();
        }
        this.mediaPlayerAudio.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,200);
    }
    // When user click to "Pause".
    public void doPause()  {
        this.mediaPlayerAudio.pause();
    }
    public void videoDoStart(){
        // The duration in milliseconds
        this.clipVideo.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,200);
    }
    public void goTOTimeSong(int value){
        if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
            mediaPlayerAudio.seekTo(value);

        }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
            clipVideo.seekTo(value);
        }
    }
    public void retrieveBundle(){
        songName=getIntent().getExtras().getString("SONG_NAME");
        artistName=getIntent().getExtras().getString("ARTIST_NAME");
        AlbumID=getIntent().getExtras().getInt("ALBUM_ID");
        context=getIntent().getExtras().getString("CONTEXT");
        fragmentForSingleInNew=getIntent().getExtras().getString("FRAGMENT","null");
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        PlaylistName=getIntent().getExtras().getString("PLAYLIST_NAME");
    }
    public void setPhotoInListeningView(){
        if (AlbumID!=0){
            photoAlbum.setImageResource(db.mAlbumDao().getAlbumFromId(AlbumID).getImage());
            photoAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromId(AlbumID).getTitleAlbum());
                    bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromId(AlbumID).getImage());
                    bundle.putInt("ALBUM_ID",AlbumID);
                    bundle.putString("ARTIST_NAME",artistName);
                    if (fragmentForSingleInNew!="null"){
                        bundle.putString("FRAGMENT_NAME",fragmentForSingleInNew);
                    }else{
                        bundle.putString("FRAGMENT_NAME",fragmentName);
                    }
                    Intent Album = new Intent(Listening.this, Album_view.class);
                    Album.putExtras(bundle);
                    startActivity(Album);
                }
            });
        }else if (AlbumID==0){
            photoAlbum.setImageResource(db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getImage());
            photoAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("ARTIST_NAME",artistName);
                    bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(artistName).getImage());
                    bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistName).getBio());
                    if (fragmentForSingleInNew!="null"){
                        bundle.putString("FRAGMENT_NAME",fragmentForSingleInNew);
                    }else{
                        bundle.putString("FRAGMENT_NAME",fragmentName);
                    }                    Intent Artist = new Intent(Listening.this, ActivityArtist.class);
                    Artist.putExtras(bundle);
                    startActivity(Artist);
                }
            });
        }
        photoAlbum.setAdjustViewBounds(false);
        photoAlbum.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
    public void setLyricsText(){
        //Read The TXT File for the lyrics ....
        //Name IN ROW IN minuscule
        lyricsText.setMovementMethod(new ScrollingMovementMethod());
        InputStream inputStream = getResources().openRawResource(db.mSingleDao().getSingleFromName(songName).getLyrics());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lyricsText.setText(byteArrayOutputStream.toString());
        lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lyrics.getDrawable().getConstantState().equals(getDrawable(R.drawable.lyrics).getConstantState())){
                    lyrics.setImageResource(R.drawable.lyricsonclick);
                    switchVideoAudio.setEnabled(false);
                    switchVideoAudio.setAlpha(0.3f);
                    if (clipVideo.isPlaying()){
                        clipVideo.setAlpha(0f);
                        isVideoPlayingWhenLyricsOn=true;
                    }else if ((clipVideo.isPlaying()==false)&&(switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState()))){
                        clipVideo.setAlpha(0f);
                    }else if (mediaPlayerAudio.isPlaying()){
                        photoAlbum.setVisibility(View.INVISIBLE);
                    }else if ((mediaPlayerAudio.isPlaying()==false)&&(switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState()))){
                        photoAlbum.setVisibility(View.INVISIBLE);
                    }
                    lyricsText.setVisibility(View.VISIBLE);
                }else if (lyrics.getDrawable().getConstantState().equals(getDrawable(R.drawable.lyricsonclick).getConstantState())){
                    lyrics.setImageResource(R.drawable.lyrics);
                    switchVideoAudio.setEnabled(true);
                    switchVideoAudio.setAlpha(1f);
                    //on retourne sur la video
                    if (isVideoPlayingWhenLyricsOn==true){
                        //on retourne sur la video
                        switchVideoAudio.setImageResource(R.drawable.audio);
                        clipVideo.setAlpha(1f);
                        photoAlbum.setVisibility(View.INVISIBLE);
                        lyricsText.setVisibility(View.INVISIBLE);
                    }//On retourne sur l'audio
                    else {
                        //on retourne sur l'audio
                        switchVideoAudio.setImageResource(R.drawable.video);
                        clipVideo.setVisibility(View.INVISIBLE);
                        photoAlbum.setVisibility(View.VISIBLE);
                        lyricsText.setVisibility(View.INVISIBLE);
                    }
                    isVideoPlayingWhenLyricsOn=false;
                }
            }
        });
    }
    public void setSongNameArtistName(){
        single.setText(songName);
        album.setText(artistName);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",artistName);
                bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(artistName).getImage());
                bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(artistName).getBio());
                Intent playListActivity = new Intent(Listening.this, ActivityArtist.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        });
    }
    public void isLiked(){
        PlaylistModel playlistLike=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
        SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistLike.getId());
        for (int j=0;j<allSingles.length;j++){
            if ((allSingles[j].getSongName().equals(songName))&&(allSingles[j].getArtistName().equals(artistName))){
                like.setImageResource(R.drawable.likeonclick);
            }
        }
    }
    public void initVideo(){
        try {
            // ID of video file.
            int id = db.mSingleDao().getSingleFromName(songName).getVideo();
            clipVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        clipVideo.requestFocus();
    }
    public void initMediaControllerAudio(){
        int songId = db.mSingleDao().getSingleFromName(songName).getMusic();
        if (mediaControllerAudio.getMediaPlayerAudio()!=null){
            if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()){
                mediaControllerAudio.getMediaPlayerAudio().pause();
            }
        }
        this.mediaPlayerAudio=MediaPlayer.create(this,songId);
        mediaControllerAudio.setMediaPlayerAudio(this.mediaPlayerAudio);
        mediaControllerAudio.setSongName(songName);
        mediaControllerAudio.setArtistName(artistName);
        mediaControllerAudio.setAlbumID(AlbumID);
        currentDurationText.setText("0:00");
    }
    public void initAudio(){
        // Create MediaPlayer.
        mediaControllerAudio=MediaControllerAudio.getInstance();
        //The first case is when the user listening to a song for the first time
        if (mediaControllerAudio.getMediaPlayerAudio()==null){
                initMediaControllerAudio();
        }else{
           //if user listening to a song we test if the "new" song is the current listening song
            if ((!songName.equals(mediaControllerAudio.getSongName())) || (!artistName.equals(mediaControllerAudio.getArtistName()))){
                initMediaControllerAudio();
            }else{
                this.mediaPlayerAudio=mediaControllerAudio.getMediaPlayerAudio();
                if (mediaPlayerAudio.isPlaying()==true){
                    playPause.setImageResource(R.drawable.pauselistening);
                    doStart();
                }else{
                    seekBar.setProgress(mediaPlayerAudio.getCurrentPosition());
                    String currentPositionStr = millisecondsToString(mediaPlayerAudio.getCurrentPosition());
                    currentDurationText.setText(currentPositionStr);
                }
            }
        }
        totalDurationText.setText(this.millisecondsToString(this.mediaPlayerAudio.getDuration()));
        seekBar.setMax(this.mediaPlayerAudio.getDuration());
        seekBar.setMin(0);

        if (AlbumID==0){
            mediaControllerAudio.setArtist(true);
        }else{
            mediaControllerAudio.setArtist(false);
        }
    }
    public void setPlaylist(){
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup();
            }
        });
    }
    public void setReplaySong(){
        replaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            //The replay Part is in the second class
            public void onClick(View view) {
                if (replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.loop).getConstantState())){
                    replaySong.setImageResource(R.drawable.looponclick);
                    mediaPlayerAudio.setLooping(true);
                }else if (replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.looponclick).getConstantState())){
                    replaySong.setImageResource(R.drawable.loop);
                    mediaPlayerAudio.setLooping(false);
                }
            }
        });
    }
    public void switchVideoToAudio(){
        switchVideoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                    switchVideoAudio.setImageResource(R.drawable.audio);
                    clipVideo.setVisibility(View.VISIBLE);
                    photoAlbum.setVisibility(View.INVISIBLE);
                    lyricsText.setVisibility(View.INVISIBLE);
                    if (mediaPlayerAudio.isPlaying()){
                        doPause();
                    }
                    clipVideo.seekTo(currentPosition);
                    seekBar.setProgress(currentPosition);
                    if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())) {
                        videoDoStart();
                    }
                }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                    switchVideoAudio.setImageResource(R.drawable.video);
                    if (clipVideo.isPlaying()){
                        clipVideo.pause();
                    }
                    clipVideo.setVisibility(View.INVISIBLE);
                    photoAlbum.setVisibility(View.VISIBLE);
                    lyricsText.setVisibility(View.INVISIBLE);
                    mediaPlayerAudio.seekTo(currentPosition);
                    seekBar.setProgress(currentPosition);
                    if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())) {
                        doStart();
                    }
                }
            }
        });
    }
    public void setPlayPause(){
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())){
                    playPause.setImageResource(R.drawable.pauselistening);
                    //Case we are in audio or video clip
                    if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                        doStart();
                    }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                        videoDoStart();
                    }
                    //Remove the music...
                }else if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())){
                    //Add the music ...
                    if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                        doPause();
                    }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                        clipVideo.pause();
                    }
                    playPause.setImageResource(R.drawable.playlistening);
                }
            }
        });
    }
    public void setSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                weWantUpdate=false;// we don't want update when we want to choose the time
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                weWantUpdate=true;
                updateSeekBarThread= new UpdateSeekBarThread();
                threadHandler.postDelayed(updateSeekBarThread,200);
                goTOTimeSong(seekBar.getProgress());
            }
        });
    }
    public void intiLaunch(){
        doStart();
        if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())) {
            playPause.setImageResource(R.drawable.pauselistening);
        }
    }
    // Thread to Update position for SeekBar.
    class UpdateSeekBarThread implements Runnable {
        public void run() {
            if (weWantUpdate==true) {
                if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                    currentPosition = mediaPlayerAudio.getCurrentPosition();
                }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                    currentPosition=clipVideo.getCurrentPosition();
                    if (((int)currentPosition/1000==(int)mediaPlayerAudio.getDuration()/1000)&&(replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.looponclick).getConstantState()))){
                        clipVideo.start();
                    }
                }
                currentDurationText.setText(millisecondsToString(currentPosition));
                seekBar.setProgress(currentPosition);
            }
            if(nextMusicBolean==true){
                goNextMusic();
                nextMusicBolean=false;
                return;
            }else if(previousMusicBolean==true){
                goPreviousMusic();
                previousMusicBolean=false;
                return;
            }
            if ((((currentPosition/1000)==(mediaPlayerAudio.getDuration()/1000)) && ((replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.loop).getConstantState()))))){
                goNextMusic();
                nextMusicBolean=false;
                return;
            }
            //We do this beacause when we switch audio to video during a short time the video is unplaying. Thus the pausePlay button get the pause value
            //So when we start the video we ensure that the playPause btn is set in the rigth way
            if ((clipVideo.isPlaying()==true)&&(playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState()))){
                playPause.setImageResource(R.drawable.pauselistening);
            }
            threadHandler.postDelayed(this, 200);
        }

    }
}
