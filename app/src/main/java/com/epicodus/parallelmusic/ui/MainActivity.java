package com.epicodus.parallelmusic.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.parallelmusic.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.searchTrackButton) Button mSearchTrackButton;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.songEditText) EditText mSongEditText;

    private DatabaseReference mSearchedSongRefeerence;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       mSearchedSongRefeerence = FirebaseDatabase
               .getInstance()
               .getReference()
               .child(Constants.FIREBASE_CHILD_SEARCHED_SONG);

        Typeface painterFont = Typeface.createFromAsset(getAssets(), "fonts/painter.ttf");
        mAppNameTextView.setTypeface(painterFont);

        mSearchTrackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mSearchTrackButton) {
            String song = mSongEditText.getText().toString();
            if(song.equals("")){
                Toast toast = Toast.makeText(MainActivity.this,"Input invalid.. Please Try Again", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }else {
                savedSongToFirebase(song);
                Intent intent = new Intent(MainActivity.this, TrackListActivity.class);
                intent.putExtra("song", song);
                startActivity(intent);
            }
        }
    }

    private void savedSongToFirebase(String track){
       mSearchedSongRefeerence.push().setValue(track);
    }
}
