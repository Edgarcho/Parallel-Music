package com.epicodus.parallelmusic.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.adapters.TrackPagerAdapter;
import com.epicodus.parallelmusic.models.Track;


import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private TrackPagerAdapter adapterViewPager;
    ArrayList<Track> mTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);
        ButterKnife.bind(this);

        mTracks = Parcels.unwrap(getIntent().getParcelableExtra("tracks"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new TrackPagerAdapter(getSupportFragmentManager(), mTracks);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
