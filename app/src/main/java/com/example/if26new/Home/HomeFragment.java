package com.example.if26new.Home;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Listening.ListeningForDeezer;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.example.if26new.Search.Themes.Album.RV_Adapter_Theme_Album;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SaveMyMusicDatabase db;
    private JSONObject jsonObjectForDeezer;

    private RecyclerView recyclerViewPlaylist;
    private RV_Adapter_Home_Playlists adapteurPlaylist;
    private ArrayList<ArrayList<String>> informationForAdapteurPlaylist;
    private int sizeOfPlaylist;
    private JSONArray jsonArrayPlaylist;
    private PlaylistModel[] allPlaylist;

    private RecyclerView recyclerViewTrack;
    private RV_Adapter_Theme_Track adapteurTrack;
    private ArrayList<ArrayList<String>> informationForAdapteurTrack;
    private int sizeOfTrack;
    private JSONArray jsonArrayTrack;

    private RecyclerView recyclerViewAlbum;
    private RV_Adapter_Theme_Album adapteurAlbum;
    private ArrayList<ArrayList<String>> informationForAdapteurAlbum;
    private int sizeOfAlbum;
    private JSONArray jsonArrayAlbum;

    private JSONArray jsonArrayFlow;

    private ImageView flowImage;
    private ImageView playButton;
    private String songIDFlow;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_home, container, false);
        initView(result);
        setRecyclersView();
        return result;
    }
    public void initView(View view){
        playButton=view.findViewById(R.id.IV_Play_Flow_Btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jsonArrayFlow!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString("FRAGMENT_NAME","HomeFragment");
                    bundle.putString("CONTEXT","Call_From_Flow_Activity");
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                songIDFlow=jsonArrayFlow.getJSONObject(0).getString("id");
                            } catch (JSONException e) {
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
                    bundle.putString("SONG_ID",songIDFlow);
                    Intent listeningActivity = new Intent(v.getContext(), ListeningForDeezer.class);
                    listeningActivity.putExtras(bundle);
                    v.getContext().startActivity(listeningActivity);
                }
            }
        });
        RecyclerView.LayoutManager linearLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPlaylist=view.findViewById(R.id.RV_Home_Playlist);
        recyclerViewPlaylist.setHasFixedSize(true);
        recyclerViewPlaylist.setAdapter(adapteurPlaylist);
        recyclerViewPlaylist.setLayoutManager(linearLayoutManagaer);

        RecyclerView.LayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrack=view.findViewById(R.id.RV_Home_Recently_Listened);
        recyclerViewTrack.setHasFixedSize(true);
        recyclerViewTrack.setLayoutManager(horizontalLayoutManagaer2);

        RecyclerView.LayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAlbum=view.findViewById(R.id.RV_Home_Released);
        recyclerViewAlbum.setHasFixedSize(true);
        recyclerViewAlbum.setLayoutManager(horizontalLayoutManagaer3);

        flowImage=view.findViewById(R.id.IV_Home_MainImage);
    }
    public void setRecyclersView(){
        Thread thread_Home_Fill_Recycler_View=new Thread(new Runnable() {
            @Override
            public void run() {
                initRecyclerViewPlaylist();
                initRecyclerViewHistoric();
                initRecyclerViewReleased();
                initFlowView();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewPlaylist.setAdapter(adapteurPlaylist);
                        setViewPlaylist();
                        recyclerViewAlbum.setAdapter(adapteurAlbum);
                        setViewReleased();
                        recyclerViewTrack.setAdapter(adapteurTrack);
                        setViewHistoric();
                    }
                });
            }
        });
        thread_Home_Fill_Recycler_View.start();
    }

    public void initFlowView(){
        Thread thread_init_Flow=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArrayFlow=getDeezerInformation("https://api.deezer.com/user/3980074442/flow").getJSONArray("data");
                    initializeImageAlbum(jsonArrayFlow.getJSONObject(0).getJSONObject("artist").getString("picture_xl"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_init_Flow.start();
    }

    public void initRecyclerViewHistoric(){
        informationForAdapteurTrack=new ArrayList<ArrayList<String>>();
        Thread thread_init_Hisotric=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArrayTrack=getDeezerInformation("https://api.deezer.com/chart/0/tracks").getJSONArray("data");
                    if (jsonArrayTrack.length()>15){
                        sizeOfTrack=15;
                    }else{
                        sizeOfTrack=jsonArrayTrack.length();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_init_Hisotric.start();
        try {
            thread_init_Hisotric.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapteurTrack=new RV_Adapter_Theme_Track(informationForAdapteurTrack,getActivity(),sizeOfTrack);
    }
    public void setViewHistoric(){
        try{
            for (int i = 0; i < sizeOfTrack; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getString("id"));
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getString("title"));
                arrayListArtist.add(jsonArrayTrack.getJSONObject(i).getJSONObject("album").getString("cover_xl"));
                arrayListArtist.add("HomeFragment");
                informationForAdapteurTrack.add(arrayListArtist);
                adapteurTrack.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initRecyclerViewReleased(){
        informationForAdapteurAlbum=new ArrayList<ArrayList<String>>();
        Thread thread_init_Released=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArrayAlbum=getDeezerInformation("https://api.deezer.com/chart/0/albums").getJSONArray("data");
                    if (jsonArrayAlbum.length()>15){
                        sizeOfAlbum=15;
                    }else{
                        sizeOfAlbum=jsonArrayAlbum.length();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_init_Released.start();
        try {
            thread_init_Released.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapteurAlbum=new RV_Adapter_Theme_Album(informationForAdapteurAlbum,getActivity(),sizeOfAlbum);
    }
    public void setViewReleased(){
        try{
            for (int i = 0; i < sizeOfAlbum; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("title"));
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("cover_xl"));
                arrayListArtist.add(jsonArrayAlbum.getJSONObject(i).getString("id"));
                arrayListArtist.add("HomeFragment");
                informationForAdapteurAlbum.add(arrayListArtist);
                adapteurAlbum.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initRecyclerViewPlaylist(){
        informationForAdapteurPlaylist=new ArrayList<ArrayList<String>>();
        db=SaveMyMusicDatabase.getInstance(getActivity());
        allPlaylist = db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
        sizeOfPlaylist=allPlaylist.length;
        if (sizeOfPlaylist>6){
            sizeOfPlaylist=6;
            adapteurPlaylist=new RV_Adapter_Home_Playlists(informationForAdapteurPlaylist,getActivity(),6);
        }else{
            adapteurPlaylist=new RV_Adapter_Home_Playlists(informationForAdapteurPlaylist,getActivity(),sizeOfPlaylist);
        }
    }
    public void setViewPlaylist(){
        int counter=0;
        boolean entrer=false;

        for (int i = 0; i < sizeOfPlaylist; i=i+2) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            if (entrer==true){
                arrayListArtist.add(allPlaylist[i].getTitles());
                if (allPlaylist[i].getImageDeezer()==null){
                    arrayListArtist.add("LocalImage");
                    arrayListArtist.add(String.valueOf(allPlaylist[i].getImage()));
                }else{
                    arrayListArtist.add("DeezerImage");
                    arrayListArtist.add(String.valueOf(allPlaylist[i].getImageDeezer()));
                }
            }else{
                PlaylistModel favoritePlaylist=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
                arrayListArtist.add(favoritePlaylist.getTitles());
                if (favoritePlaylist.getImageDeezer()==null){
                    arrayListArtist.add("LocalImage");
                    arrayListArtist.add(String.valueOf(favoritePlaylist.getImage()));
                }else{
                    arrayListArtist.add("DeezerImage");
                    arrayListArtist.add(String.valueOf(favoritePlaylist.getImageDeezer()));
                }
            }
            if (i!=sizeOfPlaylist-1){
                arrayListArtist.add(allPlaylist[i+1].getTitles());
                if (allPlaylist[i+1].getImageDeezer()==null){
                    arrayListArtist.add("LocalImage");
                    arrayListArtist.add(String.valueOf(allPlaylist[i+1].getImage()));
                }else{
                    arrayListArtist.add("DeezerImage");
                    arrayListArtist.add(String.valueOf(allPlaylist[i+1].getImageDeezer()));
                }
            }else{
                arrayListArtist.add("no Image");
            }
            informationForAdapteurPlaylist.add(arrayListArtist);
            adapteurPlaylist.notifyItemChanged(counter);
            counter++;
            entrer=true;
        }
    }
    
    public JSONObject getDeezerInformation(final String query){
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

//    public void eraseLoadingRapTrack(){
//        animationDrawableForLoading.stop();
//        linearLayoutTracksRap.removeAllViews();
//    }
//    public void loadingScreenRapTrack(){
//        LinearLayout.LayoutParams first=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        first.setMargins(0, 0, 20, 0);
//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayoutTracksRap.addView(linearLayout);
//
//        flowImage loadingIV=new flowImage(getActivity());
//        linearLayout.addView(loadingIV);
//        android.view.ViewGroup.LayoutParams params = loadingIV.getLayoutParams();
//        params.height=length+50;//+50 for the size of textview
//        params.width=length+50;
//        loadingIV.setLayoutParams(params);
//
//        loadingIV.setImageBitmap(null);
//        loadingIV.setBackgroundResource(R.drawable.loading_animation);
//        animationDrawableForLoading= (AnimationDrawable)loadingIV.getBackground();
//        animationDrawableForLoading.start();
//    }
    public void initializeImageAlbum(final String urlImage){
        Thread thread_set_Image_Flow=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlImage.equals("null")){
                        final URL newurl = new URL(urlImage);
                        final Bitmap ICON=BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                flowImage.setImageBitmap(ICON);
                            }
                        });
                        getActivity().runOnUiThread(thread);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread_set_Image_Flow.start();
    }
}

