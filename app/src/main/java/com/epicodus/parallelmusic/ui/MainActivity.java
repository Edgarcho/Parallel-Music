package com.epicodus.parallelmusic.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.parallelmusic.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.searchTrackButton) Button mSearchTrackButton;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.songEditText) EditText mSongEditText;
    @BindView(R.id.savedTracksButton) Button mSavedTracksButton;

    private DatabaseReference mSearchedSongReference;
    private ValueEventListener mSearchedSongReferenceListener;

    protected void onCreate(Bundle savedInstanceState) {
        mSearchedSongReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_SONG);

       mSearchedSongReferenceListener = mSearchedSongReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot songSnapshot : dataSnapshot.getChildren()){
                   String song = songSnapshot.getValue().toString();
                   Log.d("Song Update", "song: " + song);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface painterFont = Typeface.createFromAsset(getAssets(), "fonts/painter.ttf");
        mAppNameTextView.setTypeface(painterFont);

        mSearchTrackButton.setOnClickListener(this);
        mSavedTracksButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mSearchTrackButton) {
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

        if (view == mSavedTracksButton){
            Intent intent = new Intent(MainActivity.this, SavedTrackListActivity.class);
            startActivity(intent);
        }
    }

    private void savedSongToFirebase(String track){
       mSearchedSongReference.push().setValue(track);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSearchedSongReference.removeEventListener(mSearchedSongReferenceListener);
    }
}
