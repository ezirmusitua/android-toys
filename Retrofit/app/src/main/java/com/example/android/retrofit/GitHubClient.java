package com.example.android.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {
    @GET("/users/{username}")
    Call<GitHubUser> user(@Path("username") String username);
}
