package com.example.teamsync.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.adapters.AnnouncementsRecViewAdapter;
import com.example.teamsync.adapters.TeamsRecViewAdapter;
import com.example.teamsync.fragments.eventsFragment;
import com.example.teamsync.fragments.homeFragment;
import com.example.teamsync.fragments.statsFragment;
import com.example.teamsync.fragments.teamFragment;
import com.example.teamsync.models.Announcement;
import com.example.teamsync.models.Team;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Gson gson = new Gson();
    BottomNavigationView menu;
    FloatingActionButton profileFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        goHome();

        menu = findViewById(R.id.bottom_navigation);
        profileFab = findViewById(R.id.profileFab);

        menu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                goHome();
            } else if (item.getItemId() == R.id.events) {
                goEvents();
            } else if (item.getItemId() == R.id.stats) {
                goStats();
            } else if (item.getItemId() == R.id.team) {
                goTeam();
            }

            return true;
        });

        profileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TeamActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.settings) {
                            startActivity(new Intent(TeamActivity.this, SettingsActivity.class));
                        } else if (item.getItemId() == R.id.logout) {
                            LoginActivity.clearCurrentAcc();
                            startActivity(new Intent(TeamActivity.this, LoginActivity.class));
                            finish();
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.profile, popup.getMenu());
                popup.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private void goHome() {
        Fragment homeFragment = new homeFragment();
        replaceFragment(homeFragment);
    }

    private void goEvents() {
        Fragment eventsFragment = new eventsFragment();
        replaceFragment(eventsFragment);
    }

    private void goStats() {
        Fragment statsFragment = new statsFragment();
        replaceFragment(statsFragment);
    }

    private void goTeam() {
        Fragment teamFragment = new teamFragment();
        replaceFragment(teamFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.commit();
    }
}