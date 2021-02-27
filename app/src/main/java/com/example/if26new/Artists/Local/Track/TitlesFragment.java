package com.example.if26new.Artists.Local.Track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.util.ArrayList;

public class TitlesFragment extends Fragment {
    private SaveMyMusicDatabase db;
    private String nameArtist;
    private String fragmentName;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private SearchViewAdapterForTrackLocal adapteur;
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
        view = inflater.inflate(R.layout.fragment_artist_track, container, false);
        retrieveBundle();
        sizeMusicArtist=db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(nameArtist).getId()).length;

        recyclerView=view.findViewById(R.id.recyclerViewAlbumTrack);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new SearchViewAdapterForTrackLocal(this.informationForAdapteur,getActivity(),sizeMusicArtist);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setView();
        return view;
    }
    public void retrieveBundle(){
        db=SaveMyMusicDatabase.getInstance(getActivity());
        Bundle args = getArguments();
        fragmentName=args.getString("FRAGMENT", "null");
        nameArtist=getActivity().getIntent().getStringExtra("ARTIST_NAME");
    }
    public void setView(){
        for (int i = 0; i <sizeMusicArtist; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(nameArtist).getId())[i].getTitleSingle());
            arrayListArtist.add(db.mArtistDao().getArtistFromName(nameArtist).getName());
            arrayListArtist.add(String.valueOf(db.mSingleDao().getSingleFromName(db.mSingleDao().getSingleFromArtist(db.mArtistDao().getArtistFromName(nameArtist).getId())[i].getTitleSingle()).getAlbumId()));
            arrayListArtist.add(fragmentName);
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }
}
