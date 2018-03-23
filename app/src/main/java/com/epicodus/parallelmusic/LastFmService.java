package com.epicodus.parallelmusic;

import android.util.Log;

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
    public static final String TAG = LastFmService.class.getSimpleName();

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
            JSONObject trackmatchesJSON = new JSONObject(jsonData);
            JSONArray trackJSON = trackmatchesJSON.getJSONArray("track");
            for (int i = 0; i < trackJSON.length(); i++) {
                JSONObject singleTrackJSON = trackJSON.getJSONObject(i);
                String name = singleTrackJSON.getString("name");
                String artist = singleTrackJSON.getString("artist");
                String website = singleTrackJSON.getString("url");
                double listeners = singleTrackJSON.getDouble("listeners");
                ArrayList<String> imageUrl = new ArrayList<>();
                JSONArray imageUrlJSON = singleTrackJSON.getJSONArray("image");
                for (int y = 0; y < imageUrlJSON.length(); y++) {
                    imageUrl.add(imageUrlJSON.getJSONObject(y).getString("#text"));
                }
                Track track = new Track(name, artist, website, listeners, imageUrl);
                tracks.add(track);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
