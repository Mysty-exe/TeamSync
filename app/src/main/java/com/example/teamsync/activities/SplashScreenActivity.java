package com.example.teamsync.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private SplashScreen splashScreen;
    private Handler handler = new Handler();
    private boolean keep = true;
    private final int DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
            @Override
            public boolean shouldKeepOnScreen() {
                return keep;
            }
        });
        handler.postDelayed(runnable, DELAY);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            keep = false;
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    };
}
