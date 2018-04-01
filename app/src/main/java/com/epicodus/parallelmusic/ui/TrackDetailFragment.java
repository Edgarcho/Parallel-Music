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
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackDetailFragment extends Fragment {
    @BindView(R.id.trackDetailImageView) ImageView mTrackImage;
    @BindView(R.id.trackNameTextView) TextView mTrackName;
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mTrack = Parcels.unwrap(getArguments().getParcelable("track"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mTrack.getImageUrl()).into(mTrackImage);

        mTrackName.setText(mTrack.getName());
        mTrackArtist.setText(mTrack.getArtist());
        mTrackListener.setText(Double.toString(mTrack.getListeners()));
        mWebsite.setText(mTrack.getWebsite());

        return view;
    }

}
