package com.epicodus.parallelmusic.ui;


import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackDetailFragment extends Fragment {
    @BindView(R.id.trackDetailImageView) ImageView mTrackDetail;
    @BindView(R.id.trackArtistDetailTextView) TextView mTrackArtist;
    @BindView(R.id.listenerTextView) TextView mTrackListener;
    @BindView(R.id.webTextView) TextView mWebsite;
    @BindView(R.id.saveTrackButton) ImageButton mSaveTrack;

    private Track mTrack;

    public static TrackDetailFragment newInstance(Track track) {
        TrackDetailFragment trackDetailFragment = new TrackDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("track", Parcels.wrap(track));
        trackDetailFragment.setArguments(args);
        return trackDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_detail, container, false);
    }

}
