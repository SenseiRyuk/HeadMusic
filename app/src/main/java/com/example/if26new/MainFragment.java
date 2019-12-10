package com.example.if26new;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;


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
    private ImageButton[] mImageButtonsPlaylists;
    private Button[] mImageButtonsConcerts;
    private ImageButton[] mImageButtonsAlbums;
    private LinearLayout mLinearLayoutsPlaylists;
    private LinearLayout mLinearLayoutsAlbums;
    private LinearLayout mLinearLayoutsConcerts;
    private TextView[] mTextViewsPlaylists;
    private TextView[] mTextViewsAlbums;
    private TextView [] mTextViewsConcertsTitles;
    private TextView mTextViewsConcerts;
    private TextView mTextViewTop1;
    private TextView mTextViewTop2;
    private TextView mTextViewTop3;
    private SaveMyMusicDatabase db;
    private ConstraintLayout backgroundPopUp;
    private Button validate;
    private Button cancel;
    private String text;
    private EditText fieldPlaylist;
    private boolean isAlreadyCreate;
    private ConstraintLayout backgroundImagePopUp;
    private LinearLayout linearLayout;
    private ImageButton[] buttonWithImage;
    private LinearLayout dynamique;
    private PlaylistModel playlistToInsert;
    private Dialog newplaylistDialog;
    private Dialog chooseImageSong;

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

        newplaylistDialog=new Dialog(getActivity());
        chooseImageSong=new Dialog(getActivity());
        buttonWithImage=new ImageButton[18];

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 40, 10, 40);
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


        initPlaylist();

        mTextViewsConcertsTitles=new TextView[db.mConcertDao().getAllConcert().length];
        for(int i=0;i<db.mConcertDao().getAllConcert().length;i++) {
            //PARTIE CONCERTS
            mLinearLayoutsConcerts = new LinearLayout(getActivity());
            mLinearLayout2.addView(mLinearLayoutsConcerts);
            mLinearLayoutsConcerts.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsConcerts[i] = new Button(getActivity());
            mLinearLayoutsConcerts.addView(mImageButtonsConcerts[i]);

            mImageButtonsConcerts[i].setBackgroundResource(db.mConcertDao().getAllConcert()[i].getImage());
            android.view.ViewGroup.LayoutParams params1 = mImageButtonsConcerts[i].getLayoutParams();
            params1.height = 440;
            params1.width = 440;
            mImageButtonsConcerts[i].setLayoutParams(params1);
            mImageButtonsConcerts[i].setOnClickListener(this);
            mTextViewsConcertsTitles[i] = new TextView(getActivity());
            mTextViewsConcertsTitles[i].setText(db.mArtistDao().getArtistFromId(db.mConcertDao().getAllConcert()[i].getArtistId()).getName() + " -\n " + db.mConcertDao().getAllConcert()[i].getLocationCity());
            mTextViewsConcertsTitles[i].setTextColor(Color.WHITE);
            mTextViewsConcertsTitles[i].setOnClickListener(this);
            mTextViewsConcertsTitles[i].setGravity(Gravity.CENTER_HORIZONTAL);
            mLinearLayoutsConcerts.addView(mTextViewsConcertsTitles[i]);
            mLinearLayoutsConcerts.setLayoutParams(lp);

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
                    bundle.putString("FRAGMENT_NAME","MainFragment");
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
                    bundle.putString("FRAGMENT_NAME","MainFragment");
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
                    bundle.putString("FRAGMENT_NAME","MainFragment");
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
                bundle.putString("FRAGMENT_NAME","MainFragment");
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
                bundle.putString("FRAGMENT_NAME","MainFragment");
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            } else if(v.equals(mTextViewsAlbums[i])){
                Bundle bundle = new Bundle();
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAllAlbum()[i].getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAllAlbum()[i].getArtistId()).getName());
                bundle.putString("ALBUM_NAME",db.mAlbumDao().getAllAlbum()[i].getTitleAlbum());
                bundle.putInt("ALBUM_IMAGE_ID", db.mAlbumDao().getAllAlbum()[i].getImage());
                bundle.putString("FRAGMENT_NAME","MainFragment");
                Intent albumActivity = new Intent(getActivity(), Album_view.class);
                albumActivity.putExtras(bundle);
                startActivity(albumActivity);
            }
        }
        for (int i=0;i<db.mPlaylistDao().getPlaylistFromUser(db.getActualUser()).length+1;i++){
            if (v.equals(mImageButtonsPlaylists[i])){
                if(mImageButtonsPlaylists[i].getTag().equals(R.drawable.add_to_playlist_icon)){
                    showPopup();
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putString("PLAYLIST_NAME",mTextViewsPlaylists[i].getText().toString());
                    bundle.putInt("PLAYLIST_IMAGE_ID",(Integer) mImageButtonsPlaylists[i].getTag());
                    bundle.putString("FRAGMENT_NAME","MainFragment");
                    Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                    playListActivity.putExtras(bundle);
                    startActivity(playListActivity);
                }
            }else if (v.equals(mTextViewsPlaylists[i])){
                if(mTextViewsPlaylists[i].getText().toString().equals("Create a new Playlist")){
                    showPopup();
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putString("PLAYLIST_NAME",mTextViewsPlaylists[i].getText().toString());
                    bundle.putInt("PLAYLIST_IMAGE_ID",(Integer) mImageButtonsPlaylists[i].getTag());
                    bundle.putString("FRAGMENT_NAME","MainFragment");
                    Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                    playListActivity.putExtras(bundle);
                    startActivity(playListActivity);
                }
            }
        }
        for (int i=0;i<18;i++){
            if (v.equals(buttonWithImage[i])) {
                chooseImageSong.dismiss();
                registerPlaylist((Integer) buttonWithImage[i].getTag());
            }
        }

    }
    public void registerPlaylist(int tag){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Playlist created", Toast.LENGTH_SHORT);
        toast.show();
        playlistToInsert = new PlaylistModel(db.getActualUser(), text, tag);
        db.mPlaylistDao().insertPlaylist(playlistToInsert);
        initPlaylist();
    }
    public void showPopup(){
        newplaylistDialog.setContentView(R.layout.add_playlist_pop_up);

        backgroundPopUp=newplaylistDialog.findViewById(R.id.constrainAddPlaylist);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        backgroundPopUp.setBackground(gd);

        validate=newplaylistDialog.findViewById(R.id.validateNewPlaylist);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = fieldPlaylist.getText().toString();
                isAlreadyCreate=false;
                PlaylistModel[] allPlaylist=db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
                for (int i=0;i<allPlaylist.length;i++){
                    if (text.equals(allPlaylist[i].getTitles())){
                        isAlreadyCreate=true;
                    }
                }
                if (text.matches("")) {
                    fieldPlaylist.setError("Please enter a playlist name");
                }else if (isAlreadyCreate==true){
                    fieldPlaylist.setError("This Playlist already exist, please pick an other name");
                }else{
                    unShowPopup();
                    showPopupImage();
                }
            }
        });
        cancel=newplaylistDialog.findViewById(R.id.cancelNewPlaylist);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unShowPopup();
            }
        });
        validate.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),validate.getText().toString()));
        cancel.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),cancel.getText().toString()));
        fieldPlaylist=newplaylistDialog.findViewById(R.id.fielForNewPlaylist);
        newplaylistDialog.show();
    }
    public void unShowPopup(){
        newplaylistDialog.dismiss();
    }
    public void showPopupImage(){
        chooseImageSong.setContentView(R.layout.choose_image_playlist_pop_up);
        backgroundImagePopUp=chooseImageSong.findViewById(R.id.constraintPopUpImage);
        UserModel currentUser=db.userDao().getUserFromId(db.getActualUser());
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {currentUser.getStartColorGradient(),currentUser.getEndColorGradient()});
        gd.setCornerRadius(0f);
        backgroundImagePopUp.setBackground(gd);

        linearLayout=chooseImageSong.findViewById(R.id.linearForPopUp);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(0,25,0,25);
        for (int i = 0; i < 18; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.VERTICAL);
            buttonWithImage[i]=new ImageButton(getActivity());
            dynamique.addView(buttonWithImage[i],paramsImageButton);
            android.view.ViewGroup.LayoutParams params = buttonWithImage[i].getLayoutParams();
            params.height=700;
            params.width=700;
            buttonWithImage[i].setLayoutParams(params);
            buttonWithImage[i].setBackground(null);
            switch (i){
                case 0:
                    buttonWithImage[i].setImageResource(R.drawable.rap);
                    buttonWithImage[i].setTag(R.drawable.rap);
                    break;
                case 1:
                    buttonWithImage[i].setImageResource(R.drawable.rap2);
                    buttonWithImage[i].setTag(R.drawable.rap2);
                    break;
                case 2:
                    buttonWithImage[i].setImageResource(R.drawable.rap3);
                    buttonWithImage[i].setTag(R.drawable.rap3);
                    break;
                case 3:
                    buttonWithImage[i].setImageResource(R.drawable.rap4);
                    buttonWithImage[i].setTag(R.drawable.rap4);
                    break;
                case 4:
                    buttonWithImage[i].setImageResource(R.drawable.rock);
                    buttonWithImage[i].setTag(R.drawable.rock);
                    break;
                case 5:
                    buttonWithImage[i].setImageResource(R.drawable.rock2);
                    buttonWithImage[i].setTag(R.drawable.rock2);
                    break;
                case 6:
                    buttonWithImage[i].setImageResource(R.drawable.rock3);
                    buttonWithImage[i].setTag(R.drawable.rock3);
                    break;
                case 7:
                    buttonWithImage[i].setImageResource(R.drawable.rock4);
                    buttonWithImage[i].setTag(R.drawable.rock4);
                    break;
                case 8:
                    buttonWithImage[i].setImageResource(R.drawable.rock5);
                    buttonWithImage[i].setTag(R.drawable.rock5);
                    break;
                case 9:
                    buttonWithImage[i].setImageResource(R.drawable.reggae);
                    buttonWithImage[i].setTag(R.drawable.reggae);
                    break;
                case 10:
                    buttonWithImage[i].setImageResource(R.drawable.reggae2);
                    buttonWithImage[i].setTag(R.drawable.reggae2);
                    break;
                case 11:
                    buttonWithImage[i].setImageResource(R.drawable.jazz);
                    buttonWithImage[i].setTag(R.drawable.jazz);
                    break;
                case 12:
                    buttonWithImage[i].setImageResource(R.drawable.jazz2);
                    buttonWithImage[i].setTag(R.drawable.jazz2);
                    break;
                case 13:
                    buttonWithImage[i].setImageResource(R.drawable.jazz3);
                    buttonWithImage[i].setTag(R.drawable.jazz3);
                    break;
                case 14:
                    buttonWithImage[i].setImageResource(R.drawable.electro);
                    buttonWithImage[i].setTag(R.drawable.electro);
                    break;
                case 15:
                    buttonWithImage[i].setImageResource(R.drawable.electro2);
                    buttonWithImage[i].setTag(R.drawable.electro2);
                    break;
                case 16:
                    buttonWithImage[i].setImageResource(R.drawable.electro3);
                    buttonWithImage[i].setTag(R.drawable.electro3);
                    break;
                case 17:
                    buttonWithImage[i].setImageResource(R.drawable.pop);
                    buttonWithImage[i].setTag(R.drawable.pop);
                    break;
            }
            buttonWithImage[i].setAdjustViewBounds(true);
            buttonWithImage[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            buttonWithImage[i].setOnClickListener(this);
            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            linearLayout.addView(dynamique);
        }
        chooseImageSong.show();
    }
    public LayerDrawable roundbuttonSetting(int colorBackground, String Text){
        // Initialize two float arrays
        float[] outerRadii = new float[]{75,75,75,75,75,75,75,75};
        float[] innerRadii = new float[]{75,75,75,75,75,75,75,75};
        // Set the shape background
        ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null,
                innerRadii
        ));
        backgroundShape.getPaint().setColor(colorBackground); // background color

        // Initialize an array of drawables
        Drawable[] drawables = new Drawable[]{
                backgroundShape
        };
        Paint paint = new Paint();
        Canvas canvas = new Canvas();
        canvas.drawText(Text, 0, drawables[0].getMinimumHeight()/2, paint);
        drawables[0].draw(canvas);
        // Initialize a layer drawable object
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        return layerDrawable;
    }

    public void initPlaylist(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 40, 10, 40);
        int sizePlaylist = db.mPlaylistDao().getPlaylistFromUser(db.getActualUser()).length;
        PlaylistModel playlsit[]=db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
        mImageButtonsPlaylists= new ImageButton[sizePlaylist+1];
        mTextViewsPlaylists = new TextView[sizePlaylist+1];
        mLinearLayout1.removeAllViews();
        if(sizePlaylist==0){
            TextView textViewPlaylistEmpty = new TextView(getActivity());
            textViewPlaylistEmpty.setText("Vous ne possédez pas de playlist");
            textViewPlaylistEmpty.setTextColor(Color.WHITE);
            textViewPlaylistEmpty.setTextSize(12);
            textViewPlaylistEmpty.setGravity(Gravity.CENTER_HORIZONTAL);
            mLinearLayout1.addView(textViewPlaylistEmpty);
        }else {
            for (int i = 0; i < sizePlaylist+1; i++) {
                //PARTIE PLAYLIST
                mLinearLayoutsPlaylists = new LinearLayout(getActivity());
                mLinearLayout1.addView(mLinearLayoutsPlaylists);
                mLinearLayoutsPlaylists.setOrientation(LinearLayout.VERTICAL);
                mImageButtonsPlaylists[i] = new ImageButton(getActivity());
                mLinearLayoutsPlaylists.addView(mImageButtonsPlaylists[i]);
                mImageButtonsPlaylists[i].setBackground(null);
                mImageButtonsPlaylists[i].setBackgroundColor(Color.BLACK);
                if (i==sizePlaylist){
                    mImageButtonsPlaylists[i].setImageResource(R.drawable.add_to_playlist_icon);
                    mImageButtonsPlaylists[i].setTag(R.drawable.add_to_playlist_icon);
                }else{
                    mImageButtonsPlaylists[i].setImageResource(playlsit[i].getImage());
                    mImageButtonsPlaylists[i].setTag(playlsit[i].getImage());
                }
                mImageButtonsPlaylists[i].setAdjustViewBounds(true);
                mImageButtonsPlaylists[i].setOnClickListener(this);
                android.view.ViewGroup.LayoutParams params = mImageButtonsPlaylists[i].getLayoutParams();
                params.height = 450;
                params.width = 450;
                mImageButtonsPlaylists[i].setLayoutParams(params);
                mImageButtonsPlaylists[i].setScaleType(ImageView.ScaleType.FIT_CENTER);

                mTextViewsPlaylists[i] = new TextView(getActivity());
                if(i==sizePlaylist){
                    mTextViewsPlaylists[i].setText("Create a new Playlist");
                }else{
                    mTextViewsPlaylists[i].setText(playlsit[i].getTitles());
                }
                mTextViewsPlaylists[i].setTextColor(Color.WHITE);
                mTextViewsPlaylists[i].setOnClickListener(this);

                mLinearLayoutsPlaylists.addView(mTextViewsPlaylists[i]);
                mLinearLayoutsPlaylists.setLayoutParams(lp);
                mTextViewsPlaylists[i].setGravity(Gravity.CENTER_HORIZONTAL);
            }
        }
    }
}

