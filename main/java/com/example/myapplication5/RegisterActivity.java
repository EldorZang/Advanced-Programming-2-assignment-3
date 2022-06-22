package com.example.myapplication5;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private static int PICK_IMAGE = 1;
    private RegisterAPI registerAPI;
    private RegisterInput registrationInfo;
    private TextView invalidInfoView;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uri = null;
        this.registerAPI = new RegisterAPI();
        this.invalidInfoView = findViewById(R.id.textViewRegisterInvalidInfo);

        Button loginButton = findViewById(R.id.buttonToLogin);
        loginButton.setOnClickListener(view -> {
            /*
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
             */
            this.finish();
        });

        ImageButton imageSelect = findViewById(R.id.userImage);
        imageSelect.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE);
        });

        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(view -> {
            this.invalidInfoView.setVisibility(View.INVISIBLE);
            String id = ((EditText) findViewById(R.id.editTextRegisterID)).getText().toString();
            String nickname = ((EditText) findViewById(R.id.editTextRegisterNickname)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextRegisterPassword)).getText().toString();
            String confirm = ((EditText) findViewById(R.id.editTextRegisterConfirmPassword)).getText().toString();
            if (!password.matches("^(?=.*[a-zA-Z0-9!@#$%^&*.-_=+]).{8,16}")) {
                Toast.makeText(getBaseContext(), "Passwords must be 8-16 characters!", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirm)) {
                Toast.makeText(getBaseContext(), "Passwords Must Match!", Toast.LENGTH_SHORT).show();
            }
            else {
                registrationInfo = new RegisterInput(id,nickname,password);
                checkIfUserNameAvailable(id);
            }
        });


    }
    public void checkIfUserNameAvailable(String id){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit   retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI= retrofit.create(WebServiceAPI.class);
        Call<ResponseBody> checkRegisterCall = webServiceAPI.checkNewUser(id);
        checkRegisterCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                register();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                invalidInfoView.setVisibility(View.VISIBLE);
            }
        });
    }
    public void register(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MessagingApp.getAppContext());
        Retrofit   retrofit = new Retrofit.Builder()
                .baseUrl(validateUrl(sharedPreferences.getString("server_link", MessagingApp.getAppContext().getString(R.string.BaseUrl))))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI= retrofit.create(WebServiceAPI.class);
        Call<ResponseBody> checkRegisterCall = webServiceAPI.registerNewUser(registrationInfo);
        checkRegisterCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(uri != null) {
                    AppDB db = Room.databaseBuilder(getApplicationContext(),
                                    AppDB.class, "ContactsDB")
                            .allowMainThreadQueries()
                            .build();
                    db.UserPictureDao().insert(new UserPicture(registrationInfo.getId(), uri.toString()));
                }
                Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
                intent.putExtra("loggedUserId",registrationInfo.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == PICK_IMAGE) {
            if(Data == null){
                uri = null;
                return;
            }
             uri = Data.getData();
            ImageButton imageSelect = findViewById(R.id.userImage);
            imageSelect.setImageURI(uri);
            ContentResolver cr = MessagingApp.getAppContext().getContentResolver();
            cr.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
    public String validateUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}