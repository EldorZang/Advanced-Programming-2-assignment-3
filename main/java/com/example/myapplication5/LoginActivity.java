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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    // private ActivityMainBinding binding;
    // private AppDB appDB;
    // private ListView lvPosts;
    // private List<String> posts;
    // private List<Post> dbPosts;
    // private ArrayAdapter<String> adapter;
    // private PostDao postDao;
    private LoginAPI loginAPI;
    private LoginInput loginInfo;
    private TextView invalidInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // this.appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "PostsDB")
        //         .allowMainThreadQueries().build();
        // this.postDao = this.appDB.postDao();
        // this.dbPosts = this.postDao.index();
        this.loginAPI = new LoginAPI();
        this.invalidInfoView = findViewById(R.id.textViewLoginInvalidInfo);

        // this.lvPosts = findViewById(R.id.lvPosts);

        // handlePosts();

        EditText usernameET = findViewById(R.id.editTextLoginID);
        EditText passwordET = findViewById(R.id.editTextLoginPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(view -> {
            invalidInfoView.setVisibility(View.INVISIBLE);
            String id = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            this.loginInfo = new LoginInput(id, password);
            submitLogin(loginInfo);
        });

        Button registerButton = findViewById(R.id.buttonToRegister);
        registerButton.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });

        FloatingActionButton fab_settings = findViewById(R.id.fab_settings);
        fab_settings.setOnClickListener(view -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        });

    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_nav_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
     */

    public void connectUser(String id) {
        Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
        intent.putExtra("loggedUserId",id);
        startActivity(intent);
    }
    public void submitLogin(LoginInput loginInput){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<ResponseBody> loginCall = webServiceAPI.loginUser(loginInput);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                connectUser(loginInput.getId());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setInvalidInfoView(true);
            }
        });
    }
    public void setInvalidInfoView(boolean visibility){
        if(visibility){
            this.invalidInfoView.setVisibility(View.VISIBLE);
        }else{
            this.invalidInfoView.setVisibility(View.INVISIBLE);
        }
    }
    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}