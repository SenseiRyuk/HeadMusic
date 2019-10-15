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
        view=inflater.inflate(R.layout.fragment_album_artist, container, false);


        sizeAlbums=10;
        imageButtonAlbum=new ImageButton[sizeAlbums];
        albumName=new TextView[sizeAlbums];
        linearLayout = view.findViewById(R.id.linearForAlbumFragmentArtistID);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsAlbumName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsAlbumName.setMargins(10,60,0,0);
        for (int i = 0; i < sizeAlbums; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonAlbum[i]=new ImageButton(getActivity());
            dynamique.addView(imageButtonAlbum[i],paramsImageButton);
            imageButtonAlbum[i].setBackground(null);
            imageButtonAlbum[i].setImageResource(R.drawable.rap4);
            imageButtonAlbum[i].setTag(R.drawable.rap4);
            android.view.ViewGroup.LayoutParams params = imageButtonAlbum[i].getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonAlbum[i].setLayoutParams(params);
            imageButtonAlbum[i].setAdjustViewBounds(true);
            imageButtonAlbum[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonAlbum[i].requestLayout();
            imageButtonAlbum[i].setOnClickListener(this);

            albumName[i]=new TextView(getActivity());
            albumName[i].setText("Album " +i);
            albumName[i].setTextColor(Color.WHITE);
            albumName[i].setTextSize(20);
            albumName[i].setSingleLine(true);
            albumName[i].setOnClickListener(this);

            dynamique.addView(albumName[i],paramsAlbumName);
            linearLayout.addView(dynamique);
        }
        return view;
    }
    public void onClick(View v){
        for (int i=0;i<sizeAlbums;i++){
            if (v.equals(albumName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",albumName[i].getText().toString());
                bundle.putInt("ALBUM_IMAGE_ID",(Integer) imageButtonAlbum[i].getTag());
                Intent playListActivity = new Intent(getActivity(), Album_view.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonAlbum[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",albumName[i].getText().toString());
                bundle.putInt("ALBUM_IMAGE_ID",(Integer) imageButtonAlbum[i].getTag());
                Intent playListActivity = new Intent(getActivity(), Album_view.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
}

