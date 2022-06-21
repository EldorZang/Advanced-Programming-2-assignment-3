package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {


    ArrayList<Message> messageList;
    ListView listView;
    MessageListAdapter adapter;
    String loggedUserId;
    String contactId;
    EditText msgText;
    Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_list);
        Intent activityIntent = getIntent();
        loggedUserId = activityIntent.getStringExtra("loggedUserId");
        contactId = activityIntent.getStringExtra("contactId");
        msgText = (EditText) findViewById(R.id.message_form);
        sendButton = (Button) findViewById(R.id.submit_form);
        messageList = new ArrayList<Message>();
        // Http get to get all contacts.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5067")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContactsAPI service = retrofit.create(ContactsAPI.class);
        Call<ArrayList<Message>> call = service.getMessages(contactId,loggedUserId);
        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                messageList = response.body();
                UpdateList();
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                messageList = new ArrayList<Message>();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessageObj newMsj = new SendMessageObj(msgText.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:5067")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ContactsAPI service = retrofit.create(ContactsAPI.class);
                Call<SendMessageObj> postMsg = service.PostMessage(contactId,loggedUserId,newMsj);
                postMsg.enqueue(new Callback<SendMessageObj>() {
                    @Override
                    public void onResponse(Call<SendMessageObj> call, Response<SendMessageObj> response) {
                        msgText.setText("");
                        UpdateList();
                    }

                    @Override
                    public void onFailure(Call<SendMessageObj> call, Throwable t) {
                    }
                });
            }
    });
    }


    private void UpdateList() {
        listView = findViewById(R.id.list_view);
        adapter = new MessageListAdapter(getApplicationContext(), messageList);
        listView.setAdapter(adapter);
       // listView.setClickable(true);
    }
}
