package com.example.if26new.Search.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Albums.Album_view_For_Deezer;
import com.example.if26new.Artists.Deezer.ActivityArtistForDeezerSearch;
import com.example.if26new.Playlist.Deezer_Playlist;
import com.example.if26new.Listening.ListeningForDeezer;
import com.example.if26new.Podcast.PodcastActivity;
import com.example.if26new.R;
import com.example.if26new.Radio.RadioActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_Search extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Bitmap mIcon_val;
    private Activity activity;
    private Bitmap[] allBitmap;
    private int length;

    public RV_Adapter_Search(ArrayList<ArrayList<String>> all,Activity activity,int length){
        allJsonObject=all;
        this.activity=activity;
        allBitmap=new Bitmap[length];
        this.length=length;
    }

    public void reinitialzedBitmap(){
        allBitmap=new Bitmap[length];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView textView;
        private TextView typeTV;
        private ImageView photo;
        private String albumsID;
        private String artistName;
        private String artistDeezerID;
        private String trackID;
        private String type;

        private String playlistID;
        private String playlistImage;
        private String playlistName;

        private String radioID;
        private String podcastID;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.TV_Name_Search);
            textView.setOnClickListener(this);
            typeTV=v.findViewById(R.id.TV_Type_Search);
            typeTV.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Photo_Search);
        }
        public void onClick(View v){
            if (type.equals("Album")){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_ID",albumsID);
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                bundle.putInt("IS_FROM_ARTIST_VIEW",0);

                Intent AlbumActivity = new Intent(v.getContext(), Album_view_For_Deezer.class);
                AlbumActivity.putExtras(bundle);
                v.getContext().startActivity(AlbumActivity);
            } else if(type.equals("Artist")){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_ID",artistDeezerID);
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");

                Intent artistActivity = new Intent(v.getContext(), ActivityArtistForDeezerSearch.class);
                artistActivity.putExtras(bundle);
                v.getContext().startActivity(artistActivity);
            } else if (type.equals("Track")) {
                Bundle bundle=new Bundle();
                bundle.putString("SONG_ID",trackID);
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                bundle.putString("CONTEXT","SearchViewActivity");

                Intent trackActivity = new Intent(v.getContext(), ListeningForDeezer.class);
                trackActivity.putExtras(bundle);
                v.getContext().startActivity(trackActivity);
            }else if (type.equals("Playlist")){
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_ID",playlistID);
                bundle.putString("PLAYLIST_IMAGE_ID",playlistImage);
                bundle.putString("PLAYLIST_NAME",playlistName);
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                Intent playListActivity = new Intent(v.getContext(), Deezer_Playlist.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);
            }
            else if (type.equals("Radio")){
                Bundle bundle=new Bundle();
                bundle.putString("RADIO_ID",radioID);
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                Intent radioActivity = new Intent(v.getContext(), RadioActivity.class);
                radioActivity.putExtras(bundle);
                v.getContext().startActivity(radioActivity);
            }
            else if (type.equals("Podcast")){
//                Bundle bundle=new Bundle();
//                bundle.putString("PODCAST_ID",podcastID);
//                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
//                Intent podcastActivity = new Intent(v.getContext(), PodcastActivity.class);
//                podcastActivity.putExtras(bundle);
//                v.getContext().startActivity(podcastActivity);
            }
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView title;
        private TextView more;
        private String type;
        private String wordSearch;
        private RecyclerView recyclerView;
        private RV_Adapter_Search adapteur;
        private ArrayList<ArrayList<String>> informationForAdapteur;
        public MyViewHolder2(View v) {
            super(v);
            title = v.findViewById(R.id.TV_Title_In_Search);
            more=v.findViewById(R.id.TV_More);
            more.setOnClickListener(this);
        }
        public void initRecyclerView(View v,int length){
            recyclerView=activity.findViewById(R.id.RV_Search);
            informationForAdapteur=new ArrayList<ArrayList<String>>();
            adapteur=new RV_Adapter_Search(informationForAdapteur,activity,length);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapteur);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        @Override
        public void onClick(final View v) {
            Thread thread_search_more=new Thread(new Runnable() {
                @Override
                public void run() {
                    switch (type){
                        case "artist":
                            try {
                                final JSONArray jsonArray_Artist=getDeererInformationFromDeezerServer("https://api.deezer.com/search/artist?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Artist.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Artist.length();i++){
                                    load_Artists(jsonArray_Artist.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                        case"album":
                            try {
                                final JSONArray jsonArray_Album=getDeererInformationFromDeezerServer("https://api.deezer.com/search/album?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Album.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Album.length();i++){
                                    load_Albums(jsonArray_Album.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                        case "track":
                            try {
                                final JSONArray jsonArray_Track=getDeererInformationFromDeezerServer("https://api.deezer.com/search/track?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Track.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Track.length();i++){
                                    load_Tracks(jsonArray_Track.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                        case "playlist":
                            try {
                                final JSONArray jsonArray_Playlist=getDeererInformationFromDeezerServer("https://api.deezer.com/search/playlist?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Playlist.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Playlist.length();i++){
                                    load_Playlists(jsonArray_Playlist.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                        case "radio":
                            try {
                                final JSONArray jsonArray_Radio=getDeererInformationFromDeezerServer("https://api.deezer.com/search/radio?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Radio.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Radio.length();i++){
                                    load_Radios(jsonArray_Radio.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                        case "podcast":
                            try {
                                final JSONArray jsonArray_Podcast=getDeererInformationFromDeezerServer("https://api.deezer.com/search/podcast?q="+wordSearch).getJSONArray("data");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView(v,jsonArray_Podcast.length());
                                    }
                                });
                                for (int i=0;i<jsonArray_Podcast.length();i++){
                                    load_Podcasts(jsonArray_Podcast.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        break;
                    }
                }
            });
            thread_search_more.start();

        }
        public void load_Artists(JSONObject artistObject){
            try {
                final ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add("0");
                arrayListArtist.add("Artist");
                arrayListArtist.add(artistObject.getString("name"));
                arrayListArtist.add(artistObject.getString("picture_xl"));
                arrayListArtist.add(artistObject.getString("id"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListArtist);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void load_Albums(JSONObject albumObject){
            try {
                final ArrayList <String> arrayListAlbum=new ArrayList<>();
                arrayListAlbum.add("0");
                arrayListAlbum.add("Album");
                arrayListAlbum.add(albumObject.getString("title"));
                arrayListAlbum.add(albumObject.getString("id"));
                arrayListAlbum.add(albumObject.getString("cover_xl"));
                arrayListAlbum.add(albumObject.getJSONObject("artist").getString("name"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListAlbum);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void load_Tracks(JSONObject trackObject){
            try {
                final ArrayList <String> arrayListTrack=new ArrayList<>();
                arrayListTrack.add("0");
                arrayListTrack.add("Track");
                arrayListTrack.add(trackObject.getString("title_short"));
                arrayListTrack.add(trackObject.getString("id"));
                arrayListTrack.add(trackObject.getJSONObject("artist").getString("name"));
                arrayListTrack.add(trackObject.getJSONObject("album").getString("cover_xl"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListTrack);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void load_Playlists(JSONObject playlistObject){
            try {
                final ArrayList <String> arrayListPlaylist=new ArrayList<>();
                arrayListPlaylist.add("0");
                arrayListPlaylist.add("Playlist");
                arrayListPlaylist.add(playlistObject.getString("title"));
                arrayListPlaylist.add(playlistObject.getString("picture_xl"));
                arrayListPlaylist.add(playlistObject.getString("id"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListPlaylist);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void load_Radios(JSONObject radioObject){
            try {
                final ArrayList <String> arrayListRadio=new ArrayList<>();
                arrayListRadio.add("0");
                arrayListRadio.add("Radio");
                arrayListRadio.add(radioObject.getString("title"));
                arrayListRadio.add(radioObject.getString("picture_small"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListRadio);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void load_Podcasts(JSONObject podcastObject){
            try {
                final ArrayList <String> arrayListPodcast=new ArrayList<>();
                arrayListPodcast.add("0");
                arrayListPodcast.add("Podcast");
                arrayListPodcast.add(podcastObject.getString("title"));
                arrayListPodcast.add(podcastObject.getString("picture_small"));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        informationForAdapteur.add(arrayListPodcast);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.recycler_view_for_search, parent, false);
                return new MyViewHolder(view);
            case 1:
                view = inflater.inflate(R.layout.recycler_view_titles, parent, false);
                return new MyViewHolder2(view);
        }
        return null;
    }

//    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    public int getItemCount() {
        return this.allJsonObject.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(allJsonObject.get(position).get(0).equals("0")){
            position=0;
        }else if(allJsonObject.get(position).get(0).equals("1")){
            position=1;
        }
        return position;
    }

    //    Cette méthode est appelée pour chacune des lignes visibles affichées dans notre RecyclerView.
//    C'est généralement ici que l'on met à jour leur apparence.
//    Dans notre cas nous appelons la méthode du ViewHolder que nous avons précédemment créée, afin de mettre à jour son TextView à partir d'un GithubUser.
//    D'ailleurs, nous avons grâce à la variable position, récupéré l'objet GithubUser correspondant dans notre liste d'objet.
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                MyViewHolder viewHolder = (MyViewHolder)holder;
                System.out.println("---------------------------");
                if(allJsonObject.get(position).get(1).equals("Artist")){
                    System.out.println("JE RENTRE ARTIIISST");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(3),true,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializeNameArtist(viewHolder,allJsonObject.get(position).get(2),allJsonObject.get(position).get(1));
                    viewHolder.artistDeezerID=allJsonObject.get(position).get(4);
                }
                else if (allJsonObject.get(position).get(1).equals("Album")){
                    System.out.println("JE RENTRE ALBMMMM");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(4),false,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializeAlbums(viewHolder,position);
                }
                else if (allJsonObject.get(position).get(1).equals("Track")){
                    System.out.println("JE RENTRE TRRRRAACCK");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(5),false,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializeTracks(viewHolder,position);
                }
                else if (allJsonObject.get(position).get(1).equals("Playlist")){
                    System.out.println("JE RENTRE PLAYLLLIST");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(3),false,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializePlaylist(viewHolder,position);
                    viewHolder.type=allJsonObject.get(position).get(1);
                    viewHolder.playlistName=allJsonObject.get(position).get(2);
                    viewHolder.playlistImage=allJsonObject.get(position).get(3);
                    viewHolder.playlistID=allJsonObject.get(position).get(4);
                }
                else if (allJsonObject.get(position).get(1).equals("Radio")){
                    System.out.println("JE RENTRE RADDDDIIIOOO");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(3),false,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializeRadio(viewHolder,position);
                }
                else if (allJsonObject.get(position).get(1).equals("Podcast")){
                    System.out.println("JE RENTRE POOOOODDDDCCASSTTT");
                    if(allBitmap[position]==null){
                        initializeImage(viewHolder,allJsonObject.get(position).get(3),false,position);
                    }else{
                        viewHolder.photo.setImageBitmap(allBitmap[position]);
                    }
                    this.initializePodcast(viewHolder,position);
                }
                System.out.println("---------------------------");
                break;
            case 1:
                MyViewHolder2 viewHolder2 = (MyViewHolder2)holder;
                viewHolder2.title.setText(allJsonObject.get(position).get(1));
                viewHolder2.type=allJsonObject.get(position).get(2);
                viewHolder2.wordSearch=allJsonObject.get(position).get(3);
                break;
        }
    }
    public void initializeNameArtist(MyViewHolder holder,String artistName,String type){
        if (!artistName.equals("null")){
            holder.textView.setText(artistName);
            holder.typeTV.setText(type);
            holder.type=type;
        }
    }
    public void initializeAlbums(MyViewHolder holder,int position){
        holder.albumsID=allJsonObject.get(position).get(3);
        holder.artistName=allJsonObject.get(position).get(5);

        holder.textView.setText(allJsonObject.get(position).get(2));
        holder.typeTV.setText(allJsonObject.get(position).get(1)+" by "+holder.artistName);
        holder.type=allJsonObject.get(position).get(1);
    }
    public void initializeTracks(MyViewHolder holder,int position){
        holder.textView.setText(allJsonObject.get(position).get(2));
        holder.trackID=allJsonObject.get(position).get(3);
        holder.artistName=allJsonObject.get(position).get(4);
        holder.typeTV.setText(allJsonObject.get(position).get(1)+" by "+holder.artistName);
        holder.type=allJsonObject.get(position).get(1);
    }
    public void initializePlaylist(MyViewHolder holder,int position){
        holder.textView.setText(allJsonObject.get(position).get(2));
        holder.typeTV.setText(allJsonObject.get(position).get(1)+" by "+holder.artistName);
        holder.type=allJsonObject.get(position).get(1);
    }
    public void initializeRadio(MyViewHolder holder,int position){
        holder.textView.setText(allJsonObject.get(position).get(2));
        holder.radioID=allJsonObject.get(position).get(4);
        holder.typeTV.setText(allJsonObject.get(position).get(1)+" by "+holder.artistName);
        holder.type=allJsonObject.get(position).get(1);
    }
    public void initializePodcast(MyViewHolder holder,int position){
        holder.textView.setText(allJsonObject.get(position).get(2));
        holder.podcastID=allJsonObject.get(position).get(4);
        holder.typeTV.setText(allJsonObject.get(position).get(1)+" by "+holder.artistName);
        holder.type=allJsonObject.get(position).get(1);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPixelSize) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = roundPixelSize;
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF,roundPx,roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    public void initializeImage(final MyViewHolder holder, final String urlImage,final boolean isForArtist,final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                try {
                    if(!urlImage.equals("null")){
                        newurl = new URL(urlImage);
                        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        Thread thread1=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (isForArtist){
                                    holder.photo.setImageBitmap(getRoundedCornerBitmap(mIcon_val,500));
                                    allBitmap[position]=getRoundedCornerBitmap(mIcon_val,500);
                                }else{
                                    holder.photo.setImageBitmap(mIcon_val);
                                    allBitmap[position]=mIcon_val;
                                }
                            }
                        });
                        activity.runOnUiThread(thread1);
                        try {
                            thread1.join();
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
}
