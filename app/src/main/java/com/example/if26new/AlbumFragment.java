package com.example.if26new;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private LinearLayout linearLayout;
    private LinearLayout dynamique;
    private TextView AlbumName;
    private ImageButton imageButtonAlbum;
    public static AlbumFragment newInstance() {
        return (new AlbumFragment());
    }
    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_album, container, false);

        linearLayout = view.findViewById(R.id.linearForAlbum);
        ViewGroup.MarginLayoutParams paramsImageButton = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsImageButton.setMargins(40,0,0,0);
        ViewGroup.MarginLayoutParams paramsAlbumName = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        paramsAlbumName.setMargins(10,60,0,0);
        for (int i = 0; i <= 10; i++) {
            dynamique = new LinearLayout(getActivity());
            dynamique.setOrientation(LinearLayout.HORIZONTAL);

            imageButtonAlbum=new ImageButton(getActivity());
            dynamique.addView(imageButtonAlbum,paramsImageButton);
            imageButtonAlbum.setBackground(null);
            imageButtonAlbum.setImageResource(R.drawable.hazy1);
            android.view.ViewGroup.LayoutParams params = imageButtonAlbum.getLayoutParams();
            params.height=200;
            params.width=200;
            imageButtonAlbum.setLayoutParams(params);
            imageButtonAlbum.setAdjustViewBounds(true);
            imageButtonAlbum.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButtonAlbum.requestLayout();

            AlbumName=new TextView(getActivity());
            AlbumName.setText("Album");
            AlbumName.setTextColor(Color.WHITE);
            AlbumName.setTextSize(20);
            AlbumName.setSingleLine(true);

            //dynamique.addView(imageButtonPlaylist,paramsImageButton);
            dynamique.addView(AlbumName,paramsAlbumName);
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
                Intent AlbumActivity = new Intent(getActivity(), Album_view.class);
                startActivity(AlbumActivity);
                }
            });
        }

        return view;
    }

}
