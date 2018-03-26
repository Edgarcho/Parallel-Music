package com.epicodus.parallelmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TrackSearchActivity extends AppCompatActivity {
    public static final String TAG = TrackSearchActivity.class.getSimpleName();

    @BindView(R.id.songTextView) TextView mSongTextView;
    @BindView(R.id.songListView) ListView mSongListView;

    public ArrayList<Track> tracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        mSongTextView.setText("Searching for: " + song);
        getSongs(song);
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

                TrackSearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] trackNames = new String[tracks.size()];
                        for (int i = 0; i < trackNames.length; i++) {
                            trackNames[i] = tracks.get(i).getName();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(TrackSearchActivity.this, android.R.layout.simple_list_item_1, trackNames);
                        mSongListView.setAdapter(adapter);

                        for (Track track : tracks ){
                            Log.d(TAG, "Name: " + track.getName());
                        }
                    }
                });
            }
        });
    }
}
