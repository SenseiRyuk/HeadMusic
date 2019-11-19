package com.example.if26new;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.OnConflictStrategy;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private TabLayout onglet;
    private ViewPager viewPagerForFragments;
    private ImageView mImageView;
    private PageAdaptaterForUser pageAdaptaterForUser;
    private Button createNewPlaylist;
    private Dialog newplaylistDialog;
    private Dialog chooseImageSong;
    private Button validate;
    private Button cancel;
    private EditText fieldPlaylist;
    private LinearLayout linearLayout;
    private View view;
    private LinearLayout dynamique;
    private ImageButton[] buttonWithImage;
    private String text;
    private SaveMyMusicDatabase db;
    private PlaylistModel playlistToInsert;
    private boolean isAlreadyCreate;
    private int contextChoose=0;
    private UserModel user;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_user, container, false);
        onglet=view.findViewById(R.id.tabLayout);
        db=SaveMyMusicDatabase.getInstance(getActivity());
        viewPagerForFragments=view.findViewById(R.id.viewPagerUser);
        mImageView = view.findViewById(R.id.imageView);
        viewPagerForFragments.setOffscreenPageLimit(2);
        user=db.userDao().getUserFromId(db.getActualUser());
        if(user.getAvatar()==R.drawable.default_avatar){
        }
        mImageView.setImageResource(user.getAvatar());
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextChoose=1;
                showPopupImage();
            }
        });
        //FragmentManager fragmentManager=myContext.getSupportFragmentManager();
        viewPagerForFragments.setAdapter(pageAdaptaterForUser);
        //viewPagerForFragments.addOnPageChangeListener(view);
        onglet.setupWithViewPager(viewPagerForFragments);
        //Design purpose. Tabs have the same width
        onglet.setTabMode(TabLayout.MODE_FIXED);
        newplaylistDialog=new Dialog(getActivity());
        chooseImageSong=new Dialog(getActivity());
        createNewPlaylist=view.findViewById(R.id.createNewPlaylist);
        createNewPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        //myContext=(FragmentActivity) activity;
        pageAdaptaterForUser = new PageAdaptaterForUser(getChildFragmentManager());
        super.onAttach(activity);
    }
    public void showPopupImage(){
        chooseImageSong.setContentView(R.layout.choose_image_playlist_pop_up);

        linearLayout=chooseImageSong.findViewById(R.id.linearForPopUp);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(0,25,0,25);
        buttonWithImage=new ImageButton[18];
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
    public void showPopup(){

        newplaylistDialog.setContentView(R.layout.add_playlist_pop_up);
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
                    contextChoose=2;
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

        fieldPlaylist=newplaylistDialog.findViewById(R.id.fielForNewPlaylist);
        newplaylistDialog.show();
    }
    public void unShowPopup(){
        newplaylistDialog.dismiss();
    }
    public void registerPlaylist(int tag){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Playlist created", Toast.LENGTH_SHORT);
        toast.show();
        playlistToInsert = new PlaylistModel(db.getActualUser(), text, tag);
        db.mPlaylistDao().insertPlaylist(playlistToInsert);
        //Pour refresh la vue
        viewPagerForFragments.setAdapter(pageAdaptaterForUser);
        //storage in data base the text value here
        //stockage the image name in the data base
    }
    public void onClick(View v){
        for (int i=0;i<18;i++){
            if (v.equals(buttonWithImage[i])) {
                chooseImageSong.dismiss();
                if (contextChoose==1){
                    mImageView.setImageResource((Integer) buttonWithImage[i].getTag());
                    //user.setAvatar((Integer) buttonWithImage[i].getTag());
                    db.userDao().UpdateUser((Integer) buttonWithImage[i].getTag(),db.getActualUser());
                    mImageView.setAdjustViewBounds(true);
                    mImageView.setOnClickListener(this);
                    android.view.ViewGroup.LayoutParams params = mImageView.getLayoutParams();
                    params.height = 500;
                    params.width = 500;
                    mImageView.setLayoutParams(params);
                    mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }else if (contextChoose==2){
                    registerPlaylist((Integer) buttonWithImage[i].getTag());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pageAdaptaterForUser.notifyDataSetChanged();
    }
}
