package com.example.if26new.Artists.Deezer.Album;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentAlbumForDeezer extends Fragment /*implements View.OnClickListener */{

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView[] albumName;
    private int sizeAlbums;
    private String artistName;
    private int isCallFromArtistView;
    private String fragmentName;
    private String[] albumsNames;
    private String[] albumsImages;
    private String[] albumsID;
    private String[] albumsDates;
    private String artistImageID;
    private String artistID;

    private ArrayList<ArrayList<String>> informationForAdapteur;
    private RV_Adapteur_Artist_Album adapteur;

    private View view;
    private RecyclerView recyclerView;

    private long [][] dateInMilliseconds;

    public FragmentAlbumForDeezer() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_artist_album, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewAlbumArtist);
        retrieveBundle();
        this.informationForAdapteur=new ArrayList<ArrayList<String>>();
        this.adapteur=new RV_Adapteur_Artist_Album(this.informationForAdapteur,getActivity(),sizeAlbums);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapteur);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setAlbumsView();
        return view;
    }
    public void retrieveBundle(){
        Bundle args = this.getArguments();
        artistName=args.getString("ARTIST_NAME");
        artistImageID=args.getString("ARTIST_IMAGE_ID");
        artistID=args.getString("ARTIST_ID");
        fragmentName=args.getString("FRAGMENT");
        albumsNames=args.getStringArray("ALBUMS_NAMES");
        albumsImages=args.getStringArray("ALBUMS_IMAGES");
        albumsID=args.getStringArray("ALBUMS_ID");
        albumsDates=args.getStringArray("ALBUMS_DATES");
        getAlbumMillisecondDate();

        isCallFromArtistView=args.getInt("IS_ARTIST",0);
        sizeAlbums=albumsNames.length;
    }
    public void getAlbumMillisecondDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateInMilliseconds=new long[albumsDates.length][2];
        for (int i=0;i<albumsDates.length;i++){
            Date date = null;
            try {
                date = sdf.parse(albumsDates[i]);
                dateInMilliseconds[i][0] = date.getTime();
                dateInMilliseconds[i][1]=i;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        java.util.Arrays.sort(dateInMilliseconds, new java.util.Comparator<long[]>() {
            public int compare(final long[] entry1, final long[] entry2) {
                if(entry1[0]<entry2[0]){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }
    public void setAlbumsView(){
        for (int i = 0; i < dateInMilliseconds.length; i++) {
            ArrayList<String> arrayListArtist=new ArrayList<>();
            int indice=(int) dateInMilliseconds[i][1];
            arrayListArtist.add(albumsNames[indice]);
            arrayListArtist.add(albumsImages[indice]);
            arrayListArtist.add(albumsID[indice]);
            arrayListArtist.add(fragmentName);
            arrayListArtist.add(albumsDates[indice]);
            informationForAdapteur.add(arrayListArtist);
            adapteur.notifyItemChanged(i);
        }
    }

}