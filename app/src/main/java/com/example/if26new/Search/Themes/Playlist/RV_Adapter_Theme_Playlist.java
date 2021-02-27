package com.example.if26new.Search.Themes.Playlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Playlist.Deezer_Playlist;
import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_Theme_Playlist extends RecyclerView.Adapter<RV_Adapter_Theme_Playlist.MyViewHolder>{


    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] allImageDeezerAlbum;
    public RV_Adapter_Theme_Playlist(ArrayList<ArrayList<String>> all, Activity activity, int length){
        allJsonObject=all;
        this.activity=activity;
        this.allImageDeezerAlbum=new Bitmap[length];
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView name;
        private ImageView photo;
        private String playlistID;
        private String playlistImage;
        private String fragmentName;
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.TV_Theme);
            name.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Theme);
            photo.setOnClickListener(this);
        }
        public void onClick(View v){
            goToDeezerView(v);
        }
        public void goToDeezerView(View v){
            Bundle bundle=new Bundle();
            bundle.putString("PLAYLIST_ID",playlistID);
            bundle.putString("PLAYLIST_IMAGE_ID",playlistImage);
            bundle.putString("PLAYLIST_NAME",name.getText().toString());
            bundle.putString("FRAGMENT_NAME",fragmentName);
            Intent playListActivity = new Intent(v.getContext(), Deezer_Playlist.class);
            playListActivity.putExtras(bundle);
            v.getContext().startActivity(playListActivity);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    Cette méthode est appelée pour chacune des lignes visibles affichées dans notre RecyclerView.
//    C'est généralement ici que l'on met à jour leur apparence.
//    Dans notre cas nous appelons la méthode du ViewHolder que nous avons précédemment créée, afin de mettre à jour son TextView à partir d'un GithubUser.
//    D'ailleurs, nous avons grâce à la variable position, récupéré l'objet GithubUser correspondant dans notre liste d'objet.
    @Override
    public void onBindViewHolder(final RV_Adapter_Theme_Playlist.MyViewHolder holder, int position) {
        holder.name.setText(allJsonObject.get(position).get(0));
        this.initializeImageAlbum(holder, allJsonObject.get(position).get(1),position);
        holder.playlistImage=allJsonObject.get(position).get(1);
        holder.playlistID=allJsonObject.get(position).get(2);
        holder.fragmentName=allJsonObject.get(position).get(3);

    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_Theme_Playlist.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_inside_theme, parent, false);
        RV_Adapter_Theme_Playlist.MyViewHolder vh = new RV_Adapter_Theme_Playlist.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }

    public void initializeImageAlbum(final RV_Adapter_Theme_Playlist.MyViewHolder holder, final String urlArtistImage, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON=BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        allImageDeezerAlbum[position]=ICON;
                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                holder.photo.setImageBitmap(ICON);
                            }
                        });
                        activity.runOnUiThread(thread);
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
}