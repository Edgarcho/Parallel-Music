package com.epicodus.parallelmusic.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Guest on 3/23/18.
 */

@Parcel
public class Track {
    String name;
    String artist;
    String website;
    double listeners;
    String imageUrl;


    public Track(){}

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


