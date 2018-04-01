package com.epicodus.parallelmusic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.ui.TrackDetailFragment;

import java.util.ArrayList;

/**
 * Created by Edgar on 4/1/2018.
 */

public class TrackPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Track> mTracks;

    public TrackPagerAdapter(FragmentManager fm, ArrayList<Track> tracks){
        super(fm);
        mTracks = tracks;
    }

    @Override
    public Fragment getItem(int position){
        return TrackDetailFragment.newInstance(mTracks.get(position));
    }

    @Override
    public int getCount(){
        return mTracks.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTracks.get(position).getName();
    }
}
