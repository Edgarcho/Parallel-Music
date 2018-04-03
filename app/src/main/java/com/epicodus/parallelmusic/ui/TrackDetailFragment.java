package com.epicodus.parallelmusic.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.trackDetailImageView) ImageView mTrackImage;
    @BindView(R.id.trackNameDetailTextView) TextView mTrackName;
    @BindView(R.id.trackArtistDetailTextView) TextView mTrackArtist;
    @BindView(R.id.listenerTextView) TextView mTrackListener;
    @BindView(R.id.webTextView) TextView mWebsite;
    @BindView(R.id.saveTrackButton) ImageButton mSaveTrackButton;

    private static final int MAX_WIDTH = 300;
    private static final int MAX_HEIGHT = 300;

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

        Picasso.with(view.getContext()).load(mTrack.getImageUrl()).resize(MAX_WIDTH, MAX_HEIGHT).into(mTrackImage);

        mTrackName.setText(mTrack.getName());
        mTrackArtist.setText(mTrack.getArtist());
        mTrackListener.setText(Double.toString(mTrack.getListeners()));
        mWebsite.setOnClickListener(this);
        mSaveTrackButton.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v){
        if(v == mWebsite){
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTrack.getWebsite()));
            startActivity(webIntent);
        }
        if(v == mSaveTrackButton){
            DatabaseReference trackRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_TRACKS);
            trackRef.push().setValue(mSaveTrackButton);
            Toast.makeText(getContext(),"Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
