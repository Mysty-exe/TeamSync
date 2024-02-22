package com.example.teamsync.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.teamsync.R;
import com.example.teamsync.activities.HomePageActivity;
import com.example.teamsync.adapters.AnnouncementsRecViewAdapter;
import com.example.teamsync.models.Announcement;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class homeFragment extends Fragment {
    private RecyclerView announcementsRecView;
    private LinearLayout announcementBtns;
    private RelativeLayout notFound;
    private TextInputLayout announcementInput;
    private TextInputEditText announcementTxt;
    private MaterialButton cancelBtn, postBtn;
    private TextView teamName, coachName;
    private AnnouncementsRecViewAdapter adapter;
    public AnnouncementsRecViewAdapter getAdapter() {
        return adapter;
    }
    private Handler handler = new Handler();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Team team = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam());

        announcementsRecView = view.findViewById(R.id.announcementsRecView);
        announcementInput = view.findViewById(R.id.announcementInput);
        announcementTxt = view.findViewById(R.id.announcementInputTxt);
        announcementBtns = view.findViewById(R.id.announcementBtns);
        notFound = view.findViewById(R.id.notFoundGroup);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        postBtn = view.findViewById(R.id.postBtn);
        teamName = view.findViewById(R.id.teamNameTxt);
        coachName = view.findViewById(R.id.coachTxt);

        teamName.setText(team.getName());
        coachName.setText(HomePageActivity.getPersonObj(team.getCoach()).getFullName());

        if (team.getAnnouncements().isEmpty()) {
            notFound.setVisibility(View.VISIBLE);
        } else {
            notFound.setVisibility(View.INVISIBLE);
        }

        adapter = new AnnouncementsRecViewAdapter(getContext());
        adapter.setAnnouncements(team.getAnnouncements());
        announcementsRecView.setAdapter(adapter);
        announcementsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        announcementTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    announcementBtns.setVisibility(View.VISIBLE);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcementBtns.setVisibility(View.GONE);
                announcementTxt.setText("");
                hideKeyboardFrom(getContext(), v);
                announcementInput.clearFocus();
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (announcementTxt.getText().toString().trim().isEmpty()) {
                    return;
                }
                Date currentTime = Calendar.getInstance().getTime();
                boolean isPM = (currentTime.getHours() >= 12);
                String time = String.format("%02d:%02d %s", (currentTime.getHours() == 12 || currentTime.getHours() == 0) ? 12 : currentTime.getHours() % 12, currentTime.getMinutes(), isPM ? "PM" : "AM");
                Announcement announcement = new Announcement(HomePageActivity.getCurrentAcc().getFullName(), time, announcementTxt.getText().toString(), team.getId());
                ArrayList<Announcement> announcements = HomePageActivity.getTeam(team.getId()).getAnnouncements();;
                announcements.add(0, announcement);
                adapter.setAnnouncements(announcements);
                announcementTxt.setText("");
                hideKeyboardFrom(getContext(), v);
                announcementInput.clearFocus();
                announcementBtns.setVisibility(View.GONE);
                notFound.setVisibility(View.INVISIBLE);
                HomePageActivity.getTeam(team.getId()).setAnnouncements(announcements);
            }
        });
        handler.post(runnable);

        return view;
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkAnnouncements();
            handler.postDelayed(runnable, 100);
        }
    };

    public void checkAnnouncements() {
        RelativeLayout notFoundGroup = view.findViewById(R.id.notFoundGroup);
        Team team = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam());
        if (team != null) {
            if (team.getAnnouncements().isEmpty()) {
                notFoundGroup.setVisibility(View.VISIBLE);
            } else {
                notFoundGroup.setVisibility(View.GONE);
            }
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
