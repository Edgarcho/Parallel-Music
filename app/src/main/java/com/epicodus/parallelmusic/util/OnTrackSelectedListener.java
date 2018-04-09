package com.epicodus.parallelmusic.util;

import com.epicodus.parallelmusic.models.Track;

import java.util.ArrayList;

/**
 * Created by Edgar on 4/8/2018.
 */

public interface OnTrackSelectedListener {
    public void onTrackSelected(Integer position, ArrayList<Track> tracks);
}
