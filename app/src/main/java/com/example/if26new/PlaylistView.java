package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlaylistView extends AppCompatActivity {


    private TextView songName;
    private TextView artistName;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);

        mImageView = findViewById(R.id.playlistImageInPlaylistView);
        mImageView.setImageResource(R.drawable.hazy1);
        mImageView.setAdjustViewBounds(false);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        linearLayout = findViewById(R.id.linearForPlaylistView);
        ViewGroup.MarginLayoutParams paramsSingle = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsSingle.setMargins(50,25,0,0);
        ViewGroup.MarginLayoutParams paramsArtist = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsArtist.setMargins(50,0,0,25);
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(this);
            dynamique.setOrientation(LinearLayout.VERTICAL);
            artistName = new TextView(this);
            songName = new TextView(this);
            songName.setText("Universer");
            songName.setTextColor(Color.WHITE);
            songName.setTextSize(20);
            songName.setSingleLine(true);
            artistName.setText("Hazy - centrifuge");
            artistName.setTextColor(Color.WHITE);
            artistName.setTextSize(10);

            artistName.setSingleLine(true);
            dynamique.addView(songName,paramsSingle);
            dynamique.addView(artistName,paramsArtist);
            songName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToListeningViewOfTheSong();
                }
            });
            artistName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToListeningViewOfTheSong();
                }
            });
            linearLayout.addView(dynamique);
        }
    }
    public void goToListeningViewOfTheSong(){
        Intent signInActivity = new Intent(PlaylistView.this, listening.class);
        startActivity(signInActivity);
    }
}
