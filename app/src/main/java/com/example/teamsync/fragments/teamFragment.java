package com.example.teamsync.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.teamsync.R;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.adapters.AnnouncementsRecViewAdapter;
import com.example.teamsync.adapters.CoachesRecViewAdapter;
import com.example.teamsync.adapters.PlayersRecViewAdapter;
import com.example.teamsync.models.Team;

public class teamFragment extends Fragment {

    private View view;
    private RelativeLayout coachesGroup, playersGroup;
    private RecyclerView coachesRecView, playersRecView;
    private CoachesRecViewAdapter coachesAdapter;
    private PlayersRecViewAdapter playersAdapter;
    public CoachesRecViewAdapter getCoachesAdapter() {
        return coachesAdapter;
    }
    public PlayersRecViewAdapter getPlayersAdapter() {
        return playersAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team, container, false);
        Team team = LoginActivity.getTeam(LoginActivity.getCurrentAcc().getActiveTeam());

        coachesGroup = view.findViewById(R.id.coaches);
        playersGroup = view.findViewById(R.id.players);
        coachesRecView = view.findViewById(R.id.coachesRecView);
        playersRecView = view.findViewById(R.id.playersRecView);

        coachesAdapter = new CoachesRecViewAdapter(getContext());
        coachesAdapter.setCoaches(team.getCoaches());
        coachesRecView.setAdapter(coachesAdapter);
        coachesRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        playersAdapter = new PlayersRecViewAdapter(getContext());
        playersAdapter.setPlayers(team.getPlayers());
        coachesRecView.setAdapter(playersAdapter);
        coachesRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (team.getCoaches().isEmpty()) {
            coachesGroup.setVisibility(View.GONE);
        } if (team.getPlayers().isEmpty()) {
            playersGroup.setVisibility(View.GONE);
        }

        return view;
    }
}