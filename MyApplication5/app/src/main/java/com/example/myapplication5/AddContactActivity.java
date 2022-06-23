package com.example.myapplication5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddContactActivity extends AppCompatActivity{
    String loggedUserId;
    Button submitButton;
    TextView errorMsg;
    EditText idField ;
    EditText nickNameField;
    EditText serverField;
    boolean firstPostSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);
        Intent activityIntent = getIntent();
        loggedUserId = activityIntent.getStringExtra("loggedUserId");
        submitButton = findViewById(R.id.submit_form);
         idField = (EditText) findViewById(R.id.contact_id_form);
         nickNameField = (EditText) findViewById(R.id.contact_nickName_form);
         serverField = (EditText) findViewById(R.id.contact_server_form);
        errorMsg = (TextView)  findViewById(R.id.error_text);
        firstPostSent = false;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
                Contact contact = new Contact(idField.getText().toString(),nickNameField.getText().toString(),serverField.getText().toString(),null,null);
                AddContactObject addContactObj = new AddContactObject(loggedUserId,idField.getText().toString(),"http://10.0.2.2:5067");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ContactsAPI service = retrofit.create(ContactsAPI.class);
                Call<AddContactObject> invite = service.Invite(addContactObj);
                invite.enqueue(new Callback<AddContactObject>() {
                    @Override
                    public void onResponse(Call<AddContactObject> call, Response<AddContactObject> response) {
                        if(firstPostSent) {
                            finish();
                        }else{
                            firstPostSent = true;
                        }
                    }
                    @Override
                    public void onFailure(Call<AddContactObject> call, Throwable t) {
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                });
                Call<Contact> call = service.AddContact(loggedUserId,contact);
                call.enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
                        if(firstPostSent) {
                            finish();
                        }else{
                            firstPostSent = true;
                        }
                    }
                });







            }
        });

    }
    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}