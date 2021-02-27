package com.example.if26new.Listening;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.if26new.Albums.Album_view_For_Deezer;
import com.example.if26new.Artists.Deezer.ActivityArtistForDeezerSearch;
import com.example.if26new.Model.HistoricModel;
import com.example.if26new.Playlist.Deezer_Playlist;
import com.example.if26new.HomeActivity;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;
import com.example.if26new.Model.UserModel;
import com.example.if26new.Playlist.PlaylistView;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ListeningForDeezer extends AppCompatActivity implements View.OnClickListener {
    private ImageButton like;
    private ImageButton previousMusic;
    private ImageButton nextMusic;
    private ImageButton playPause;
    private Switch switchVideoAudio;
    private ImageButton replaySong;
    private ImageButton addPlaylist;
    private ImageView photoAlbum;
    private VideoView clipVideo;
    private SeekBar seekBar;
    private TextView totalDurationText;
    private TextView currentDurationText;
    private TextView lyricsText;
    private TextView single;
    private TextView artist;
    private boolean weWantUpdate=true;
    private Handler threadHandler = new Handler();
    private ListeningForDeezer.UpdateSeekBarThread updateSeekBarThread;
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
    private String albumImage;
    private MediaControllerAudio mediaControllerAudio;
    private MediaPlayer mediaPlayerAudio;
    private ImageButton returnButton;
    private String context;
    private String fragmentForSingleInNew;
    private String fragmentName;
    private String PlaylistName;
    private boolean nextMusicBolean;
    private boolean previousMusicBolean;
    private ConstraintLayout background;
    private boolean firstTouche;
    private float beginingX;
    private float beginingY;
    private float endX;
    private float endY;
    private boolean isSwitchImageView;
    private AlphaAnimation alphaAnimation;
    private String musicMP3URL;
    private Bitmap mIcon_val;
    private String[] songsInAlbum;
    private String[] urlMP3AllMusic;
    private String albumIDForDeezer=null;
    private String albumName;
    private String artistImageURL;
    private String artistID;
    private Pattern pattern=Pattern.compile(" - ");
    private int isCallFromArtist;
    private String trackDuration="2:42";
    private JSONObject jsonObjectForDeezer;
    private String songID;

    private String playlistImage;
    private String playlistID;
    private String previous_Song_ID;
    private String next_Song_ID;
    private JSONArray tracksArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        //Init of the android view
        initView();

        //Retrieve the bundles
        retrieveBundle();

        //Add to historic
        addToHistoric();

        retrieve_Track_information();

        //Set the lyrics
            //setLyricsText();

        //Test if the Current Song is like or not
        isLiked();

        //Set playlist
        setPlaylist();

        //Set replaySong;
        setReplaySong();

        //Switch video to audio and audio to video
            //switchVideoToAudio();

        //Set the method for the button play-pause
        setPlayPause();

        //Set the seekbar
        setSeekBar();

        //Init of the video
            //initVideo();

    }

    /////////////////////////////////////////////////////////////
    ////////////////////////INIT VIEW-RETRIEVE BUNDLE////////////
    /////////////////////////////////////////////////////////////
    public void initView(){
        db=SaveMyMusicDatabase.getInstance(this);
        background=findViewById(R.id.layoutListening);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        background.setBackground(gd);
        playlistDialog=new Dialog(this);
        like=findViewById(R.id.likeButton);
        previousMusic=findViewById(R.id.previousMusic);
        playPause=findViewById(R.id.playButton);
        switchVideoAudio=findViewById(R.id.switch_Video_Audio);
        switchVideoAudio.setEnabled(false);
        replaySong=findViewById(R.id.replayButton);
        addPlaylist=findViewById(R.id.addPlaylistButton);
        photoAlbum=findViewById(R.id.imageAlbum);
        clipVideo=findViewById(R.id.videoView);
        seekBar=findViewById(R.id.seekBarMusic);
        totalDurationText=findViewById(R.id.totalDuration);
        currentDurationText=findViewById(R.id.currentDuration);
        lyricsText=findViewById(R.id.lyricsText);
        single=findViewById(R.id.ArtistIDinListening);
        artist=findViewById(R.id.AlbumIDinListening);
        returnButton=findViewById(R.id.retunrListening);
        previousMusic=findViewById(R.id.previousMusic);
        nextMusic=findViewById(R.id.nextMusic);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeOnClick();
            }
        });
        DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(like.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        switchVideoAudio.setHighlightColor(Color.DKGRAY);
        DrawableCompat.setTint(returnButton.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(replaySong.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(previousMusic.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(nextMusic.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        DrawableCompat.setTint(addPlaylist.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
//        DrawableCompat.setTint(lyrics.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        seekBar.getProgressDrawable().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(), PorterDuff.Mode.MULTIPLY);


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
    }
    public void retrieveBundle(){
        songID=getIntent().getExtras().getString("SONG_ID");
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        PlaylistName=getIntent().getExtras().getString("PLAYLIST_NAME");
        isCallFromArtist=getIntent().getExtras().getInt("IS_FROM_ARTIST_VIEW");
        context=getIntent().getExtras().getString("CONTEXT","null");
        fragmentForSingleInNew=getIntent().getExtras().getString("FRAGMENT","null");

    }
    public void addToHistoric(){
        System.out.println("SONG ID "+songID);
        HistoricModel trackToAddInHistoric=new HistoricModel(songID,db.getActualUser());
        trackToAddInHistoric.setTrackID(songID);
        db.mHistoricDao().insertTrackInHistoric(trackToAddInHistoric);
        HistoricModel[] allHistoricTrack=db.mHistoricDao().getAllTrackHistoric();
        for (int i=0;i<allHistoricTrack.length;i++){
            System.out.println("AAAAAAAAa----- "+allHistoricTrack[i].getTrackID());
        }
    }
    /////////////////////////////////////////////////////////////
    ////////////////////////TRACK INFORMATION////////////////////
    /////////////////////////////////////////////////////////////
    public void retrieve_Track_information(){
        Thread thread_Track_information=new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject track_JSONObject=getDeererInformationFromDeezerServer("https://api.deezer.com/track/"+songID);
                retrieve_Album_Information(track_JSONObject);
                retrieve_Artist_Information(track_JSONObject);
                retrieve_Song_Information(track_JSONObject);
            }
        });
        thread_Track_information.start();
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////ALBUM/SONG///////////////////////
    /////////////////////////////////////////////////////////////
    public void setPhotoInListeningView(){
        firstTouche=false;
        getImageFromDeezerURL(albumImage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoAlbum.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(firstTouche==false){
                            beginingX=event.getX();
                            beginingY=event.getY();
                            firstTouche=true;
                        }
                        endX=event.getX();
                        endY=event.getY();
                        return false;
                    }
                });
                photoAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(beginingX-endX>150){
                            goNextMusic();
                            nextMusicBolean=true;
                        }else if (beginingX-endX <-150){
                            goPreviousMusic();
                            previousMusicBolean=true;
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("ALBUM_ID",albumIDForDeezer);
                            bundle.putString("FRAGMENT_NAME",fragmentName);
                            Intent Album = new Intent(ListeningForDeezer.this, Album_view_For_Deezer.class);
                            Album.putExtras(bundle);
                            startActivity(Album);
                        }
                    }
                });
            }
        });
    }
    public void getImageFromDeezerURL(final String imageURL){
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoAlbum.setImageBitmap(mIcon_val);
            }
        });
    }
    public void goToArtistView(){

        //FAIRE LES RECHERCHE AVEC L'ID DE L'artist
        //getArtistInformation(artistID);
        Bundle bundle=new Bundle();
        bundle.putString("ARTIST_ID",artistID);
        bundle.putString("FRAGMENT_NAME",fragmentName);

        Intent Artist = new Intent(ListeningForDeezer.this, ActivityArtistForDeezerSearch.class);
        Artist.putExtras(bundle);
        startActivity(Artist);
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////RETURN///////////////////////////
    /////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void returnMethod(){
        Bundle bundle=new Bundle();
        switch (context) {
            case "HomeActivity":
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("FRAGMENT", fragmentForSingleInNew);
                bundle.putString("CONTEXT", context);
                Intent returnhome = new Intent(ListeningForDeezer.this, HomeActivity.class);
                returnhome.putExtras(bundle);
                startActivity(returnhome);
                break;
            case "AlbumActivity":
                bundle.putString("ALBUM_ID", albumIDForDeezer);
                bundle.putString("FRAGMENT_NAME", fragmentName);

                Intent Album = new Intent(ListeningForDeezer.this, Album_view_For_Deezer.class);
                Album.putExtras(bundle);
                startActivity(Album);
                break;
            case "ArtistActivity":
                goToArtistView();
                break;
            case "PlaylistActivity":
                PlaylistModel currentPlaylist = db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(), PlaylistName);
                bundle.putString("PLAYLIST_NAME", currentPlaylist.getTitles());
                bundle.putInt("PLAYLIST_IMAGE_ID", currentPlaylist.getImage());
                bundle.putString("FRAGMENT_NAME", fragmentName);
                Intent playListActivity = new Intent(ListeningForDeezer.this, PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
                break;
            case "SearchViewActivity":
                bundle.putString("FRAGMENT_NAME", fragmentName);
                Intent search = new Intent(ListeningForDeezer.this, HomeActivity.class);
                search.putExtras(bundle);
                startActivity(search);
                break;
            case "PlaylistActivity_Deezer":
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_IMAGE_ID", playlistImage);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("PLAYLIST_ID", playlistID);
                Intent playlistDeezer = new Intent(ListeningForDeezer.this, Deezer_Playlist.class);
                playlistDeezer.putExtras(bundle);
                startActivity(playlistDeezer);
                break;
        }
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////PLAYLIST/////////////////////////
    /////////////////////////////////////////////////////////////
    public void setPlaylist(){
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup();
            }
        });
    }
    public void ShowPopup(){
        playlistDialog.setContentView(R.layout.pop_up_playlist);
        playlistDialog.show();
        linearLayout = playlistDialog.findViewById(R.id.backgroundpopUp);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        linearLayout.setBackground(gd);

        LinearLayout layoutSingle=playlistDialog.findViewById(R.id.linearForSingle);

        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsPlaylistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsPlaylistName.setMargins(10,60,0,0);

        PlaylistModel[] allPlaylist = db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
        sizePlaylist=allPlaylist.length;
        playlistTitle=new TextView[sizePlaylist];
        imageButtonPlaylist=new ImageButton[sizePlaylist];

        for (int i=0; i<sizePlaylist; i++){
            dynamique = new LinearLayout(this);
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            if ( (!allPlaylist[i].getTitles().equals("Favorite")) && (!allPlaylist[i].getTitles().equals("Recognition"))){
                int id = allPlaylist[i].getImage();

                imageButtonPlaylist[i]=new ImageButton(this);
                imageButtonPlaylist[i].setBackground(null);
                imageButtonPlaylist[i].setImageResource(id);
                imageButtonPlaylist[i].setTag(id);
                dynamique.addView(imageButtonPlaylist[i],paramsImageButton);

                android.view.ViewGroup.LayoutParams paramButton = imageButtonPlaylist[i].getLayoutParams();
                paramButton.height=200;
                paramButton.width=200;
                imageButtonPlaylist[i].setLayoutParams(paramButton);

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

                ViewGroup.LayoutParams paramText = playlistTitle[i].getLayoutParams();
                paramText.height=100;
                paramText.width= ViewGroup.LayoutParams.WRAP_CONTENT;
                playlistTitle[i].setLayoutParams(paramText);

                layoutSingle.addView(dynamique);

            }else{
                playlistTitle[i]=new TextView(this);
                playlistTitle[i].setText("null");
            }
        }
    }

    public void likeOnClick(){
        boolean isAlreadyInLikedPlaylist=false;
        SinglePlaylistModel singlePlaylistModelToDelete=null;
        PlaylistModel playlistLike=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
        SinglePlaylistModel [] allLikedTracks=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistLike.getId());
        for (int i=0;i<allLikedTracks.length;i++){
            if (allLikedTracks[i].getSongID().equals(songID)){
                isAlreadyInLikedPlaylist=true;
                singlePlaylistModelToDelete=allLikedTracks[i];
            }
        }
        if(isAlreadyInLikedPlaylist==false){
            SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistLike.getId(),songName.toString(),artistName.toString());
            singleToAdd.setForDeezer(true);
            singleToAdd.setTrackDuration(trackDuration);
            singleToAdd.setSongID(songID);
            singleToAdd.setArtistName(artistName);
            singleToAdd.setSongName(songName);
            singleToAdd.setAlbumImage(albumImage);
            db.mSinglePlaylistDao().insertSingle(singleToAdd);
            like.setImageResource(R.drawable.likeonclick);
            DrawableCompat.setTint(like.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        }else{
            db.mSinglePlaylistDao().deleteSingle(singlePlaylistModelToDelete.getId());
            like.setImageResource(R.drawable.likenoclick);
            DrawableCompat.setTint(like.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        }
    }
    public void onClick(View v){
        boolean isAlreadyInLikedPlaylist=false;
        SinglePlaylistModel singlePlaylistModelToDelete=null;
        for (int i=0; i<sizePlaylist; i++){
            if(v.equals(playlistTitle[i]) || v.equals(imageButtonPlaylist[i])) {
                PlaylistModel playlistChoose = db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(), playlistTitle[i].getText().toString());
                SinglePlaylistModel[] tracks = db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistChoose.getId());
                for (int j = 0; j < tracks.length; j++) {
                    if (tracks[j].getSongID().equals(songID)) {
                        isAlreadyInLikedPlaylist = true;
                        singlePlaylistModelToDelete = tracks[j];
                    }
                }
                if (isAlreadyInLikedPlaylist == false) {
                    SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistChoose.getId(), songName.toString(), artistName.toString());
                    singleToAdd.setForDeezer(true);
                    singleToAdd.setTrackDuration(trackDuration);
                    singleToAdd.setSongID(songID);
                    singleToAdd.setArtistName(artistName);
                    singleToAdd.setSongName(songName);
                    singleToAdd.setAlbumImage(albumImage);
                    db.mSinglePlaylistDao().insertSingle(singleToAdd);
                    unShowPopUp(playlistTitle[i].getText().toString(),isAlreadyInLikedPlaylist);
                } else {
                    unShowPopUp(playlistTitle[i].getText().toString(),isAlreadyInLikedPlaylist);
                }
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
            toast = Toast.makeText(getApplicationContext(), "This song is already in playlist : "+playlistName, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    public void isLiked(){
        PlaylistModel playlistLike=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
        SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistLike.getId());
        for (int j=0;j<allSingles.length;j++){
            if (allSingles[j].getSongID().equals(songID)){
                like.setImageResource(R.drawable.likeonclick);
                DrawableCompat.setTint(like.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
            }
        }
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR LYRICS///////////////////////////
    /////////////////////////////////////////////////////////////
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
//        lyrics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (lyrics.getDrawable().getConstantState().equals(getDrawable(R.drawable.lyrics).getConstantState())){
//                    lyrics.setImageResource(R.drawable.lyricsonclick);
//                    switchVideoAudio.setEnabled(false);
//                    switchVideoAudio.setAlpha(0.3f);
//                    if (clipVideo.isPlaying()){
//                        clipVideo.setAlpha(0f);
//                        isVideoPlayingWhenLyricsOn=true;
//                    }else if ((clipVideo.isPlaying()==false)&&(switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState()))){
//                        clipVideo.setAlpha(0f);
//                    }else if (mediaPlayerAudio.isPlaying()){
//                        photoAlbum.setVisibility(View.INVISIBLE);
//                    }else if ((mediaPlayerAudio.isPlaying()==false)&&(switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState()))){
//                        photoAlbum.setVisibility(View.INVISIBLE);
//                    }
//                    lyricsText.setVisibility(View.VISIBLE);
//                }else if (lyrics.getDrawable().getConstantState().equals(getDrawable(R.drawable.lyricsonclick).getConstantState())){
//                    lyrics.setImageResource(R.drawable.lyrics);
//                    switchVideoAudio.setEnabled(true);
//                    switchVideoAudio.setAlpha(1f);
//                    //on retourne sur la video
//                    if (isVideoPlayingWhenLyricsOn==true){
//                        //on retourne sur la video
//                        switchVideoAudio.setImageResource(R.drawable.audio);
//                        clipVideo.setAlpha(1f);
//                        photoAlbum.setVisibility(View.INVISIBLE);
//                        lyricsText.setVisibility(View.INVISIBLE);
//                    }//On retourne sur l'audio
//                    else {
//                        //on retourne sur l'audio
//                        switchVideoAudio.setImageResource(R.drawable.video);
//                        clipVideo.setVisibility(View.INVISIBLE);
//                        photoAlbum.setVisibility(View.VISIBLE);
//                        lyricsText.setVisibility(View.INVISIBLE);
//                    }
//                    isVideoPlayingWhenLyricsOn=false;
//                }
//            }
//        });
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR NEXT/PREVIOUS////////////////////
    /////////////////////////////////////////////////////////////
    public void goPreviousMusic(){
        Bundle bundle=new Bundle();
        int position=0;
        Intent switchListening;
        switch (context) {
            case "Call_From_Album_Activity":
                Thread thread_previous_track=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/album/"+getDeererInformationFromDeezerServer("https://api.deezer.com/track/"+songID).getJSONObject("album").getString("id")).getJSONObject("tracks").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==0){
                                previous_Song_ID=tracksArray.getJSONObject(tracksArray.length()-1).getString("id");
                            }else{
                                previous_Song_ID=tracksArray.getJSONObject(position-1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_previous_track.start();
                try {
                    thread_previous_track.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", previous_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Playlist_Local_Activity":
                SinglePlaylistModel[] allSinglePlaylist=db.mSinglePlaylistDao().getSinglesFromPlaylist(db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),PlaylistName).getId());
                for (int i=0;i<allSinglePlaylist.length;i++){
                    if (allSinglePlaylist[i].getSongName().equalsIgnoreCase(songName) && allSinglePlaylist[i].getArtistName().equalsIgnoreCase(artistName)){
                        position=i;
                    }
                }
                if (position==0){
                    bundle.putString("SONG_ID", allSinglePlaylist[allSinglePlaylist.length-1].getSongID());
                }else{
                    bundle.putString("SONG_ID", allSinglePlaylist[position-1].getSongID());
                }
                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME",PlaylistName);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Playlist_Deezer_Activity":
                Thread thread_previous_playlist=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //PlaylistName=PLAYLIST DEEZER ID
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/playlist/"+PlaylistName+"/tracks").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==0){
                                previous_Song_ID=tracksArray.getJSONObject(tracksArray.length()-1).getString("id");
                            }else{
                                previous_Song_ID=tracksArray.getJSONObject(position-1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_previous_playlist.start();
                try {
                    thread_previous_playlist.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", previous_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Artist_Activity":
                Thread thread_previous_track_TOP=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+getDeererInformationFromDeezerServer("https://api.deezer.com/track/"+songID).getJSONObject("artist").getString("id")+"/top").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==0){
                                previous_Song_ID=tracksArray.getJSONObject(tracksArray.length()-1).getString("id");
                            }else{
                                previous_Song_ID=tracksArray.getJSONObject(position-1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_previous_track_TOP.start();
                try {
                    thread_previous_track_TOP.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", previous_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Flow_Activity":
                Thread thread_previous_track_Flow=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/user/3980074442/flow").getJSONArray("data");
                            int position=0;
                            //VU QUE LE FLOW SE MET A JOUR A CHAQUE FOIS QU4ON L'APPELLE JE VIENS CHERCHER TOUT LE TEMPS LE PREMIER
                            previous_Song_ID=tracksArray.getJSONObject(position).getString("id");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_previous_track_Flow.start();
                try {
                    thread_previous_track_Flow.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", previous_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Historic_Activity":

                break;
        }
    }
    public void goNextMusic(){
        int position=0;
        Bundle bundle=new Bundle();
        Intent switchListening;
        switch (context) {
            case "Call_From_Album_Activity":
                Thread thread_next_track=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/album/"+getDeererInformationFromDeezerServer("https://api.deezer.com/track/"+songID).getJSONObject("album").getString("id")).getJSONObject("tracks").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==tracksArray.length()-1){
                                next_Song_ID=tracksArray.getJSONObject(0).getString("id");
                            }else{
                                next_Song_ID=tracksArray.getJSONObject(position+1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_next_track.start();
                try {
                    thread_next_track.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", next_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);

                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Playlist_Local_Activity":
                SinglePlaylistModel[] allSinglePlaylist=db.mSinglePlaylistDao().getSinglesFromPlaylist(db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),PlaylistName).getId());
                for (int i=0;i<allSinglePlaylist.length;i++){
                    if (allSinglePlaylist[i].getSongName().equalsIgnoreCase(songName) && allSinglePlaylist[i].getArtistName().equalsIgnoreCase(artistName)){
                        position=i;
                    }
                }
                if (position==allSinglePlaylist.length-1){
                    bundle.putString("SONG_ID", allSinglePlaylist[0].getSongID());
                }else{
                    bundle.putString("SONG_ID", allSinglePlaylist[position+1].getSongID());
                }

                bundle.putString("CONTEXT", context);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME",PlaylistName);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Artist_Activity":
                Thread thread_previous_track_TOP=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+getDeererInformationFromDeezerServer("https://api.deezer.com/track/"+songID).getJSONObject("artist").getString("id")+"/top").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==tracksArray.length()-1){
                                next_Song_ID=tracksArray.getJSONObject(0).getString("id");
                            }else{
                                next_Song_ID=tracksArray.getJSONObject(position+1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_previous_track_TOP.start();
                try {
                    thread_previous_track_TOP.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", next_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Flow_Activity":
                Thread thread_next_track_Flow=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/user/3980074442/flow").getJSONArray("data");
                            int position=0;
                            //VU QUE LE FLOW SE MET A JOUR A CHAQUE FOIS QU4ON L'APPELLE JE VIENS CHERCHER TOUT LE TEMPS LE PREMIER
                            next_Song_ID=tracksArray.getJSONObject(position).getString("id");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_next_track_Flow.start();
                try {
                    thread_next_track_Flow.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", next_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Playlist_Deezer_Activity":
                Thread thread_next_playlist=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //PlaylistName=PLAYLIST DEEZER ID
                            tracksArray=getDeererInformationFromDeezerServer("https://api.deezer.com/playlist/"+PlaylistName+"/tracks").getJSONArray("data");
                            int position=-1;
                            for (int i=0;i<tracksArray.length();i++){
                                if (tracksArray.getJSONObject(i).getString("id").equals(songID)){
                                    position=i;
                                }
                            }
                            if (position==tracksArray.length()-1){
                                next_Song_ID=tracksArray.getJSONObject(0).getString("id");
                            }else{
                                next_Song_ID=tracksArray.getJSONObject(position+1).getString("id");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread_next_playlist.start();
                try {
                    thread_next_playlist.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("SONG_ID", next_Song_ID);
                bundle.putString("FRAGMENT_NAME", fragmentName);
                bundle.putString("PLAYLIST_NAME", PlaylistName);
                bundle.putString("CONTEXT", context);
                switchListening = new Intent(ListeningForDeezer.this, ListeningForDeezer.class);
                switchListening.putExtras(bundle);
                startActivity(switchListening);
                break;
            case "Call_From_Historic_Activity":

                break;
        }
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR REPLAY///////////////////////////
    /////////////////////////////////////////////////////////////
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
                    DrawableCompat.setTint(replaySong.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                    mediaPlayerAudio.setLooping(false);
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR AUDIO////////////////////////////
    /////////////////////////////////////////////////////////////
    public void intiLaunch() {
        doStart();
        if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())) {
            playPause.setImageResource(R.drawable.pauselistening);
            DrawableCompat.setTint(playPause.getDrawable(), db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        }
    }
    public void doStart()  {
        // The duration in milliseconds
        if(this.millisecondsToString(this.mediaPlayerAudio.getCurrentPosition()).equals(this.millisecondsToString(this.mediaPlayerAudio.getDuration())))  {
            // Resets the MediaPlayer to its uninitialized state.
            this.mediaPlayerAudio.seekTo(0);
        }
        this.mediaPlayerAudio.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new ListeningForDeezer.UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,200);
    }
    public void doPause()  {
        this.mediaPlayerAudio.pause();
    }
    public void initAudio(){
        // Create MediaPlayer.
        mediaControllerAudio=MediaControllerAudio.getInstance();
        //The first case is when the user listening to a song for the first time
        if (mediaControllerAudio.getMediaPlayerAudio()==null){
            initMediaControllerAudio();
        }else{
            //if user listening to a song we music_headband if the "new" song is the current listening song
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
        seekBar.setMax(30000);
        seekBar.setMin(0);
    }
    public void initMediaControllerAudio(){
        if (mediaControllerAudio.getMediaPlayerAudio()!=null){
            if (mediaControllerAudio.getMediaPlayerAudio().isPlaying()){
                mediaControllerAudio.getMediaPlayerAudio().pause();
            }
        }

        this.mediaPlayerAudio=new MediaPlayer();
        try {
            this.mediaPlayerAudio.setDataSource(musicMP3URL);
            mediaPlayerAudio.prepare();
            mediaPlayerAudio.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaControllerAudio.setMediaPlayerAudio(this.mediaPlayerAudio);
        mediaControllerAudio.setSongName(songName);
        mediaControllerAudio.setArtistName(artistName);
        mediaControllerAudio.setIsDeezerMusic(true);
        mediaControllerAudio.setArtistIdDeezer(artistID);
        mediaControllerAudio.setMusicMP3URL(musicMP3URL);
        mediaControllerAudio.setUrlMP3AllMusic(urlMP3AllMusic);
        mediaControllerAudio.setSongsInAlbum(songsInAlbum);
        mediaControllerAudio.setAlbumImage(albumImage);
        mediaControllerAudio.setAlbumIDForDeezer(albumIDForDeezer);
        mediaControllerAudio.setAlbumName(albumName);
        mediaControllerAudio.setSongID(songID);
//        mediaControllerAudio.setAlbumID(AlbumID);
        currentDurationText.setText("0:00");

    }
    public void setPlayPause(){
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())){
                    playPause.setImageResource(R.drawable.pauselistening);
                    DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                    //Case we are in audio or video clip
                    if (!switchVideoAudio.isChecked()){
                        doStart();
                    }/*else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                        videoDoStart();
                    }*/
                    //Remove the music...
                }else if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())){
                    //Add the music ...
                    if (!switchVideoAudio.isChecked()){
                        doPause();
                    }else {
                        clipVideo.pause();
                    }
                    playPause.setImageResource(R.drawable.playlistening);
                    DrawableCompat.setTint(playPause.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR VIDEO////////////////////////////
    /////////////////////////////////////////////////////////////
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
    public void switchVideoToAudio(){
        switchVideoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchVideoAudio.isChecked()){
                    switchVideoAudio.setText("Audio ");
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
                }else{
                    switchVideoAudio.setText("Video ");
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
    public void videoDoStart(){
        // The duration in milliseconds
        this.clipVideo.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new ListeningForDeezer.UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,200);
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////FOR SEEKBAR//////////////////////////
    /////////////////////////////////////////////////////////////
    // Thread to Update position for SeekBar.
    class UpdateSeekBarThread implements Runnable {
        public void run() {
            if (weWantUpdate==true) {
                if (!switchVideoAudio.isChecked()){
                    currentPosition = mediaPlayerAudio.getCurrentPosition();
                }else {
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
                if (songsInAlbum.length>1){
                    goNextMusic();
                }
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
    public void goTOTimeSong(int value){
        if (!switchVideoAudio.isChecked()){
            mediaPlayerAudio.seekTo(value);
        }else {
            clipVideo.seekTo(value);
        }
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
                updateSeekBarThread= new ListeningForDeezer.UpdateSeekBarThread();
                threadHandler.postDelayed(updateSeekBarThread,200);
                goTOTimeSong(seekBar.getProgress());
            }
        });
    }
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds)-(minutes*60) ;
        if ((seconds<10)&& (seconds>=0)){
            return minutes+":0"+ seconds;

        }else{
            return minutes+":"+ seconds;
        }
    }

    public void retrieve_Album_Information(JSONObject trackJSON){
        try {
            albumName=trackJSON.getJSONObject("album").getString("title");
            albumIDForDeezer=trackJSON.getJSONObject("album").getString("id");
            albumImage=trackJSON.getJSONObject("album").getString("cover_xl");
            setPhotoInListeningView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void retrieve_Artist_Information(JSONObject trackJSON){
        try {
            artistName=trackJSON.getJSONObject("artist").getString("name");
            artistID=trackJSON.getJSONObject("artist").getString("id");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    artist.setText(artistName);
                    artist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToArtistView();
                        }
                    });                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void retrieve_Song_Information(JSONObject trackJSON){
        try {
            songName=trackJSON.getString("title");
            musicMP3URL=trackJSON.getString("preview");
            if (Integer.valueOf(trackJSON.getString("duration"))%60<10){
                trackDuration=Integer.valueOf(trackJSON.getString("duration"))/60+":0"+Integer.valueOf(trackJSON.getString("duration"))%60;
            }else{
                trackDuration=Integer.valueOf(trackJSON.getString("duration"))/60+":"+Integer.valueOf(trackJSON.getString("duration"))%60;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    single.setText(songName);
                    initAudio();
                    intiLaunch();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getDeererInformationFromDeezerServer(final String query){
                URL url;
                try {
                    url = new URL(query);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String inputLine = "";
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        try {
                            jsonObjectForDeezer = new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
        return jsonObjectForDeezer;
    }
}

