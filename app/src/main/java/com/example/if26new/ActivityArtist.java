package com.example.if26new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button followButton;
    private ImageView imageArtist;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private TextView artistName;
    private String txtBio;
    private SaveMyMusicDatabase db;
    private ImageButton returnButton;
    private String fragmentName;
    private int isArtist=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        followBtn = findViewById(R.id.followArtistBtnID);
        followButton=findViewById(R.id.followTxtID);
        mTableLayout = findViewById(R.id.tableLayoutArtistID);
        mViewPager = findViewById(R.id.viewPagerArtistID);
        returnButton=findViewById(R.id.returnButtonArtistView);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMethod();
            }
        });

        followBtn.setBackground(null);
        setViewPager(mViewPager);
        mTableLayout.setupWithViewPager(mViewPager);
        db=SaveMyMusicDatabase.getInstance(this);
        if (db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).isLike()==true){
            followButton.setText("Unfollow");
        }else{
            followButton.setText("Follow");
        }
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followButton.getText().toString()=="Follow"){
                    followButton.setText("Unfollow");
                    db.mArtistDao().updateLike(true,db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getId());
                }else{
                    followButton.setText("Follow");
                    db.mArtistDao().updateLike(false,db.mArtistDao().getArtistFromName(getIntent().getExtras().getString("ARTIST_NAME")).getId());
                }
            }
        });
        setImageArtist(getIntent().getExtras().getInt("ARTIST_IMAGE_ID"));
        setArtistName(getIntent().getExtras().getString("ARTIST_NAME"));
        txtBio=getIntent().getExtras().getString("ARTIST_BIO");
        fragmentName=getIntent().getExtras().getString("FRAGMENT_NAME");
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

        Bundle args = new Bundle();
        fragment_album frg=new fragment_album();
        args.putString("ARTIST","1");
        args.putString("FRAGMENT", getIntent().getExtras().getString("FRAGMENT_NAME"));
        frg.setArguments(args);
        adapter.addFragment(frg, "Album");

        Bundle args2 = new Bundle();
        TitlesFragment t = new TitlesFragment();
        args2.putString("FRAGMENT", getIntent().getExtras().getString("FRAGMENT_NAME"));
        t.setArguments(args2);
        adapter.addFragment(t , "Titles");


        adapter.addFragment(new fragment_bio(), "Bio");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void returnMethod(){
        Bundle bundle = new Bundle();
        bundle.putString("FRAGMENT_NAME",fragmentName);
        bundle.putString("CONTEXT","ArtistActivity");
        Intent playListActivity = new Intent(ActivityArtist.this, HomeActivity.class);
        playListActivity.putExtras(bundle);
        startActivity(playListActivity);
    }
}
