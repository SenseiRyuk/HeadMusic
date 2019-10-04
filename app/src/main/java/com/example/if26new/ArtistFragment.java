package com.example.if26new;


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
public class ArtistFragment extends Fragment {
    public static ArtistFragment newInstance() {
        return (new ArtistFragment());
    }

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_artist, container, false);

        //Retrieve all the playlist in data base
        TableLayout table = view.findViewById(R.id.artistUser);
        TableLayout.LayoutParams tl=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow row;
        for (int i=0;i<=12;i++){
            row=new TableRow(getActivity());
            row.setGravity(Gravity.CENTER);
            ImageButton myButton= new ImageButton(getActivity());
            myButton.setBackground(null);
            myButton.setImageResource(R.drawable.iconforplaylist);
            TextView myText = new TextView(getActivity());
            //Set the TEXT OF THE PLAYLIST
            myText.setTextColor(Color.parseColor("#1C376E"));
            myText.setText("    Artist n°" +(i+1) + " ("+(i+20)+" songs)");
            myText.setTextSize(20);
            myText.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(myButton);
            row.addView(myText);
            table.addView(row,tl);
            //table.addView(myText,tl);
            //IT'S HERE WE WILL SET THE ON CLICK ON THE BUTTON AND THE TEXT TO GO TO THE VIEW PLAYLIST
            /*myText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Mucic add in the playlist Playlist n° ", Toast.LENGTH_SHORT);
                    toast.show();
                    playlistDialog.dismiss();
                }
            });*/
        }
        return view;
    }

}
