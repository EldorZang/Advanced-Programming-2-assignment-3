package com.example.myapplication5;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    Call<Void> createPost(@Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @POST("register")
    Call<ResponseBody> registerNewUser(@Body RegisterInput registerInput);

    @POST("login")
    Call<ResponseBody> loginUser(@Body LoginInput loginInput);

    @GET("register/{id}")
    Call<ResponseBody> checkNewUser(@Path("id") String id);
}
