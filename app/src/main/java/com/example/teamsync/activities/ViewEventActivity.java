package com.example.teamsync.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teamsync.R;
import com.example.teamsync.models.Event;

public class ViewEventActivity extends Activity {
    RelativeLayout notes;
    ImageView exitBtn;
    TextView eventTxt, typeTxt, timeTxt, attendanceTxt, addressTxt, notesTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewevent);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("Event");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.6));

        exitBtn = findViewById(R.id.exitBtn);
        eventTxt = findViewById(R.id.eventNameTxt);
        typeTxt = findViewById(R.id.eventTypeTxt);
        timeTxt = findViewById(R.id.timeTxt);
        attendanceTxt = findViewById(R.id.attendanceTxt);
        addressTxt = findViewById(R.id.placeTxt);
        notes = findViewById(R.id.notes);
        notesTxt = findViewById(R.id.notesTxt);

        eventTxt.setText(event.getName());
        typeTxt.setText(event.getType());
        timeTxt.setText(event.getTime());
        addressTxt.setText(event.getPlace());
        notesTxt.setText(event.getNotes());

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
