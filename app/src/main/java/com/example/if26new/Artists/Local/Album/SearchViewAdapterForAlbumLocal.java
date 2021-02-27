package com.example.if26new.Artists.Local.Album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Albums.Album_view;
import com.example.if26new.R;

import java.util.ArrayList;

public class SearchViewAdapterForAlbumLocal extends RecyclerView.Adapter<SearchViewAdapterForAlbumLocal.MyViewHolder>  {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    public SearchViewAdapterForAlbumLocal(ArrayList<ArrayList<String>> all){
        allJsonObject=all;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView albumName;
        private ImageView photo;
        private int albumImage;
        private int albumID;
        private String artistName;
        private int isCallFromArtistView;
        private String fragmentName;
        private boolean isClikable;
        public MyViewHolder(View v) {
            super(v);
            albumName = v.findViewById(R.id.TV_Album_Name);
            albumName.setTextSize(20);
            albumName.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Album_Photo);
        }
        public void onClick(View v){
            if(isClikable==true){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",albumName.getText().toString());
                bundle.putInt("ALBUM_IMAGE_ID",albumImage);
                bundle.putInt("ALBUM_ID",albumID);
                bundle.putString("ARTIST_NAME",artistName);
                bundle.putString("FRAGMENT_NAME",fragmentName);
                bundle.putInt("IS_FROM_ARTIST_VIEW",isCallFromArtistView);
                Intent playListActivity = new Intent(v.getContext(), Album_view.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);
            }
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
    public void onBindViewHolder(final SearchViewAdapterForAlbumLocal.MyViewHolder holder, int position) {
        holder.albumName.setText(allJsonObject.get(position).get(0));
        if (allJsonObject.get(position).size()>1){
            holder.photo.setImageResource(Integer.valueOf(allJsonObject.get(position).get(1)));
            holder.albumImage=Integer.valueOf(allJsonObject.get(position).get(1));
            holder.albumID=Integer.valueOf(allJsonObject.get(position).get(2));
            holder.artistName=allJsonObject.get(position).get(3);
            holder.isCallFromArtistView=Integer.valueOf(allJsonObject.get(position).get(4));
            holder.fragmentName=allJsonObject.get(position).get(5);
            holder.isClikable=true;
        }else{
            holder.isClikable=false;
        }
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public SearchViewAdapterForAlbumLocal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_album, parent, false);
        SearchViewAdapterForAlbumLocal.MyViewHolder vh = new SearchViewAdapterForAlbumLocal.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }


}
