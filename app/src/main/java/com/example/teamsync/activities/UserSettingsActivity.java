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

public class UserSettingsActivity extends AppCompatActivity {

    MaterialButton profileBtn, notiBtn, delAccBtn;
    Gson gson = new Gson();
    private AlertDialog.Builder builder;

    private void confirmDialog() {
        builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Delete Announcement");
        builder.setMessage("Are you sure you want to delete this account?");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);

        profileBtn = findViewById(R.id.profileBtn);
        notiBtn = findViewById(R.id.notiBtn);
        delAccBtn = findViewById(R.id.delAccBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserSettingsActivity.this, "Profile Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserSettingsActivity.this, "Notification Button Clicked", Toast.LENGTH_SHORT).show();
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
                                String currentId = HomePageActivity.getCurrentAcc().getId();
                                HomePageActivity.clearCurrentAcc();
                                HomePageActivity.deleteAccount(currentId);
                                deleteUser();
                                startActivity(new Intent(UserSettingsActivity.this, HomePageActivity.class));
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
        String accountsJson = gson.toJson(HomePageActivity.getAccounts());
        String teamsJson = gson.toJson(HomePageActivity.getTeams());
        prefsEditor.putString("Accounts", accountsJson);
        prefsEditor.putString("Teams", teamsJson);
        if (HomePageActivity.getCurrentAcc() != null) {
            String accountJson = gson.toJson(HomePageActivity.getCurrentAcc());
            prefsEditor.putString("Account", accountJson);
        } else {
            prefsEditor.remove("Account");
        }
        prefsEditor.apply();
    }
}