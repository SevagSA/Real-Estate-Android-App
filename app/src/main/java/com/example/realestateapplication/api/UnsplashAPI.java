package com.example.realestateapplication.api;

import com.example.realestateapplication.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UnsplashAPI {

//    TODO place API key in BuildConfig (google SK as well)
//    static String CLIENT_ID = BuildConfig.CLIENT_ID;
    static String CLIENT_ID = "uRvrCRmwDBO3UyAsT5jq7g0kAQUp7iSqogQSUSbW390";
    static String BASE_URL = "https://api.unsplash.com/";

    @Headers({"Accept-Version: v1", "Authorization: Client-ID " + CLIENT_ID})
    @GET("search/photos")
    public Call<UnsplashResponse> searchPhoto(@Query("query") String query);

}
