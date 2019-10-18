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

import com.example.if26new.Model.PlaylistModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment implements View.OnClickListener {

    private ImageButton[] imageButtonPlaylist;
    private TextView[] playlistTitle;
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private View view;
    private Context context;
    private int idImagePlaylist;
    private int sizePlaylist;
    private SaveMyMusicDatabase db;

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
        getAndDisplayAllPlaylistFromTheDataBase();
        return view;
    }
    public void getAndDisplayAllPlaylistFromTheDataBase(){
        linearLayout = view.findViewById(R.id.linearForPlaylistUser);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsPlaylistName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsPlaylistName.setMargins(10,60,0,0);


        db=SaveMyMusicDatabase.getInstance(getActivity());
        PlaylistModel[] allPlaylist = db.mPlaylistDao().loadAllPlaylist();
        sizePlaylist=allPlaylist.length;
        playlistTitle=new TextView[sizePlaylist];
        imageButtonPlaylist=new ImageButton[sizePlaylist];

        for (int i=0; i<sizePlaylist; i++){
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonPlaylist[i]=new ImageButton(getActivity());
            dynamique.addView(imageButtonPlaylist[i],paramsImageButton);
            imageButtonPlaylist[i].setBackground(null);

            int id = allPlaylist[i].getImage();
            System.out.println("VALEUUUUUR" + id);
            imageButtonPlaylist[i].setImageResource(id);
            imageButtonPlaylist[i].setTag(id);
            android.view.ViewGroup.LayoutParams params = imageButtonPlaylist[i].getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonPlaylist[i].setLayoutParams(params);
            imageButtonPlaylist[i].setAdjustViewBounds(true);
            imageButtonPlaylist[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonPlaylist[i].requestLayout();
            imageButtonPlaylist[i].setOnClickListener(this);

            playlistTitle[i]=new TextView(getActivity());
            playlistTitle[i].setText(allPlaylist[i].getTitles());
            playlistTitle[i].setTextColor(Color.WHITE);
            playlistTitle[i].setTextSize(20);
            playlistTitle[i].setSingleLine(true);
            playlistTitle[i].setOnClickListener(this);

            dynamique.addView(playlistTitle[i],paramsPlaylistName);
            linearLayout.addView(dynamique);
        }

    }
    public void onClick(View v){
        for (int i=0;i<sizePlaylist;i++){
            if (v.equals(playlistTitle[i])){
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_NAME",playlistTitle[i].getText().toString());
                bundle.putInt("PLAYLIST_IMAGE_ID",(Integer) imageButtonPlaylist[i].getTag());
                Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }else if (v.equals(imageButtonPlaylist[i])){
                Bundle bundle=new Bundle();
                bundle.putString("PLAYLIST_NAME",playlistTitle[i].getText().toString());
                bundle.putInt("PLAYLIST_IMAGE_ID",(Integer) imageButtonPlaylist[i].getTag());
                Intent playListActivity = new Intent(getActivity(), PlaylistView.class);
                playListActivity.putExtras(bundle);
                startActivity(playListActivity);
            }
        }
    }
}
