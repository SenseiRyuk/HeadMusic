package com.example.if26new.Albums;

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
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Artists.Deezer.ActivityArtistForDeezerSearch;
import com.example.if26new.HomeActivity;
import com.example.if26new.Model.AlbumModelDeezer;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Album_view_For_Deezer extends AppCompatActivity{

    private TextView albumNameTV;
    private Button followbtn;
    private ImageView mImageView;
    private String nameArtist;
    private String imageAlbum;
    private String nameAlbum;
    private ImageButton returnButton;
    private String fragmentName;
    private int isCallFromArtistView;
    private Bitmap mIcon_val;
    private SaveMyMusicDatabase db;
    private String albumID;
    private String artistID;
    private TextView artistNameTV;
    private JSONObject jsonObjectForDeezer;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Album adapteur;
    private RecyclerView recyclerView;

    private int callFromHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        db=SaveMyMusicDatabase.getInstance(this);

        initRecyclerView();
        initView();
        retrieveBundle();

        setAlbumInformation();
        setcolorBtn();
        retrieveArtistInfo();
        checkIfAlbumIsFollowed();
    }
    public void checkIfAlbumIsFollowed(){
        AlbumModelDeezer albumModel=db.mAlbumDeezerDao().getAlbumFromId(Integer.valueOf(albumID));
        if (albumModel!=null){
            if(albumModel.isLike()){
                followbtn.setText("Dislike");
            }else{
                followbtn.setText("Like");
            }
        }else{
            followbtn.setText("Like");
        }
    }
    public void retrieveArtistInfo(){
        Thread thread_Retrieve_Artist_Info_Album_View=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    artistID=getDeererInformationFromDeezerServer("https://api.deezer.com/album/"+albumID).getJSONObject("artist").getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_Retrieve_Artist_Info_Album_View.start();
    }
    public void initView(){
        albumNameTV=findViewById(R.id.TV_Album_Name_Activity);
        mImageView = findViewById(R.id.IV_Album_Photo_Activity);
        artistNameTV=findViewById(R.id.TV_Album_Artist_Activity);
        followbtn=findViewById(R.id.followButtonAlbum);
        returnButton=findViewById(R.id.returnButtonAlbumView);

        artistNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_ID",artistID);
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent playListActivity = new Intent(Album_view_For_Deezer.this, ActivityArtistForDeezerSearch.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callFromHomeActivity==1){
                    returnMethod();
                }else{
                    onBackPressed();
                }
            }
        });

        followbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followbtn.getText().equals("Like")){
                    followbtn.setText("Dislike");
                    AlbumModelDeezer albumModelDeezer=new AlbumModelDeezer(Integer.valueOf(albumID),db.getActualUser(),nameAlbum,imageAlbum,nameArtist);
                    albumModelDeezer.setLike(true);
                    db.mAlbumDeezerDao().insertAlbum(albumModelDeezer);
                }else{
                    followbtn.setText("Like");
                    db.mAlbumDeezerDao().deleteAlbum(Integer.valueOf(albumID));
                }
            }
        });
    }
    public void initRecyclerView(){
        recyclerView=findViewById(R.id.RV_Album);
        informationForAdapteur=new ArrayList<ArrayList<String>>();
        adapteur=new RV_Adapter_Album(this.informationForAdapteur,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adapteur);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void retrieveBundle(){
        albumID=getIntent().getExtras().getString("ALBUM_ID");
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        isCallFromArtistView=getIntent().getExtras().getInt("IS_FROM_ARTIST_VIEW",0);
        callFromHomeActivity=getIntent().getExtras().getInt("IS_CALL_FROM_HOME_ACTIVITY",0);
    }
    public void setAlbumInformation(){
        Thread thread_Album_Information=new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonAlbumObject=getDeererInformationFromDeezerServer("https://api.deezer.com/album/"+albumID);
                //DEFINE ALBUM IMAGE
                setImageAlbum(jsonAlbumObject);
                //DEFINE ALBUM NAME
                setAlbumName(jsonAlbumObject);
                //DEFINE ARTIST NAME
                setArtistName(jsonAlbumObject);
                //DEFINE TRACK IN ALBUM
                setAlbumTracks(jsonAlbumObject);
            }
        });
        thread_Album_Information.start();
    }
    public void setImageAlbum(JSONObject jsonAlbumObject){
        URL newurl = null;
        try{
            imageAlbum=jsonAlbumObject.getString("cover_xl");
            if(!imageAlbum.equals("null")){
                newurl = new URL(imageAlbum);
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(mIcon_val);
                    }
                });
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void setAlbumName(final JSONObject jsonAlbumObject){
        try {
            nameAlbum = jsonAlbumObject.getString("title");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    albumNameTV.setText(nameAlbum);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setArtistName(final JSONObject jsonAlbumObject){
        try {
            nameArtist=jsonAlbumObject.getJSONObject("artist").getString("name");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    artistNameTV.setText(nameArtist);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setAlbumTracks(final JSONObject jsonAlbumObject){
        try {
            final JSONArray tracks=jsonAlbumObject.getJSONObject("tracks").getJSONArray("data");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<tracks.length(); i++){
                        ArrayList<String> arrayListTrack=new ArrayList<>();
                        try {
                            arrayListTrack.add(tracks.getJSONObject(i).getString("id"));
                            arrayListTrack.add(tracks.getJSONObject(i).getString("title"));
                            arrayListTrack.add(jsonAlbumObject.getJSONObject("artist").getString("name"));
                            String trackDuration="";
                            if (Integer.valueOf(tracks.getJSONObject(i).getString("duration"))%60<10){
                                trackDuration=Integer.valueOf(tracks.getJSONObject(i).getString("duration"))/60+":0"+Integer.valueOf(tracks.getJSONObject(i).getString("duration"))%60;
                            }else{
                                trackDuration=Integer.valueOf(tracks.getJSONObject(i).getString("duration"))/60+":"+Integer.valueOf(tracks.getJSONObject(i).getString("duration"))%60;
                            }
                            arrayListTrack.add(trackDuration);
                            arrayListTrack.add(fragmentName);
                            informationForAdapteur.add(arrayListTrack);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapteur.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        Bundle bundle=new Bundle();
        if (isCallFromArtistView==1){
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putString("ARTIST_ID",artistID);
            Intent playListActivity = new Intent(Album_view_For_Deezer.this, ActivityArtistForDeezerSearch.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }else if (fragmentName.equals("UserFragment")){
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putString("CONTEXT","AlbumActivity");
            Intent playListActivity = new Intent(Album_view_For_Deezer.this, HomeActivity.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }else if (fragmentName.equals("HomeFragment")){
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putString("CONTEXT","AlbumActivity");
            Intent playListActivity = new Intent(Album_view_For_Deezer.this, HomeActivity.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }
    }
    public void setcolorBtn(){
        DrawableCompat.setTint(returnButton.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        ((GradientDrawable) followbtn.getBackground()).setStroke(5, db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        //followbtn.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),followbtn.getText().toString()));
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
