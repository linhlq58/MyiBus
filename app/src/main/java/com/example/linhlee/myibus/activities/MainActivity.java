package com.example.linhlee.myibus.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;
import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.adapters.MyPagerAdapter;
import com.example.linhlee.myibus.fragments.MainFragment;
import com.example.linhlee.myibus.fragments.MapsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        MainFragment mainFragment = new MainFragment();
        MapsFragment mapsFragment = new MapsFragment();

        fragments = new ArrayList<>();
        fragments.add(mainFragment);
        fragments.add(mapsFragment);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(pagerAdapter);

        tabs.setViewPager(pager);
    }
}
