package com.example.if26new.Home;

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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Playlist.PlaylistView;
import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_Home_Playlists extends RecyclerView.Adapter<RV_Adapter_Home_Playlists.MyViewHolder>  {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] imagesFirstColumn;
    private Bitmap[] imagesSecondColumn;
    public RV_Adapter_Home_Playlists(ArrayList<ArrayList<String>> all, Activity activity, int lengthBitmapArray){
        allJsonObject=all;
        this.activity=activity;
        imagesFirstColumn=new Bitmap[(lengthBitmapArray/2)+1];
        imagesSecondColumn=new Bitmap[(lengthBitmapArray/2)];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView playlist1NameTV;
        private TextView playlist2NameTV;
        private ImageView imageTheme1IV;
        private ImageView imageTheme2IV;
        private CardView CV_playlist1;
        private CardView CV_Playlist2;
        private String playlist1ID;
        private String playlist2ID;
        private String playlist1Name;
        private String playlist2Name;

        public MyViewHolder(View v) {
            super(v);
            CV_playlist1=v.findViewById(R.id.CV_Home_Playlis1);
            CV_Playlist2=v.findViewById(R.id.CV_Home_Playlist2);
            playlist1NameTV = v.findViewById(R.id.TV_Home_Title_Playlist1);
            playlist1NameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFirstTheme(v);
                }
            });
            playlist2NameTV = v.findViewById(R.id.TV_Home_Title_Playlist2);
            playlist2NameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSecondTheme(v);
                }
            });

            imageTheme1IV = v.findViewById(R.id.IV_Home_Playlist1);
            imageTheme1IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFirstTheme(v);
                }
            });
            imageTheme2IV = v.findViewById(R.id.IV_Home_Playlist2);
            imageTheme2IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSecondTheme(v);
                }
            });
        }
        public void goToFirstTheme(View v){
            Bundle bundle=new Bundle();
            bundle.putString("FRAGMENT_NAME","HomeFragment");
            bundle.putString("PLAYLIST_NAME",playlist1Name);
            bundle.putString("PLAYLIST_IMAGE_ID",String.valueOf(playlist1ID));
            Intent themeActivity = new Intent(v.getContext(), PlaylistView.class);
            themeActivity.putExtras(bundle);
            v.getContext().startActivity(themeActivity);
        }
        public void goToSecondTheme(View v){
            Bundle bundle=new Bundle();
            bundle.putString("FRAGMENT_NAME","HomeFragment");
            bundle.putString("PLAYLIST_NAME",playlist2Name);
            bundle.putString("PLAYLIST_IMAGE_ID",String.valueOf(playlist2ID));
            Intent themeActivity = new Intent(v.getContext(), PlaylistView.class);
            themeActivity.putExtras(bundle);
            v.getContext().startActivity(themeActivity);
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
    public void onBindViewHolder(final RV_Adapter_Home_Playlists.MyViewHolder holder, int position) {
        System.out.println("POSITION " +position);
        holder.playlist1Name=allJsonObject.get(position).get(0);
        holder.playlist1NameTV.setText(allJsonObject.get(position).get(0));
        if (imagesFirstColumn[position]!=null){
            holder.imageTheme1IV.setImageBitmap(imagesFirstColumn[position]);
        }else{
            if (allJsonObject.get(position).get(1).equals("LocalImage")){
                holder.imageTheme1IV.setImageResource(Integer.valueOf(allJsonObject.get(position).get(2)));
            }else{
                initializeImages(holder,allJsonObject.get(position).get(2),true,position);
            }
        }


        if (allJsonObject.get(position).get(3).equals("no Image")){
            holder.imageTheme2IV.setVisibility(View.INVISIBLE);
            holder.playlist2NameTV.setVisibility(View.INVISIBLE);
            holder.CV_Playlist2.setVisibility(View.INVISIBLE);
        }else{
            holder.playlist2Name=allJsonObject.get(position).get(3);
            holder.playlist2NameTV.setText(allJsonObject.get(position).get(3));
            if (imagesSecondColumn[position]!=null){
                holder.imageTheme2IV.setImageBitmap(imagesSecondColumn[position]);
            }else {
                if (allJsonObject.get(position).get(4).equals("LocalImage")){
                    holder.imageTheme2IV.setImageResource(Integer.valueOf(allJsonObject.get(position).get(5)));
                }else{
                    initializeImages(holder,allJsonObject.get(position).get(5),false,position);
                }
            }
        }
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_Home_Playlists.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_home_playlist, parent, false);
        RV_Adapter_Home_Playlists.MyViewHolder vh = new RV_Adapter_Home_Playlists.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }

    public void initializeImages(final RV_Adapter_Home_Playlists.MyViewHolder holder, final String urlImage, final boolean firstColumn, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlImage.equals("null")){
                        final URL newurl = new URL(urlImage);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        if (firstColumn==true){
                            imagesFirstColumn[position]=ICON;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.imageTheme1IV.setImageBitmap(ICON);
                                }
                            });
                        }else{
                            imagesSecondColumn[position]=ICON;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.imageTheme2IV.setImageBitmap(ICON);
                                }
                            });
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

