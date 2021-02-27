package com.example.if26new.Artists.Deezer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.if26new.Artists.Deezer.Album.FragmentAlbumForDeezer;
import com.example.if26new.Artists.Deezer.Related.RelatedArtistsForDeezer;
import com.example.if26new.Artists.Deezer.Track.TitlesFragmentForDeezer;
import com.example.if26new.Artists.PageAdapterForArtist;
import com.example.if26new.HomeActivity;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivityArtistForDeezerSearch extends AppCompatActivity {
    private Button followButton;
    private ImageView imageArtist;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private TextView artistNameTextView;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;
    private ConstraintLayout background;
    private Bitmap mIcon_val;
    private String[] albumsNames;
    private String[] albumsImages;
    private String[] albumsDates;
    private TextView numberOfFanTextView;
    private String [] albumsID;
    private String artistName;
    private String artistImageURL;
    private String[] topMusicURL;
    private String[] topMusicID;
    private String[] topMusicName;
    private String[] topMusicDuration;
    private String numberOfFans;
    private String artistID;
    private JSONObject jsonObjectForDeezer;
    private int lengthOfAlbums;
    private String[] artistsIDsRelated;
    private String[] artistsNamesRelated;
    private String[] artistsImagesRelated;
    private int callFromHomeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        db=SaveMyMusicDatabase.getInstance(this);

        initView();
        retrieveBundle();
        set_View_Information();

        setViewPager(mViewPager);
        checkIfArtistFollow();
        setcolorBtn();
    }

    public void initView(){
        background=findViewById(R.id.layoutArtist);

        followButton=findViewById(R.id.followTxtID);
        mTableLayout = findViewById(R.id.tableLayoutArtistID);

        mTableLayout.setSelectedTabIndicatorColor(db.userDao().getUserFromId(db.getActualUser()).getButtonColor());

        mViewPager = findViewById(R.id.viewPagerArtistID);
        returnButton=findViewById(R.id.returnButtonArtistView);
        numberOfFanTextView=findViewById(R.id.numberOfFan);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callFromHomeActivity==1){
                    System.out.println("RAPPEL DE TOUTE L'activité ");
                    returnMethod();
                }else{
                    System.out.println("RAPPEL DE ON BACK PRESSED ");
                    onBackPressed();
                }
            }
        });
        mTableLayout.setupWithViewPager(mViewPager);

        imageArtist = findViewById(R.id.imageArtistID);
        imageArtist.setScaleType(ImageView.ScaleType.FIT_CENTER);

        artistNameTextView=findViewById(R.id.nameArtistID);
    }
    public void retrieveBundle(){
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        artistID=getIntent().getExtras().getString("ARTIST_ID");
        callFromHomeActivity=getIntent().getExtras().getInt("IS_CALL_FROM_HOME_ACTIVITY",0);
    }
    public void set_View_Information(){
        Thread thread_Artist=new Thread(new Runnable() {
            @Override
            public void run() {
                retrieve_Artist_Information(artistID);
                retrieve_Albums_Information(artistID);
                retrieve_TopSong_Information(artistID);
                retrieve_Related_Artist_Information(artistID);
            }
        });
        thread_Artist.start();
        try {
            thread_Artist.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void checkIfArtistFollow(){
        ArtistModel artistModel=db.mArtistDao().getArtistFromId(Integer.valueOf(artistID));
        if (artistModel!=null){
            if(db.mArtistDao().getArtistFromId(Integer.valueOf(artistID)).isLike()){
                followButton.setText("Unfollow");
            }else{
                followButton.setText("Follow");
            }
        }else{
            followButton.setText("Follow");
        }
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followButton.getText().toString()=="Follow"){
                    followButton.setText("Unfollow");
                    ArtistModel artistModel=new ArtistModel();
                    artistModel.setLike(true);
                    artistModel.setDeezer(true);
                    artistModel.setName(artistName);
                    artistModel.setDeezerID(artistID);
                    artistModel.setImageDeezer(artistImageURL);
                    artistModel.setUserId(db.getActualUser());
                    artistModel.setId(Integer.valueOf(artistID));
                    artistModel.setNumberOfFans(numberOfFans);
                    db.mArtistDao().insertArtist(artistModel);
                    //db.mArtistDao().updateLike(true,artistModel);
                    //db.mLikeArtistDao().insertLike(new LikeArtistModel(db.getActualUser(),db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getId()));
                }else{
                    followButton.setText("Follow");
                    db.mArtistDao().deleteArtist(db.mArtistDao().getArtistFromId(Integer.valueOf(artistID)).getId());
//                    db.mArtistDao().updateLike(false,db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getId());
//                    db.mLikeArtistDao().deleteLike(db.mLikeArtistDao().getLikeFromArtistAndUser(db.getActualUser(),db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getId()).getId());
                }
            }
        });
    }

    public void setArtistName(String name){
        artistNameTextView.setText(name);
    }
    private void setViewPager(final ViewPager viewPager) {
        PageAdapterForArtist adapter = new PageAdapterForArtist(getSupportFragmentManager());
        Bundle args = new Bundle();
        Bundle args2=new Bundle();
        Bundle args3=new Bundle();

        FragmentAlbumForDeezer frg=new FragmentAlbumForDeezer();
        args.putInt("IS_ARTIST",1);
        args.putString("ARTIST_NAME", artistName);
        args.putString("ARTIST_ID", artistID);
        args.putStringArray("ALBUMS_NAMES", albumsNames);
        args.putStringArray("ALBUMS_IMAGES", albumsImages);
        args.putStringArray("ALBUMS_ID",albumsID);
        args.putStringArray("ALBUMS_DATES",albumsDates);
        args.putString("FRAGMENT", fragmentName);
        args.putString("ARTIST_IMAGE_ID", artistImageURL);
        frg.setArguments(args);

//        args.clear();
        TitlesFragmentForDeezer titlesFragmentForDeezer = new TitlesFragmentForDeezer();
        args2.putString("FRAGMENT", fragmentName);
        args2.putString("ARTIST_NAME", artistName);
        args2.putString("ARTIST_ID", artistID);
        args2.putStringArray("TOP_MUSIC",topMusicName);
        args2.putStringArray("URL_TOP_MUSIC",topMusicURL);
        args2.putStringArray("TOP_MUSIC_ID",topMusicID);
        args2.putStringArray("ALBUMS_IMAGES", albumsImages);
        args2.putStringArray("ALBUMS_NAMES", albumsNames);
        args2.putStringArray("ALBUMS_ID",albumsID);
        args2.putStringArray("TOP_MUSIC_DURATION",topMusicDuration);
        titlesFragmentForDeezer.setArguments(args2);

//        args.clear();
        RelatedArtistsForDeezer relatedArtistsForDeezer=new RelatedArtistsForDeezer();
        args3.putStringArray("Artists_IDs",artistsIDsRelated);
        args3.putStringArray("Artists_Names",artistsNamesRelated);
        args3.putStringArray("Artists_Images",artistsImagesRelated);
        args3.putString("FRAGMENT", fragmentName);
        relatedArtistsForDeezer.setArguments(args3);

        adapter.addFragment(frg, "Album");
        adapter.addFragment(titlesFragmentForDeezer , "Titles");
        adapter.addFragment(relatedArtistsForDeezer, "Related");

        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        if (callFromHomeActivity==1){
            returnMethod();
        }else{
            super.onBackPressed();
        }
    }
    public void returnMethod(){
        Bundle bundle = new Bundle();
        bundle.putString("FRAGMENT_NAME",fragmentName);
        bundle.putString("CONTEXT","Call_From_Artist_Activity");
        Intent playListActivity = new Intent(ActivityArtistForDeezerSearch.this, HomeActivity.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
    public void setcolorBtn(){
        DrawableCompat.setTint(returnButton.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        ((GradientDrawable) followButton.getBackground()).setStroke(5, db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        //followButton.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),followButton.getText().toString()));
    }

    //Retrieve JSON DEEZER INFORMATION
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

    public void retrieve_Albums_Information(String artistID){
        JSONObject albumsJSONObject=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+artistID+"/albums");
        try{
            JSONArray jsonArray=albumsJSONObject.getJSONArray("data");
            lengthOfAlbums=jsonArray.length();
            albumsID=new String[lengthOfAlbums];
            albumsNames=new String[lengthOfAlbums];
            albumsImages=new String[lengthOfAlbums];
            albumsDates=new String[lengthOfAlbums];
            for (int i=0;i<lengthOfAlbums;i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                albumsID[i]=jsonObject.getString("id");
                albumsNames[i]=jsonObject.getString("title");
                albumsImages[i]=jsonObject.getString("cover_xl");
                albumsDates[i]=jsonObject.getString("release_date");
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public void retrieve_TopSong_Information(final String artistID){
        JSONObject albumsJSONObject=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+artistID+"/top");
        try{
            JSONArray jsonArray=albumsJSONObject.getJSONArray("data");
            topMusicID=new String[jsonArray.length()];
            topMusicName=new String[jsonArray.length()];
            topMusicURL=new String[jsonArray.length()];
            topMusicDuration=new String[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                topMusicDuration[i]=jsonObject.getString("duration");
                topMusicID[i]=jsonObject.getString("id");
                topMusicName[i]=jsonObject.getString("title");
                topMusicURL[i]=jsonObject.getString("preview");
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public void retrieve_Related_Artist_Information(String artistID){
        JSONObject relatedArtistJSONObject=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+artistID+"/related");
        try {
            JSONArray jsonArray=relatedArtistJSONObject.getJSONArray("data");
            if (jsonArray.length()>=10){
                artistsIDsRelated=new String[10];
                artistsNamesRelated=new String[10];
                artistsImagesRelated=new String[10];
                for (int i=0;i<10;i++){
                    artistsIDsRelated[i]=jsonArray.getJSONObject(i).getString("id");
                    artistsNamesRelated[i]=jsonArray.getJSONObject(i).getString("name");
                    artistsImagesRelated[i]=jsonArray.getJSONObject(i).getString("picture_xl");
                }
            }else{
                artistsIDsRelated=new String[jsonArray.length()];
                artistsNamesRelated=new String[jsonArray.length()];
                artistsImagesRelated=new String[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    artistsIDsRelated[i]=jsonArray.getJSONObject(i).getString("id");
                    artistsNamesRelated[i]=jsonArray.getJSONObject(i).getString("name");
                    artistsImagesRelated[i]=jsonArray.getJSONObject(i).getString("picture_xl");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void retrieve_Artist_Information(String artistID){
        JSONObject artistJSONObject=getDeererInformationFromDeezerServer("https://api.deezer.com/artist/"+artistID);
        try{
            artistImageURL=artistJSONObject.getString("picture_xl");
            setImageArtist(artistImageURL);
            artistName=artistJSONObject.getString("name");
            numberOfFans=artistJSONObject.getString("nb_fan");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    artistNameTextView.setText(artistName);
                    setNumberOfFans(numberOfFans);

                }
            });
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public void setNumberOfFans(String numberOfFans) {
        int blankNumber;
        if (numberOfFans.length()%3==0){
            blankNumber=(numberOfFans.length()/3)-1;
        }else{
            blankNumber=(numberOfFans.length()/3);
        }

        int length=numberOfFans.length()+blankNumber;
        int lengthNumberOfFan=numberOfFans.length();
        char toto[]=new char[length];

        int div=numberOfFans.length()/3;
        if (div==0){
            for (int i=0;i<numberOfFans.length();i++){
                toto[i]=numberOfFans.charAt(i);
            }
        }else if (numberOfFans.length()%3==0){
            for (int i=0;i<div;i++){
                toto[(length-1)-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-(i*3));
                toto[(length-1)-1-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-1-(i*3));
                toto[(length-1)-2-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-2-(i*3));
                if ((length-1)-3-(i*4)>=0){
                    toto[(length-1)-3-(i*4)]=' ';
                }
            }
        }else{
            for (int i=0;i<div;i++){
                toto[(length-1)-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-(i*3));
                toto[(length-1)-1-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-1-(i*3));
                toto[(length-1)-2-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-2-(i*3));
                toto[(length-1)-3-(i*4)]=' ';
            }
            int resultDivEuclidienne=numberOfFans.length()%3;
            for (int i=0;i<resultDivEuclidienne;i++){
                toto[i]=numberOfFans.charAt(i);
            }
        }
        char[] finale=new char[length+5];
        for (int i=0;i<length;i++){
            finale[i]=toto[i];
        }
        finale[length]=' ';
        finale[length+1]='F';
        finale[length+2]='a';
        finale[length+3]='n';
        finale[length+4]='s';

        //VOIR POUR 99 a 1
        numberOfFanTextView.setText(finale,0,finale.length);
    }
    public void setImageArtist(final String urlArtistImage){
        Thread thread_Set_Image_Artist=new Thread(new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                try {
                    if(!urlArtistImage.equals("null")){
                        newurl = new URL(urlArtistImage);
                        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageArtist.setImageBitmap(mIcon_val);
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
        thread_Set_Image_Artist.start();
    }
}
