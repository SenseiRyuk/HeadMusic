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
public class NewsFragment extends Fragment implements View.OnClickListener{
    private LinearLayout mLinearLayoutNew1;
    private LinearLayout mLinearLayoutNew2;
    private LinearLayout mLinearLayoutNew3;
    private ImageButton[] mImageButtonsSingles;
    private Button[] mImageButtonsConcerts;
    private ImageButton[] mImageButtonsAlbums;
    private LinearLayout mLinearLayoutsSingles;
    private LinearLayout mLinearLayoutsAlbums;
    private LinearLayout mLinearLayoutsConcerts;
    private TextView[] mTextViewsSingles;
    private TextView[] mTextViewsAlbums;
    private TextView mTextViewsConcerts;
    private SaveMyMusicDatabase db;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_news, container, false);
        db=SaveMyMusicDatabase.getInstance(getActivity());
        mLinearLayoutNew1 = result.findViewById(R.id.linearNewSinglesID);
        mLinearLayoutNew2 = result.findViewById(R.id.linearNewConcertsID);
        mLinearLayoutNew3 = result.findViewById(R.id.linearNewAlbumsID);
        mTextViewsSingles=new TextView[db.mSingleDao().getSingleFromNew(true).length];
        mImageButtonsSingles=new ImageButton[db.mSingleDao().getSingleFromNew(true).length];
        mImageButtonsAlbums=new ImageButton[db.mAlbumDao().getAlbumFromNew().length];
        mTextViewsAlbums=new TextView[db.mAlbumDao().getAlbumFromNew().length];
        mImageButtonsConcerts=new Button[db.mConcertDao().getConcertFromNew(true).length];

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 50, 20, 50);

        for (int i=0;i<db.mSingleDao().getSingleFromNew(true).length;i++) {
            //PARTIE SINGLES
            mLinearLayoutsSingles = new LinearLayout(getActivity());
            mLinearLayoutNew1.addView(mLinearLayoutsSingles);
            mLinearLayoutsSingles.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsSingles[i] = new ImageButton(getActivity());
            mLinearLayoutsSingles.addView(mImageButtonsSingles[i]);
            mImageButtonsSingles[i].setBackground(null);
            mImageButtonsSingles[i].setImageResource(R.drawable.iconforplaylist);
            mImageButtonsSingles[i].setAdjustViewBounds(true);
            android.view.ViewGroup.LayoutParams params = mImageButtonsSingles[i].getLayoutParams();
            params.height = 450;
            params.width = 450;
            mImageButtonsSingles[i].setLayoutParams(params);
            mImageButtonsSingles[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsSingles[i] = new TextView(getActivity());
            mTextViewsSingles[i].setText(db.mArtistDao().getArtistFromId(db.mSingleDao().getSingleFromNew(true)[i].getArtistId()).getName()+" -\n"+db.mSingleDao().getSingleFromNew(true)[i].getTitleSingle());
            mTextViewsSingles[i].setTextColor(Color.WHITE);
            mLinearLayoutsSingles.addView(mTextViewsSingles[i]);
            mLinearLayoutsSingles.setLayoutParams(lp);
            mTextViewsSingles[i].setGravity(Gravity.CENTER_HORIZONTAL);
            mTextViewsSingles[i].setOnClickListener(this);
            mImageButtonsSingles[i].setOnClickListener(this);
        }
        for(int i=0;i<db.mConcertDao().getConcertFromNew(true).length;i++) {

            //PARTIE CONCERTS
            mLinearLayoutsConcerts = new LinearLayout(getActivity());
            mLinearLayoutNew2.addView(mLinearLayoutsConcerts);
            mLinearLayoutsConcerts.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp1.setMargins(25, 50, 25, 50);
            mImageButtonsConcerts[i] = new Button(getActivity());
            mLinearLayoutsConcerts.addView(mImageButtonsConcerts[i]);
            mImageButtonsConcerts[i].setBackgroundResource(R.drawable.concertstade);
            android.view.ViewGroup.LayoutParams params1 = mImageButtonsConcerts[i].getLayoutParams();
            params1.height = 440;
            params1.width = 440;
            mImageButtonsConcerts[i].setLayoutParams(params1);
            mImageButtonsConcerts[i].setText(db.mArtistDao().getArtistFromId(db.mConcertDao().getConcertFromNew(true)[i].getArtistId()).getName()+"\n-\n"+db.mConcertDao().getConcertFromNew(true)[i].getLocationCity());
            mImageButtonsConcerts[i].setTextColor(Color.BLACK);
            mImageButtonsConcerts[i].setOnClickListener(this);
            /*mTextViewsConcerts = new TextView(getActivity());
            mTextViewsConcerts.setText("Concert " + i);
            mTextViewsConcerts.setTextColor(Color.WHITE);
            mLinearLayoutsConcerts.addView(mTextViewsConcerts);
            mTextViewsConcerts.setGravity(Gravity.CENTER_HORIZONTAL);*/
            mLinearLayoutsConcerts.setLayoutParams(lp1);

        }
        for(int i=0;i<db.mAlbumDao().getAlbumFromNew().length;i++){

            //PARTIE ALBUM
            mLinearLayoutsAlbums = new LinearLayout(getActivity());
            mLinearLayoutNew3.addView(mLinearLayoutsAlbums);
            mLinearLayoutsAlbums.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsAlbums[i] = new ImageButton(getActivity());
            mLinearLayoutsAlbums.addView(mImageButtonsAlbums[i]);
            mImageButtonsAlbums[i].setImageResource(db.mAlbumDao().getAlbumFromNew()[i].getImage());
            mImageButtonsAlbums[i].setBackground(null);
            mImageButtonsAlbums[i].setBackgroundColor(Color.BLACK);
            android.view.ViewGroup.LayoutParams params3 = mImageButtonsAlbums[i].getLayoutParams();
            params3.height=450;
            params3.width=450;
            mImageButtonsAlbums[i].setLayoutParams(params3);
            mImageButtonsAlbums[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImageButtonsAlbums[i].setAdjustViewBounds(true);
            mTextViewsAlbums[i] = new TextView(getActivity());
            mTextViewsAlbums[i].setText(db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromNew()[i].getArtistId()).getName()+" - \n"+db.mAlbumDao().getAllAlbum()[i].getTitleAlbum());
            mTextViewsAlbums[i].setTextColor(Color.WHITE);
            mTextViewsAlbums[i].setOnClickListener(this);
            mImageButtonsAlbums[i].setOnClickListener(this);
            mLinearLayoutsAlbums.addView(mTextViewsAlbums[i]);
            mLinearLayoutsAlbums.setLayoutParams(lp);
            mTextViewsAlbums[i].setGravity(Gravity.CENTER_HORIZONTAL);
            mTextViewsAlbums[i].setOnClickListener(this);
            mImageButtonsAlbums[i].setOnClickListener(this);
        }
        return result;


    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<db.mSingleDao().getSingleFromNew(true).length;i++){
            if (v.equals(mImageButtonsSingles[i])){
                Bundle bundle = new Bundle();
                bundle.putString("SONG_NAME",db.mSingleDao().getSingleFromNew(true)[i].getTitleSingle());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mSingleDao().getSingleFromNew(true)[i].getArtistId()).getName());
                Intent playListActivity = new Intent(getContext(), listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            } else if(v.equals(mTextViewsSingles[i])){
                Bundle bundle = new Bundle();
                bundle.putString("SONG_NAME",db.mSingleDao().getSingleFromNew(true)[i].getTitleSingle());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mSingleDao().getSingleFromNew(true)[i].getArtistId()).getName());
                Intent playListActivity = new Intent(getContext(), listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
        for (int i=0;i<db.mConcertDao().getConcertFromNew(true).length;i++){
            if(v.equals(mImageButtonsConcerts[i])){
                Bundle bundle = new Bundle();
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mConcertDao().getConcertFromNew(true)[i].getArtistId()).getName());
                bundle.putString("DATE",db.mConcertDao().getConcertFromNew(true)[i].getDate());
                bundle.putString("LOCATION", db.mConcertDao().getConcertFromNew(true)[i].getLocation());
                bundle.putString("LOCATION_CITY", db.mConcertDao().getConcertFromNew(true)[i].getLocationCity());
                bundle.putDouble("LOCATION_LAT", db.mConcertDao().getConcertFromNew(true)[i].getLocationLat());
                bundle.putDouble("LOCATION_LGN",db.mConcertDao().getConcertFromNew(true)[i].getLocationLgn());
                bundle.putString("TITLE_CONCERT",db.mConcertDao().getConcertFromNew(true)[i].getTitleConcert());
                bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromId(db.mConcertDao().getConcertFromNew(true)[i].getArtistId()).getImage());
                Intent concertActivity = new Intent(getActivity(), ConcertActivity.class);
                concertActivity.putExtras(bundle);
                startActivity(concertActivity);
            }
        }
        for (int i=0;i<db.mAlbumDao().getAlbumFromNew().length;i++){
            if(v.equals(mImageButtonsConcerts[i])){
                Bundle bundle = new Bundle();
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromNew()[i].getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromNew()[i].getArtistId()).getName());
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromNew()[i].getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID", db.mAlbumDao().getAlbumFromNew()[i].getImage());
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            } else if(v.equals(mTextViewsAlbums[i])){
                Bundle bundle = new Bundle();
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromNew()[i].getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromNew()[i].getArtistId()).getName());
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAlbumFromNew()[i].getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID", db.mAlbumDao().getAlbumFromNew()[i].getImage());
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            }
        }

    }
}
