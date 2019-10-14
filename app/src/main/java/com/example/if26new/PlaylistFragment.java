package com.example.if26new;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class PlaylistFragment extends Fragment implements View.OnClickListener {

    private ImageButton[] imageButtonPlaylist;
    private TextView[] playlistTitle;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private View view;
    private int counter;
    private Context context;
    private int idImagePlaylist;
    private int sizePlaylist;

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
        view=inflater.inflate(R.layout.fragment_playlist, container, false);
        counter=0;
        getAndDisplayAllPlaylistFromTheDataBase();
        return view;
    }
    public void getAndDisplayAllPlaylistFromTheDataBase(){

        linearLayout = view.findViewById(R.id.linearForPlaylistUser);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsPlaylistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsPlaylistName.setMargins(10,60,0,0);

        //here we will get all the playlist Name in the dataBase, hence we got the length
        sizePlaylist=10;
        playlistTitle=new TextView[sizePlaylist];

        //here we retrieve all the imageName for each Playlist
        imageButtonPlaylist=new ImageButton[sizePlaylist];

        for (counter=0; counter<10; counter++){
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonPlaylist[counter]=new ImageButton(getActivity());
            dynamique.addView(imageButtonPlaylist[counter],paramsImageButton);
            imageButtonPlaylist[counter].setBackground(null);
            context=imageButtonPlaylist[counter].getContext();
            //Ici au lieu de rap on ira chercher le nom de l'image qui se trouvera dans notre base de donnée
            idImagePlaylist=context.getResources().getIdentifier("rap","drawable",context.getPackageName());
            imageButtonPlaylist[counter].setImageResource(idImagePlaylist);
            android.view.ViewGroup.LayoutParams params = imageButtonPlaylist[counter].getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonPlaylist[counter].setLayoutParams(params);
            imageButtonPlaylist[counter].setAdjustViewBounds(true);
            imageButtonPlaylist[counter].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonPlaylist[counter].requestLayout();

            playlistTitle[counter]=new TextView(getActivity());
            playlistTitle[counter].setText("Playlist "+counter);
            playlistTitle[counter].setTextColor(Color.WHITE);
            playlistTitle[counter].setTextSize(20);
            playlistTitle[counter].setSingleLine(true);
            playlistTitle[counter].setOnClickListener(this);

            dynamique.addView(playlistTitle[counter],paramsPlaylistName);
            linearLayout.addView(dynamique);
        }

    }
    public void onClick(View v){
        for (int i=0;i<sizePlaylist;i++){
            if (v.equals(playlistTitle[i])){
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_NAME",playlistTitle[i].getText().toString());
                Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonPlaylist[i])){
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_NAME",playlistTitle[i].getText().toString());
                Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
}
