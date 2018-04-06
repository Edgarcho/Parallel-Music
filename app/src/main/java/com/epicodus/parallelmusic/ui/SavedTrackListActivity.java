package com.epicodus.parallelmusic.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.adapters.FirebaseTrackListAdapter;
import com.epicodus.parallelmusic.adapters.FirebaseTrackViewHolder;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.util.ItemTouchHelperAdapter;
import com.epicodus.parallelmusic.util.OnStartDragListener;
import com.epicodus.parallelmusic.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedTrackListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mTrackReference;
    private FirebaseTrackListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mTrackReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_TRACKS)
                .child(uid);

        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mTrackReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_TRACKS)
                .child(uid);

        mFirebaseAdapter = new FirebaseTrackListAdapter(Track.class,
                R.layout.track_list_item_drag, FirebaseTrackViewHolder.class, mTrackReference, this,this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}
