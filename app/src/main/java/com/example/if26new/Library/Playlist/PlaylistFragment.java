package com.example.if26new.Library.Playlist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    private View view;
    private int sizePlaylist;
    private SaveMyMusicDatabase db;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_User_Playlist adapteur;
    private RecyclerView recyclerView;
    private PlaylistModel[] allPlaylist;
    private SearchView searchView;

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
        view=inflater.inflate(R.layout.fragment_user_playlist, container, false);
        searchView=view.findViewById(R.id.SV_User_Playlist);
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
                    getAndDisplayAllPlaylistFromTheDataBase();
                }
                return false;
            }
        });
        setLengthPlaylistDataBase();
        setRecyclerView(view);

        getAndDisplayAllPlaylistFromTheDataBase();
        return view;
    }
    public void setLengthPlaylistDataBase(){
        db=SaveMyMusicDatabase.getInstance(getActivity());
        allPlaylist = db.mPlaylistDao().getPlaylistFromUser(db.getActualUser());
        sizePlaylist=allPlaylist.length;
    }
    public void setRecyclerView(View view){
        recyclerView=view.findViewById(R.id.recyclerViewPlaylistUser);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_User_Playlist(this.informationForAdapteur,getActivity(),50);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getAndDisplayAllPlaylistFromTheDataBase(){
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        ArrayList<String> arrayListArtist2=new ArrayList<>();
        arrayListArtist2.add("Create new playlist");
        arrayListArtist2.add("UserFragment");
        informationForAdapteur.add(arrayListArtist2);
        adapteur.notifyItemChanged(0);
        for (int i=0; i<sizePlaylist; i++){
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(allPlaylist[i].getTitles());
            arrayListArtist.add(String.valueOf(allPlaylist[i].getImage()));
            arrayListArtist.add("UserFragment");
            arrayListArtist.add(String.valueOf(allPlaylist[i].getId()));
            arrayListArtist.add(String.valueOf(db.mSinglePlaylistDao().getSinglesFromPlaylist(allPlaylist[i].getId()).length));
            arrayListArtist.add(allPlaylist[i].getImageDeezer());
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i+1);
        }
    }
    public void sortByName(String query){
        int count=0;
        ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>(informationForAdapteur);
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        for (int i=0;i<newList.size();i++){
            if (newList.get(i).get(0).toLowerCase().contains(query)){
                informationForAdapteur.add(newList.get(i));
                count++;
            }
        }
        adapteur.notifyDataSetChanged();
    }
}
