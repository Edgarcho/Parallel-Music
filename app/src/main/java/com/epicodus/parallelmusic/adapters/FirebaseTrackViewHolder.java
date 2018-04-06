package com.epicodus.parallelmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.ui.Constants;
import com.epicodus.parallelmusic.ui.TrackDetailActivity;
import com.epicodus.parallelmusic.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseTrackViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mTrackImageView;

    public FirebaseTrackViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindTrack(Track track){
        mTrackImageView = mView.findViewById(R.id.trackImageView);
        TextView trackNameTextView = mView.findViewById(R.id.trackNameTextView);
        TextView artistTextView = mView.findViewById(R.id.artistTextView);

        Picasso.with(mContext)
                .load(track.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .into(mTrackImageView);

        trackNameTextView.setText(track.getName());
        artistTextView.setText(track.getArtist());
    }

    @Override
    public void onItemSelected() {
      itemView.animate()
              .alpha(0.7f)
              .scaleX(0.9f)
              .scaleY(0.9f)
              .setDuration(500);
    }

    @Override
    public void onIemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
