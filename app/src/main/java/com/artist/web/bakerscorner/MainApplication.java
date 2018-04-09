package com.artist.web.bakerscorner;

import android.app.Application;

import com.artist.web.bakerscorner.network.HttpClient;

/**
 * Created by User on 05-Apr-18.
 */

public class MainApplication extends Application {
    public static HttpClient sHttpClient;

    public void onCreate(){
        super.onCreate();
        sHttpClient = HttpClient.getInstance();
    }
}
