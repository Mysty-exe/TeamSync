package com.example.teamsync.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.activities.AddEventActivity;
import com.example.teamsync.activities.HomePageActivity;
import com.example.teamsync.adapters.EventsRecViewAdapter;
import com.example.teamsync.models.Event;
import com.example.teamsync.models.Team;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class eventsFragment extends Fragment {

    private View view;
    private EventsRecViewAdapter adapter;
    private RelativeLayout notFoundGroup;
    private RecyclerView eventsRecyclerView;
    private CalendarView calendarView;
    private FloatingActionButton addEventFab;
    private TextView dateTxt, numEventsTxt;
    private Handler handler = new Handler();
    private Team team;
    String currentCalDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        team = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam());

        Calendar calTd = Calendar.getInstance();
        Calendar calDate = Calendar.getInstance();
        currentCalDate = DateFormat.format("MMMM dd, yyyy", calDate).toString();

        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        calendarView = view.findViewById(R.id.calendar);
        dateTxt = view.findViewById(R.id.dateTxt);
        numEventsTxt = view.findViewById(R.id.numEventsTxt);
        notFoundGroup = view.findViewById(R.id.notFoundGroup);
        addEventFab = view.findViewById(R.id.addEventFab);

        adapter = new EventsRecViewAdapter(getContext());
        updateRecycler(team);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        checkEvents();
        numEventsTxt.setText(Event.getEventsForDay(team.getEvents(), currentCalDate).size() + " Activities");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calDate.set(year, month, dayOfMonth);
                currentCalDate = DateFormat.format("MMMM dd, yyyy", calDate).toString();

                String date;
                int currentMonth = calTd.get(Calendar.MONTH);
                int currentDay = calTd.get(Calendar.DAY_OF_MONTH);
                int currentYear = calTd.get(Calendar.YEAR);

                if (currentMonth == month && currentDay == dayOfMonth && currentYear == year) {
                    date = "Today";
                } else if (currentMonth == month && currentYear == year && currentDay == (dayOfMonth - 1)) {
                    date = "Tomorrow";
                } else if (currentMonth == month && currentYear == year && currentDay == (dayOfMonth + 1)) {
                    date = "Yesterday";
                } else if (currentYear != year) {
                    date = currentCalDate;
                } else {
                    date = DateFormat.format("MMMM dd", calDate).toString();
                }

                dateTxt.setText(date);
                updateRecycler(team);
                }
        });

        addEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calDate.before(calTd)) {
                    Toast.makeText(getContext(), "Can't Create an Event in the Past", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                intent.putExtra("Date", currentCalDate);
                startActivity(intent);
            }
        });
        handler.post(runnable);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        team = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam());
        updateRecycler(team);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkEvents();
            numEventsTxt.setText(Event.getEventsForDay(team.getEvents(), currentCalDate).size() + " Activities");
            handler.postDelayed(runnable, 100);
        }
    };

    public void updateRecycler(Team team) {
        adapter.setEvents(Event.getEventsForDay(team.getEvents(), currentCalDate));
    }

    public void checkEvents() {
        notFoundGroup = view.findViewById(R.id.notFoundGroup);
        if (HomePageActivity.getCurrentAcc() != null) {
            if (Event.getEventsForDay(team.getEvents(), currentCalDate).isEmpty()) {
                notFoundGroup.setVisibility(View.VISIBLE);
            } else {
                notFoundGroup.setVisibility(View.GONE);
            }
        }
    }
}
