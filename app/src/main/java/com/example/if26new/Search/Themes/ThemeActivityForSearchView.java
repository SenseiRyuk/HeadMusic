package com.example.if26new.Search.Themes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.if26new.Model.UserModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.example.if26new.Search.Themes.Album.RV_Adapter_Theme_Album;
import com.example.if26new.Search.Themes.Artist.RV_Adapter_Theme_Artist;
import com.example.if26new.Search.Themes.Playlist.RV_Adapter_Theme_Playlist;
import com.example.if26new.Search.Themes.Track.RV_Adapter_Theme_Track;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ThemeActivityForSearchView extends AppCompatActivity {
    private SaveMyMusicDatabase db;
    private ConstraintLayout background;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Theme_Album adapteur;
    private RecyclerView recyclerView;
    private int sizeOfAlbum;
    private JSONArray jsonArrayAlbum;


    private RecyclerView recyclerViewArtist;
    private RV_Adapter_Theme_Artist adapteurArtist;
    private ArrayList<ArrayList<String>> informationForAdapteurArtist;
    private int sizeOfArtist;
    private JSONArray jsonArrayArtist;

    private RecyclerView recyclerViewPlaylist;
    private RV_Adapter_Theme_Playlist adapteurPlaylist;
    private ArrayList<ArrayList<String>> informationForAdapteurPlaylist;
    private int sizeOfPlaylist;
    private JSONArray jsonArrayPlaylist;

    private RecyclerView recyclerViewTrack;
    private RV_Adapter_Theme_Track adapteurTrack;
    private ArrayList<ArrayList<String>> informationForAdapteurTrack;
    private int sizeOfTrack;
    private JSONArray jsonArrayTrack;

    private JSONObject jsonObjectForDeezer;
    private String themeNumber="0";
    private String fragmentName;
    private TextView theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        db= SaveMyMusicDatabase.getInstance(this);
        background=findViewById(R.id.CL_Theme);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        background.setBackground(gd);

        retrieveBundle();

        setRecyclerViewArtist();
        setViewArtist();

        setRecyclerViewAlbum();
        setViewAlbum();

        setRecyclerViewPlaylist();
        setViewPlaylist();

        setRecyclerViewTrack();
        setViewTrack();

    }
    public void retrieveBundle(){
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        themeNumber=getIntent().getExtras().getString("THEME_ID");
        theme=findViewById(R.id.genreTVSearchThemeActivity);
        theme.setText(getIntent().getExtras().getString("THEME_NAME"));
    }
    public void setRecyclerViewAlbum(){
        recyclerView=findViewById(R.id.RV_Theme_Album);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/"+themeNumber+"/albums");
        try {
            jsonArrayAlbum=jsonObject.getJSONArray("data");
            if (jsonArrayAlbum.length()<10){
                sizeOfAlbum=jsonArrayAlbum.length();
            }else {
                sizeOfAlbum = 10;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapteur=new RV_Adapter_Theme_Album(this.informationForAdapteur,this,sizeOfAlbum);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
    }
    public void setRecyclerViewArtist(){
        recyclerViewArtist=findViewById(R.id.RV_Theme_Artist);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewArtist.setLayoutManager(horizontalLayoutManagaer);

        this.informationForAdapteurArtist=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/"+themeNumber+"/artists");
        try {
            jsonArrayArtist=jsonObject.getJSONArray("data");
            if (jsonArrayArtist.length()<10){
                sizeOfArtist=jsonArrayArtist.length();
            }else {
                sizeOfArtist = 10;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapteurArtist=new RV_Adapter_Theme_Artist(this.informationForAdapteurArtist,this,sizeOfArtist);
        recyclerViewArtist.setHasFixedSize(true);
        this.recyclerViewArtist.setAdapter(this.adapteurArtist);
    }
    public void setRecyclerViewPlaylist(){
        recyclerViewPlaylist=findViewById(R.id.RV_Theme_Playlist);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPlaylist.setLayoutManager(horizontalLayoutManagaer);

        this.informationForAdapteurPlaylist=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/"+themeNumber+"/playlists");
        try {
            jsonArrayPlaylist=jsonObject.getJSONArray("data");
            if (jsonArrayPlaylist.length()<10){
                sizeOfPlaylist=jsonArrayPlaylist.length();
            }else {
                sizeOfPlaylist = 10;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapteurPlaylist=new RV_Adapter_Theme_Playlist(this.informationForAdapteurPlaylist,this,sizeOfPlaylist);
        recyclerViewPlaylist.setHasFixedSize(true);
        this.recyclerViewPlaylist.setAdapter(this.adapteurPlaylist);
    }
    public void setRecyclerViewTrack(){
        recyclerViewTrack=findViewById(R.id.RV_Theme_Tracks);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrack.setLayoutManager(horizontalLayoutManagaer);

        this.informationForAdapteurTrack=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/"+themeNumber+"/tracks");
        try {
            jsonArrayTrack=jsonObject.getJSONArray("data");
            if (jsonArrayTrack.length()<10){
                sizeOfTrack=jsonArrayTrack.length();
            }else {
                sizeOfTrack = 10;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapteurTrack=new RV_Adapter_Theme_Track(this.informationForAdapteurTrack,this,sizeOfTrack);
        recyclerViewTrack.setHasFixedSize(true);
        this.recyclerViewTrack.setAdapter(this.adapteurTrack);
    }

    public void setViewAlbum(){
        try{
            for (int i = 0; i < sizeOfAlbum; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("title"));
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("cover_xl"));
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("id"));
                arrayListArtist.add(fragmentName);
                informationForAdapteur.add(arrayListArtist);
                adapteur.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setViewArtist(){
        try{
            for (int i = 0; i < sizeOfArtist; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("name"));
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("picture_xl"));
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("id"));
                arrayListArtist.add(fragmentName);
                informationForAdapteurArtist.add(arrayListArtist);
                adapteurArtist.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setViewPlaylist(){
        try{
            for (int i = 0; i < sizeOfPlaylist; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayPlaylist.getJSONObject(i).getString("title"));
                arrayListArtist.add(jsonArrayPlaylist.getJSONObject(i).getString("picture_xl"));
                arrayListArtist.add(jsonArrayPlaylist.getJSONObject(i).getString("id"));
                arrayListArtist.add(fragmentName);
                informationForAdapteurPlaylist.add(arrayListArtist);
                adapteurPlaylist.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setViewTrack(){
        try{
            for (int i = 0; i < sizeOfTrack; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getString("id"));
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getString("title"));
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getJSONObject("album").getString("cover_xl"));
                arrayListArtist.add(fragmentName);
                informationForAdapteurTrack.add(arrayListArtist);
                adapteurTrack.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getDeezerInformation(final String query){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
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
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObjectForDeezer;
    }

}
