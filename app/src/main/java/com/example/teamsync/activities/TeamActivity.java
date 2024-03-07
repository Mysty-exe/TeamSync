package com.example.teamsync.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.teamsync.R;
import com.example.teamsync.fragments.eventsFragment;
import com.example.teamsync.fragments.homeFragment;
import com.example.teamsync.fragments.settingsFragment;
import com.example.teamsync.fragments.statsFragment;
import com.example.teamsync.fragments.teamFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class TeamActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Gson gson = new Gson();
    BottomNavigationView menu;
    FloatingActionButton profileFab;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        menu = findViewById(R.id.bottom_navigation);
        profileFab = findViewById(R.id.profileFab);

        if (savedInstanceState != null) {
            currentFragment = fragmentManager.getFragment(savedInstanceState, "Fragment");
            replaceFragment(currentFragment);
        } else {
            commenceNavigation(menu.getSelectedItemId());
        }

        menu.setOnItemSelectedListener(item -> {
            commenceNavigation(item.getItemId());
            return true;
        });

        profileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TeamActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.profile) {
                            startActivity(new Intent(TeamActivity.this, UserSettingsActivity.class));
                        } else if (item.getItemId() == R.id.logout) {
                            HomePageActivity.clearCurrentAcc();
                            startActivity(new Intent(TeamActivity.this, HomePageActivity.class));
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManager.putFragment(outState, "Fragment", currentFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private void commenceNavigation(int item) {
        if (item == R.id.home) {
            goHome();
        } else if (item == R.id.events) {
            goEvents();
        } else if (item == R.id.stats) {
            goStats();
        } else if (item == R.id.team) {
            goTeam();
        } else if (item == R.id.settings) {
            goSettings();
        }
    }

    private void goHome() {
        Fragment homeFragment = new homeFragment();
        currentFragment = homeFragment;
        replaceFragment(homeFragment);
    }

    private void goEvents() {
        Fragment eventsFragment = new eventsFragment();
        currentFragment = eventsFragment;
        replaceFragment(eventsFragment);
    }

    private void goStats() {
        Fragment statsFragment = new statsFragment();
        currentFragment = statsFragment;
        replaceFragment(statsFragment);
    }

    private void goTeam() {
        Fragment teamFragment = new teamFragment();
        currentFragment = teamFragment;
        replaceFragment(teamFragment);
    }

    private void goSettings() {
        Fragment settingsFragment = new settingsFragment();
        currentFragment = settingsFragment;
        replaceFragment(settingsFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.commit();
    }
}