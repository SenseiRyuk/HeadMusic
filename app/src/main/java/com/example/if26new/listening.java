package com.example.if26new;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class listening extends AppCompatActivity {

    private ImageButton home;
    private ImageButton news;
    private ImageButton profile;
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
    private MediaPlayer mediaPlayerAudio;
    private SeekBar seekBar;
    private TextView totalDurationText;
    private TextView currentDurationText;
    private TextView lyricsText;
    private Handler threadHandler = new Handler();
    private boolean weWantUpdate=true;
    private UpdateSeekBarThread updateSeekBarThread;
    private int currentPosition;
    private boolean isVideoPlayingWhenLyricsOn=false;
    private Dialog playlistDialog;


    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
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
    public void doStart()  {
        // The duration in milliseconds
        String totalDuration = this.millisecondsToString(this.mediaPlayerAudio.getDuration());
        String currentduration = this.millisecondsToString(this.mediaPlayerAudio.getCurrentPosition());
        if(currentduration==totalDuration)  {
            // Resets the MediaPlayer to its uninitialized state.
            this.mediaPlayerAudio.reset();
        }
        this.mediaPlayerAudio.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,50);
    }
    public void videoDoStart(){
        // The duration in milliseconds
        this.clipVideo.start();
        // Create a thread to update position of SeekBar.
        updateSeekBarThread=new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,50);
    }


    // Thread to Update position for SeekBar.
    class UpdateSeekBarThread implements Runnable {
            public void run() {
                    if (weWantUpdate==true) {
                        if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                            currentPosition = mediaPlayerAudio.getCurrentPosition();
                            if (((int)currentPosition/1000==(int)mediaPlayerAudio.getDuration()/1000)&&(replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.looponclick).getConstantState()))){
                                mediaPlayerAudio.start();
                            }
                        }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                            currentPosition=clipVideo.getCurrentPosition();
                            if (((int)currentPosition/1000==(int)mediaPlayerAudio.getDuration()/1000)&&(replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.looponclick).getConstantState()))){
                                clipVideo.start();
                            }
                        }
                        String currentPositionStr = millisecondsToString(currentPosition);
                        currentDurationText.setText(currentPositionStr);
                        seekBar.setProgress(currentPosition);
                         //Delay thread 50 milisecond.
                    }
                    if ((((mediaPlayerAudio.getDuration()/1000==currentPosition/1000)||(clipVideo.getDuration()==currentPosition))) && (replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.loop).getConstantState()))){
                        playPause.setImageResource(R.drawable.playlistening);
                    }
                threadHandler.postDelayed(this, 50);
            }
    }

    // When user click to "Pause".
    public void doPause()  {
        this.mediaPlayerAudio.pause();
    }

    public void goTOTimeSong(int value){
        if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
            mediaPlayerAudio.seekTo(value);

        }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
            clipVideo.seekTo(value);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
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

        //Set photo Music Artist
        //Bitmap resized = Bitmap.createScaledBitmap(mybitmap, 340, 340, true);
        photoAlbum.setImageResource(R.drawable.hazy);
        photoAlbum.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        photoAlbum.setAdjustViewBounds(true);


        lyricsText.setMovementMethod(new ScrollingMovementMethod());
        //For the Video
        try {
            // ID of video file.
            int id = this.getRawResIdByName("hazy_universe_video");
            clipVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        clipVideo.requestFocus();

        //Pour l'audio
        int songId = this.getRawResIdByName("hazy_universe");
        // Create MediaPlayer.
        this.mediaPlayerAudio=MediaPlayer.create(this,songId);// MediaPlayer(this, songId);
        String totalDuration = this.millisecondsToString(this.mediaPlayerAudio.getDuration());
        totalDurationText.setText(totalDuration);
        currentDurationText.setText("0:00");
        seekBar.setMax(this.mediaPlayerAudio.getDuration());
        seekBar.setMin(0);
        /*home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeActivity = new Intent(listening.this, HomeActivity.class);
                startActivity(homeActivity);
            }
        });*/
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like.getDrawable().getConstantState().equals(getDrawable(R.drawable.likenoclick).getConstantState())){
                    like.setImageResource(R.drawable.likeonclick);
                }else if (like.getDrawable().getConstantState().equals(getDrawable(R.drawable.likeonclick).getConstantState())){
                    like.setImageResource(R.drawable.likenoclick);
                }
            }
        });
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ShowPopup();
            }
        });
        replaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.loop).getConstantState())){
                    replaySong.setImageResource(R.drawable.looponclick);

                }else if (replaySong.getDrawable().getConstantState().equals(getDrawable(R.drawable.looponclick).getConstantState())){
                    replaySong.setImageResource(R.drawable.loop);
                }
            }
        });
        switchVideoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                    switchVideoAudio.setImageResource(R.drawable.audio);
                    clipVideo.setVisibility(View.VISIBLE);
                    photoAlbum.setVisibility(View.INVISIBLE);
                    lyricsText.setVisibility(View.INVISIBLE);
                    doPause();
                    clipVideo.seekTo(currentPosition);
                    seekBar.setProgress(currentPosition);
                    if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())) {
                        videoDoStart();
                    }
                }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                    switchVideoAudio.setImageResource(R.drawable.video);
                    clipVideo.pause();
                    clipVideo.setVisibility(View.INVISIBLE);
                    photoAlbum.setVisibility(View.VISIBLE);
                    lyricsText.setVisibility(View.INVISIBLE);
                    System.out.println("current position " + currentPosition);
                    mediaPlayerAudio.seekTo(currentPosition);
                    seekBar.setProgress(currentPosition);
                    if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.pauselistening).getConstantState())) {
                        doStart();
                    }
                }
            }
        });
       playPause.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.playlistening).getConstantState())){
                   playPause.setImageResource(R.drawable.pauselistening);
                   //Case we are in audio or video clip
                   if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.video).getConstantState())){
                       doStart();
                   }else if (switchVideoAudio.getDrawable().getConstantState().equals(getDrawable(R.drawable.audio).getConstantState())){
                       //clipVideo.start();
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
               threadHandler.postDelayed(updateSeekBarThread,50);
               goTOTimeSong(seekBar.getProgress());
           }
       });
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

       //Read The TXT File for the lyrics ....
        //Name IN ROW IN minuscule
        InputStream inputStream = getResources().openRawResource(R.raw.betternowpostmalonelyrics);
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
    }

    public void ShowPopup(){
        playlistDialog.setContentView(R.layout.playlist_pop_up);
        playlistDialog.show();

        TableLayout table = playlistDialog.findViewById(R.id.tablePlaylist);
        TableLayout.LayoutParams tl=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow row;
        for (int i=0;i<=7;i++){
            row=new TableRow(this);
            row.setGravity(Gravity.CENTER);
            ImageButton myButton=new ImageButton(this);
            myButton.setBackground(null);
            myButton.setImageResource(R.drawable.iconforplaylist);
            final TextView myText = new TextView(this);
            //Set the TEXT OF THE PLAYLIST
            myText.setTextColor(Color.parseColor("#1C376E"));
            myText.setText("    Playlist n°" +(i+1) + " ("+(i+20)+" songs)");
            myText.setTextSize(20);
            myText.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(myButton);
            row.addView(myText);
            table.addView(row,tl);
            //table.addView(myText,tl);
            //IT'S HERE WE WILL SET THE ON CLICK ON THE BUTTON AND THE TEXT TO GO TO THE VIEW PLAYLIST
            myText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Mucic add in the playlist Playlist n° ", Toast.LENGTH_SHORT);
                    toast.show();
                    playlistDialog.dismiss();
                }
            });
        }

    }

}
