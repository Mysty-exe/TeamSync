package com.example.teamsync.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamsync.R;
import com.example.teamsync.models.Event;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class EditEventActivity extends AppCompatActivity {

    private ImageView exitBtn;
    private MaterialButton editEventBtn;
    private TextInputLayout eventNameInput, eventsDropdown, addressInput, notesInput;
    private AutoCompleteTextView eventsChoice;
    private TextInputEditText eventNameInputTxt, addressInputTxt, notesInputTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editevent);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("Event");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.7));

        exitBtn = findViewById(R.id.exitBtn);
        eventNameInput = findViewById(R.id.eventNameInput);
        eventNameInputTxt = findViewById(R.id.eventNameInputTxt);
        addressInput = findViewById(R.id.addressInput);
        addressInputTxt = findViewById(R.id.addressInputTxt);
        notesInput = findViewById(R.id.notesInput);
        notesInputTxt = findViewById(R.id.notesInputTxt);
        eventsDropdown = findViewById(R.id.eventsDropdown);
        eventsChoice = findViewById(R.id.eventChoice);
        editEventBtn = findViewById(R.id.editEventBtn);

        eventNameInputTxt.setText(event.getName());
        eventsChoice.setText(event.getType());
        addressInputTxt.setText(event.getPlace());
        notesInputTxt.setText(event.getNotes());

        ArrayList<String> eventTypes = new ArrayList<>();
        eventTypes.add("Meeting");
        eventTypes.add("Practice");
        eventTypes.add("Game");
        eventTypes.add("Other");

        ArrayAdapter<String> eventTypesAdapter = new ArrayAdapter<>(
                this, R.layout.item_adapter, eventTypes
        );
        eventsChoice.setAdapter(eventTypesAdapter);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (eventNameInputTxt.getText().toString().trim().isEmpty()) {
                    eventNameInput.setError("You must enter an Event Name");
                    error = true;
                } else {
                    eventNameInput.setError(null);
                }

                if (addressInputTxt.getText().toString().trim().isEmpty()) {
                    addressInput.setError("You must enter an Address");
                    error = true;
                } else {
                    addressInput.setError(null);
                }

                if (!error) {
                    event.setName(eventNameInputTxt.getText().toString().trim());
                    event.setType(eventsChoice.getText().toString());
                    event.setPlace(addressInputTxt.getText().toString().trim());
                    event.setNotes(notesInputTxt.getText().toString().trim());
                    editEvent(event);
                    Toast.makeText(EditEventActivity.this, "Event Edited", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void editEvent(Event event) {
        ArrayList<Event> events = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam()).getEvents();
        for(int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(event.getId())) {
                events.set(i, event);
            }
        }
        HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam()).setEvents(events);
    }
}
