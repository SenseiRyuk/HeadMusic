package com.example.if26new.Library.Artist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Model.ArtistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {
    public static ArtistFragment newInstance() {
        return (new ArtistFragment());
    }
    private int sizeArtists;
    private SaveMyMusicDatabase db;
    private View view;
    private ArtistModel[] allArtist;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_User_Artist adapteur;
    private RecyclerView recyclerView;
    private SearchView searchView;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_user_artist, container, false);
        searchView=view.findViewById(R.id.SV_User_Artist);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sortByName(query.toLowerCase());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sortByName(newText.toLowerCase());
                if(newText.equals("")){
                    getAndDisplayAllArtistsFromTheDataBase();
                }
                return false;
            }
        });
        //Retrieve all the playlist in data base
        setSizeArtist();
        setRecyclerView(view);
        getAndDisplayAllArtistsFromTheDataBase();

        return view;
    }
    public void getAndDisplayAllArtistsFromTheDataBase(){
        adapteur.resetBitmap(sizeArtists);
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        for (int i = 0; i <sizeArtists; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            if (allArtist[i].getDeezer()) {
                arrayListArtist.add("DEEZER");
                arrayListArtist.add(allArtist[i].getName());
                arrayListArtist.add(allArtist[i].getImageDeezer());
                arrayListArtist.add(allArtist[i].getDeezerID());
                arrayListArtist.add("UserFragment");
                arrayListArtist.add(allArtist[i].getNumberOfFans());
            }
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }
    public void setRecyclerView(View view){
        recyclerView=view.findViewById(R.id.recyclerViewArtistUser);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_User_Artist(this.informationForAdapteur,getActivity(),sizeArtists);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void setSizeArtist(){
        db=SaveMyMusicDatabase.getInstance(getActivity());
        allArtist=db.mArtistDao().getLikedArtist(true);
        sizeArtists=allArtist.length;
    }

    public void sortByName(String query){
        int count=0;
        ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>(informationForAdapteur);
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        for (int i=0;i<newList.size();i++){
            if (newList.get(i).get(1).toLowerCase().contains(query)){
                informationForAdapteur.add(newList.get(i));
                count++;
            }
        }
        adapteur.resetBitmap(count);
        adapteur.notifyDataSetChanged();
    }
}
