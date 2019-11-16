package com.example.if26new;


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

import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.LikeArtistModel;
import com.example.if26new.Model.PlaylistModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements View.OnClickListener {
    public static ArtistFragment newInstance() {
        return (new ArtistFragment());
    }

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] ArtistName;
    private ImageButton[] imageButtonArtist;
    private int sizeArtists;
    private SaveMyMusicDatabase db;
    private View view;
    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_artist, container, false);
        //Retrieve all the playlist in data base
        getAndDisplayAllArtistsFromTheDataBase();
        return view;
    }
    public void getAndDisplayAllArtistsFromTheDataBase(){
        linearLayout = view.findViewById(R.id.linearForArtist);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsArtistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtistName.setMargins(10,60,0,0);

        db=SaveMyMusicDatabase.getInstance(getActivity());
        //ArtistModel[] allArtist = db.mArtistDao().getLikedArtist(true);
        LikeArtistModel[] allLikeArtist=db.mLikeArtistDao().getLikeFromUser(db.getActualUser());
        ArtistModel[] allArtist;
        allArtist=new ArtistModel[allLikeArtist.length];
        for (int i=0;i<allLikeArtist.length;i++){
            allArtist[i]=db.mArtistDao().getArtistFromId(allLikeArtist[i].getArtistId());
        }
        sizeArtists=allArtist.length;
        ArtistName=new TextView[sizeArtists];
        imageButtonArtist=new ImageButton[sizeArtists];
        for (int i = 0; i <sizeArtists; i++) {
                dynamique = new LinearLayout(getActivity());
                dynamique.setOrientation(LinearLayout.HORIZONTAL);

                imageButtonArtist[i]=new ImageButton(getActivity());
                dynamique.addView(imageButtonArtist[i],paramsImageButton);
                imageButtonArtist[i].setBackground(null);
                imageButtonArtist[i].setImageResource(allArtist[i].getImage());
                imageButtonArtist[i].setTag(allArtist[i].getImage());
                android.view.ViewGroup.LayoutParams params = imageButtonArtist[i].getLayoutParams();
                params.height=200;
                params.width=200;
                imageButtonArtist[i].setLayoutParams(params);
                imageButtonArtist[i].setAdjustViewBounds(true);
                imageButtonArtist[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButtonArtist[i].requestLayout();
                imageButtonArtist[i].setOnClickListener(this);

                ArtistName[i]=new TextView(getActivity());
                ArtistName[i].setText(allArtist[i].getName());
                ArtistName[i].setTextColor(Color.WHITE);
                ArtistName[i].setTextSize(20);
                ArtistName[i].setSingleLine(true);
                ArtistName[i].setOnClickListener(this);
                //dynamique.addView(imageButtonPlaylist,paramsImageButton);
                dynamique.addView(ArtistName[i],paramsArtistName);
                linearLayout.addView(dynamique);
            }
    }
    public void onClick(View v){
        for (int i=0;i<sizeArtists;i++){
            if (v.equals(ArtistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",ArtistName[i].getText().toString());
                bundle.putInt("ARTIST_IMAGE_ID",(Integer) imageButtonArtist[i].getTag());
                bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(ArtistName[i].getText().toString()).getBio());
                bundle.putString("FRAGMENT_NAME","UserFragment");
                Intent playListActivity = new Intent(getActivity(), ActivityArtist.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonArtist[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",ArtistName[i].getText().toString());
                bundle.putInt("ARTIST_IMAGE_ID",(Integer) imageButtonArtist[i].getTag());
                bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(ArtistName[i].getText().toString()).getBio());
                bundle.putString("FRAGMENT_NAME","UserFragment");
                Intent playListActivity = new Intent(getActivity(), ActivityArtist.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }

}
