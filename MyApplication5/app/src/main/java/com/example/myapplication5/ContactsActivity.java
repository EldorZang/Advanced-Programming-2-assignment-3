package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import retrofit2.*;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsActivity extends AppCompatActivity {

    AppDB db;
    ArrayList<Contact> contactList;
    ListView listView;
    CustomListAdapter adapter;
Button addContactButton;
String loggedUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent activityIntent = getIntent();
        loggedUserId = activityIntent.getStringExtra("loggedUserId");
        contactList = new ArrayList<Contact>();
        listView = findViewById(R.id.list_view);
         db = Room.databaseBuilder(getApplicationContext(),
                AppDB.class, "ContactsDB")
                 .allowMainThreadQueries()
                .build();

        ContactDAO contactDao = db.contactDao();
        List<Contact> queryResult = contactDao.getAll();
        contactList.addAll(queryResult);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);

                intent.putExtra("loggedUserId", loggedUserId);
                intent.putExtra("contactId", contactList.get(i).getId());
                intent.putExtra("contactServer", contactList.get(i).getServer());
                startActivity(intent);
            }
        });
        addContactButton = findViewById(R.id.add_contact_button);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                intent.putExtra("loggedUserId", loggedUserId);
                startActivity(intent);
            }
        });
        CreateToken();
        UpdateList();


    }
    private void CreateToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ContactsActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                sendToken(newToken);
            }
        });
    }
    private void sendToken(String token){
        System.out.println("token:"+token);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContactsAPI service = retrofit.create(ContactsAPI.class);
        Call<AndroidToken> call = service.AddFireBaseToken(new AndroidToken(loggedUserId,token));
        call.enqueue(new Callback<AndroidToken>() {
            @Override
            public void onResponse(Call<AndroidToken> call, Response<AndroidToken> response) {
                System.out.println(response.toString());
            }
            @Override
            public void onFailure(Call<AndroidToken> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
    private void RemoveToken(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContactsAPI service = retrofit.create(ContactsAPI.class);
        Call<AndroidToken> call = service.deleteFireBaseToken(loggedUserId);
        call.enqueue(new Callback<AndroidToken>() {
            @Override
            public void onResponse(Call<AndroidToken> call, Response<AndroidToken> response) {

            }
            @Override
            public void onFailure(Call<AndroidToken> call, Throwable t) {

            }
        });
    }
    private void UpdateData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContactsAPI service = retrofit.create(ContactsAPI.class);
        Call<ArrayList<Contact>> call = service.getContacts(loggedUserId);
        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                contactList = response.body();
                UpdateList();
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                contactList = new ArrayList<Contact>();
                UpdateList();
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        UpdateData();
      //  UpdateList();

    }
    @Override
    protected void onStop(){
        super.onStop();

        db.contactDao().deleteTable();
        db.contactDao().vacuumDb(new SimpleSQLiteQuery("VACUUM"));
        db.contactDao().insertAll(contactList);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
     //   RemoveToken();

    }
    private void UpdateList() {

        adapter = new CustomListAdapter(getApplicationContext(), contactList,db);
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}
