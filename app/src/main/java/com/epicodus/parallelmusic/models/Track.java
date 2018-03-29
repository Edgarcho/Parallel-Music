package com.epicodus.parallelmusic.models;

import java.util.ArrayList;

/**
 * Created by Guest on 3/23/18.
 */

public class Track {
    private String name;
    private String artist;
    private String website;
    private double listeners;
    private String imageUrl;

    public Track(String name, String artist, String website, double listeners, String imageUrl){
        this.name = name;
        this.artist = artist;
        this.website = website;
        this.listeners = listeners;
        this.imageUrl = imageUrl;
    }
    public String getName(){
        return name;
    }
    public String getArtist(){
        return artist;
    }
    public String getWebsite(){
        return website;
    }
    public double getListeners(){
        return listeners;
    }
    public String getImageUrl(){
        return imageUrl;
    }

}
