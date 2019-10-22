package com.example.if26new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ActivityArtist extends AppCompatActivity{

    private ImageButton followBtn;
    private ImageView imageArtist;
    private TextView followTxt;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private TextView artistName;
    private String txtBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        followBtn = findViewById(R.id.followArtistBtnID);
        followTxt = findViewById(R.id.followTxtID);
        mTableLayout = findViewById(R.id.tableLayoutArtistID);
        mViewPager = findViewById(R.id.viewPagerArtistID);

        followBtn.setBackground(null);
        setViewPager(mViewPager);
        mTableLayout.setupWithViewPager(mViewPager);

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followBtn.getDrawable().getConstantState().equals(getDrawable(R.drawable.ic_followwhite).getConstantState())) {
                    followBtn.setImageResource(R.drawable.ic_followgreen);
                } else if (followBtn.getDrawable().getConstantState().equals(getDrawable(R.drawable.ic_followgreen).getConstantState())) {
                    followBtn.setImageResource(R.drawable.ic_followwhite);
                }

            }
        });
        setImageArtist(getIntent().getExtras().getInt("ARTIST_IMAGE_ID"));
        setArtistName(getIntent().getExtras().getString("ARTIST_NAME"));
        txtBio=getIntent().getExtras().getString("ARTIST_BIO");

    }
    public void setImageArtist(int id){
        imageArtist = findViewById(R.id.imageArtistID);
        imageArtist.setAdjustViewBounds(true);
        android.view.ViewGroup.LayoutParams params = imageArtist.getLayoutParams();
        params.height=650;
        params.width=650;
        imageArtist.setLayoutParams(params);
        imageArtist.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageArtist.setImageResource(id);
    }

    public void setArtistName(String name){
        artistName=findViewById(R.id.nameArtistID);
        artistName.setText(name);
    }
    private void setViewPager(ViewPager viewPager) {
        PageAdapterForArtist adapter = new PageAdapterForArtist(getSupportFragmentManager());
        adapter.addFragment(new fragment_album(), "Album");
        adapter.addFragment(new TitlesFragment(), "Titles");
        adapter.addFragment(new fragment_bio(), "Bio");
        viewPager.setAdapter(adapter);
    }

}
