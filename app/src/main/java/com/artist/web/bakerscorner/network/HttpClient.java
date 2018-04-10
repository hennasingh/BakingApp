package com.artist.web.bakerscorner.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by User on 05-Apr-18.
 */

public class HttpClient {

    private static HttpClient sHttpClient;
    OkHttpClient client;
    Request request;


    private HttpClient(){
         client = new OkHttpClient();
         request = new Request.Builder()
                                .url(Utils.RECIPE_URL)
                                .build();

    }

    public static HttpClient getInstance(){
        if(sHttpClient==null){
            sHttpClient = new HttpClient();
        }
        return sHttpClient;
    }


    public void getRecipes(Callback callback){
        client.newCall(request).enqueue(callback);
    }
}
