package com.example.teamsync.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.example.teamsync.R;
import com.example.teamsync.models.Team;
import com.example.teamsync.adapters.TeamsRecViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView teamsRecView;
    private TeamsRecViewAdapter adapter = new TeamsRecViewAdapter(this);
    public TeamsRecViewAdapter getAdapter() {
        return adapter;
    }
    private FloatingActionButton profileFab, addFab;
    private Handler handler = new Handler();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomePageActivity.getCurrentAcc().setActiveTeam(null);
        teamsRecView = findViewById(R.id.teamsRecView);

        adapter.setTeamIds(HomePageActivity.getCurrentAcc().getTeamIds());
        teamsRecView.setAdapter(adapter);
        teamsRecView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(teamsRecView);

        checkTeams();

        addFab = findViewById(R.id.addFab);
        profileFab = findViewById(R.id.profileFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTeamActivity.class));
            }
        });
        profileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.settings) {
                            startActivity(new Intent(MainActivity.this, UserSettingsActivity.class));
                        } else if (item.getItemId() == R.id.logout) {
                            HomePageActivity.clearCurrentAcc();
                            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.profile, popup.getMenu());
                popup.show();
            }
        });
        handler.post(runnable);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(adapter.getTeamIds(), fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
            return false;
        };

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkTeams();
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPrefs = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        String accountsJson = gson.toJson(HomePageActivity.getAccounts());
        String teamsJson = gson.toJson(HomePageActivity.getTeams());
        prefsEditor.putString("Accounts", accountsJson);
        prefsEditor.putString("Teams", teamsJson);
        if (HomePageActivity.currentAcc != null) {
            String accountJson = gson.toJson(HomePageActivity.getCurrentAcc());
            prefsEditor.putString("Account", accountJson);
        } else {
            Log.d("error", "Removing");
            prefsEditor.remove("Account");
        }
        prefsEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTeamIds(HomePageActivity.getCurrentAcc().getTeamIds());
        checkTeams();
    }

    public void addTeam(Team team) {
        ArrayList<String> teamIds = HomePageActivity.getCurrentAcc().getTeamIds();
        teamIds.add(team.getId());
        HomePageActivity.getCurrentAcc().setTeamIds(teamIds);

        ArrayList<Team> teams = HomePageActivity.getTeams();
        teams.add(team);
        HomePageActivity.setTeams(teams);
    }

    public void editTeam(Team team) {
        ArrayList<Team> teams = HomePageActivity.getTeams();
        for(int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getId().equals(team.getId())) {
                teams.set(i, team);
            }
        }
        HomePageActivity.setTeams(teams);
    }

    public void checkTeams() {
        RelativeLayout notFoundGroup = findViewById(R.id.notFoundGroup);
        if (HomePageActivity.getCurrentAcc() != null) {
            if (HomePageActivity.getCurrentAcc().getTeamIds().isEmpty()) {
                notFoundGroup.setVisibility(View.VISIBLE);
            } else {
                notFoundGroup.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
