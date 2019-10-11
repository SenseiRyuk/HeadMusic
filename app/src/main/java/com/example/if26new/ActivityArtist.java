package com.example.if26new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ActivityArtist extends AppCompatActivity {

    private ImageButton followBtn;
    private ImageView imageArtist;
    private TextView followTxt;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private PageAdapterForArtist mPageAdapterForArtist;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        followBtn = findViewById(R.id.followArtistBtnID);
        followTxt = findViewById(R.id.followTxtID);
        mTableLayout = findViewById(R.id.tableLayoutArtistID);
        imageArtist = findViewById(R.id.imageArtistID);
        mViewPager = findViewById(R.id.viewPagerArtistID);

        //imageArtist.setImageResource();
        imageArtist.setAdjustViewBounds(true);
        imageArtist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        followBtn.setBackground(null);
        mViewPager.setAdapter(mPageAdapterForArtist);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followBtn.getDrawable().equals(R.drawable.ic_followwhite)){
                    followBtn.setImageResource(R.drawable.ic_followgreen);
                } else if(followBtn.getDrawable().equals(R.drawable.ic_followgreen)){
                    followBtn.setImageResource(R.drawable.ic_followwhite);
                }
            }
        });

    }



}
