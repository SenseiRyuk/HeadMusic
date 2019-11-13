package com.example.if26new;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.if26new.Model.AlbumModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] AlbumName;
    private ImageButton[] imageButtonAlbum;
    private int sizeAlbum;
    private SaveMyMusicDatabase db;
    public static AlbumFragment newInstance() {
        return (new AlbumFragment());
    }
    public AlbumFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_album, container, false);


        //here we will get all the playlist Name in the dataBase, hence we got the length
        db=SaveMyMusicDatabase.getInstance(getActivity());
        AlbumModel[] allAlbum=db.mAlbumDao().getLikedAlbum(true);
        sizeAlbum=allAlbum.length;
        AlbumName=new TextView[sizeAlbum];
        //here we retrieve all the imageName for each Playlist
        imageButtonAlbum=new ImageButton[sizeAlbum];
        linearLayout = view.findViewById(R.id.linearForAlbum);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsAlbumName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsAlbumName.setMargins(10,60,0,0);
        for (int i = 0; i <sizeAlbum; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonAlbum[i]=new ImageButton(getActivity());
            dynamique.addView(imageButtonAlbum[i],paramsImageButton);
            imageButtonAlbum[i].setBackground(null);
            imageButtonAlbum[i].setImageResource(allAlbum[i].getImage());
            imageButtonAlbum[i].setTag(allAlbum[i].getImage());
            android.view.ViewGroup.LayoutParams params = imageButtonAlbum[i].getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonAlbum[i].setLayoutParams(params);
            imageButtonAlbum[i].setAdjustViewBounds(true);
            imageButtonAlbum[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonAlbum[i].requestLayout();
            imageButtonAlbum[i].setOnClickListener(this);

            AlbumName[i]=new TextView(getActivity());
            AlbumName[i].setText(allAlbum[i].getTitleAlbum());
            AlbumName[i].setTextColor(Color.WHITE);
            AlbumName[i].setTextSize(20);
            AlbumName[i].setSingleLine(true);
            AlbumName[i].setOnClickListener(this);

            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            dynamique.addView(AlbumName[i],paramsAlbumName);
            linearLayout.addView(dynamique);
        }
        return view;
    }
    public void onClick(View v){
        for (int i=0;i<sizeAlbum;i++){
            if (v.equals(AlbumName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",AlbumName[i].getText().toString());
                bundle.putInt("ALBUM_IMAGE_ID",(Integer) imageButtonAlbum[i].getTag());
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromName(AlbumName[i].getText().toString()).getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromName(AlbumName[i].getText().toString()).getArtistId()).getName());
                bundle.putString("FRAGMENT_NAME","UserFragment");
                Intent playListActivity = new Intent(getActivity(), Album_view.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonAlbum[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",AlbumName[i].getText().toString());
                bundle.putInt("ALBUM_IMAGE_ID",(Integer) imageButtonAlbum[i].getTag());
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromName(AlbumName[i].getText().toString()).getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromName(AlbumName[i].getText().toString()).getArtistId()).getName());
                bundle.putString("FRAGMENT_NAME","UserFragment");
                Intent playListActivity = new Intent(getActivity(), Album_view.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }

}
