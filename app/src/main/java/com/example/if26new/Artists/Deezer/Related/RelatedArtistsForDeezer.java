package com.example.if26new.Artists.Deezer.Related;

import android.graphics.Bitmap;
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

import java.util.ArrayList;

public class RelatedArtistsForDeezer extends Fragment{
    private String fragmentName;
    private String[] artistsIDsRelated;
    private String[] artistsImagesRelated;
    private String[] artistsNamesRelated;

    private Bitmap mIcon_val;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_Artist_Related adapteur;

    public RelatedArtistsForDeezer() {
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
        this.adapteur=new RV_Adapter_Artist_Related(this.informationForAdapteur,getActivity(),artistsNamesRelated.length);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.setView(view);

        return view;
    }
    public void retrieveBundle(){
        Bundle args = getArguments();
        artistsIDsRelated=args.getStringArray("Artists_IDs");
        artistsNamesRelated=args.getStringArray("Artists_Names");
        artistsImagesRelated=args.getStringArray("Artists_Images");
        fragmentName=args.getString("FRAGMENT", "null");

    }
    public void setView(View view){
        for (int i = 0; i < artistsIDsRelated.length; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(artistsIDsRelated[i]);
            arrayListArtist.add(artistsNamesRelated[i]);
            arrayListArtist.add(artistsImagesRelated[i]);
            arrayListArtist.add(fragmentName);
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }

}