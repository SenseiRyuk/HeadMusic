package com.example.if26new.Artists.Deezer.Track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.R;

import java.util.ArrayList;

public class TitlesFragmentForDeezer extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] songName;
    private TextView[] artistName;
    private String nameArtist;
    private String fragmentName;
    private String[] topMusic;
    private String[] urlTop5Music;
    private String[] albumsImagesOfTheArtist;
    private String[] albumsIDOfTheArtist;
    private String[] albumsNamesOfTheArtist;
    private String[] topMusicID;
    private String[] topMusicDuration;
    private String artistID;

    private int sizeMusicArtist;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Artist_Track adapteur;


    public TitlesFragmentForDeezer() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_artist_track, container, false);
        this.retrieveBundle();
        recyclerView=view.findViewById(R.id.recyclerViewAlbumTrack);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_Artist_Track(this.informationForAdapteur,getActivity());
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.setView();
        return view;
    }
    public void retrieveBundle(){
        Bundle args = getArguments();
        nameArtist=args.getString("ARTIST_NAME");
        artistID=args.getString("ARTIST_ID");
        topMusic=args.getStringArray("TOP_MUSIC");
        urlTop5Music=args.getStringArray("URL_TOP_MUSIC");
        topMusicID=args.getStringArray("TOP_MUSIC_ID");
        fragmentName=args.getString("FRAGMENT", "null");
        topMusicDuration=args.getStringArray("TOP_MUSIC_DURATION");

        sizeMusicArtist=topMusic.length;
    }
    public void setView(){
        for (int i = 0; i < sizeMusicArtist; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(nameArtist);
            arrayListArtist.add(topMusic[i]);
            arrayListArtist.add(topMusicID[i]);
            arrayListArtist.add(fragmentName);
            if (Integer.valueOf(topMusicDuration[i])%60<10){
                topMusicDuration[i]=Integer.valueOf(topMusicDuration[i])/60+":0"+Integer.valueOf(topMusicDuration[i])%60;
            }else{
                topMusicDuration[i]=Integer.valueOf(topMusicDuration[i])/60+":"+Integer.valueOf(topMusicDuration[i])%60;
            }
            arrayListArtist.add(topMusicDuration[i]);
            arrayListArtist.add("Call_From_Artist_Activity");
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }

    public void onClick(final View v){

    }


}