package com.example.if26new;


import android.app.Activity;
import android.app.Dialog;
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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private TabLayout onglet;
    private ViewPager viewPagerForFragments;
    private ImageView mImageView;
    private FragmentActivity myContext;
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
    private ImageButton buttonWithImage;
    private String text;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_user, container, false);
        onglet=view.findViewById(R.id.tabLayout);
        viewPagerForFragments=view.findViewById(R.id.viewPagerUser);
        viewPagerForFragments.setOffscreenPageLimit(2);
        mImageView = view.findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.hazy1);
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.VERTICAL);

            buttonWithImage=new ImageButton(getActivity());
            dynamique.addView(buttonWithImage,paramsImageButton);
            android.view.ViewGroup.LayoutParams params = buttonWithImage.getLayoutParams();
            params.height=700;
            params.width=700;
            buttonWithImage.setLayoutParams(params);
            buttonWithImage.setBackground(null);
            if (i==2){
               // buttonWithImage.setImageResource(R.drawable.hazy_cosmos);
            }else{
                buttonWithImage.setImageResource(R.drawable.hazy1);
            }
            buttonWithImage.setAdjustViewBounds(true);
            buttonWithImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //dynamique.addView(imageButtonPlaylist,paramsImageButton);


            linearLayout.addView(dynamique);
            buttonWithImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseImageSong.dismiss();
                    registerPlaylist();
                }
            });
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
                if (text.matches("")) {
                    fieldPlaylist.setError("Please enter a playlist name");
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

        fieldPlaylist=newplaylistDialog.findViewById(R.id.fielForNewPlaylist);

        newplaylistDialog.show();
    }
    public void unShowPopup(){
        newplaylistDialog.dismiss();
    }
    public void registerPlaylist(){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Playlist created", Toast.LENGTH_SHORT);
        toast.show();
        //storage in data base the text value here
        //stockage the image name in the data base

    }
}
