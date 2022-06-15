package com.example.testapp;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    private static int PICK_IMAGE = 1;
    private RegisterAPI registerAPI;
    private RegisterInput registrationInfo;
    private TextView invalidInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            i.setAction(Intent.ACTION_GET_CONTENT);
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
                Toast.makeText(getBaseContext(), "Invalid Password!", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirm)) {
                Toast.makeText(getBaseContext(), "Passwords Must Match!", Toast.LENGTH_SHORT).show();
            }
            else {
                this.registrationInfo = new RegisterInput(id, nickname, password);
                if (this.registerAPI.registerCheckUser(id)) {
                    this.registerAPI.registerUser(this.registrationInfo);
                    finish();
                }
                else {
                    this.invalidInfoView.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == PICK_IMAGE) {
            assert Data != null;
            Uri uri = Data.getData();
            ImageButton imageSelect = findViewById(R.id.userImage);
            imageSelect.setImageURI(uri);
        }
    }
}