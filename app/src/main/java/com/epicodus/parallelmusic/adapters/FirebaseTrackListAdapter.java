package com.epicodus.parallelmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.ui.TrackDetailActivity;
import com.epicodus.parallelmusic.util.ItemTouchHelperAdapter;
import com.epicodus.parallelmusic.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Guest on 4/6/18.
 */

public class FirebaseTrackListAdapter extends FirebaseRecyclerAdapter<Track, FirebaseTrackViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Track> mTrack = new ArrayList<>();

    public FirebaseTrackListAdapter(Class<Track> modelClass, int modelLayout, Class<FirebaseTrackViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context){
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mTrack.add(dataSnapshot.getValue(Track.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseTrackViewHolder viewHolder, Track model, int position) {
        viewHolder.bindTrack(model);
        viewHolder.mTrackImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TrackDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("track", Parcels.wrap(mTrack));
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mTrack, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mTrack.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase(){
        for (Track track : mTrack){
            int index = mTrack.indexOf(track);
            DatabaseReference ref = getRef(index);
            track.setIndex(Integer.toString(index));
            ref.setValue(track);
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
