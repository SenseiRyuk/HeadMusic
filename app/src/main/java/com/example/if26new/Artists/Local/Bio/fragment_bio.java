package com.example.if26new.Artists.Local.Bio;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class fragment_bio extends Fragment {
    TextView mTextView;
    private SaveMyMusicDatabase db;
    private int bio;
    private String artistName;
    public fragment_bio() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        db=SaveMyMusicDatabase.getInstance(getActivity());
        artistName=getActivity().getIntent().getStringExtra("ARTIST_NAME");
        getActivity().getIntent().getIntExtra("ARTIST_BIO",0);
        view=inflater.inflate(R.layout.fragment_bio, container, false);
        mTextView = view.findViewById(R.id.textBioID);
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        bio=db.mArtistDao().getArtistFromName(artistName).getBio();
        //Read The TXT File for the lyrics ....
        //Name IN ROW IN minuscule
        InputStream inputStream = getResources().openRawResource(bio);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mTextView.setText(byteArrayOutputStream.toString());
        return view;
    }
}

