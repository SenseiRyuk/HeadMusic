package com.example.if26new.Search.Themes;

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

import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_Themes extends RecyclerView.Adapter<RV_Adapter_Themes.MyViewHolder>  {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] imagesFirstColumn;
    private Bitmap[] imagesSecondColumn;
    public RV_Adapter_Themes(ArrayList<ArrayList<String>> all, Activity activity, int lengthBitmapArray){
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
        private TextView theme1NameTV;
        private TextView theme2NameTV;
        private ImageView imageTheme1IV;
        private ImageView imageTheme2IV;
        private String theme1ID;
        private String theme2ID;
        private String theme1Name;
        private String theme2Name;

        public MyViewHolder(View v) {
            super(v);
            theme1NameTV = v.findViewById(R.id.TV_Theme1);
            theme1NameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFirstTheme(v);
                }
            });
            theme2NameTV = v.findViewById(R.id.TV_Theme2);
            theme2NameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSecondTheme(v);
                }
            });

            imageTheme1IV = v.findViewById(R.id.IV_Theme1);
            imageTheme1IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFirstTheme(v);
                }
            });
            imageTheme2IV = v.findViewById(R.id.IV_Theme2);
            imageTheme2IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSecondTheme(v);
                }
            });
        }
        public void goToFirstTheme(View v){
            Bundle bundle=new Bundle();
            bundle.putString("FRAGMENT_NAME","SearchViewFragment");
            bundle.putString("THEME_ID",String.valueOf(theme1ID));
            bundle.putString("THEME_NAME",theme1Name);
            Intent themeActivity = new Intent(v.getContext(), ThemeActivityForSearchView.class);
            themeActivity.putExtras(bundle);
            v.getContext().startActivity(themeActivity);
        }
        public void goToSecondTheme(View v){
            Bundle bundle=new Bundle();
            bundle.putString("FRAGMENT_NAME","SearchViewFragment");
            bundle.putString("THEME_ID",String.valueOf(theme2ID));
            bundle.putString("THEME_NAME",theme2Name);
            Intent themeActivity = new Intent(v.getContext(), ThemeActivityForSearchView.class);
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
    public void onBindViewHolder(final RV_Adapter_Themes.MyViewHolder holder, int position) {
        holder.theme1ID=allJsonObject.get(position).get(0);
        holder.theme1Name=allJsonObject.get(position).get(1);
        holder.theme1NameTV.setText(allJsonObject.get(position).get(1));

        holder.theme2ID=allJsonObject.get(position).get(3);
        holder.theme2Name=allJsonObject.get(position).get(4);
        holder.theme2NameTV.setText(allJsonObject.get(position).get(4));
        if (imagesFirstColumn[position]!=null && imagesSecondColumn[position]!=null){
            holder.imageTheme1IV.setImageBitmap(imagesFirstColumn[position]);
            holder.imageTheme2IV.setImageBitmap(imagesSecondColumn[position]);
        }else {
            initializeImages(holder,allJsonObject.get(position).get(2),allJsonObject.get(position).get(5),position);
        }
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_Themes.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_themes, parent, false);
        RV_Adapter_Themes.MyViewHolder vh = new RV_Adapter_Themes.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }

    public void initializeImages(final RV_Adapter_Themes.MyViewHolder holder, final String urlImage1, final String urlImage2, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlImage1.equals("null")){
                        final URL newurl = new URL(urlImage1);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        imagesFirstColumn[position]=ICON;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.imageTheme1IV.setImageBitmap(ICON);
                            }
                        });
                    }
                    if(!urlImage2.equals("null")){
                        final URL newurl = new URL(urlImage2);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        imagesSecondColumn[position]=ICON;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.imageTheme2IV.setImageBitmap(ICON);
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

