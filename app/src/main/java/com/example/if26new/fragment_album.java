package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_album extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] albumName;
    private ImageButton[] imageButtonAlbum;
    private int sizeAlbums;
    private String artistName;
    private SaveMyMusicDatabase db;
    private int isCallFromArtistView;
    private String fragmentName;
    public fragment_album() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        db=SaveMyMusicDatabase.getInstance(getActivity());
        view=inflater.inflate(R.layout.fragment_album_artist, container, false);
        artistName=getActivity().getIntent().getStringExtra("ARTIST_NAME");
        Bundle args = getArguments();
        isCallFromArtistView=args.getInt("IS_FROM_ARTIST_VIEW",0);
        fragmentName=args.getString("FRAGMENT","null");

        sizeAlbums=db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId()).length;
        imageButtonAlbum=new ImageButton[sizeAlbums];
        albumName=new TextView[sizeAlbums];
        linearLayout = view.findViewById(R.id.linearForAlbumFragmentArtistID);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsAlbumName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsAlbumName.setMargins(10,60,0,0);
        if(sizeAlbums==0){
            albumName=new TextView[1];
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);
            albumName[0]=new TextView(getActivity());
            albumName[0].setText("Cet artiste ne possède pas d'album.");
            albumName[0].setTextColor(Color.WHITE);
            albumName[0].setTextSize(20);
            albumName[0].setSingleLine(true);
            dynamique.addView(albumName[0],paramsAlbumName);
            linearLayout.addView(dynamique);
        }else {
            for (int i = 0; i < sizeAlbums; i++) {
                dynamique = new LinearLayout(getActivity());
                dynamique.setOrientation(LinearLayout.HORIZONTAL);

                imageButtonAlbum[i] = new ImageButton(getActivity());
                dynamique.addView(imageButtonAlbum[i], paramsImageButton);
                imageButtonAlbum[i].setBackground(null);
                imageButtonAlbum[i].setImageResource(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getImage());
                imageButtonAlbum[i].setTag(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getImage());
                android.view.ViewGroup.LayoutParams params = imageButtonAlbum[i].getLayoutParams();
                params.height = 200;
                params.width = 200;
                imageButtonAlbum[i].setLayoutParams(params);
                imageButtonAlbum[i].setAdjustViewBounds(true);
                imageButtonAlbum[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButtonAlbum[i].requestLayout();
                imageButtonAlbum[i].setOnClickListener(this);

                albumName[i] = new TextView(getActivity());
                albumName[i].setText(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getTitleAlbum());
                albumName[i].setTextColor(Color.WHITE);
                albumName[i].setTextSize(20);
                albumName[i].setSingleLine(true);
                albumName[i].setOnClickListener(this);

                dynamique.addView(albumName[i], paramsAlbumName);
                linearLayout.addView(dynamique);
            }
        }
        return view;
    }
    public void onClick(View v){
        for (int i=0;i<sizeAlbums;i++){

                if (v.equals(albumName[i])){
                    Bundle bundle=new Bundle();
                    bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getTitleAlbum());
                    bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getImage());
                    bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getId());
                    bundle.putString("ARTIST_NAME",artistName);
                    bundle.putString("FRAGMENT_NAME",fragmentName);
                    bundle.putInt("IS_FROM_ARTIST_VIEW",isCallFromArtistView);
                    Intent playListActivity = new Intent(getActivity(), Album_view.class);
                    playListActivity.putExtras(bundle);
                    startActivity(playListActivity);
                }else if (v.equals(imageButtonAlbum[i])){
                    Bundle bundle=new Bundle();
                    bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getTitleAlbum());
                    bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getImage());
                    bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getId());
                    bundle.putString("ARTIST_NAME",artistName);
                    bundle.putString("FRAGMENT_NAME",fragmentName);
                    bundle.putInt("IS_FROM_ARTIST_VIEW",isCallFromArtistView);
                    Intent playListActivity = new Intent(getActivity(), Album_view.class);
                    playListActivity.putExtras(bundle);
                    startActivity(playListActivity);
                }
        }
    }
}

