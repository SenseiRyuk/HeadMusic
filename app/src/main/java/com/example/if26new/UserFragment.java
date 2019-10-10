package com.example.if26new;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    private FragmentActivity myContext;
    private PageAdaptaterForUser pageAdaptaterForUser;
    private Button createNewPlaylist;
    private Dialog newplaylistDialog;
    private Button validate;
    private Button cancel;
    private EditText fieldPlaylist;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user, container, false);
        onglet=view.findViewById(R.id.tabLayout);
        viewPagerForFragments=view.findViewById(R.id.viewPagerUser);
        viewPagerForFragments.setOffscreenPageLimit(2);
        //FragmentManager fragmentManager=myContext.getSupportFragmentManager();
        viewPagerForFragments.setAdapter(pageAdaptaterForUser);
        //viewPagerForFragments.addOnPageChangeListener(view);
        onglet.setupWithViewPager(viewPagerForFragments);
        //Design purpose. Tabs have the same width
        onglet.setTabMode(TabLayout.MODE_FIXED);


        newplaylistDialog=new Dialog(getActivity());
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
    public void showPopup(){
        newplaylistDialog.setContentView(R.layout.add_playlist_pop_up);

        validate=newplaylistDialog.findViewById(R.id.validateNewPlaylist);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPlaylist();
                unShowPopup();
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
    }
}
