package com.example.myapplication5;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactsAPI {
    @GET("/api/contacts")
    Call<ArrayList<Contact>> getContacts(@Query("loggedUserId") String loggedUser);
    @POST("/api/contacts")
    Call<Contact> AddContact(@Query("loggedUserId") String loggedUser,@Body Contact contact);
    @POST("/api/api/invitations")
    Call<AddContactObject> Invite(@Body AddContactObject contact);
}