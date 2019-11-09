package com.example.if26new;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.if26new.Model.AlbumModel;
import com.example.if26new.Model.ArtistModel;
import com.example.if26new.Model.SearchViewAdapteur;
import com.example.if26new.Model.SingleModel;

import java.util.ArrayList;
import java.util.List;


public class SearchViewFragment extends Fragment {

    private SearchView searchView;
    private SingleModel[] allSingles;
    private ArtistModel[] allArtist;
    private AlbumModel[] allAlbum;
    private List<String> all;
    private SaveMyMusicDatabase db;
    private View v;
    private RecyclerView recyclerView;
    private SearchViewAdapteur adapteur;
    private List<String>newAllRequest;


    public SearchViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_search_view, container, false);
        recyclerView=v.findViewById(R.id.recyclerViewFragment);

        db=SaveMyMusicDatabase.getInstance(getContext());
        allSingles=db.mSingleDao().getAllSingles();
        allArtist=db.mArtistDao().getAllArtist();
        allAlbum=db.mAlbumDao().getAllAlbum();
        all=new ArrayList<>();
        searchView=v.findViewById(R.id.searchViewFragment);
        /*for (int i=0;i<allSingles.length;i++){
            all.add(allSingles[i].getTitleSingle() +" - Single");
            db.mSingleDao().get
        }*/
        for (int i=0;i<allArtist.length;i++){
            all.add(allArtist[i].getName()+" - Artist");
            SingleModel[] currentSingle=db.mSingleDao().getSingleFromArtist(allArtist[i].getId());
            for (int j=0;j<currentSingle.length;j++){
                all.add(currentSingle[j].getTitleSingle()+" - Single By " + allArtist[i].getName());
            }
        }
        for (int i=0;i<allAlbum.length;i++){
            all.add(allAlbum[i].getTitleAlbum()+" - Album");
        }
        java.util.Collections.sort(all);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newAllRequest=new ArrayList<>();
                for (int i=0;i<all.size();i++){
                    if ((newText.compareToIgnoreCase(all.get(i).substring(0,newText.length()))==0) && (newText.length()>=1)){
                        newAllRequest.add(all.get(i));
                    }
                    updateRecyclerView(newAllRequest);
                }
                return false;
            }
        });
        this.configureRecyclerView(); // - 4 Call during UI creation
        return v;
    }
    public void configureRecyclerView(){
        this.adapteur=new SearchViewAdapteur(all);
        this.recyclerView.setAdapter(adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void updateRecyclerView(List<String> newAll){
         this.adapteur=new SearchViewAdapteur(newAll);
        this.recyclerView.setAdapter(adapteur);
        //this.adapteur.notifyDataSetChanged();
    }
}
