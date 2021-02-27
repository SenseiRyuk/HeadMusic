package com.example.if26new.Library.Tracks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.util.ArrayList;


public class TrackFragment extends Fragment {

    private View view;
    private int sizeTrack;
    private SaveMyMusicDatabase db;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_User_Track adapteur;
    private RecyclerView recyclerView;
    private SinglePlaylistModel[] allTracks;
    private PlaylistModel playlistToAddSong;
    private SearchView searchView;

    public static TrackFragment newInstance() {
        return (new TrackFragment());
    }

    public TrackFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_user_track, container, false);
        searchView=view.findViewById(R.id.SV_User_Track);
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
                    getAndDisplayAllTrackFromTheDataBase();
                }
                return false;
            }
        });
        setLengthTrackDataBase();
        setRecyclerView(view);
        getAndDisplayAllTrackFromTheDataBase();
        return view;
    }
    public void setLengthTrackDataBase(){
        db=SaveMyMusicDatabase.getInstance(getActivity());
        playlistToAddSong=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
        allTracks=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistToAddSong.getId());
        sizeTrack=allTracks.length;
    }
    public void setRecyclerView(View view){
        recyclerView=view.findViewById(R.id.recyclerViewTrackUser);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_User_Track(this.informationForAdapteur,getActivity());
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getAndDisplayAllTrackFromTheDataBase(){
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        for (int i=0; i<sizeTrack; i++){
            ArrayList<String> arrayListTrack=new ArrayList<>();
            arrayListTrack.add(allTracks[i].getSongName());
            arrayListTrack.add(allTracks[i].getArtistName());
            arrayListTrack.add("UserFragment");
            arrayListTrack.add(playlistToAddSong.getTitles());
            arrayListTrack.add(allTracks[i].getTrackDuration());
            arrayListTrack.add(allTracks[i].getSongID());
            informationForAdapteur.add(arrayListTrack);
            adapteur.notifyItemChanged(i);
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
