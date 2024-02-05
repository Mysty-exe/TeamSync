package com.example.teamsync.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teamsync.R;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    MaterialButton profileBtn, notiBtn, delAccBtn;

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
                Toast.makeText(SettingsActivity.this, "Delete Account Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}