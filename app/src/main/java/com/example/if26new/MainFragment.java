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

import com.example.if26new.Model.PlaylistModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
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
    private Button[] mImageButtonsConcerts;
    private ImageButton[] mImageButtonsAlbums;
    private LinearLayout mLinearLayoutsPlaylists;
    private LinearLayout mLinearLayoutsAlbums;
    private LinearLayout mLinearLayoutsConcerts;
    private TextView mTextViewsPlaylists;
    private TextView[] mTextViewsAlbums;
    private TextView mTextViewsConcerts;
    private TextView mTextViewTop1;
    private TextView mTextViewTop2;
    private TextView mTextViewTop3;
    private SaveMyMusicDatabase db;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        db=SaveMyMusicDatabase.getInstance(getActivity());

        mImageButtonsAlbums=new ImageButton[db.mAlbumDao().getAllAlbum().length];
        mTextViewsAlbums=new TextView[db.mAlbumDao().getAllAlbum().length];
        mImageButtonsConcerts=new Button[db.mConcertDao().getAllConcert().length];

        mLinearLayout1 = result.findViewById(R.id.linearLayoutplaylistID);
        mLinearLayout2 = result.findViewById(R.id.linearLayoutConcertsID);
        mLinearLayout3 = result.findViewById(R.id.linearLayoutAlbumsID);
        mImageButton1Artist = result.findViewById(R.id.firstTopArtistsID);
        mImageButton2Artist = result.findViewById(R.id.secondTopArtist);
        mImageButton3Artist = result.findViewById(R.id.thirdTopArtistID);;
        mImageButton1Rank = result.findViewById(R.id.firstRankID);;
        mImageButton2Rank= result.findViewById(R.id.secondRankID);
        mImageButton3Rank = result.findViewById(R.id.thirdRankID);
        mTextViewTop1 = result.findViewById(R.id.txtViewTop1);
        mTextViewTop2 = result.findViewById(R.id.txtViewTop2);
        mTextViewTop3 = result.findViewById(R.id.txtViewTop3);



        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 50, 20, 50);
        android.view.ViewGroup.LayoutParams params2 = mImageButton1Artist.getLayoutParams();
        params2.height=500;



        mImageButton1Artist.setBackground(null);
        mImageButton1Artist.setImageResource(db.mArtistDao().getTop(1).getImage());
        mImageButton1Artist.setLayoutParams(params2);
        mImageButton1Artist.setAdjustViewBounds(true);
        mImageButton1Artist.setBackground(null);
        mImageButton1Artist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mTextViewTop1.setText(db.mArtistDao().getTop(1).getName());
        mImageButton2Artist.setImageResource(db.mArtistDao().getTop(2).getImage());
        mImageButton2Artist.setLayoutParams(params2);
        mImageButton2Artist.setAdjustViewBounds(true);
        mImageButton2Artist.setBackground(null);
        mImageButton2Artist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mTextViewTop2.setText(db.mArtistDao().getTop(2).getName());
        mImageButton3Artist.setImageResource(db.mArtistDao().getTop(3).getImage());
        mImageButton3Artist.setBackground(null);
        mImageButton3Artist.setLayoutParams(params2);
        mImageButton3Artist.setAdjustViewBounds(true);
        mImageButton3Artist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mTextViewTop3.setText(db.mArtistDao().getTop(3).getName());
        mImageButton1Rank.setBackground(null);
        mImageButton1Rank.setImageResource(R.drawable.ic_first);
        mImageButton2Rank.setBackground(null);
        mImageButton2Rank.setImageResource(R.drawable.ic_second);
        mImageButton3Rank.setBackground(null);
        mImageButton3Rank.setImageResource(R.drawable.ic_third);

        int sizePlaylist = db.mPlaylistDao().loadAllPlaylist().length;
        PlaylistModel playlsit[]=db.mPlaylistDao().loadAllPlaylist();
        for (int i=0;i<sizePlaylist;i++) {
            //PARTIE PLAYLIST
            mLinearLayoutsPlaylists = new LinearLayout(getActivity());
            mLinearLayout1.addView(mLinearLayoutsPlaylists);
            mLinearLayoutsPlaylists.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsPlaylists = new ImageButton(getActivity());
            mLinearLayoutsPlaylists.addView(mImageButtonsPlaylists);
            mImageButtonsPlaylists.setBackground(null);
            mImageButtonsPlaylists.setImageResource(playlsit[i].getImage());
            mImageButtonsPlaylists.setAdjustViewBounds(true);
            android.view.ViewGroup.LayoutParams params = mImageButtonsPlaylists.getLayoutParams();
            params.height = 450;
            params.width = 450;
            mImageButtonsPlaylists.setLayoutParams(params);
            mImageButtonsPlaylists.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsPlaylists = new TextView(getActivity());
            mTextViewsPlaylists.setText(playlsit[i].getTitles());
            mTextViewsPlaylists.setTextColor(Color.WHITE);
            mLinearLayoutsPlaylists.addView(mTextViewsPlaylists);
            mLinearLayoutsPlaylists.setLayoutParams(lp);
            mTextViewsPlaylists.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        for(int i=0;i<db.mConcertDao().getAllConcert().length;i++) {

            //PARTIE CONCERTS
            mLinearLayoutsConcerts = new LinearLayout(getActivity());
            mLinearLayout2.addView(mLinearLayoutsConcerts);
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
            mImageButtonsConcerts[i].setText(db.mArtistDao().getArtistFromId(db.mConcertDao().getAllConcert()[i].getArtistId()).getName() + "\n-\n" + db.mConcertDao().getAllConcert()[i].getLocationCity());
            mImageButtonsConcerts[i].setOnClickListener(this);
            mImageButtonsConcerts[i].setTextColor(Color.BLACK);
            mLinearLayoutsConcerts.setLayoutParams(lp1);

        }
        for(int i=0; i<db.mAlbumDao().getAllAlbum().length ; i++){


            //PARTIE ALBUM
            mLinearLayoutsAlbums = new LinearLayout(getActivity());
            mLinearLayout3.addView(mLinearLayoutsAlbums);
            mLinearLayoutsAlbums.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsAlbums[i] = new ImageButton(getActivity());
            mLinearLayoutsAlbums.addView(mImageButtonsAlbums[i]);
            mImageButtonsAlbums[i].setImageResource(db.mAlbumDao().getAllAlbum()[i].getImage());
            mImageButtonsAlbums[i].setBackgroundColor(Color.BLACK);
            android.view.ViewGroup.LayoutParams params3 = mImageButtonsAlbums[i].getLayoutParams();
            params3.height=450;
            params3.width=450;
            mImageButtonsAlbums[i].setLayoutParams(params3);
            mImageButtonsAlbums[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImageButtonsAlbums[i].setAdjustViewBounds(true);
            mImageButtonsAlbums[i].setOnClickListener(this);
            mTextViewsAlbums[i] = new TextView(getActivity());
            mTextViewsAlbums[i].setText(db.mArtistDao().getArtistFromId(db.mAlbumDao().getAllAlbum()[i].getArtistId()).getName()+" -\n "+db.mAlbumDao().getAllAlbum()[i].getTitleAlbum());
            mTextViewsAlbums[i].setTextColor(Color.WHITE);
            mTextViewsAlbums[i].setOnClickListener(this);
            mLinearLayoutsAlbums.addView(mTextViewsAlbums[i]);
            mLinearLayoutsAlbums.setLayoutParams(lp);
            mTextViewsAlbums[i].setGravity(Gravity.CENTER_HORIZONTAL);

            mImageButton1Artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getTop(1).getImage());
                    bundle.putString("ARTIST_NAME",db.mArtistDao().getTop(1).getName());
                    bundle.putInt("ARTIST_BIO",db.mArtistDao().getTop(1).getBio());
                    Intent artistActivity = new Intent(getActivity(),ActivityArtist.class);
                    artistActivity.putExtras(bundle);
                    startActivity(artistActivity);
                }
            });
            mImageButton2Artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getTop(2).getImage());
                    bundle.putString("ARTIST_NAME",db.mArtistDao().getTop(2).getName());
                    bundle.putInt("ARTIST_BIO",db.mArtistDao().getTop(2).getBio());
                    Intent artistActivity = new Intent(getActivity(),ActivityArtist.class);
                    artistActivity.putExtras(bundle);
                    startActivity(artistActivity);
                }
            });
            mImageButton3Artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getTop(3).getImage());
                    bundle.putString("ARTIST_NAME",db.mArtistDao().getTop(3).getName());
                    bundle.putInt("ARTIST_BIO",db.mArtistDao().getTop(3).getBio());
                    Intent artistActivity = new Intent(getActivity(),ActivityArtist.class);
                    artistActivity.putExtras(bundle);
                    startActivity(artistActivity);
                }
            });

        }
        return result;

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<db.mConcertDao().getAllConcert().length;i++){
            if(v.equals(mImageButtonsConcerts[i])){
                Bundle bundle = new Bundle();
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mConcertDao().getAllConcert()[i].getArtistId()).getName());
                bundle.putString("DATE",db.mConcertDao().getAllConcert()[i].getDate());
                bundle.putString("LOCATION", db.mConcertDao().getAllConcert()[i].getLocation());
                bundle.putString("LOCATION_CITY", db.mConcertDao().getAllConcert()[i].getLocationCity());
                bundle.putDouble("LOCATION_LAT", db.mConcertDao().getAllConcert()[i].getLocationLat());
                bundle.putDouble("LOCATION_LGN",db.mConcertDao().getAllConcert()[i].getLocationLgn());
                bundle.putString("TITLE_CONCERT",db.mConcertDao().getAllConcert()[i].getTitleConcert());
                bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromId(db.mConcertDao().getAllConcert()[i].getArtistId()).getImage());
                Intent concertActivity = new Intent(getActivity(), ConcertActivity.class);
                concertActivity.putExtras(bundle);
                startActivity(concertActivity);
            }
        }
        for (int i=0;i<db.mAlbumDao().getAllAlbum().length;i++){
            if(v.equals(mImageButtonsAlbums[i])){
                Bundle bundle = new Bundle();
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAllAlbum()[i].getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAllAlbum()[i].getArtistId()).getName());
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAllAlbum()[i].getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID", db.mAlbumDao().getAllAlbum()[i].getImage());
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            } else if(v.equals(mTextViewsAlbums[i])){
                Bundle bundle = new Bundle();
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAllAlbum()[i].getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAllAlbum()[i].getArtistId()).getName());
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAllAlbum()[i].getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID", db.mAlbumDao().getAllAlbum()[i].getImage());
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            }
        }


    }
}





