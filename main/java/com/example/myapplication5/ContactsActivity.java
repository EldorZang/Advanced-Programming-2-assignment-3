package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.iid.FirebaseInstanceId;

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
        // Http get to get all contacts.
/*
        UpdateData();
        UpdateList();
*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);

                intent.putExtra("loggedUserId", loggedUserId);
                intent.putExtra("contactId", contactList.get(i).getId());
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



    }
    private void UpdateData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5067")
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
    private void UpdateList() {

        db.contactDao().deleteTable();
        db.contactDao().vacuumDb(new SimpleSQLiteQuery("VACUUM"));
        db.contactDao().insertAll(contactList);
        adapter = new CustomListAdapter(getApplicationContext(), contactList);
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
}