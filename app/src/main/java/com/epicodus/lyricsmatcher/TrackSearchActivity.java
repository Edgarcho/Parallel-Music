package com.epicodus.lyricsmatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackSearchActivity extends AppCompatActivity {
    private String[] dummySongs = new String[] {"God's Plan","Psycho","All The Stars (with SZA)","Friends","IDGAF", "Look Alive", "The Middle", "Mine"};

    @BindView(R.id.songTextView) TextView mSongTextView;
    @BindView(R.id.songListView) ListView mSongListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dummySongs);
        mSongListView.setAdapter(adapter);



        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        mSongTextView.setText("Here are all the songs that fit that search query: " + song);
    }
}
