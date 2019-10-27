package com.example.if26new;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private SearchView search;
    private BottomNavigationView bottomNavigationView;
    private View home;
    private View news;
    private View profil;
    private boolean isOnClickHome;
    private TextView musicTitle;
    private Fragment mainFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        musicTitle=findViewById(R.id.musicTitle);
        isOnClickHome=false;
        search=findViewById(R.id.searchView);
        search.clearFocus();
      //  home=findViewById(R.id.homeButtonBottomBar);
      //  news=findViewById(R.id.newButtonBottomBar);
      //  profil=findViewById(R.id.profileButtonBottomBar);
        bottomNavigationView=findViewById(R.id.bottonView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);

      //  home.setIcon(R.drawable.homeonclick);

       /* musicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listenActivity = new Intent(HomeActivity.this, listening.class);
                startActivity(listenActivity);
            }
        });*/
        loadFragment(new MainFragment());
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.homeButtonBottomBar:
                fragment = new MainFragment();
                break;

            case R.id.newButtonBottomBar:
                fragment = new NewsFragment();
                break;

            case R.id.profileButtonBottomBar:
                fragment = new UserFragment();
                break;
        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainter, fragment).addToBackStack(null).commit();
            return true;
        }
        return false;
    }

}
