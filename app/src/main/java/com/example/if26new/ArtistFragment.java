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
public class ArtistFragment extends Fragment {
    public static ArtistFragment newInstance() {
        return (new ArtistFragment());
    }

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView ArtistName;
    private ImageButton imageButtonArtist;
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
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonArtist=new ImageButton(getActivity());
            dynamique.addView(imageButtonArtist,paramsImageButton);
            imageButtonArtist.setBackground(null);
            imageButtonArtist.setImageResource(R.drawable.hazy1);
            android.view.ViewGroup.LayoutParams params = imageButtonArtist.getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonArtist.setLayoutParams(params);
            imageButtonArtist.setAdjustViewBounds(true);
            imageButtonArtist.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonArtist.requestLayout();

            ArtistName=new TextView(getActivity());
            ArtistName.setText("Artist");
            ArtistName.setTextColor(Color.WHITE);
            ArtistName.setTextSize(20);
            ArtistName.setSingleLine(true);
            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            dynamique.addView(ArtistName,paramsArtistName);
            linearLayout.addView(dynamique);
            /*imageButtonPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInActivity = new Intent(getActivity(), PlaylistView.class);
                    startActivity(signInActivity);
                }
            });*/
            ArtistName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   Intent signInActivity = new Intent(getActivity(), PlaylistView.class);
                 //   startActivity(signInActivity);
                }
            });
        }
        return view;
    }

}
