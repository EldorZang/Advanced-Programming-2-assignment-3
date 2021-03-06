package com.example.myapplication5;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
            .baseUrl(MessagingApp.getAppContext().getString(R.string.BaseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void postRegister() {

    }

    public void get() {
        Call<List<Post>> call = webServiceAPI.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

            /*
            new Thread(() -> {
                dao.clear();
                dao.insertList(response.body());
                postListData.postValue(dao.get());
            }).start();
             */
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {}
        });
    }
}

