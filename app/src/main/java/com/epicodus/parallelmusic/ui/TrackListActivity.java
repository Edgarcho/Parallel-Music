package com.epicodus.parallelmusic.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.util.OnTrackSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TrackListActivity extends AppCompatActivity implements OnTrackSelectedListener {
    private Integer mPosition;
    ArrayList<Track> mTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);

        if (savedInstanceState != null){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mTracks = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_TRACKS));
                if(mPosition != null && mTracks != null){
                    Intent intent = new Intent(this, TrackDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_TRACKS, Parcels.wrap(mTracks));
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        if(mPosition != null && mTracks != null){
            outstate.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outstate.putParcelable(Constants.EXTRA_KEY_TRACKS, Parcels.wrap(mTracks));
        }
    }

    @Override
    public void onTrackSelected(Integer position, ArrayList<Track> tracks){
        mPosition = position;
        mTracks = tracks;
    }

}
