package com.epicodus.parallelmusic.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.ui.Constants;
import com.epicodus.parallelmusic.ui.TrackDetailActivity;
import com.epicodus.parallelmusic.ui.TrackDetailFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Edgar on 3/29/2018.
 */

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Track> mTracks = new ArrayList<>();
    private Context mContext;

    public TrackListAdapter(Context context, ArrayList<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @Override
    public TrackListAdapter.TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_list_item, parent, false);
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrackListAdapter.TrackViewHolder holder, int position) {
        holder.bindTrack(mTracks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trackImageView)
        ImageView mTrackImageView;
        @BindView(R.id.trackNameTextView)
        TextView mTrackNameTextView;
        @BindView(R.id.artistTextView)
        TextView mArtistTextView;

        private Context mContext;
        private int mOrientation;

        public TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mOrientation = itemView.getResources().getConfiguration().orientation;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
        }

        public void bindTrack(Track track) {
            Picasso.with(mContext)
                    .load(track.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mTrackImageView);

            mTrackNameTextView.setText(track.getName());
            mArtistTextView.setText(track.getArtist());
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, TrackDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_TRACKS, Parcels.wrap(mTracks));
                mContext.startActivity(intent);
            }
        }

        private void createDetailFragment(int position) {
            TrackDetailFragment detailFragment = TrackDetailFragment.newInstance(mTracks, position);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.trackDetailContainer, detailFragment);
            ft.commit();
        }
    }
}
