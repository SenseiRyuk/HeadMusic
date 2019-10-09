package com.example.if26new;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    private TableLayout table;
    private TableRow row;
    private ImageButton imageButtonPlaylist;
    private  TextView playlistTitle;
    public static PlaylistFragment newInstance() {
        return (new PlaylistFragment());
    }


    public PlaylistFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_playlist, container, false);

        //Retrieve all the playlist in data base
        table = view.findViewById(R.id.playlistUser);
        TableLayout.LayoutParams tl=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT,1f);

        for (int i=0;i<=7;i++){
            row=new TableRow(getActivity());
            row.setGravity(Gravity.CENTER);
            imageButtonPlaylist=new ImageButton(getActivity());
            imageButtonPlaylist.setBackground(null);
            imageButtonPlaylist.setImageResource(R.drawable.iconforplaylist);
            playlistTitle=new TextView(getActivity());
            playlistTitle.setText("    Playlist n°" +(i+1) + " ("+(i+20)+" songs)");
            playlistTitle.setTextColor(Color.WHITE);
            playlistTitle.setTextSize(20);
            playlistTitle.setSingleLine(true);
            playlistTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(imageButtonPlaylist);
            row.addView(playlistTitle);
            table.addView(row,tl);
            //table.addView(myText,tl);
            //IT'S HERE WE WILL SET THE ON CLICK ON THE BUTTON AND THE TEXT TO GO TO THE VIEW PLAYLIST
            playlistTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInActivity = new Intent(getActivity(), PlaylistView.class);
                    startActivity(signInActivity);
                }
            });
        }
        return view;

    }

}
