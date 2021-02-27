package com.example.if26new.Library;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.if26new.Library.Album.AlbumFragment;
import com.example.if26new.Library.Artist.ArtistFragment;
import com.example.if26new.Library.Playlist.PlaylistFragment;
import com.example.if26new.Library.Tracks.TrackFragment;

public class PageAdaptaterForUser extends FragmentPagerAdapter {
    //Default Constructor
    public PageAdaptaterForUser(FragmentManager mgr) {
        super(mgr);
    }
    @Override
    public int getCount() {
        return(4);
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
            case 3: //Page number 4
                return TrackFragment.newInstance();
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
            case 3: //Page number 4
                return "Track";
            default:
                return null;
        }
    }
}
