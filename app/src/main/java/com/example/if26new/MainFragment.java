package com.example.if26new;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    //la
    private ImageButton mImageButton1Artist;
    private ImageButton mImageButton2Artist;
    private ImageButton mImageButton3Artist;
    private ImageButton mImageButton1Rank;
    private ImageButton mImageButton2Rank;
    private ImageButton mImageButton3Rank;
    private LinearLayout mLinearLayout1;
    private LinearLayout mLinearLayout2;
    private LinearLayout mLinearLayout3;
    private ImageButton mImageButtonsPlaylists;
    private Button mImageButtonsConcerts;
    private ImageButton mImageButtonsAlbums;
    private LinearLayout mLinearLayoutsPlaylists;
    private LinearLayout mLinearLayoutsAlbums;
    private LinearLayout mLinearLayoutsConcerts;
    private TextView mTextViewsPlaylists;
    private TextView mTextViewsAlbums;
    private TextView mTextViewsConcerts;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_main, container, false);

        mLinearLayout1 = result.findViewById(R.id.linearLayoutplaylistID);
        mLinearLayout2 = result.findViewById(R.id.linearLayoutConcertsID);
        mLinearLayout3 = result.findViewById(R.id.linearLayoutAlbumsID);
        mImageButton1Artist = result.findViewById(R.id.firstTopArtistsID);
        mImageButton2Artist = result.findViewById(R.id.secondTopArtist);
        mImageButton3Artist = result.findViewById(R.id.thirdTopArtistID);;
        mImageButton1Rank = result.findViewById(R.id.firstRankID);;
        mImageButton2Rank= result.findViewById(R.id.secondRankID);
        mImageButton3Rank = result.findViewById(R.id.thirdRankID);



        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 50, 20, 50);
        android.view.ViewGroup.LayoutParams params2 = mImageButton1Artist.getLayoutParams();
        params2.height=500;



        mImageButton1Artist.setBackground(null);
        mImageButton1Artist.setImageResource(R.drawable.postmalone);
        mImageButton1Artist.setLayoutParams(params2);
        mImageButton1Artist.setAdjustViewBounds(true);
        mImageButton2Artist.setBackground(null);
        mImageButton2Artist.setImageResource(R.drawable.arianagrande);
        mImageButton2Artist.setLayoutParams(params2);
        mImageButton2Artist.setAdjustViewBounds(true);
        mImageButton3Artist.setBackground(null);
        mImageButton3Artist.setImageResource(R.drawable.vegedream);
        mImageButton3Artist.setLayoutParams(params2);
        mImageButton3Artist.setAdjustViewBounds(true);
        mImageButton3Artist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageButton1Rank.setBackground(null);
        mImageButton1Rank.setImageResource(R.drawable.ic_first);
        mImageButton2Rank.setBackground(null);
        mImageButton2Rank.setImageResource(R.drawable.ic_second);
        mImageButton3Rank.setBackground(null);
        mImageButton3Rank.setImageResource(R.drawable.ic_third);

        for (int i=0;i<10;i++) {
            //PARTIE PLAYLIST
            mLinearLayoutsPlaylists = new LinearLayout(getActivity());
            mLinearLayout1.addView(mLinearLayoutsPlaylists);
            mLinearLayoutsPlaylists.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsPlaylists = new ImageButton(getActivity());
            mLinearLayoutsPlaylists.addView(mImageButtonsPlaylists);
            mImageButtonsPlaylists.setBackground(null);
            mImageButtonsPlaylists.setImageResource(R.drawable.hazy1);
            mImageButtonsPlaylists.setAdjustViewBounds(true);
            android.view.ViewGroup.LayoutParams params = mImageButtonsPlaylists.getLayoutParams();
            params.height=450;
            params.width=450;
            mImageButtonsPlaylists.setLayoutParams(params);
            mImageButtonsPlaylists.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsPlaylists = new TextView(getActivity());
            mTextViewsPlaylists.setText("Playlist " + i);
            mTextViewsPlaylists.setTextColor(Color.WHITE);
            mLinearLayoutsPlaylists.addView(mTextViewsPlaylists);
            mLinearLayoutsPlaylists.setLayoutParams(lp);
            mTextViewsPlaylists.setGravity(Gravity.CENTER_HORIZONTAL);

            //PARTIE CONCERTS
            mLinearLayoutsConcerts = new LinearLayout(getActivity());
            mLinearLayout2.addView(mLinearLayoutsConcerts);
            mLinearLayoutsConcerts.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp1.setMargins(25, 50, 25, 50);
            mImageButtonsConcerts = new Button(getActivity());
            mLinearLayoutsConcerts.addView(mImageButtonsConcerts);
            mImageButtonsConcerts.setBackgroundResource(R.drawable.concertstade);
            android.view.ViewGroup.LayoutParams params1 = mImageButtonsConcerts.getLayoutParams();
            params1.height=440;
            params1.width=440;
            mImageButtonsConcerts.setLayoutParams(params1);
            mImageButtonsConcerts.setText("Artiste\n-\nVille");
            mImageButtonsConcerts.setTextColor(Color.BLACK);
            /*mTextViewsConcerts = new TextView(getActivity());
            mTextViewsConcerts.setText("Concert " + i);
            mTextViewsConcerts.setTextColor(Color.WHITE);
            mLinearLayoutsConcerts.addView(mTextViewsConcerts);
            mTextViewsConcerts.setGravity(Gravity.CENTER_HORIZONTAL);*/
            mLinearLayoutsConcerts.setLayoutParams(lp1);


            //PARTIE ALBUM
            mLinearLayoutsAlbums = new LinearLayout(getActivity());
            mLinearLayout3.addView(mLinearLayoutsAlbums);
            mLinearLayoutsAlbums.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsAlbums = new ImageButton(getActivity());
            mLinearLayoutsAlbums.addView(mImageButtonsAlbums);
            mImageButtonsAlbums.setImageResource(R.drawable.postmalonealb);
            mImageButtonsAlbums.setBackgroundColor(Color.BLACK);
            android.view.ViewGroup.LayoutParams params3 = mImageButtonsAlbums.getLayoutParams();
            params3.height=450;
            params3.width=450;
            mImageButtonsAlbums.setLayoutParams(params3);
            mImageButtonsAlbums.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImageButtonsAlbums.setAdjustViewBounds(true);
            mTextViewsAlbums = new TextView(getActivity());
            mTextViewsAlbums.setText("Album " + i+" ");
            mTextViewsAlbums.setTextColor(Color.WHITE);
            mLinearLayoutsAlbums.addView(mTextViewsAlbums);
            mLinearLayoutsAlbums.setLayoutParams(lp);
            mTextViewsAlbums.setGravity(Gravity.CENTER_HORIZONTAL);

            mImageButton1Artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ArtistActivity = new Intent(getActivity(),ActivityArtist.class);
                    startActivity(ArtistActivity);
                }
            });
        }
        return result;

    }



    public void updateDesignWhenUserClickedBottomView(String request) {
    }
}





