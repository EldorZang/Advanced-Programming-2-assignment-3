package com.example.testapp;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {
    public Retrofit retrofit;
    public WebServiceAPI webServiceAPI;
    public boolean responseResult;
    public SharedPreferences sharedPreferences;

    public RegisterAPI() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApp.context);
        retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MyApp.context.getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public boolean registerCheckUser(String id) {
        this.responseResult = false;
        retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MyApp.context.getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<ResponseBody> checkRegisterCall = webServiceAPI.checkNewUser(id);
        checkRegisterCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                setResponseResult(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return this.responseResult;
    }

    public boolean registerUser(RegisterInput registerInput) {
        this.responseResult = false;
        retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MyApp.context.getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<ResponseBody> registerCall = webServiceAPI.registerNewUser(registerInput);
        registerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                setResponseResult(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return this.responseResult;
    }

    public void setResponseResult(boolean result) {
        this.responseResult = result;
    }

    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}
