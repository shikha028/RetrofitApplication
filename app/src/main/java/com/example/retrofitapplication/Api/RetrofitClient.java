package com.example.retrofitapplication.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = null;

    public static String URL = "https://api.github.com/search/";

    private static Retrofit getClient(String baseurl) {
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MyApi getApi(String URL) {
        return getClient(URL).create(MyApi.class);
    }
}
