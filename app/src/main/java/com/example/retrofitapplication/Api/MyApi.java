package com.example.retrofitapplication.Api;



import com.example.retrofitapplication.Model.GitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {

    //?q=kotlin
    @GET("repositories")
    Call<GitResponse> getContactResponseCall(@Query("q") String name);

}
