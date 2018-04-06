package com.epicodus.parallelmusic.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.epicodus.parallelmusic.R;
import com.epicodus.parallelmusic.adapters.TrackListAdapter;
import com.epicodus.parallelmusic.models.Track;
import com.epicodus.parallelmusic.services.LastFmService;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TrackListActivity extends AppCompatActivity {
    @BindView(R.id.songTextView) TextView mSongTextView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private TrackListAdapter mAdapter;


    public ArrayList<Track> tracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        mSongTextView.setText("Searching: " + song);
        getSongs(song);
    }

}
