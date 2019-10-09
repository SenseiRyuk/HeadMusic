package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlaylistView extends AppCompatActivity {

    private TableLayout table;
    private TableRow row;
    private TextView songName;
    private TextView artistName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);
        //Retrieve all the playlist in data base
        table= findViewById(R.id.tablelayoutForPlaylistView);
        TableLayout.LayoutParams tl=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT,1f);
        for (int i=0;i<=12;i++){
            row=new TableRow(this);
            row.setGravity(Gravity.LEFT);
            artistName=new TextView(this);
            songName = new TextView(this);
            songName.setText("    Universer");
            songName.setTextColor(Color.WHITE);
            songName.setTextSize(20);
            songName.setGravity(Gravity.CENTER_HORIZONTAL);
            songName.setSingleLine(true);
            row.addView(songName);

            artistName.setText("    Hazy - centrifuge");
            artistName.setTextColor(Color.WHITE);
            artistName.setTextSize(10);
            artistName.setGravity(Gravity.CENTER_HORIZONTAL);
            artistName.setSingleLine(true);
            row.addView(artistName);
            table.addView(row,tl);
            songName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInActivity = new Intent(PlaylistView.this, listening.class);
                    startActivity(signInActivity);
                }
            });
        }
    }
}
