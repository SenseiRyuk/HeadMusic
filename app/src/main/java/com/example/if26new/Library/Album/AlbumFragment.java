package com.example.if26new.Library.Album;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Model.AlbumModelDeezer;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment{

    private int sizeAlbumDeezer;
//    private int sizeAlbumLocal;
//    private int sizeAlbumAll;
    private SearchView searchView;
    private SaveMyMusicDatabase db;
    private AlbumModelDeezer[] albumModelDeezers;
    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapter_User_Album adapteur;
    private RecyclerView recyclerView;
//    private AlbumModel[] allAlbum;

    public static AlbumFragment newInstance() {
        return (new AlbumFragment());
    }
    public AlbumFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_user_album, container, false);
        //here we will get all the playlist Name in the dataBase, hence we got the length
        searchView=view.findViewById(R.id.SV_User_Album);
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
                    setAlbumLocalAndDeezer();
                }
                return false;
            }
        });
        db=SaveMyMusicDatabase.getInstance(getActivity());
        setSize();
        setRecyclerView(view);
        setAlbumLocalAndDeezer();

        return view;
    }
    public void setSize(){
        //For Deezer
        albumModelDeezers=db.mAlbumDeezerDao().getAllAlbum();
        sizeAlbumDeezer=albumModelDeezers.length;
//        //For Local
//        LikeAlbumModel[] allLikeAlbum=db.mLikeAlbumDao().getLikeFromUser(db.getActualUser());
//        allAlbum=new AlbumModel[allLikeAlbum.length];
//        for (int i=0;i<allLikeAlbum.length;i++){
//            allAlbum[i]=db.mAlbumDao().getAlbumFromId(allLikeAlbum[i].getAlbumId());
//        }
//        sizeAlbumLocal=allAlbum.length;
//        sizeAlbumAll=sizeAlbumDeezer+sizeAlbumLocal;
    }
    public void setAlbumLocalAndDeezer() {
        adapteur.resetBitmap(sizeAlbumDeezer);
        informationForAdapteur.clear();
        adapteur.notifyDataSetChanged();
        for (int i = 0; i < sizeAlbumDeezer; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            arrayListArtist.add(albumModelDeezers[i].getTitleAlbum());
            arrayListArtist.add(albumModelDeezers[i].getAlbumImageDeezer());
            arrayListArtist.add(String.valueOf(albumModelDeezers[i].getId()));
            arrayListArtist.add(albumModelDeezers[i].getArtistName());
            arrayListArtist.add("UserFragment");
            arrayListArtist.add("0");
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }
    public void setRecyclerView(View view){
        recyclerView=view.findViewById(R.id.recyclerViewAlbumUser);
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapter_User_Album(this.informationForAdapteur,getActivity(),sizeAlbumDeezer);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
