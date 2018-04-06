package com.epicodus.parallelmusic.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.adapters.TrackListAdapter;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.services.LastFmService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackListFragment extends Fragment {
    @BindView(R.id.songTextView) TextView mSongTextView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private TrackListAdapter mAdapter;
    public ArrayList<Track> tracks = new ArrayList<>();


    public TrackListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    private void getSongs(String song){
        final LastFmService lastFmService = new LastFmService();
        lastFmService.searchTrackButton(song, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                tracks = lastFmService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new TrackListAdapter(getActivity(), tracks);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_list, container, false);
    }

}
