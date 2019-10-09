package com.example.if26new;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private TabLayout onglet;
    private ViewPager viewPagerForFragments;
    private FragmentActivity myContext;
    private PageAdaptaterForUser pageAdaptaterForUser;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user, container, false);
        onglet=view.findViewById(R.id.tabLayout);
        viewPagerForFragments=view.findViewById(R.id.viewPagerUser);
        viewPagerForFragments.setOffscreenPageLimit(2);
        //FragmentManager fragmentManager=myContext.getSupportFragmentManager();
        viewPagerForFragments.setAdapter(pageAdaptaterForUser);
        //viewPagerForFragments.addOnPageChangeListener(view);
        onglet.setupWithViewPager(viewPagerForFragments);
        //Design purpose. Tabs have the same width
        onglet.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        //myContext=(FragmentActivity) activity;
        pageAdaptaterForUser = new PageAdaptaterForUser(getChildFragmentManager());
        super.onAttach(activity);
    }
}
