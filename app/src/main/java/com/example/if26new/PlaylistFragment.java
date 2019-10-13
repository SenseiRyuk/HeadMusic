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
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    private ImageButton imageButtonPlaylist;
    private TextView playlistTitle;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;

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

        linearLayout = view.findViewById(R.id.linearForPlaylistUser);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsPlaylistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsPlaylistName.setMargins(10,60,0,0);
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonPlaylist=new ImageButton(getActivity());
            dynamique.addView(imageButtonPlaylist,paramsImageButton);
            imageButtonPlaylist.setBackground(null);
            imageButtonPlaylist.setImageResource(R.drawable.hazy_cosmos);
            android.view.ViewGroup.LayoutParams params = imageButtonPlaylist.getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonPlaylist.setLayoutParams(params);
            imageButtonPlaylist.setAdjustViewBounds(true);
            imageButtonPlaylist.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonPlaylist.requestLayout();

            playlistTitle=new TextView(getActivity());
            playlistTitle.setText("Playlist");
            playlistTitle.setTextColor(Color.WHITE);
            playlistTitle.setTextSize(20);
            playlistTitle.setSingleLine(true);

            dynamique.addView(playlistTitle,paramsPlaylistName);
            linearLayout.addView(dynamique);
            /*imageButtonPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInActivity = new Intent(getActivity(), PlaylistView.class);
                    startActivity(signInActivity);
                }
            });*/
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
