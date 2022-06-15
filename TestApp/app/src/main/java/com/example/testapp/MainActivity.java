package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

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
            if (this.loginAPI.loginUser(this.loginInfo)) {
                connectUser();
            }
            else {
                this.invalidInfoView.setVisibility(View.VISIBLE);
            }
        });

        Button registerButton = findViewById(R.id.buttonToRegister);
        registerButton.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    public void connectUser() {
        // login information is valid, login the user
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void handlePosts() {
        lvPosts = binding.lvPosts;
        posts = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,posts);

        loadPosts();
        lvPosts.setAdapter(adapter);
        lvPosts.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("id", dbPosts.get(i).getId());
            startActivity(intent);
        });

        lvPosts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            posts.remove(i);
            Post post = dbPosts.remove(i);
            postDao.delete(post);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void loadPosts() {
        posts.clear();
        dbPosts = postDao.index();
        for (Post post : dbPosts) {
            posts.add(post.getId() + "," + post.getContent());
        }
        adapter.notifyDataSetChanged();
    }

     */
}