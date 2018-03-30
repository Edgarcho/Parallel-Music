package com.epicodus.parallelmusic.services;

import android.util.Log;

import com.epicodus.parallelmusic.ui.Constants;
import com.epicodus.parallelmusic.models.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Guest on 3/23/18.
 */

public class LastFmService {

    public static void searchTrackButton(String song, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LASTFM_BASE_TRACKSEARCH_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.LASTFM_SONG_QUERY_PARAMETER, song);
        urlBuilder.addQueryParameter("api_key",Constants.LASTFM_TOKEN);
        urlBuilder.addQueryParameter("format", Constants.LASTFM_FORMAT_PARAMETER);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Track> processResults(Response response) {
        ArrayList<Track> tracks = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject lastJSON = new JSONObject(jsonData);
            JSONObject resultsJSON = lastJSON.getJSONObject("results");
            JSONObject trackmatchesJSON = resultsJSON.getJSONObject("trackmatches");
            JSONArray trackJSON = trackmatchesJSON.getJSONArray("track");
            for (int i = 0; i < trackJSON.length(); i++) {
                JSONObject singleTrackJSON = trackJSON.getJSONObject(i);
                String name = singleTrackJSON.getString("name");
                String artist = singleTrackJSON.getString("artist");
                String website = singleTrackJSON.getString("url");
                double listeners = singleTrackJSON.getDouble("listeners");

                ArrayList<String>images = new ArrayList<>();
                JSONArray imageJSON = singleTrackJSON.getJSONArray("image");
                for(int x = 0; x < imageJSON.length(); x++) {
                    images.add(imageJSON.getJSONObject(x).getString("#text"));
                }
                int imageCount = images.size();
                //String image = String.valueOf(imageCount);
                if(imageCount == 4 && "".equals(images.get(0))){
                    String img = "https://lastfm-img2.akamaized.net/i/u/174s/c6f59c1e5e7240a4c0d427abd71f3dbb.png";
                    Track track = new Track(name, artist, website, listeners, img);
                    tracks.add(track);
                }else{
                    String img = images.get(3);
                    Track track = new Track(name, artist, website, listeners, img);
                    tracks.add(track);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
