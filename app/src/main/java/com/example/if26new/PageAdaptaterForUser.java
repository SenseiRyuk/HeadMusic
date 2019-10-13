package com.example.if26new;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdaptaterForUser extends FragmentPagerAdapter {
    //Default Constructor
    public PageAdaptaterForUser(FragmentManager mgr) {
        super(mgr);
    }
    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return PlaylistFragment.newInstance();
            case 1: //Page number 2
                return ArtistFragment.newInstance();
            case 2: //Page number 3
                return AlbumFragment.newInstance();
            default:
                return null;
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: //Page number 1
                return "Playlist";
            case 1: //Page number 2
                return "Artist";
            case 2: //Page number 3
                return "Album";
            default:
                return null;
        }
    }
}
