package com.example.if26new;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private LinearLayout mLinearLayoutNew1;
    private LinearLayout mLinearLayoutNew2;
    private LinearLayout mLinearLayoutNew3;
    private ImageButton mImageButtonsSingles;
    private Button mImageButtonsConcerts;
    private ImageButton mImageButtonsAlbums;
    private LinearLayout mLinearLayoutsSingles;
    private LinearLayout mLinearLayoutsAlbums;
    private LinearLayout mLinearLayoutsConcerts;
    private TextView mTextViewsSingles;
    private TextView mTextViewsAlbums;
    private TextView mTextViewsConcerts;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_news, container, false);
        mLinearLayoutNew1 = result.findViewById(R.id.linearNewSinglesID);
        mLinearLayoutNew2 = result.findViewById(R.id.linearNewConcertsID);
        mLinearLayoutNew3 = result.findViewById(R.id.linearNewAlbumsID);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 50, 20, 50);

        for (int i=0;i<10;i++) {
            //PARTIE SINGLES
            mLinearLayoutsSingles = new LinearLayout(getActivity());
            mLinearLayoutNew1.addView(mLinearLayoutsSingles);
            mLinearLayoutsSingles.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsSingles = new ImageButton(getActivity());
            mLinearLayoutsSingles.addView(mImageButtonsSingles);
            mImageButtonsSingles.setBackground(null);
            mImageButtonsSingles.setImageResource(R.drawable.iconforplaylist);
            mImageButtonsSingles.setAdjustViewBounds(true);
            android.view.ViewGroup.LayoutParams params = mImageButtonsSingles.getLayoutParams();
            params.height = 450;
            params.width = 450;
            mImageButtonsSingles.setLayoutParams(params);
            mImageButtonsSingles.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mTextViewsSingles = new TextView(getActivity());
            mTextViewsSingles.setText("Singles " + i);
            mTextViewsSingles.setTextColor(Color.WHITE);
            mLinearLayoutsSingles.addView(mTextViewsSingles);
            mLinearLayoutsSingles.setLayoutParams(lp);
            mTextViewsSingles.setGravity(Gravity.CENTER_HORIZONTAL);


            //PARTIE CONCERTS
            mLinearLayoutsConcerts = new LinearLayout(getActivity());
            mLinearLayoutNew2.addView(mLinearLayoutsConcerts);
            mLinearLayoutsConcerts.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp1.setMargins(25, 50, 25, 50);
            mImageButtonsConcerts = new Button(getActivity());
            mLinearLayoutsConcerts.addView(mImageButtonsConcerts);
            mImageButtonsConcerts.setBackgroundResource(R.drawable.concertstade);
            android.view.ViewGroup.LayoutParams params1 = mImageButtonsConcerts.getLayoutParams();
            params1.height = 440;
            params1.width = 440;
            mImageButtonsConcerts.setLayoutParams(params1);
            mImageButtonsConcerts.setText("Artiste\n-\nVille");
            mImageButtonsConcerts.setTextColor(Color.BLACK);
            /*mTextViewsConcerts = new TextView(getActivity());
            mTextViewsConcerts.setText("Concert " + i);
            mTextViewsConcerts.setTextColor(Color.WHITE);
            mLinearLayoutsConcerts.addView(mTextViewsConcerts);
            mTextViewsConcerts.setGravity(Gravity.CENTER_HORIZONTAL);*/
            mLinearLayoutsConcerts.setLayoutParams(lp1);

            //PARTIE ALBUM
            mLinearLayoutsAlbums = new LinearLayout(getActivity());
            mLinearLayoutNew3.addView(mLinearLayoutsAlbums);
            mLinearLayoutsAlbums.setOrientation(LinearLayout.VERTICAL);
            mImageButtonsAlbums = new ImageButton(getActivity());
            mLinearLayoutsAlbums.addView(mImageButtonsAlbums);
            mImageButtonsAlbums.setImageResource(R.drawable.postmalonealb);
            mImageButtonsAlbums.setBackground(null);
            mImageButtonsAlbums.setBackgroundColor(Color.BLACK);
            android.view.ViewGroup.LayoutParams params3 = mImageButtonsAlbums.getLayoutParams();
            params3.height=450;
            params3.width=450;
            mImageButtonsAlbums.setLayoutParams(params3);
            mImageButtonsAlbums.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImageButtonsAlbums.setAdjustViewBounds(true);
            mTextViewsAlbums = new TextView(getActivity());
            mTextViewsAlbums.setText("Album " + i + " ");
            mTextViewsAlbums.setTextColor(Color.WHITE);
            mLinearLayoutsAlbums.addView(mTextViewsAlbums);
            mLinearLayoutsAlbums.setLayoutParams(lp);
            mTextViewsAlbums.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        return result;


    }

}
