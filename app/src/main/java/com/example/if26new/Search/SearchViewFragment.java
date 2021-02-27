package com.example.if26new.Search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;
import com.example.if26new.Search.Search.RV_Adapter_Search;
import com.example.if26new.Search.Themes.RV_Adapter_Themes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SearchViewFragment extends Fragment {

    private SearchView searchView;
    private SaveMyMusicDatabase db;
    private View v;
    private int counter;

    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RecyclerView recyclerView;
    private RV_Adapter_Search adapteur;

    private RecyclerView recyclerView_Theme;
    private ArrayList<ArrayList<String>> informationForAdapteur_Themes;
    private RV_Adapter_Themes adapteurForThemes;
    private JSONArray jsonArrayThemes;

    private Thread thread_Search_View;

    private CheckBox artistCB;
    private CheckBox albumCB;
    private CheckBox playlistCB;
    private CheckBox trackCB;
    private CheckBox radioCB;
    private CheckBox podcastCB;
    public SearchViewFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_search, container, false);
        initView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                recyclerView_Theme.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(adapteur);
                informationForAdapteur.clear();
                adapteur.notifyDataSetChanged();
                adapteur.reinitialzedBitmap();
                //Get DEEZER INFORMATION
                thread_Search_View=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        counter=0;
                        if (!artistCB.isChecked() && !albumCB.isChecked() && !trackCB.isChecked() && !podcastCB.isChecked() && !radioCB.isChecked() && !playlistCB.isChecked()){
                            System.out.println("1");
                            getDeezerSearchInformationArtist(query);
                            System.out.println("2");
                            getDeezerSearchInformationAlbums(query);
                            System.out.println("3");
                            getDeezerSearchInformationSongs(query);
                            System.out.println("4");
                            getDeezerSearchInformationPlaylists(query);
                            System.out.println("5");
                            getDeezerSearchInformationRadio(query);
                            System.out.println("6");
                            getDeezerSearchInformationPodcast(query);
                        }
                        else{
                            if (artistCB.isChecked()){
                                getDeezerSearchInformationArtist(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                            if (albumCB.isChecked()){
                                getDeezerSearchInformationAlbums(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                            if (trackCB.isChecked()){
                                getDeezerSearchInformationSongs(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                            if(podcastCB.isChecked()){
                                getDeezerSearchInformationPodcast(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                            if (radioCB.isChecked()){
                                getDeezerSearchInformationRadio(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                            if (playlistCB.isChecked()){
                                getDeezerSearchInformationPlaylists(query);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapteur.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                });
                thread_Search_View.start();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    if (thread_Search_View!=null && thread_Search_View.isAlive()){
                        thread_Search_View.interrupt();
                    }
                    recyclerView_Theme.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapteur);
                    informationForAdapteur.clear();
                    adapteur.notifyDataSetChanged();
                    adapteur.reinitialzedBitmap();
                }
                return false;
            }
        });
        configureRecyclerView();
        configureRecyclerViewTheme();
        fillRecyclerViewForTheme();
        return v;
    }
    public void initView(){
        searchView=v.findViewById(R.id.searchViewFragment);

        artistCB=v.findViewById(R.id.checkBoxArtist);
        albumCB=v.findViewById(R.id.checkBoxAlbum);
        playlistCB=v.findViewById(R.id.checkBoxPlaylist);
        trackCB=v.findViewById(R.id.checkBoxTrack);
        radioCB=v.findViewById(R.id.checkBoxRadio);
        podcastCB=v.findViewById(R.id.checkBoxPodcast);
    }

    public void configureRecyclerView(){
        recyclerView=v.findViewById(R.id.RV_Search);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_Search(this.informationForAdapteur,getActivity(),18);

        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void configureRecyclerViewTheme(){
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=getDeererInformationFromDeezerServer("https://api.deezer.com/genre");
                try {
                    jsonArrayThemes=jsonObject.getJSONArray("data");
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
        recyclerView_Theme=v.findViewById(R.id.RV_Search_Theme);
        this.informationForAdapteur_Themes=new ArrayList<ArrayList<String>>();
        this.adapteurForThemes=new RV_Adapter_Themes(informationForAdapteur_Themes,getActivity(),jsonArrayThemes.length());

        recyclerView_Theme.setHasFixedSize(true);
        this.recyclerView_Theme.setAdapter(this.adapteurForThemes);
        this.recyclerView_Theme.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void fillRecyclerViewForTheme(){
        int countForRecycler=0;
        for (int i=0;i<jsonArrayThemes.length();i=i+2){
            ArrayList <String> arrayList=new ArrayList<>();
            try{
                JSONObject jsonObject2=jsonArrayThemes.getJSONObject(i);
                arrayList.add(jsonObject2.getString("id"));
                arrayList.add(jsonObject2.getString("name"));
                arrayList.add(jsonObject2.getString("picture_xl"));

                JSONObject jsonObject3=jsonArrayThemes.getJSONObject(i+1);
                arrayList.add(jsonObject3.getString("id"));
                arrayList.add(jsonObject3.getString("name"));
                arrayList.add(jsonObject3.getString("picture_xl"));

                informationForAdapteur_Themes.add(arrayList);
                adapteurForThemes.notifyItemChanged(countForRecycler);
                countForRecycler++;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    //////////////////////////////////////////
    ///////////////FILL ARTIST////////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationArtist(String query){
        try {
            JSONArray jsonarray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/artist?q="+query).getJSONArray("data");
            if (jsonarray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Artists","artist",query);
                    }
                    add_Artist_Information(jsonarray,i);
                    break;
                }
            }else{
                for (int i=0;i<jsonarray.length();i++){
                    if (i==0){
                        addTitle("Artists","artist",query);
                    }
                    add_Artist_Information(jsonarray,i);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void add_Artist_Information(JSONArray jsonArray,int i){
        JSONObject artistObject= null;
        try {
            final ArrayList<String> arrayListArtist=new ArrayList<>();
            artistObject = jsonArray.getJSONObject(i);
            arrayListArtist.add("0");
            arrayListArtist.add("Artist");
            arrayListArtist.add(artistObject.getString("name"));
            arrayListArtist.add(artistObject.getString("picture_xl"));
            arrayListArtist.add(artistObject.getString("id"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListArtist);
                    adapteur.notifyItemChanged(counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    ///////////////FILL ALBUM/////////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationAlbums(String query){
        try {
            JSONArray jsonArray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/album?q="+query).getJSONArray("data");
            if (jsonArray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Albums","album",query);
                    }
                    add_Album_Information(jsonArray,i);
                }
            }else{
                for (int i=0;i<jsonArray.length();i++){
                    if (i==0){
                        addTitle("Albums","album",query);
                    }
                    add_Album_Information(jsonArray,i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void add_Album_Information(JSONArray jsonArray,int i){
        try {
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            final ArrayList <String> arrayListAlbum=new ArrayList<>();
            arrayListAlbum.add("0");
            arrayListAlbum.add("Album");
            arrayListAlbum.add(jsonObject2.getString("title"));
            arrayListAlbum.add(jsonObject2.getString("id"));
            arrayListAlbum.add(jsonObject2.getString("cover_xl"));
            arrayListAlbum.add(jsonObject2.getJSONObject("artist").getString("name"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListAlbum);
                    adapteur.notifyItemChanged(counter);
//                    System.out.println("COUNTER "+counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    ///////////////FILL SONG//////////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationSongs(String query){
        try {
            JSONArray jsonArray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/track?q="+query).getJSONArray("data");
            if (jsonArray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Tracks","track",query);
                    }
                    add_Track_Information(jsonArray,i);
                }
            }else {
                for (int i=0;i<jsonArray.length();i++){
                    if (i==0){
                        addTitle("Tracks","track",query);
                    }
                    add_Track_Information(jsonArray,i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void add_Track_Information(JSONArray jsonArray,int i){
        try {
            final ArrayList <String> arrayListTrack=new ArrayList<>();
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            arrayListTrack.add("0");
            arrayListTrack.add("Track");
            arrayListTrack.add(jsonObject2.getString("title_short"));
            arrayListTrack.add(jsonObject2.getString("id"));
            arrayListTrack.add(jsonObject2.getJSONObject("artist").getString("name"));
            arrayListTrack.add(jsonObject2.getJSONObject("album").getString("cover_xl"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListTrack);
                    adapteur.notifyItemChanged(counter);
//                    System.out.println("COUNTER "+counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    ///////////////FILL PLAYLIST//////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationPlaylists(String query){
        try {
            JSONArray jsonArray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/playlist?q="+query).getJSONArray("data");
            if (jsonArray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Playlists","playlist",query);
                    }
                    add_Playlist_Information(jsonArray,i);
                }
            }else{
                for (int i=0;i<jsonArray.length();i++){
                    if (i==0){
                        addTitle("Playlists","playlist",query);
                    }
                    add_Playlist_Information(jsonArray,i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void add_Playlist_Information(JSONArray jsonArray,int i){
        try {
            final ArrayList <String> arrayListPlaylist=new ArrayList<>();
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            arrayListPlaylist.add("0");
            arrayListPlaylist.add("Playlist");
            arrayListPlaylist.add(jsonObject2.getString("title"));
            arrayListPlaylist.add(jsonObject2.getString("picture_xl"));
            arrayListPlaylist.add(jsonObject2.getString("id"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListPlaylist);
                    adapteur.notifyItemChanged(counter);
//                    System.out.println("COUNTER "+counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    ///////////////FILL RADIO/////////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationRadio(String query){
        try {
            JSONArray jsonArray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/radio?q="+query).getJSONArray("data");
            if (jsonArray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Radios","radio",query);
                    }
                    add_Radio_Information(jsonArray,i);
                }
            }else{
                for (int i=0;i<jsonArray.length();i++){
                    if (i==0){
                        addTitle("Radios","radio",query);
                    }
                    add_Radio_Information(jsonArray,i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void add_Radio_Information(JSONArray jsonArray,int i){
        try {
            final ArrayList <String> arrayListRadio=new ArrayList<>();
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            arrayListRadio.add("0");
            arrayListRadio.add("Radio");
            arrayListRadio.add(jsonObject2.getString("title"));
            arrayListRadio.add(jsonObject2.getString("picture_xl"));
            arrayListRadio.add(jsonObject2.getString("id"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListRadio);
                    adapteur.notifyItemChanged(counter);
//                    System.out.println("COUNTER "+counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    ///////////////FILL PODCAST///////////////
    //////////////////////////////////////////
    public void getDeezerSearchInformationPodcast(String query){
        try {
            JSONArray jsonArray=getDeererInformationFromDeezerServer("https://api.deezer.com/search/podcast?q="+query).getJSONArray("data");
            if (jsonArray.length()>=3){
                for (int i=0;i<3;i++){
                    if (i==0){
                        addTitle("Podcasts","podcast",query);
                    }
                    add_Podcast_Information(jsonArray,i);
                }
            }else{
                for (int i=0;i<jsonArray.length();i++){
                    if (i==0){
                        addTitle("Podcasts","podcast",query);
                    }
                    add_Podcast_Information(jsonArray,i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void add_Podcast_Information(JSONArray jsonArray,int i){
        try {
            final ArrayList <String> arrayListPodcast=new ArrayList<>();
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            arrayListPodcast.add("0");
            arrayListPodcast.add("Podcast");
            arrayListPodcast.add(jsonObject2.getString("title"));
            arrayListPodcast.add(jsonObject2.getString("picture_xl"));
            arrayListPodcast.add(jsonObject2.getString("id"));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    informationForAdapteur.add(arrayListPodcast);
                    adapteur.notifyItemChanged(counter);
//                    System.out.println("COUNTER "+counter);
                    counter++;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void addTitle(String title,String type,String researchWord){
        final ArrayList <String> arrayListTitle=new ArrayList<>();
        arrayListTitle.add("1");
        arrayListTitle.add(title);
        arrayListTitle.add(type);
        arrayListTitle.add(researchWord);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                informationForAdapteur.add(arrayListTitle);
                adapteur.notifyDataSetChanged();
                counter++;
            }
        });
    }
    public JSONObject getDeererInformationFromDeezerServer(String query){
        URL url;
        JSONObject jsonObject = null;
        try {
            url = new URL(query);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                try {
                    jsonObject = new JSONObject(inputLine);
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
        return jsonObject;
    }
    public void initializeImageAlbum(final String urlImage, final ImageView imageToChange){
        try {
            if(!urlImage.equals("null")){
                final URL newurl = new URL(urlImage);
                final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        imageToChange.setImageBitmap(ICON);
                    }
                });
                getActivity().runOnUiThread(thread);
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
}
