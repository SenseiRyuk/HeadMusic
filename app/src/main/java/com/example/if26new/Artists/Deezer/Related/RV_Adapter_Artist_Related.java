package com.example.if26new.Artists.Deezer.Related;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Artists.Deezer.ActivityArtistForDeezerSearch;
import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_Artist_Related extends RecyclerView.Adapter<RV_Adapter_Artist_Related.MyViewHolder> {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Bitmap mIcon_val;
    private Bitmap bitmap;
    private Activity activity;
    private Bitmap[] allImageArtist;
    public RV_Adapter_Artist_Related(ArrayList<ArrayList<String>> all, Activity activity, int lengthBitmapArray){
        allJsonObject=all;
        this.activity=activity;
        allImageArtist=new Bitmap[lengthBitmapArray];
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView textView;
        private ImageView photo;
        private String artistID;
        private String fragmentName;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.TV_Album_Name);
            textView.setTextSize(20);
            textView.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Album_Photo);
        }
        public void onClick(View v){
            Bundle bundle=new Bundle();
            bundle.putString("ARTIST_ID",artistID);
            bundle.putString("FRAGMENT_NAME", fragmentName);
            Intent playListActivity = new Intent(v.getContext(), ActivityArtistForDeezerSearch.class);
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
    public void onBindViewHolder(final RV_Adapter_Artist_Related.MyViewHolder holder, int position) {
        holder.artistID=allJsonObject.get(position).get(0);
        this.initializeNameArtist(holder, allJsonObject.get(position).get(1));

        if (allImageArtist[position]==null){
            this.initializeImageArtist(holder, allJsonObject.get(position).get(2),position);
        }else{
            holder.photo.setImageBitmap(allImageArtist[position]);
        }
        holder.fragmentName=allJsonObject.get(position).get(3);
    }

    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_Artist_Related.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_album, parent, false);
        RV_Adapter_Artist_Related.MyViewHolder vh = new RV_Adapter_Artist_Related.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
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
    public void initializeNameArtist(RV_Adapter_Artist_Related.MyViewHolder holder, String artistName){
        holder.textView.setText(artistName);
    }
    public void initializeImageArtist(final RV_Adapter_Artist_Related.MyViewHolder holder, final String urlArtistImage, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON=BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        allImageArtist[position]=getRoundedCornerBitmap(ICON,500);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.photo.setImageBitmap(getRoundedCornerBitmap(ICON,500));
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
