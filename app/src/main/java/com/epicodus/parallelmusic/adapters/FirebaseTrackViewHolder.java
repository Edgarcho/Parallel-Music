package com.epicodus.parallelmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.ui.Constants;
import com.epicodus.parallelmusic.ui.TrackDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseTrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseTrackViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindTrack(Track track){
        ImageView trackImageView = mView.findViewById(R.id.trackImageView);
        TextView trackNameTextView = mView.findViewById(R.id.trackNameTextView);
        TextView artistTextView = mView.findViewById(R.id.artistTextView);

        Picasso.with(mContext)
                .load(track.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .into(trackImageView);

        trackNameTextView.setText(track.getName());
        artistTextView.setText(track.getArtist());
    }

    @Override
    public void onClick(View view){
        final ArrayList<Track> tracks = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_TRACKS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tracks.add(snapshot.getValue(Track.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, TrackDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("tracks", Parcels.wrap(tracks));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
