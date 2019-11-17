package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.if26new.Model.LikeAlbumModel;

public class Album_view extends AppCompatActivity implements View.OnClickListener {

    private TextView[] songName;
    private TextView[] artistName;
    private TextView albumName;
    private Button followbtn;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private ImageView mImageView;
    private int sizeSongInAlbum;
    private String nameArtist;
    private int imageAlbum;
    private int idAlbum;
    private String nameAlbum;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;
    private int isCallFromArtistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);
        db=SaveMyMusicDatabase.getInstance(this);
        followbtn=findViewById(R.id.followButtonAlbum);
        returnButton=findViewById(R.id.returnButtonAlbumView);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });
        imageAlbum=getIntent().getExtras().getInt("ALBUM_IMAGE_ID");
        setImageAlbum(imageAlbum);
        idAlbum=getIntent().getExtras().getInt("ALBUM_ID");
        setAlbumMusic(idAlbum); //Use dataBase
        nameAlbum=getIntent().getExtras().getString("ALBUM_NAME");
        setAlbumName(nameAlbum);
        nameArtist=getIntent().getStringExtra("ARTIST_NAME");
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
        isCallFromArtistView=getIntent().getExtras().getInt("IS_FROM_ARTIST_VIEW",0);
        if (db.mLikeAlbumDao().getLikeFromAlbumAndUserExist(db.getActualUser(),db.mAlbumDao().getAlbumFromId(idAlbum).getId())){
            followbtn.setText("Dislike");
        }else{
            followbtn.setText("Like");
        }
            followbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followbtn.getText().equals("Like")){
                    db.mAlbumDao().updateLike(true,idAlbum);
                    db.mLikeAlbumDao().insertLike(new LikeAlbumModel(db.getActualUser(), idAlbum));
                    followbtn.setText("Dislike");
                }else{
                    db.mAlbumDao().updateLike(false,idAlbum);
                    db.mLikeAlbumDao().deleteLike(db.mLikeAlbumDao().getLikeFromAlbumAndUser(db.getActualUser(),idAlbum).getId());
                    followbtn.setText("Like");
                }
            }
        });

    }
    public void setImageAlbum(int idImage){
        mImageView = findViewById(R.id.AlbumImageInAlbumView);
        android.view.ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height=650;
        params.width=650;
        mImageView.setLayoutParams(params);
        mImageView.setImageResource(idImage);
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
    public void setAlbumMusic(int albumId){
        //BDD --> Grace a la base de donnée on va venir chercher notre album(les singles, ainsi que sa taille) dans la base de donnée grâce à son nom passer en paramètre
        sizeSongInAlbum=db.mSingleDao().getSingleFromAlbum(albumId).length;
        artistName=new TextView[sizeSongInAlbum];
        songName=new TextView[sizeSongInAlbum];

        linearLayout = findViewById(R.id.linearForAlbumView);
        ViewGroup.MarginLayoutParams paramsSingle = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsSingle.setMargins(50,25,0,0);
        ViewGroup.MarginLayoutParams paramsArtist = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtist.setMargins(50,0,0,25);
        for (int i = 0; i<sizeSongInAlbum; i++) {
            dynamique = new LinearLayout(this);
            dynamique.setOrientation(LinearLayout.VERTICAL);
            artistName[i] = new TextView(this);
            songName[i] = new TextView(this);
            songName[i].setText(db.mSingleDao().getSingleFromAlbum(albumId)[i].getTitleSingle());
            songName[i].setTextColor(Color.WHITE);
            songName[i].setTextSize(20);
            songName[i].setSingleLine(true);
            songName[i].setOnClickListener(this);
            artistName[i].setText(nameArtist);
            artistName[i].setTextColor(Color.WHITE);
            artistName[i].setTextSize(10);
            artistName[i].setSingleLine(true);
            artistName[i].setOnClickListener(this);
            dynamique.addView(songName[i],paramsSingle);
            dynamique.addView(artistName[i],paramsArtist);
            linearLayout.addView(dynamique);
        }
    }
    public void setAlbumName(String AlbumName){
        albumName=findViewById(R.id.albumNameInAlbumView);
        albumName.setText(AlbumName);
    }
    public void onClick(View v){
        for (int i=0;i<sizeSongInAlbum;i++){
            if (v.equals(songName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",db.mSingleDao().getSingleFromAlbum(idAlbum)[i].getTitleSingle());
                bundle.putString("ARTIST_NAME",nameArtist);
                bundle.putInt("ALBUM_ID",idAlbum);
                bundle.putString("CONTEXT","AlbumActivity");
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent playListActivity = new Intent(Album_view.this, Listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(artistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",db.mSingleDao().getSingleFromAlbum(idAlbum)[i].getTitleSingle());
                bundle.putString("ARTIST_NAME",nameArtist);
                bundle.putInt("ALBUM_ID",idAlbum);
                bundle.putString("CONTEXT","AlbumActivity");
                bundle.putString("FRAGMENT_NAME",fragmentName);
                Intent playListActivity = new Intent(Album_view.this, Listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void  returnMethod(){
        Bundle bundle=new Bundle();
        if (isCallFromArtistView==1){
            bundle.putString("ARTIST_NAME",nameArtist);
            bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(nameArtist).getImage());
            bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(nameArtist).getBio());
            bundle.putString("FRAGMENT_NAME",fragmentName);
            Intent playListActivity = new Intent(Album_view.this, ActivityArtist.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }else{
            bundle.putString("FRAGMENT_NAME",fragmentName);
            bundle.putString("CONTEXT","AlbumActivity");
            Intent playListActivity = new Intent(Album_view.this, HomeActivity.class);
            playListActivity.putExtras(bundle);
            startActivity(playListActivity);
        }
    }
}
