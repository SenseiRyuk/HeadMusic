package com.example.if26new.Radio;

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

public class RadioActivity extends AppCompatActivity {
    private TextView TV_radioName;
    private ImageView IV_radioImage;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;
    private ConstraintLayout background;
    private TextView numberofTracks;

    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Artist_Track adapteur;
    private RecyclerView recyclerView;
    private String radioDeezerID;

    private JSONObject jsonObjectForDeezer;

    private String trackDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        db=SaveMyMusicDatabase.getInstance(this);
        retrieveBundle();
        initView();
        initRecyclerView();
        getAllSongFromTheDataBase();
    }
    public void retrieveBundle(){
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        radioDeezerID=getIntent().getExtras().getString("RADIO_ID");
    }
    public void initView(){
        numberofTracks=findViewById(R.id.TV_Number_Of_Tracks_Radio_Activity);

        returnButton=findViewById(R.id.IB_Return_Button_Radio_Activity);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });
        TV_radioName=findViewById(R.id.TV_Name_Radio_Activity);
        IV_radioImage = findViewById(R.id.IV_Radio_Activity);
        IV_radioImage.setAdjustViewBounds(false);
        IV_radioImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        JSONObject jsonObject =getDeererInformationFromDeezerServer("https://api.deezer.com/radio/"+radioDeezerID);
        try {
            initializeImageAlbum(jsonObject.getString("picture_xl"));
            TV_radioName.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void initRecyclerView(){
        recyclerView=findViewById(R.id.RV_Radio_Activity);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_Artist_Track(this.informationForAdapteur,this);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    public void getAllSongFromTheDataBase(){
        try {
            JSONArray jsonArray =getDeererInformationFromDeezerServer("https://api.deezer.com/radio/"+radioDeezerID+"/tracks").getJSONArray("data");
            numberofTracks.setText(jsonArray.length()+" Tracks");
            for (int i=0; i<jsonArray.length(); i++){
                ArrayList<String> arrayListTrack=new ArrayList<>();
                arrayListTrack.add(jsonArray.getJSONObject(i).getJSONObject("artist").getString("name"));
                arrayListTrack.add(jsonArray.getJSONObject(i).getString("title"));
                arrayListTrack.add(jsonArray.getJSONObject(i).getString("id"));
                arrayListTrack.add("UserFragment");
                if (Integer.valueOf(jsonArray.getJSONObject(i).getString("duration"))%60<10){
                    trackDuration=Integer.valueOf(jsonArray.getJSONObject(i).getString("duration"))/60+":0"+Integer.valueOf(jsonArray.getJSONObject(i).getString("duration"))%60;
                }else{
                    trackDuration=Integer.valueOf(jsonArray.getJSONObject(i).getString("duration"))/60+":"+Integer.valueOf(jsonArray.getJSONObject(i).getString("duration"))%60;
                }
                arrayListTrack.add(trackDuration);
                arrayListTrack.add("SearchViewActivity");
                informationForAdapteur.add(arrayListTrack);
                adapteur.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        Intent playListActivity = new Intent(this, HomeActivity.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
    public void initializeImageAlbum(final String urlImage){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlImage.equals("null")){
                        final URL newurl = new URL(urlImage);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                IV_radioImage.setImageBitmap(ICON);
                            }
                        });
                        runOnUiThread(thread);
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
    public JSONObject getDeererInformationFromDeezerServer(final String query){
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
