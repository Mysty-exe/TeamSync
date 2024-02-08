package com.example.teamsync.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teamsync.R;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {

    MaterialButton profileBtn, notiBtn, delAccBtn;
    Gson gson = new Gson();
    private AlertDialog.Builder builder;

    private void confirmDialog() {
        builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Delete Announcement");
        builder.setMessage("Are you sure you want to delete this announcement?");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileBtn = findViewById(R.id.profileBtn);
        notiBtn = findViewById(R.id.notiBtn);
        delAccBtn = findViewById(R.id.delAccBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Profile Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Notification Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        delAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String currentId = LoginActivity.getCurrentAcc().getId();
                                LoginActivity.clearCurrentAcc();
                                LoginActivity.deleteAccount(currentId);
                                deleteUser();
                                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void deleteUser() {
        SharedPreferences sharedPrefs = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        String accountsJson = gson.toJson(LoginActivity.getAccounts());
        String teamsJson = gson.toJson(LoginActivity.getTeams());
        prefsEditor.putString("Accounts", accountsJson);
        prefsEditor.putString("Teams", teamsJson);
        if (LoginActivity.currentAcc != null) {
            String accountJson = gson.toJson(LoginActivity.getCurrentAcc());
            prefsEditor.putString("Account", accountJson);
        } else {
            prefsEditor.remove("Account");
        }
        prefsEditor.apply();
    }
}