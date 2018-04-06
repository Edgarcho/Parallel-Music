package com.epicodus.parallelmusic.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.util.ItemTouchHelperAdapter;
import com.epicodus.parallelmusic.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Guest on 4/6/18.
 */

public class FirebaseTrackListAdapter extends FirebaseRecyclerAdapter<Track, FirebaseTrackViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseTrackListAdapter(Class<Track> modelClass, int modelLayout, Class<FirebaseTrackViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context){
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
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

    }
}
