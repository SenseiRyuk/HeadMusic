package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TitlesFragment extends Fragment {
    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView AlbumName;
    private ImageButton imageButtonAlbum;

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
        view = inflater.inflate(R.layout.fragment_titles, container, false);
        linearLayout = view.findViewById(R.id.linearForFragmentTitleID);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40, 0, 0, 0);
        ViewGroup.MarginLayoutParams paramsAlbumName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsAlbumName.setMargins(10, 60, 0, 0);
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonAlbum = new ImageButton(getActivity());
            dynamique.addView(imageButtonAlbum, paramsImageButton);
            imageButtonAlbum.setBackground(null);
            imageButtonAlbum.setImageResource(R.drawable.playlistadd);
            android.view.ViewGroup.LayoutParams params = imageButtonAlbum.getLayoutParams();
            params.height = 200;
            params.width = 200;
            imageButtonAlbum.setLayoutParams(params);
            imageButtonAlbum.setAdjustViewBounds(true);
            imageButtonAlbum.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonAlbum.requestLayout();

            AlbumName = new TextView(getActivity());
            AlbumName.setText("Single");
            AlbumName.setTextColor(Color.WHITE);
            AlbumName.setTextSize(20);
            AlbumName.setSingleLine(true);

            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            dynamique.addView(AlbumName, paramsAlbumName);
            linearLayout.addView(dynamique);
            /*imageButtonPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInActivity = new Intent(getActivity(), PlaylistView.class);
                    startActivity(signInActivity);
                }
            });*/
            AlbumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent listeningActivity = new Intent(getActivity(), listening.class);
                    startActivity(listeningActivity);
                }
            });
        }
        return view;
    }
}
