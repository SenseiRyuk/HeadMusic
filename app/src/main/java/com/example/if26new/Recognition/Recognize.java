package com.example.if26new.Recognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acrcloud.rec.ACRCloudClient;
import com.acrcloud.rec.ACRCloudConfig;
import com.acrcloud.rec.ACRCloudResult;
import com.acrcloud.rec.IACRCloudListener;
import com.example.if26new.Albums.Album_view_For_Deezer;
import com.example.if26new.Artists.Deezer.ActivityArtistForDeezerSearch;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.example.if26new.Search.Themes.Track.RV_Adapter_Theme_Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Recognize extends Fragment implements IACRCloudListener, View.OnClickListener {

    private ConstraintLayout background;
    private ImageView logoRecognition;
    private ImageView artistImage;
    private TextView musicTitle;
    private TextView musicArtist;
    private TextView musicAlbum;
    private TextView interactTextView;
    private TextView undelineArtist;
    private TextView undelineAlbum;
    private TextView undelineTrack;
    private TextView line;

    private AnimationDrawable animationDrawableForLoading;
    private boolean stopAnimationBiggerSmaller;
    private Bitmap mIcon_val;

    private SaveMyMusicDatabase db;
    public static final String API_KEY = "AIzaSyAgrSw1zwvIWX0KLzOyLeSdik6RH0ykGBU";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "YQHsXMglC9A";

    private boolean isStart;
    private boolean mProcessing = false;
    private boolean initState = false;

    private ACRCloudConfig mConfig = null;
    private ACRCloudClient mClient = null;
    private AlphaAnimation alphaAnimation;


    //FOR LISTENING VIEW
    private ConstraintLayout layoutWithTextViewRecognition;
    private String musicMP3URL;
    private String urlMP3AllMusic;
    private String songsInAlbum;
    private String albumImage;
    private String albumName;
    private String artistID;
    private String artistName;
    private String albumID;
    private String songID;

    private ConstraintLayout layoutForRecentRecognition;

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> informationForAdapteurTrack;
    private RV_Adapter_Theme_Track adapteurTrack;
    private int sizeOfTrack;
    private JSONArray jsonArrayTrack;
    private JSONObject jsonObjectForDeezer;

    public Recognize() {
        // Required empty public constructor
    }

    public static Recognize newInstance(String param1, String param2) {
        Recognize fragment = new Recognize();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        verifyPermissions();
        view =inflater.inflate(R.layout.fragment_recognize, container, false);
        db=SaveMyMusicDatabase.getInstance(getContext());
        albumID="null";
        artistName="null";
        artistID="null";
        albumName="null";
        albumImage="null";

        background=view.findViewById(R.id.backgroundRecognition);
        logoRecognition=view.findViewById(R.id.imageViewRecognition);
        artistImage=view.findViewById(R.id.imageViewArtistImageForRecognition);
        musicTitle=view.findViewById(R.id.titileForRecognition);
        musicTitle.setOnClickListener(this);
        musicArtist=view.findViewById(R.id.artistForRecognition);
        musicArtist.setOnClickListener(this);
        musicAlbum=view.findViewById(R.id.albumForRecognition);
        musicAlbum.setOnClickListener(this);
        interactTextView=view.findViewById(R.id.clickToInteract);
        undelineArtist=view.findViewById(R.id.artistUnderlineForRecognition);
        undelineArtist.setOnClickListener(this);
        undelineAlbum=view.findViewById(R.id.albumUnderlineForRecognition);
        undelineAlbum.setOnClickListener(this);
        undelineTrack=view.findViewById(R.id.trackUnderlineForRecognition);
        undelineTrack.setOnClickListener(this);
        layoutWithTextViewRecognition=view.findViewById(R.id.contraintLayoutTextForRecognition);

        layoutForRecentRecognition=view.findViewById(R.id.contraintLayoutLastRecognition);
        displayLatestRecognitionMusic();

        line=view.findViewById(R.id.introTV);
        this.textAppeard(line);

        isStart=false;
        initARCloud();
        stopAnimationBiggerSmaller=false;
        scaleViewBigger(logoRecognition,1,1.15f);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
        logoRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoRecognition.setImageBitmap(null);
                logoRecognition.setBackgroundResource(R.drawable.change_color_animation_for_recognition);
                animationDrawableForLoading= (AnimationDrawable)logoRecognition.getBackground();
                if(isStart==false){
                    stopAnimationBiggerSmaller=true;
                    animationDrawableForLoading.start();
                    startRecognition();
                    isStart=true;
                }else{
                    musicTitle.setText("");
                    musicArtist.setText("");
                    animationDrawableForLoading.stop();
                    stopRecognition();
                    isStart=false;
                    logoRecognition.setImageBitmap(null);
                    logoRecognition.setBackgroundResource(R.drawable.logocolor1);
                }
            }
        });
        //Set Alpha Animation
        alphaAnimation= new AlphaAnimation(0.2f,1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        return view;
    }

    public void initARCloud(){

        this.mConfig = new ACRCloudConfig();
        this.mConfig.acrcloudListener = this;
        this.mConfig.context = getContext();

        // Please create project in "http://console.acrcloud.cn/service/avr".
        this.mConfig.host = "identify-eu-west-1.acrcloud.com";
        this.mConfig.accessKey = "5f183b697271da1e2c616e1277a55113";
        this.mConfig.accessSecret = "1P7gwXLXINSmDlyWdHKFy6xQGBVL4xrg76HSMGHf";

        // auto recognize access key
        /*this.mConfig.hostAuto = "";
        this.mConfig.accessKeyAuto = "";
        this.mConfig.accessSecretAuto = "";*/

        this.mConfig.recorderConfig.rate = 8000;
        this.mConfig.recorderConfig.channels = 1;

        // If you do not need volume callback, you set it false.
        this.mConfig.recorderConfig.isVolumeCallback = false;

        this.mClient = new ACRCloudClient();
        //ACRCloudLogger.setLog(true);
        //Init the client with the previous configuration
        this.initState = this.mClient.initWithConfig(this.mConfig);
    }
    public void startRecognition() {
        if (!this.initState) {
            System.out.println("Initialisation error");
            return;
        }
        if (!mProcessing) {
            mProcessing = true;
            if (this.mClient == null || !this.mClient.startRecognize()) {
                mProcessing = false;
                System.out.println("ERROR");
            }
        }
    }
    public void stopRecognition() {
        if (mProcessing && this.mClient != null) {
            this.mClient.cancel();
            this.mClient.stopRecordToRecognize();
        }
        mProcessing = false;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO
    };
    public void verifyPermissions() {
        for (int i=0; i<PERMISSIONS.length; i++) {
            int permission = ActivityCompat.checkSelfPermission(getContext(), PERMISSIONS[i]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_EXTERNAL_STORAGE);
                break;
            }
        }
    }
    @Override
    public void onResult(ACRCloudResult results) {
        animationDrawableForLoading.stop();
        isStart=false;
        String result = results.getResult();
        try {
            JSONObject j = new JSONObject(result);
            JSONObject j1 = j.getJSONObject("status");
            if(j1.getInt("code") == 0){
                JSONObject metadata = j.getJSONObject("metadata");
                if (metadata.has("music")) {
                    JSONArray musics = metadata.getJSONArray("music");
                    JSONObject musicInformation = musics.getJSONObject(0);
                    //Title of the Song
                    String title = musicInformation.getString("title");
                    //Artist Information/ Artist Name
                    artistName = musicInformation.getJSONArray("artists").getJSONObject(0).getString("name");
                    //Album Information
                    String albumName=musicInformation.getJSONObject("album").getString("name");

                    //Get Deezer Information
                    JSONObject external_metadata = musicInformation.getJSONObject("external_metadata");
                    if(external_metadata.has("deezer")){
                        JSONObject artistDeezerInformation=external_metadata.getJSONObject("deezer").getJSONArray("artists").getJSONObject(0);
                        if (artistDeezerInformation.has("id")){
                            artistID=artistDeezerInformation.getString("id");
                        }else{
                            artistID="null";
                        }
                        JSONObject albumObject=external_metadata.getJSONObject("deezer").getJSONObject("album");
                        if (albumObject.has("id")){
                            albumID=albumObject.getString("id");
                        }else{
                            albumID="null";
                        }
                        JSONObject trackObject=external_metadata.getJSONObject("deezer").getJSONObject("track");
                        if (trackObject.has("id")){
                            songID=trackObject.getString("id");
                        }else{
                            songID="null";
                        }
                    }
                    //artistID="5542343";
                    //albumID="182234382";
                    //songID="1121729702";
                    if (artistID!="null" && albumID=="null" && songID=="null"){
                        //Trouver toutes les infos juste avec l'artist il faut trouver l'albumID et le track ID
                        getAlbumIDFromArtistID(albumName);
                        getTrackIDFromAlbumID(title);
                        if (albumID!="null"&&songID!="null"){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }else if (artistID!="null" && albumID=="null" && songID!="null"){
                        //Trouver toutes les infos juste avec l'artist il faut trouver l'albumID et le track ID
                        getAlbumIDFromTrackID();
                        if (albumID!="null"){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }else if (artistID!="null" && albumID!="null" && songID=="null"){
                        //Trouver toutes les infos juste avec l'artist il faut trouver l'albumID et le track ID
                        getTrackIDFromAlbumID(title);
                        if (albumID!="null"){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }
                    else if (artistID!="null" && albumID!="null" && songID!="null"){
                        addMusicInPlaylist(title,artistName,albumID,songID);
                        imageArtistAppears(title,artistName,artistID,albumName);
                    } else if (artistID=="null" && albumID=="null" && songID=="null"){
                        failedToAuthenticate();
                    }
                    else if (artistID=="null" && albumID!="null" && songID=="null"){
                        getTrackIDFromAlbumID(title);
                        if (songID!="null"){
                            getArtistIDFromTrackID();
                        }
                        if (songID!=null && artistID!=null){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }else if (artistID=="null" && albumID!="null" && songID!="null"){
                        getArtistIDFromTrackID();
                        if (artistID!="null"){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }else if (artistID=="null" && albumID=="null" && songID!="null"){
                        getArtistIDFromTrackID();
                        getAlbumIDFromTrackID();
                        if (artistID!="null"){
                            addMusicInPlaylist(title,artistName,albumID,songID);
                            imageArtistAppears(title,artistName,artistID,albumName);
                        }else{
                            failedToAuthenticate();
                        }
                    }
                }
            }else{
                failedToAuthenticate();
            }
            stopRecognition();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void failedToAuthenticate(){
        stopAnimationBiggerSmaller=false;
        scaleViewBigger(logoRecognition,1,1.15f);
        logoRecognition.setImageBitmap(null);
        logoRecognition.setBackgroundResource(R.drawable.logocolor1);
        Toast.makeText(getActivity(), "Failed to authenticate.", Toast.LENGTH_LONG).show();
    }
    public void getAlbumIDFromTrackID(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //For Album Information
                URL newurl;
                try {
                    newurl = new URL("https://api.deezer.com/track/"+songID);
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    String inputLine="";
                    JSONObject jsonObject=null;
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                    JSONObject albumInformation=jsonObject.getJSONObject("album");
                    albumID=albumInformation.getString("id");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
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
    }
    public void getArtistIDFromTrackID(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //For Album Information
                URL newurl;
                try {
                    newurl = new URL("https://api.deezer.com/track/"+songID);
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    String inputLine="";
                    JSONObject jsonObject=null;
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                    JSONObject albumInformation=jsonObject.getJSONObject("artist");
                    artistID=albumInformation.getString("id");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
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
    }
    public void getAlbumIDFromArtistID(final String albumName){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //For Album Information
                URL newurl;
                try {
                    newurl = new URL("https://api.deezer.com/artist/"+artistID+"/albums");
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    String inputLine="";
                    JSONObject jsonObject=null;
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        if (jsonArray.getJSONObject(i).getString("title").equalsIgnoreCase(albumName)){
                            albumID=jsonArray.getJSONObject(i).getString("id");
                            break;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
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
    }
    public void getTrackIDFromAlbumID(final String songName){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //For Album Information
                URL newurl;
                try {
                    newurl = new URL("https://api.deezer.com/album/"+albumID);
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    String inputLine="";
                    JSONObject jsonObject=null;
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                    JSONArray jsonArray=jsonObject.getJSONObject("tracks").getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        if (jsonArray.getJSONObject(i).getString("title").equalsIgnoreCase(songName)){
                            songID=jsonArray.getJSONObject(i).getString("id");
                            break;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
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
    }

    @Override
    public void onVolumeChanged(double volume) {
        /*long time = (System.currentTimeMillis() - startTime) / 1000;
        mVolume.setText(getResources().getString(R.string.volume) + volume + "\n\nTime: " + time + " s");*/
    }
    public void addMusicInPlaylist(String title,String artist,String albumID,String songID){
        boolean alreadyExist=false;
        PlaylistModel playlistRecognition=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Recognition");
        SinglePlaylistModel [] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistRecognition.getId());
        for (int i=0;i<allSingles.length;i++) {
            if( ( allSingles[i].getSongName().equals(title) ) && ( allSingles[i].getArtistName().equals(artist) ) ){
                alreadyExist=true;
            }
        }
        retrieveDeezerInformationForTrackAndAlbum(albumID,songID);
        if(alreadyExist==false){
            SinglePlaylistModel singleToAdd = new SinglePlaylistModel(playlistRecognition.getId(),title,artist);
            singleToAdd.setAlbumImage(albumImage);
            singleToAdd.setSongName(title);
            singleToAdd.setSongID(songID);
            singleToAdd.setForDeezer(true);
            db.mSinglePlaylistDao().insertSingle(singleToAdd);
        }
    }
    public void retrieveDeezerInformationForTrackAndAlbum(final String albumID,final String songID){
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                String result="null";
                BufferedReader bufferedReader;
                String inputLine;
                JSONObject jsonObject=null;
                try {

                    //For Album Information
                    newurl = new URL("https://api.deezer.com/album/"+albumID);
                    bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    inputLine="";
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();

                    try {
                        if (jsonObject.has("cover_xl")){
                            albumImage=jsonObject.getString("cover_xl");
                        }
                        if (jsonObject.has("title")){
                            albumName=jsonObject.getString("title");
                        }
                        JSONObject tracks=jsonObject.getJSONObject("tracks");
                        JSONArray jsonArray=tracks.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject musique=jsonArray.getJSONObject(i);
                            if (i==0){
                                songsInAlbum=musique.getString("title");
                                urlMP3AllMusic=musique.getString("preview");
                            }else{
                                songsInAlbum+="/"+musique.getString("title");
                                urlMP3AllMusic+="/"+musique.getString("preview");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //For Track Information
                    newurl = new URL("https://api.deezer.com/track/"+songID);
                    bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
                    inputLine="";
                    while ((inputLine=bufferedReader.readLine()) != null){
                        try {
                            jsonObject=new JSONObject(inputLine);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();
                    try {
                        if (jsonObject.has("preview")) {
                            musicMP3URL = jsonObject.getString("preview");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
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

    }
    public void scaleViewBigger(final View v,float startScale, final float endScale) {
        final Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
        );
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(3000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(stopAnimationBiggerSmaller==true){
                    anim.cancel();
                }else{
                    scaleViewSmaller(v,endScale,0.85f);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);

    }
    public void scaleViewSmaller(final View v,final float startScale, final float endScale) {
        final Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
        );
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(3000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(stopAnimationBiggerSmaller==true){
                    anim.cancel();
                }else{
                    scaleViewBigger(v,endScale, startScale);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }
    public void imageArtistAppears(String title, String artist,final String artistDeezerID,String album){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                try {
                    if(!getUrlOfArtistImageFromDeezer(artistDeezerID).equals("null")){
                        newurl = new URL(getUrlOfArtistImageFromDeezer(artistDeezerID));
                        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                    }else{
                        //Image PAR DEFAUT
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
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

        layoutForRecentRecognition.setVisibility(View.INVISIBLE);
        background.setBackgroundColor(Color.BLACK);
        background.setAnimation(alphaAnimation);
        logoRecognition.setBackground(null);
        logoRecognition.setOnClickListener(null);

        artistImage.setImageBitmap(mIcon_val);
        artistImage.setForeground(getContext().getDrawable(R.drawable.gradientforphoto));
        artistImage.startAnimation(alphaAnimation);

        musicTitle.setText(title);
        musicTitle.startAnimation(alphaAnimation);

        musicArtist.setText(artist);
        musicArtist.startAnimation(alphaAnimation);

        musicAlbum.setText(album);
        musicAlbum.startAnimation(alphaAnimation);

        line.setText("");
        line.setVisibility(View.INVISIBLE);

        interactTextView.setVisibility(View.VISIBLE);
        undelineTrack.setVisibility(View.VISIBLE);
        undelineAlbum.setVisibility(View.VISIBLE);
        undelineArtist.setVisibility(View.VISIBLE);
        this.textAppeard(interactTextView);
    }
    public String getUrlOfArtistImageFromDeezer(String artistDeezerID){
        URL newurl = null;
        String result="null";
        try {
            newurl = new URL("https://api.deezer.com/artist/"+artistDeezerID);
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(newurl.openStream()));
            String inputLine="";
            JSONObject jsonObject=null;
            while ((inputLine=bufferedReader.readLine()) != null){
                try {
                    jsonObject=new JSONObject(inputLine);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            bufferedReader.close();
            try {
                if (jsonObject.has("picture_xl")){
                    result=jsonObject.getString("picture_xl");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void goToArtistView(){
        Bundle bundle=new Bundle();
        bundle.putString("ARTIST_ID",artistID);
        bundle.putString("FRAGMENT_NAME","HomeFragment");

        Intent Artist = new Intent(getActivity(), ActivityArtistForDeezerSearch.class);
        Artist.putExtras(bundle);
        startActivity(Artist);
    }
    public void goToAlbumView(){
        if ((!artistID.equals("null")) && (!artistName.equals("null")) && (!albumID.equals("null")) && (!albumImage.equals("null")) && (!albumName.equals("null"))){
            Bundle bundle=new Bundle();
            bundle.putString("ARTIST_ID",artistID);
            bundle.putString("ARTIST_NAME",artistName);
            bundle.putString("ALBUM_ID",albumID);
            bundle.putString("ALBUM_IMAGE_ID",albumImage);
            bundle.putString("ALBUM_NAME",albumName);
            bundle.putInt("IS_FROM_ARTIST_VIEW",0);
            bundle.putString("FRAGMENT_NAME","HomeFragment");

            Intent Artist = new Intent(getActivity(), Album_view_For_Deezer.class);
            Artist.putExtras(bundle);
            startActivity(Artist);
        }
    }
    public void textAppeard(final TextView textViewToInteract){
        AlphaAnimation alphaAnimation= new AlphaAnimation(0.2f,1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textDisappeard(textViewToInteract);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textViewToInteract.startAnimation(alphaAnimation);
    }
    public void textDisappeard(final TextView textViewToInteract){
        AlphaAnimation alphaAnimation2= new AlphaAnimation(1f,0.2f);
        alphaAnimation2.setDuration(2000);
        alphaAnimation2.setFillAfter(true);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textAppeard(textViewToInteract);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textViewToInteract.startAnimation(alphaAnimation2);
    }
    public void onClick(View v){
        if (v.equals(musicTitle) || v.equals(undelineTrack)){

        }else if (v.equals(musicArtist)  || v.equals(undelineArtist)){
            goToArtistView();
        }else if (v.equals(musicAlbum) || v.equals(undelineAlbum)){
            goToAlbumView();
        }
    }

    public void displayLatestRecognitionMusic(){
        PlaylistModel playlistToAddSong=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Recognition");
        final SinglePlaylistModel[] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistToAddSong.getId());
        final int length=allSingles.length;
        setRecyclerView(view,length);
        setViewTrack(allSingles);
    }
    public void setViewTrack(SinglePlaylistModel[] allTrack){
        for (int i = 0; i < allTrack.length; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(allTrack[i].getSongID());
            arrayListArtist.add(allTrack[i].getSongName());
            arrayListArtist.add(allTrack[i].getAlbumImage());
            arrayListArtist.add("UserFragment");
            informationForAdapteurTrack.add(arrayListArtist);
            adapteurTrack.notifyItemChanged(i);
        }
    }
    public void setRecyclerView(View view,int length){
        recyclerView=view.findViewById(R.id.RV_Recognition);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        this.informationForAdapteurTrack=new ArrayList<ArrayList<String>>();
        this.adapteurTrack=new RV_Adapter_Theme_Track(this.informationForAdapteurTrack,getActivity(),length);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteurTrack);
    }
}
