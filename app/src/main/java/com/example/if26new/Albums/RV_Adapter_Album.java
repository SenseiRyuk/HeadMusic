package com.example.if26new.Albums;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Listening.ListeningForDeezer;
import com.example.if26new.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RV_Adapter_Album extends RecyclerView.Adapter<RV_Adapter_Album.MyViewHolder> {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;

    public RV_Adapter_Album(ArrayList<ArrayList<String>> all, Activity activity) {
        allJsonObject = all;
        this.activity = activity;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView trackNameTV;
        private TextView artistNameTV;
        private TextView duration;
        private String trackID;
        private String fragment;

        public MyViewHolder(View v) {
            super(v);
            trackNameTV = v.findViewById(R.id.TV_Track_Name);
            trackNameTV.setOnClickListener(this);
            artistNameTV = v.findViewById(R.id.TV_Track_Artist_Name);
            artistNameTV.setOnClickListener(this);
            duration=v.findViewById(R.id.durationMusicTV);
        }
        public void onClick(View v) {
            Bundle bundle=new Bundle();
            bundle.putString("SONG_ID",trackID);
            bundle.putString("CONTEXT","Call_From_Album_Activity");
            bundle.putString("FRAGMENT_NAME",fragment);

            Intent playListActivity = new Intent(v.getContext(), ListeningForDeezer.class);
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
    public void onBindViewHolder(final RV_Adapter_Album.MyViewHolder holder, int position) {
        holder.trackID=allJsonObject.get(position).get(0);
        holder.trackNameTV.setText(allJsonObject.get(position).get(1));
        holder.artistNameTV.setText(allJsonObject.get(position).get(2));
        holder.duration.setText(allJsonObject.get(position).get(3));
        holder.fragment=allJsonObject.get(position).get(4);
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_Album.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_tracks, parent, false);
        RV_Adapter_Album.MyViewHolder vh = new RV_Adapter_Album.MyViewHolder(view);
        return vh;
    }

    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }

}


