package com.example.myapplication5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {

    AppDB db;
    ArrayList<Message> messageList;
    ListView listView;
    MessageListAdapter adapter;
    String loggedUserId;
    String contactId;
    EditText msgText;
    Button sendButton;
    ImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_list);
        Intent activityIntent = getIntent();
        loggedUserId = activityIntent.getStringExtra("loggedUserId");
        contactId = activityIntent.getStringExtra("contactId");
        msgText = (EditText) findViewById(R.id.message_form);
        sendButton = (Button) findViewById(R.id.submit_form);
        profilePic = (ImageView)  findViewById(R.id.top_bar_image);
        messageList = new ArrayList<Message>();

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDB.class, "ContactsDB")
                .allowMainThreadQueries()
                .build();
        try {
            List<UserPicture> userPicture = db.UserPictureDao().getUserPicture(contactId);
            if (userPicture.size() != 0) {
                profilePic.setImageURI(Uri.parse(userPicture.get(0).getPath()));
            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        List<Message> queryResult = db.messagesDao().getConversion(loggedUserId,contactId);
        messageList.addAll(queryResult);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
                SendMessageObj newMsj = new SendMessageObj(msgText.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ContactsAPI service = retrofit.create(ContactsAPI.class);
                Call<SendMessageObj> postMsg = service.PostMessage(contactId,loggedUserId,newMsj);
                postMsg.enqueue(new Callback<SendMessageObj>() {
                    @Override
                    public void onResponse(Call<SendMessageObj> call, Response<SendMessageObj> response) {
                        msgText.setText("");
                        UpdateData();
                    }

                    @Override
                    public void onFailure(Call<SendMessageObj> call, Throwable t) {
                    }
                });
            }
    });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateData();
    }

    private void UpdateData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
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
}
    @Override
    protected void onStop(){
        super.onStop();
        db.messagesDao().deleteConversation(loggedUserId,contactId);
        db.messagesDao().vacuumDb(new SimpleSQLiteQuery("VACUUM"));
        for(int i =0;i<messageList.size();i++){
            messageList.get(i).setBaseUser(loggedUserId);
            messageList.get(i).setContactName(contactId);
        }
        db.messagesDao().insertAll((List<Message>)messageList);
    }
    private void UpdateList() {
        listView = findViewById(R.id.list_view);
        adapter = new MessageListAdapter(getApplicationContext(), messageList);
        listView.setAdapter(adapter);
       // listView.setClickable(true);
    }
    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}
