package com.example.if26new.Library;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.example.if26new.Search.Themes.Album.RV_Adapter_Theme_Album;
import com.example.if26new.Search.Themes.Artist.RV_Adapter_Theme_Artist;
import com.example.if26new.Search.Themes.Playlist.RV_Adapter_Theme_Playlist;
import com.example.if26new.Search.Themes.Track.RV_Adapter_Theme_Track;
import com.google.android.material.tabs.TabLayout;

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
public class UserFragment extends Fragment {

    private TabLayout onglet;
    private ViewPager viewPagerForFragments;
    private PageAdaptaterForUser pageAdaptaterForUser;
    private View view;
    private SaveMyMusicDatabase db;
    private JSONObject jsonObjectForDeezer;

    private TextView textViewName;

    private TextView typeViewPager;
    private ImageView firstLine;
    private ImageView secondLine;

    private RecyclerView recyclerView;


    private RV_Adapter_Theme_Artist adapteurArtist;
    private ArrayList<ArrayList<String>> informationForAdapteurArtist;
    private int sizeOfArtist;
    private JSONArray jsonArrayArtist;

    private RV_Adapter_Theme_Playlist adapteurPlaylist;
    private ArrayList<ArrayList<String>> informationForAdapteurPlaylist;
    private int sizeOfPlaylist;
    private JSONArray jsonArrayPlaylist;

    private ArrayList<ArrayList<String>> informationForAdapteurAlbum;
    private RV_Adapter_Theme_Album adapteurAlbum;
    private int sizeOfAlbum;
    private JSONArray jsonArrayAlbum;

    private ArrayList<ArrayList<String>> informationForAdapteurTrack;
    private RV_Adapter_Theme_Track adapteurTrack;
    private int sizeOfTrack;
    private JSONArray jsonArrayTrack;

    public UserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        setSizeFirstLine();
        setSizeSecondLine();
        setRecommanded_Artist_Album_Playlist_Track();
        viewPagerForFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    textViewName.setText("Recommanded Playlists");
                    typeViewPager.setText("Your Playlists");
                    setSizeFirstLine();
                    setSizeSecondLine();
                    recyclerView.setAdapter(adapteurPlaylist);
                    setViewPlaylist();
                }else if (position==1){
                    textViewName.setText("Recommanded Artists");
                    typeViewPager.setText("Followed artists");
                    setSizeFirstLine();
                    setSizeSecondLine();

                    recyclerView.setAdapter(adapteurArtist);
                    setViewArtist();
                }else if (position==2){
                    textViewName.setText("Recommanded Albums");
                    typeViewPager.setText("Liked albums");
                    setSizeFirstLine();
                    setSizeSecondLine();
                    recyclerView.setAdapter(adapteurAlbum);
                    setViewAlbum();
                }else if (position==3){
                    textViewName.setText("Recommanded Tracks");
                    typeViewPager.setText("Liked track");
                    setSizeFirstLine();
                    setSizeSecondLine();
                    recyclerView.setAdapter(adapteurTrack);
                    setViewTrack();

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerForFragments.setAdapter(pageAdaptaterForUser);
        onglet.setupWithViewPager(viewPagerForFragments);
        //Design purpose. Tabs have the same width
        onglet.setTabMode(TabLayout.MODE_FIXED);
        setcolorBtn();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        //myContext=(FragmentActivity) activity;
        pageAdaptaterForUser = new PageAdaptaterForUser(getChildFragmentManager());
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageAdaptaterForUser.notifyDataSetChanged();
    }

    public void setRecommanded_Artist_Album_Playlist_Track(){
        Thread thread_User_Recommanded=new Thread(new Runnable() {
            @Override
            public void run() {
                setRecyclerViewRecommandedArtist(view);
                setRecyclerViewRecommandedAlbum(view);
                setRecyclerViewRecommanderTrack(view);
                setRecyclerViewRecommandedPlaylist(view);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPlaylist();
                        recyclerView.setAdapter(adapteurPlaylist);
                    }
                });
            }
        });
        thread_User_Recommanded.start();
    }
    public void initView(View view){
        onglet=view.findViewById(R.id.tabLayout);
        db=SaveMyMusicDatabase.getInstance(getActivity());
        onglet.setSelectedTabIndicatorColor(db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        viewPagerForFragments=view.findViewById(R.id.viewPagerUser);
        viewPagerForFragments.setOffscreenPageLimit(3);
        typeViewPager=view.findViewById(R.id.TV_Type_Of_View_Pager);
        textViewName=view.findViewById(R.id.textViewUserFragment);
        firstLine=view.findViewById(R.id.imageViewForLineFirstUserFragment);
        secondLine=view.findViewById(R.id.imageViewForLineSecondUserFragment);
        typeViewPager.setText("Your Playlists");
        textViewName.setText("Recommanded Playlists");

        recyclerView=view.findViewById(R.id.RV_Recommanded_User);
        RecyclerView.LayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setHasFixedSize(true);
    }

    public void setRecyclerViewRecommandedArtist(View view){
        this.informationForAdapteurArtist=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/152/artists");
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
        this.adapteurArtist=new RV_Adapter_Theme_Artist(this.informationForAdapteurArtist,getActivity(),sizeOfArtist);
    }
    public void setRecyclerViewRecommandedPlaylist(View view){
        this.informationForAdapteurPlaylist=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/132/playlists");
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
        this.adapteurPlaylist=new RV_Adapter_Theme_Playlist(this.informationForAdapteurPlaylist,getActivity(),sizeOfPlaylist);
    }
    public void setRecyclerViewRecommandedAlbum(View view){
        this.informationForAdapteurAlbum=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/116/albums");
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
        this.adapteurAlbum=new RV_Adapter_Theme_Album(this.informationForAdapteurAlbum,getActivity(),sizeOfAlbum);
    }
    public void setRecyclerViewRecommanderTrack(View view){
        this.informationForAdapteurTrack=new ArrayList<ArrayList<String>>();
        JSONObject jsonObject=getDeezerInformation("https://api.deezer.com/chart/116/tracks");
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
        this.adapteurTrack=new RV_Adapter_Theme_Track(this.informationForAdapteurTrack,getActivity(),sizeOfTrack);
    }

    public void setViewArtist(){
        try{
            for (int i = 0; i < sizeOfArtist; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("name"));
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("picture_xl"));
                arrayListArtist.add(jsonArrayArtist.getJSONObject(i).getString("id"));
                arrayListArtist.add("UserFragment");
                informationForAdapteurArtist.add(arrayListArtist);
                adapteurArtist.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setViewAlbum(){
        try{
            for (int i = 0; i < sizeOfAlbum; i++) {
                ArrayList<String> arrayListAlbum=new ArrayList<>();
                arrayListAlbum.add(jsonArrayAlbum.getJSONObject(i).getString("title"));
                arrayListAlbum.add(jsonArrayAlbum.getJSONObject(i).getString("cover_xl"));
                arrayListAlbum.add(jsonArrayAlbum.getJSONObject(i).getString("id"));
                arrayListAlbum.add("UserFragment");
                informationForAdapteurAlbum.add(arrayListAlbum);
                adapteurAlbum.notifyItemChanged(i);
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
                arrayListArtist.add("UserFragment");
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
                arrayListArtist.add("UserFragment");
                informationForAdapteurTrack.add(arrayListArtist);
                adapteurTrack.notifyItemChanged(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    public void setcolorBtn(){
//        createNewPlaylist.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),createNewPlaylist.getText().toString()));
    }
    public void setSizeFirstLine(){
        firstLine.setBackgroundColor(Color.parseColor("#000000"));
        textViewName.measure(0,0);
        firstLine.getLayoutParams().width=textViewName.getMeasuredWidth();
    }
    public void setSizeSecondLine(){
        secondLine.setBackgroundColor(Color.parseColor("#000000"));
        typeViewPager.measure(0,0);
        secondLine.getLayoutParams().width=typeViewPager.getMeasuredWidth();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
