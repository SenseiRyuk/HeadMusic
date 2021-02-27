package com.example.if26new.Artists.Local.Track;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Listening.Listening;
import com.example.if26new.R;

import java.util.ArrayList;

public class SearchViewAdapterForTrackLocal extends RecyclerView.Adapter<SearchViewAdapterForTrackLocal.MyViewHolder>  {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Bitmap mIcon_val;
    private Activity activity;
    private Bitmap[] allImageAlbum;
    public SearchViewAdapterForTrackLocal(ArrayList<ArrayList<String>> all, Activity activity, int length){
        allJsonObject=all;
        this.activity=activity;
        this.allImageAlbum=new Bitmap[length];
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView track;
        private TextView artistName;
        private int albumsID;
        private String fragmentName;

        public MyViewHolder(View v){
            super(v);
            track = v.findViewById(R.id.TV_Track_Name);
            track.setTextSize(20);
            track.setOnClickListener(this);
            artistName=v.findViewById(R.id.TV_Track_Artist_Name);
            artistName.setOnClickListener(this);
        }
        public void onClick(View v){
            Bundle bundle=new Bundle();
            bundle.putString("SONG_NAME",track.getText().toString());
            bundle.putString("ARTIST_NAME",artistName.getText().toString());
            bundle.putInt("ALBUM_ID",albumsID);
            bundle.putString("CONTEXT","ArtistActivity");
            bundle.putString("FRAGMENT_NAME",fragmentName);
            Intent playListActivity = new Intent(v.getContext(), Listening.class);
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
    public void onBindViewHolder(final SearchViewAdapterForTrackLocal.MyViewHolder holder, int position) {
        holder.track.setText(allJsonObject.get(position).get(0));
        holder.artistName.setText(allJsonObject.get(position).get(1));
        holder.albumsID=Integer.valueOf(allJsonObject.get(position).get(2));
        holder.fragmentName=allJsonObject.get(position).get(3);
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public SearchViewAdapterForTrackLocal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_tracks, parent, false);
        SearchViewAdapterForTrackLocal.MyViewHolder vh = new SearchViewAdapterForTrackLocal.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }

}
