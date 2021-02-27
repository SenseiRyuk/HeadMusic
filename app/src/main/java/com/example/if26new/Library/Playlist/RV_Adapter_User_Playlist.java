package com.example.if26new.Library.Playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.HomeActivity;
import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;
import com.example.if26new.Playlist.PlaylistView;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RV_Adapter_User_Playlist extends RecyclerView.Adapter<RV_Adapter_User_Playlist.MyViewHolder> {

    // FOR DATA
    private ArrayList<ArrayList<String>> allJsonObject;
    private Context context;
    private Activity activity;
    private Bitmap[] allImageDeezerPlaylist;

    public RV_Adapter_User_Playlist(ArrayList<ArrayList<String>> all, Activity activity, int length){
        allJsonObject=all;
        this.activity=activity;
        allImageDeezerPlaylist=new Bitmap[length];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        private TextView playlistNameTV;
        private TextView numberofTracksTV;
        private ImageView photo;
        private String playlistName;
        private int playlistImage;
        private String fragment;
        private SaveMyMusicDatabase db;
        private Dialog erasePlaylist;
        private Activity activity;
        private ConstraintLayout eraseBackground;
        private TextView erasetxt;
        private Button yes;
        private Button no;
        private int id;
        private String numberOfTracks;
        //For playlist creation
        private Dialog newplaylistDialog;
        private ConstraintLayout backgroundPopUp;
        private Button validate;
        private Button cancel;
        private EditText fieldPlaylist;
        private boolean isAlreadyCreate;
        private String text;
        private Dialog chooseImageSong;
        private ConstraintLayout backgroundImagePopUp;
        private LinearLayout linearLayout;
        private LinearLayout dynamique;
        private ImageButton[] buttonWithImage;


        public MyViewHolder(View v) {
            super(v);
            playlistNameTV = v.findViewById(R.id.TV_Name_Search);
            playlistNameTV.setOnClickListener(this);
            playlistNameTV.setOnLongClickListener(this);
            numberofTracksTV=v.findViewById(R.id.TV_Type_Search);
            numberofTracksTV.setOnClickListener(this);
            numberofTracksTV.setOnLongClickListener(this);
            photo=v.findViewById(R.id.IV_Photo_Search);
            db=SaveMyMusicDatabase.getInstance(v.getContext());
            erasePlaylist=new Dialog(v.getContext());
            newplaylistDialog=new Dialog(v.getContext());
            chooseImageSong=new Dialog(v.getContext());
        }
        public void onClick(View v){
            if (playlistName.equals("Create new playlist")){
                showPopup(v);
            }else{
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_NAME",playlistName);
                bundle.putInt("PLAYLIST_IMAGE_ID",playlistImage);
                bundle.putString("FRAGMENT_NAME",fragment);
                Intent playListActivity = new Intent(v.getContext(), PlaylistView.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);
            }
        }
        public boolean onLongClick(View v){
            if (!playlistName.equals("Favorite") && !playlistName.equals("Recognition")) {
                PlaylistModel playlistModel=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),playlistName);
                if (playlistModel!=null){
                    showErasePopUp();
                }
            }
            return true;
        }
        public void showErasePopUp(){
            erasePlaylist.setContentView(R.layout.pop_up_erase_playlist);

            eraseBackground=erasePlaylist.findViewById(R.id.constraintErasePlaylist);
            UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
            gd.setCornerRadius(0f);
            eraseBackground.setBackground(gd);

            erasetxt=erasePlaylist.findViewById(R.id.text_erase_playlist);
            yes=erasePlaylist.findViewById(R.id.yes_erase_btn);
            yes.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),yes.getText().toString()));
            no=erasePlaylist.findViewById(R.id.no_erase_btn);
            no.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),no.getText().toString()));
            erasetxt.setTextColor(Color.WHITE);
            erasetxt.setTextSize(20);
            erasetxt.setGravity(Gravity.CENTER);
            erasetxt.setText("Are you sure you want to delete the playlist : "+playlistName);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    erasePlaylist.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent switche = new Intent(v.getContext(), HomeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("CONTEXT","HomeActivity");
                    bundle.putString("FRAGMENT_NAME","UserFragment");
                    switche.putExtras(bundle);
                    v.getContext().startActivity(switche);
                    db.mPlaylistDao().deletePlaylist(id);
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Playlist " +playlistName +" is deleted", Toast.LENGTH_SHORT);
                    erasePlaylist.dismiss();
                    toast.show();

                }
            });
            erasePlaylist.show();
        }
        public LayerDrawable roundbuttonSetting(int colorBackground, String Text){
            // Initialize two float arrays
            float[] outerRadii = new float[]{75,75,75,75,75,75,75,75};
            float[] innerRadii = new float[]{75,75,75,75,75,75,75,75};
            // Set the shape background
            ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                    outerRadii,
                    null,
                    innerRadii
            ));
            backgroundShape.getPaint().setColor(colorBackground); // background color

            // Initialize an array of drawables
            Drawable[] drawables = new Drawable[]{
                    backgroundShape
            };
            Paint paint = new Paint();

            Canvas canvas = new Canvas();
            canvas.drawText(Text, 0, drawables[0].getMinimumHeight()/2, paint);

            drawables[0].draw(canvas);
            // Initialize a layer drawable object
            LayerDrawable layerDrawable = new LayerDrawable(drawables);
            return layerDrawable;
        }
        public void showPopup(View v){
            newplaylistDialog.setContentView(R.layout.pop_up_add_playlist);
            backgroundPopUp=newplaylistDialog.findViewById(R.id.constrainAddPlaylist);
            UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
            gd.setCornerRadius(0f);
            backgroundPopUp.setBackground(gd);

            validate=newplaylistDialog.findViewById(R.id.validateNewPlaylist);
            validate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text = fieldPlaylist.getText().toString();
                    isAlreadyCreate=false;
                    PlaylistModel[] allPlaylist=db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
                    for (int i=0;i<allPlaylist.length;i++){
                        if (text.equals(allPlaylist[i].getTitles())){
                            isAlreadyCreate=true;
                        }
                    }
                    if (text.matches("")) {
                        fieldPlaylist.setError("Please enter a playlist name");
                    }else if (isAlreadyCreate==true){
                        fieldPlaylist.setError("This Playlist already exist, please pick an other name");
                    }else{
                        unShowPopup();
                        Toast toast = Toast.makeText(v.getContext(), "Playlist created", Toast.LENGTH_SHORT);
                        toast.show();
                        PlaylistModel playlistToInsert = new PlaylistModel(db.getActualUser(), text,R.drawable.song_not_found_image);
                        db.mPlaylistDao().insertPlaylist(playlistToInsert);
                        Intent switche = new Intent(v.getContext(), HomeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("CONTEXT","HomeActivity");
                        bundle.putString("FRAGMENT_NAME","UserFragment");
                        switche.putExtras(bundle);
                        v.getContext().startActivity(switche);
//                        showPopupImage(v);
                    }
                }
            });
            cancel=newplaylistDialog.findViewById(R.id.cancelNewPlaylist);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unShowPopup();
                }
            });
            validate.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),validate.getText().toString()));
            cancel.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),cancel.getText().toString()));
            fieldPlaylist=newplaylistDialog.findViewById(R.id.fielForNewPlaylist);
            newplaylistDialog.show();
        }
        public void unShowPopup(){
            newplaylistDialog.dismiss();
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
    public void onBindViewHolder(final RV_Adapter_User_Playlist.MyViewHolder holder, int position) {
        if (allJsonObject.get(position).get(0).equals("Create new playlist")){
            holder.playlistName=allJsonObject.get(position).get(0);
            holder.playlistNameTV.setText(holder.playlistName);
            holder.fragment=allJsonObject.get(position).get(1);
            holder.photo.setImageResource(R.drawable.add_to_playlist_icon);
        }else{
            holder.playlistName=allJsonObject.get(position).get(0);
            holder.playlistNameTV.setText(holder.playlistName);
            holder.playlistImage=Integer.valueOf(allJsonObject.get(position).get(1));
            holder.photo.setImageResource(holder.playlistImage);
            holder.fragment=allJsonObject.get(position).get(2);
            holder.id=Integer.valueOf(allJsonObject.get(position).get(3));
            holder.activity=activity;
            holder.numberOfTracks=allJsonObject.get(position).get(4);
            holder.numberofTracksTV.setText(allJsonObject.get(position).get(4)+" Tracks");
            if (!allJsonObject.get(position).get(0).equals("Favorite") && !allJsonObject.get(position).get(0).equals("Recognition")){
                if (allImageDeezerPlaylist[position]==null){
                    if (allJsonObject.get(position).get(5)!=null){
                        initializeImageAlbum(allJsonObject.get(position).get(5),holder,position);
                    }
                }else{
                    holder.photo.setImageBitmap(allImageDeezerPlaylist[position]);
                }
            }
        }
    }
    //    Elle nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView.
//    Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
//    Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) )
//    les ViewHolder déjà créés.
//    Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources
    public RV_Adapter_User_Playlist.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_for_search, parent, false);
        RV_Adapter_User_Playlist.MyViewHolder vh = new RV_Adapter_User_Playlist.MyViewHolder(view);
        return vh;
    }


    public void initializeImageAlbum(final String urlArtistImage, final RV_Adapter_User_Playlist.MyViewHolder holder, final int position){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!urlArtistImage.equals("null")){
                        final URL newurl = new URL(urlArtistImage);
                        final Bitmap ICON= BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        allImageDeezerPlaylist[position]=ICON;
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
    //    Cette méthode permet de retourner la taille de notre liste d'objet,
//    et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
    @Override
    public int getItemCount() {
        return this.allJsonObject.size();
    }


}
