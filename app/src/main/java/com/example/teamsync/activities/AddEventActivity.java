package com.example.teamsync.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamsync.R;
import com.example.teamsync.models.Event;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddEventActivity extends AppCompatActivity {
    private ImageView exitBtn;
    private MaterialButton setTimeBtn, addEventBtn;
    private TextInputLayout eventNameInput, eventsDropdown, addressInput, notesInput;
    private AutoCompleteTextView eventsChoice;
    private TextInputEditText eventNameInputTxt, addressInputTxt, notesInputTxt;
    private TextView timeHeaderTxt, timeTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_popupwindow);

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));

        exitBtn = findViewById(R.id.exitBtn);
        eventNameInput = findViewById(R.id.eventNameInput);
        eventNameInputTxt = findViewById(R.id.eventNameInputTxt);
        addressInput = findViewById(R.id.addressInput);
        addressInputTxt = findViewById(R.id.addressInputTxt);
        timeHeaderTxt = findViewById(R.id.timeHeadingTxt);
        timeTxt = findViewById(R.id.timeTxt);
        notesInput = findViewById(R.id.notesInput);
        notesInputTxt = findViewById(R.id.notesInputTxt);
        eventsDropdown = findViewById(R.id.eventsDropdown);
        eventsChoice = findViewById(R.id.eventChoice);
        setTimeBtn = findViewById(R.id.setTimeBtn);
        addEventBtn = findViewById(R.id.addEventBtn);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> eventTypes = new ArrayList<>();
        eventTypes.add("Meet");
        eventTypes.add("Practice");
        eventTypes.add("Game");
        eventTypes.add("Tournament");
        eventTypes.add("Other");

        ArrayAdapter<String> eventTypesAdapter = new ArrayAdapter<>(
                this, R.layout.adapter_list_item, eventTypes
        );
        eventsChoice.setAdapter(eventTypesAdapter);

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(currentTime.getHours())
                        .setMinute(currentTime.getMinutes())
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setTitleText("Set Event Time")
                        .setTheme(R.style.TimePicker)
                        .build();

                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        String currentDate = DateFormat.format("MMMM dd, yyyy", calendar).toString();
                        Date currentTime = Calendar.getInstance().getTime();
                        Date chosenTime = new Date();
                        int hourOfDay = materialTimePicker.getHour();
                        int minute = materialTimePicker.getMinute();
                        chosenTime.setHours(hourOfDay);
                        chosenTime.setMinutes(minute);
                        if (currentDate.equals(date) && chosenTime.before(currentTime)) {
                            Toast.makeText(AddEventActivity.this, "Can't Create an Event in the Past", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        timeTxt.setVisibility(View.VISIBLE);
                        setTimeBtn.setText("Change Time");
                        boolean isPM = (hourOfDay >= 12);
                        timeTxt.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                    }
                });

                materialTimePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (Objects.requireNonNull(eventNameInputTxt.getText()).toString().trim().isEmpty()) {
                    eventNameInput.setError("Enter Event Name");
                    error = true;
                } else {
                    eventNameInput.setError(null);
                }

                if (eventsChoice.getText().toString().isEmpty()) {
                    eventsDropdown.setError("Choose a type of Event");
                    error = true;
                } else {
                    eventsDropdown.setError(null);
                }

                if (timeTxt.getText().toString().isEmpty()) {
                    timeHeaderTxt.setError("Set a time");
                    error = true;
                } else {
                    timeHeaderTxt.setError(null);
                }

                if (Objects.requireNonNull(addressInputTxt.getText()).toString().trim().isEmpty()) {
                    addressInput.setError("Enter Address");
                    error = true;
                } else {
                    addressInput.setError(null);
                }
                
                if (!error) {
                    Event event = new Event(date, eventNameInputTxt.getText().toString().trim(), eventsChoice.getText().toString().trim(), timeTxt.getText().toString(), addressInputTxt.getText().toString().trim());
                    event.setNotes(notesInputTxt.getText().toString().trim());
                    addEvent(event);
                    finish();
                }
            }
        });
    }

    public void addEvent(Event event) {
        ArrayList<Event> events = LoginActivity.getTeam(LoginActivity.getCurrentAcc().getActiveTeam()).getEvents();
        events.add(event);
        LoginActivity.getTeam(LoginActivity.getCurrentAcc().getActiveTeam()).setEvents(events);
    }
}