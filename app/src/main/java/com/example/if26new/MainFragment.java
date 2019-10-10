package com.example.if26new;


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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ImageButton mImageButton1Artist;
    private ImageButton mImageButton2Artist;
    private ImageButton mImageButton3Artist;
    private ImageButton mImageButton1Rank;
    private ImageButton mImageButton2Rank;
    private ImageButton mImageButton3Rank;
    private LinearLayout mLinearLayout1;
    private LinearLayout mLinearLayout2;
    private LinearLayout mLinearLayout3;
    private ImageButton[] mImageButtonsPlaylists;
    private ImageButton[] mImageButtonsConcerts;
    private ImageButton[] mImageButtonsAlbums;
    private LinearLayout[] mLinearLayoutsPlaylists;
    private LinearLayout[] mLinearLayoutsAlbums;
    private LinearLayout[] mLinearLayoutsConcerts;
    private TextView[] mTextViewsPlaylists;
    private TextView[] mTextViewsAlbums;
    private TextView[] mTextViewsConcerts;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mImageButtonsPlaylists = new ImageButton[10];
        mLinearLayoutsPlaylists = new LinearLayout[10];
        mTextViewsPlaylists = new TextView[10];
        mImageButtonsConcerts = new ImageButton[10];
        mLinearLayoutsConcerts = new LinearLayout[10];
        mTextViewsConcerts = new TextView[10];
        mImageButtonsAlbums = new ImageButton[10];
        mLinearLayoutsAlbums= new LinearLayout[10];
        mTextViewsAlbums = new TextView[10];
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
        lp.setMargins(50, 50, 50, 50);
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
            mLinearLayoutsPlaylists[i] = new LinearLayout(getActivity());
            mLinearLayout1.addView(mLinearLayoutsPlaylists[i]);
            mLinearLayoutsPlaylists[i].setOrientation(LinearLayout.VERTICAL);
            mImageButtonsPlaylists[i] = new ImageButton(getActivity());
            mLinearLayoutsPlaylists[i].addView(mImageButtonsPlaylists[i]);
            mImageButtonsPlaylists[i].setBackground(null);
            mImageButtonsPlaylists[i].setImageResource(R.drawable.iconforplaylist);
            android.view.ViewGroup.LayoutParams params = mImageButtonsPlaylists[i].getLayoutParams();
            params.height=500;
            params.width=500;
            mImageButtonsPlaylists[i].setLayoutParams(params);
            mImageButtonsPlaylists[i].setAdjustViewBounds(true);
            mImageButtonsPlaylists[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsPlaylists[i] = new TextView(getActivity());
            mTextViewsPlaylists[i].setText("Playlist " + i);
            mTextViewsPlaylists[i].setTextColor(Color.WHITE);
            mLinearLayoutsPlaylists[i].addView(mTextViewsPlaylists[i]);
            mLinearLayoutsPlaylists[i].setLayoutParams(lp);
            mTextViewsPlaylists[i].setGravity(Gravity.CENTER_HORIZONTAL);

            //PARTIE CONCERTS
            mLinearLayoutsConcerts[i] = new LinearLayout(getActivity());
            mLinearLayout2.addView(mLinearLayoutsConcerts[i]);
            mLinearLayoutsConcerts[i].setOrientation(LinearLayout.VERTICAL);
            mImageButtonsConcerts[i] = new ImageButton(getActivity());
            mLinearLayoutsConcerts[i].addView(mImageButtonsConcerts[i]);
            mImageButtonsConcerts[i].setBackground(null);
            mImageButtonsConcerts[i].setImageResource(R.drawable.concertstade);
            mImageButtonsConcerts[i].setLayoutParams(params);
            mImageButtonsConcerts[i].setAdjustViewBounds(true);
            mImageButtonsConcerts[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsConcerts[i] = new TextView(getActivity());
            mTextViewsConcerts[i].setText("Concert " + i);
            mTextViewsConcerts[i].setTextColor(Color.WHITE);
            mLinearLayoutsConcerts[i].addView(mTextViewsConcerts[i]);
            mLinearLayoutsConcerts[i].setLayoutParams(lp);
            mTextViewsConcerts[i].setGravity(Gravity.CENTER_HORIZONTAL);

            //PARTIE ALBUM
            mLinearLayoutsAlbums[i] = new LinearLayout(getActivity());
            mLinearLayout3.addView(mLinearLayoutsAlbums[i]);
            mLinearLayoutsAlbums[i].setOrientation(LinearLayout.VERTICAL);
            mImageButtonsAlbums[i] = new ImageButton(getActivity());
            mLinearLayoutsAlbums[i].addView(mImageButtonsAlbums[i]);
            mImageButtonsAlbums[i].setImageResource(R.drawable.postmalonealb);
            mImageButtonsAlbums[i].setBackground(null);
            mImageButtonsAlbums[i].setLayoutParams(params);
            mImageButtonsAlbums[i].setAdjustViewBounds(true);
            mImageButtonsAlbums[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsAlbums[i] = new TextView(getActivity());
            mTextViewsAlbums[i].setText("Album " + i+" ");
            mTextViewsAlbums[i].setTextColor(Color.WHITE);
            mLinearLayoutsAlbums[i].addView(mTextViewsAlbums[i]);
            mLinearLayoutsAlbums[i].setLayoutParams(lp);
            mTextViewsAlbums[i].setGravity(Gravity.CENTER_HORIZONTAL);

        }
        return result;

    }


    public void updateDesignWhenUserClickedBottomView(String request) {
    }
}





