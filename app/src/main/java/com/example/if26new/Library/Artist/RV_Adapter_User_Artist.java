package com.example.if26new.Library.Artist;

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
import com.example.if26new.Artists.Local.ActivityArtist;
import com.example.if26new.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_User_Artist extends RecyclerView.Adapter<RV_Adapter_User_Artist.MyViewHolder>  {


    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] allImageDeezerAlbum;
    public RV_Adapter_User_Artist(ArrayList<ArrayList<String>> all, Activity activity, int length){
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
        private TextView artistNameTV;
        private TextView numberOfFan;
        private ImageView photo;
        private String artistName;
        private String artistDeezerID;
        private String fragmentName;
        private String artistImage;
        private int bio;
        private int artistImageLocal;
        private boolean isDeezer;
        public MyViewHolder(View v) {
            super(v);
            artistNameTV = v.findViewById(R.id.TV_Name_Search);
            numberOfFan=v.findViewById(R.id.TV_Type_Search);
            artistNameTV.setOnClickListener(this);
            numberOfFan.setOnClickListener(this);
            photo=v.findViewById(R.id.IV_Photo_Search);
        }
        public void onClick(View v){
            if (isDeezer){
                goToDeezerView(v);
            }else{
                goToLocalView(v);
            }
        }
        public void goToDeezerView(View v){
            Bundle bundle=new Bundle();
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putString("ARTIST_ID",artistDeezerID);
            bundle.putInt("IS_CALL_FROM_HOME_ACTIVITY",1);
            Intent playListActivity = new Intent(v.getContext(), ActivityArtistForDeezerSearch.class);
            playListActivity.putExtras(bundle);
            v.getContext().startActivity(playListActivity);
        }
        public void goToLocalView(View v){
            Bundle bundle=new Bundle();
            bundle.putString("ARTIST_NAME",artistName);
            bundle.putInt("ARTIST_IMAGE_ID",artistImageLocal);
            bundle.putInt("ARTIST_BIO",bio);
            bundle.putString("FRAGMENT_NAME",fragmentName);
            Intent playListActivity = new Intent(v.getContext(), ActivityArtist.class);
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
    public void onBindViewHolder(final RV_Adapter_User_Artist.MyViewHolder holder, int position) {
        if (allJsonObject.get(position).get(0).equals("LOCAL")){
            holder.isDeezer=false;
            holder.artistName=allJsonObject.get(position).get(1);
            holder.artistNameTV.setText(holder.artistName);

            holder.artistImageLocal=Integer.valueOf(allJsonObject.get(position).get(2));
            holder.photo.setImageResource(Integer.valueOf(holder.artistImageLocal));

            holder.bio=Integer.valueOf(allJsonObject.get(position).get(3));
            holder.fragmentName=allJsonObject.get(position).get(4);
        }else if (allJsonObject.get(position).get(0).equals("DEEZER")){
            holder.isDeezer=true;
            holder.artistName=allJsonObject.get(position).get(1);
            holder.artistNameTV.setText(holder.artistName);

            holder.artistImage=allJsonObject.get(position).get(2);
            if (allImageDeezerAlbum[position]==null){
                initializeImageAlbum(holder,holder.artistImage,position);
            }else{
                holder.photo.setImageBitmap(allImageDeezerAlbum[position]);
            }
            holder.artistDeezerID=allJsonObject.get(position).get(3);
            holder.fragmentName=allJsonObject.get(position).get(4);
            setNumberOfFans(allJsonObject.get(position).get(5),holder);
        }
    }
//    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_User_Artist.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_search, parent, false);
        RV_Adapter_User_Artist.MyViewHolder vh = new RV_Adapter_User_Artist.MyViewHolder(view);
        return vh;
    }
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }
    public void initializeImageAlbum(final RV_Adapter_User_Artist.MyViewHolder holder, final String urlArtistImage, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON=BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        allImageDeezerAlbum[position]=getRoundedCornerBitmap(ICON,500);
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
    public void setNumberOfFans(String numberOfFans,MyViewHolder holder) {
        int blankNumber;
        if (numberOfFans.length()%3==0){
            blankNumber=(numberOfFans.length()/3)-1;
        }else{
            blankNumber=(numberOfFans.length()/3);
        }

        int length=numberOfFans.length()+blankNumber;
        int lengthNumberOfFan=numberOfFans.length();
        char toto[]=new char[length];

        int div=numberOfFans.length()/3;
        if (div==0){
            for (int i=0;i<numberOfFans.length();i++){
                toto[i]=numberOfFans.charAt(i);
            }
        }else if (numberOfFans.length()%3==0){
            for (int i=0;i<div;i++){
                toto[(length-1)-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-(i*3));
                toto[(length-1)-1-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-1-(i*3));
                toto[(length-1)-2-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-2-(i*3));
                if ((length-1)-3-(i*4)>=0){
                    toto[(length-1)-3-(i*4)]=' ';
                }
            }
        }else{
            for (int i=0;i<div;i++){
                toto[(length-1)-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-(i*3));
                toto[(length-1)-1-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-1-(i*3));
                toto[(length-1)-2-(i*4)]=numberOfFans.charAt((lengthNumberOfFan-1)-2-(i*3));
                toto[(length-1)-3-(i*4)]=' ';
            }
            int resultDivEuclidienne=numberOfFans.length()%3;
            for (int i=0;i<resultDivEuclidienne;i++){
                toto[i]=numberOfFans.charAt(i);
            }
        }
        char[] finale=new char[length+5];
        for (int i=0;i<length;i++){
            finale[i]=toto[i];
        }
        finale[length]=' ';
        finale[length+1]='F';
        finale[length+2]='a';
        finale[length+3]='n';
        finale[length+4]='s';

        //VOIR POUR 99 a 1
        holder.numberOfFan.setText(finale,0,finale.length);

    }
}
