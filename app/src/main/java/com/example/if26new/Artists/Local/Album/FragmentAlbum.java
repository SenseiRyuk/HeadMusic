package com.example.if26new.Artists.Local.Album;

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

public class FragmentAlbum extends Fragment {
    private int sizeAlbums;
    private String artistName;
    private SaveMyMusicDatabase db;
    private int isCallFromArtistView;
    private String fragmentName;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private SearchViewAdapterForAlbumLocal adapteur;
    public FragmentAlbum() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_artist_album, container, false);
        retrieveBundle(view);

        recyclerView=view.findViewById(R.id.recyclerViewAlbumArtist);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new SearchViewAdapterForAlbumLocal(this.informationForAdapteur);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setAlbumView();

        return view;
    }
    public void retrieveBundle(View view){
        Bundle args = this.getArguments();
        db=SaveMyMusicDatabase.getInstance(getActivity());
        artistName=getActivity().getIntent().getStringExtra("ARTIST_NAME");
        isCallFromArtistView=Integer.valueOf(args.getString("ARTIST","0"));
        fragmentName=args.getString("FRAGMENT","null");
        sizeAlbums=db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId()).length;
    }
    public void setAlbumView(){
        if(sizeAlbums==0){
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add("Cet artiste ne possède pas d'album.");
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyDataSetChanged();
        }else {
            for (int i = 0; i < sizeAlbums; i++) {
                ArrayList<String> arrayListArtist=new ArrayList<>();
                arrayListArtist.add(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getTitleAlbum());
                arrayListArtist.add(String.valueOf(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getImage()));
                arrayListArtist.add(String.valueOf(db.mAlbumDao().getAlbumFromArtist(db.mArtistDao().getArtistFromName(artistName).getId())[i].getId()));
                arrayListArtist.add(artistName);
                arrayListArtist.add(String.valueOf(isCallFromArtistView));
                arrayListArtist.add(fragmentName);

                informationForAdapteur.add(arrayListArtist);
                adapteur.notifyItemChanged(i);
            }
        }
    }
}

