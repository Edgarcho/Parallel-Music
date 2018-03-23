package com.epicodus.parallelmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackSearchActivity extends AppCompatActivity {
    private String[] dummySongs = new String[] {"God's Plan","Psycho","All The Stars (with SZA)","Friends","IDGAF", "Look Alive",
            "The Middle", "Mine", "These Days", "Sad!", "Love Lies", "Rockstar (REMIX)", "Pray For Me"};

    @BindView(R.id.songTextView) TextView mSongTextView;
    @BindView(R.id.songListView) ListView mSongListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dummySongs);
        mSongListView.setAdapter(adapter);

        mSongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String dummySongs = ((TextView)view).getText().toString();
                Toast.makeText(TrackSearchActivity.this, dummySongs, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        mSongTextView.setText("Searching for: " + song);
    }
}
