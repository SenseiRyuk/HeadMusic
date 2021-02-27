package com.example.if26new.Library.Album;

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

import com.example.if26new.Albums.Album_view_For_Deezer;
import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_User_Album extends RecyclerView.Adapter<RV_Adapter_User_Album.MyViewHolder>{


    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] allImageDeezerAlbum;
    public RV_Adapter_User_Album(ArrayList<ArrayList<String>> all, Activity activity, int length){
        allJsonObject=all;
        this.activity=activity;
        this.allImageDeezerAlbum=new Bitmap[length];
    }
    public void resetBitmap(int length){
        this.allImageDeezerAlbum=new Bitmap[length];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView albumNameTV;
        private TextView artistNameTV;
        private ImageView photo;
        private String albumsID;
        private String fragmentName;
        private int isCallFromartist;
        public MyViewHolder(View v) {
            super(v);
            albumNameTV = v.findViewById(R.id.TV_Name_Search);
            albumNameTV.setOnClickListener(this);
            artistNameTV=v.findViewById(R.id.TV_Type_Search);
            artistNameTV.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Photo_Search);
        }
        public void onClick(View v){
            goToDeezerView(v);
        }
        public void goToDeezerView(View v){
            Bundle bundle=new Bundle();
            bundle.putString("ALBUM_ID",albumsID);
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putInt("IS_FROM_ARTIST_VIEW",isCallFromartist);
            bundle.putInt("IS_CALL_FROM_HOME_ACTIVITY",1);
            Intent playListActivity = new Intent(v.getContext(), Album_view_For_Deezer.class);
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
    public void onBindViewHolder(final RV_Adapter_User_Album.MyViewHolder holder, int position) {
        holder.albumNameTV.setText(allJsonObject.get(position).get(0));
        if(allImageDeezerAlbum[position]==null){
            initializeImageAlbum(holder,allJsonObject.get(position).get(1),position);
        }else{
            holder.photo.setImageBitmap(allImageDeezerAlbum[position]);
        }
        holder.albumsID=allJsonObject.get(position).get(2);
        holder.artistNameTV.setText(allJsonObject.get(position).get(3));
        holder.fragmentName=allJsonObject.get(position).get(4);
        holder.isCallFromartist=Integer.valueOf(allJsonObject.get(position).get(5));
    }
//    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_User_Album.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_search, parent, false);
        RV_Adapter_User_Album.MyViewHolder vh = new RV_Adapter_User_Album.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }
    public void initializeImageAlbum(final RV_Adapter_User_Album.MyViewHolder holder, final String urlArtistImage, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON=BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        allImageDeezerAlbum[position]=ICON;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.photo.setImageBitmap(ICON);
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
        thread.start();
    }

}
