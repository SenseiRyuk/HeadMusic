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
    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_artist, container, false);

        //Retrieve all the playlist in data base
        linearLayout = view.findViewById(R.id.linearForArtist);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsArtistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtistName.setMargins(10,60,0,0);

        sizeArtists=10;
        ArtistName=new TextView[sizeArtists];
        imageButtonArtist=new ImageButton[sizeArtists];
        for (int i = 0; i <sizeArtists; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonArtist[i]=new ImageButton(getActivity());
            dynamique.addView(imageButtonArtist[i],paramsImageButton);
            imageButtonArtist[i].setBackground(null);

            if (i==1){
                imageButtonArtist[i].setImageResource(R.drawable.jazz);
                imageButtonArtist[i].setTag(R.drawable.jazz);
            }else{
                imageButtonArtist[i].setImageResource(R.drawable.jazz2);
                imageButtonArtist[i].setTag(R.drawable.jazz2);
            }
            android.view.ViewGroup.LayoutParams params = imageButtonArtist[i].getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonArtist[i].setLayoutParams(params);
            imageButtonArtist[i].setAdjustViewBounds(true);
            imageButtonArtist[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonArtist[i].requestLayout();
            imageButtonArtist[i].setOnClickListener(this);

            ArtistName[i]=new TextView(getActivity());
            ArtistName[i].setText("Artist "+i);
            ArtistName[i].setTextColor(Color.WHITE);
            ArtistName[i].setTextSize(20);
            ArtistName[i].setSingleLine(true);
            ArtistName[i].setOnClickListener(this);
            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            dynamique.addView(ArtistName[i],paramsArtistName);
            linearLayout.addView(dynamique);
        }
        return view;
    }
    public void onClick(View v){
        for (int i=0;i<sizeArtists;i++){
            if (v.equals(ArtistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",ArtistName[i].getText().toString());
                bundle.putInt("ARTIST_IMAGE_ID",(Integer) imageButtonArtist[i].getTag());
                Intent playListActivity = new Intent(getActivity(), ActivityArtist.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonArtist[i])){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",ArtistName[i].getText().toString());
                bundle.putInt("ARTIST_IMAGE_ID",(Integer) imageButtonArtist[i].getTag());
                Intent playListActivity = new Intent(getActivity(), ActivityArtist.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }

}
