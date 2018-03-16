package com.epicodus.lyricsmatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackSearchActivity extends AppCompatActivity {
    @BindView(R.id.songTextView) TextView mSongTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        mSongTextView.setText("Here are all the songs that fit that search query: " + song);
    }
}
