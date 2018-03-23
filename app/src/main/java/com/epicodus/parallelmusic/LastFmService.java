package com.epicodus.parallelmusic;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
        Log.v("LastFmService", url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
