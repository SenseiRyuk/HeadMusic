package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TitlesFragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] songName;
    private TextView[] artistName;

    private int sizeMusicArtist;

    public TitlesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_titles, container, false);

        sizeMusicArtist=10;
        songName=new TextView[sizeMusicArtist];
        artistName=new TextView[sizeMusicArtist];

        linearLayout = view.findViewById(R.id.linearForFragmentTitleID);
        ViewGroup.MarginLayoutParams paramsSingle = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsSingle.setMargins(50,25,0,0);
        ViewGroup.MarginLayoutParams paramsArtist = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtist.setMargins(50,0,0,25);
        for (int i = 0; i <sizeMusicArtist; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.VERTICAL);
            artistName[i] = new TextView(getContext());
            songName[i] = new TextView(getContext());
            songName[i].setText("Royal Bacon");
            songName[i].setTextColor(Color.WHITE);
            songName[i].setTextSize(20);
            songName[i].setSingleLine(true);
            songName[i].setOnClickListener(this);
            artistName[i].setText("Vald - Ce monde est cruel");
            artistName[i].setTextColor(Color.WHITE);
            artistName[i].setTextSize(10);
            artistName[i].setSingleLine(true);
            artistName[i].setOnClickListener(this);
            dynamique.addView(songName[i],paramsSingle);
            dynamique.addView(artistName[i],paramsArtist);
            linearLayout.addView(dynamique);
        }
        return view;
    }
    public void onClick(View v){
        for (int i=0;i<sizeMusicArtist;i++){
            if (v.equals(songName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",songName[i].getText().toString());
                bundle.putString("ARTIST_NAME",artistName[i].getText().toString());
                Intent playListActivity = new Intent(getContext(), listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(artistName[i])){
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",songName[i].getText().toString());
                bundle.putString("ARTIST_NAME",artistName[i].getText().toString());
                Intent playListActivity = new Intent(getContext(), listening.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
}
