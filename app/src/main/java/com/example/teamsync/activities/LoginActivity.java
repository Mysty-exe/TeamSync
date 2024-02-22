package com.example.teamsync.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.teamsync.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {

    Gson gson = new Gson();
    private TextInputLayout email, password;
    private TextInputEditText emailTxt, passwordTxt;
    private MaterialButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLoginInput);
        emailTxt = findViewById(R.id.emailLoginInputTxt);
        password = findViewById(R.id.passwordLoginInput);
        passwordTxt = findViewById(R.id.passwordLoginInputTxt);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomePageActivity.accountExists(emailTxt.getText().toString(), passwordTxt.getText().toString())) {
                    HomePageActivity.setCurrentAcc(HomePageActivity.getRequestedAccount(emailTxt.getText().toString(), passwordTxt.getText().toString()));
                    SharedPreferences sharedPrefs = getSharedPreferences("Data", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
                    String json = gson.toJson(HomePageActivity.getCurrentAcc());
                    prefsEditor.putString("Account", json).apply();

                    Toast.makeText(LoginActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    email.setError("Invalid Email or Password");
                    password.setError("Invalid Email or Password");
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}