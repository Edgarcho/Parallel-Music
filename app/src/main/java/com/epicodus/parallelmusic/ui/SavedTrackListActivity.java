package com.epicodus.parallelmusic.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.adapters.FirebaseTrackViewHolder;
import com.epicodus.parallelmusic.models.Track;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedTrackListActivity extends AppCompatActivity {
    private DatabaseReference mTrackReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);
        mTrackReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_TRACKS);
        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Track, FirebaseTrackViewHolder>(Track.class, R.layout.track_list_item, FirebaseTrackViewHolder.class, mTrackReference) {

            @Override
            protected void populateViewHolder(FirebaseTrackViewHolder viewHolder, Track model, int position){
                viewHolder.bindTrack(model);
            }
        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
