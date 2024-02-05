package com.example.teamsync.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teamsync.R;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class AddTeamActivity extends Activity {

    private ImageView exitBtn;
    private TextInputEditText teamNameTxt;
    private TextInputLayout sportsDropdown;
    private AutoCompleteTextView sportChoice;
    private TextView ageTxt;
    private RadioGroup ageGroup;
    private TextView genderTxt;
    private RadioGroup genderGroup;
    private MaterialButton addTeamBtn;
    private final MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team_popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));

        teamNameTxt = findViewById(R.id.teamNameInputTxt);
        sportsDropdown = findViewById(R.id.sportsDropdown);
        sportChoice = findViewById(R.id.sportChoice);
        ageTxt = findViewById(R.id.ageTxt);
        ageGroup = findViewById(R.id.ageRadioGroup);
        genderTxt = findViewById(R.id.genderTxt);
        genderGroup = findViewById(R.id.genderRadioGroup);
        addTeamBtn = findViewById(R.id.addTeamBtn);
        exitBtn = findViewById(R.id.exitBtn);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> sports = new ArrayList<>();
        sports.add("Running");
        sports.add("Hockey");
        sports.add("Ringette");
        sports.add("Football");

        ArrayAdapter<String> sportsAdapter = new ArrayAdapter<>(
                this, R.layout.sports_list_item, sports
        );
        sportChoice.setAdapter(sportsAdapter);

        addTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (Objects.requireNonNull(teamNameTxt.getText()).toString().trim().isEmpty()) {
                    teamNameTxt.setError("Enter Team Name");
                    error = true;
                } else {
                    teamNameTxt.setError(null);
                }

                if (sportChoice.getText().toString().isEmpty()) {
                    sportsDropdown.setError("Choose a Sport");
                    error = true;
                } else {
                    sportsDropdown.setError(null);
                }

                RadioButton ageChoice = findViewById(ageGroup.getCheckedRadioButtonId());
                if (ageChoice == null) {
                    ageTxt.requestFocus();
                    ageTxt.setError("Choose an Age");
                    error = true;
                } else {
                    ageTxt.setError(null);
                }

                RadioButton genderChoice = findViewById(genderGroup.getCheckedRadioButtonId());
                if (genderChoice == null) {
                    genderTxt.requestFocus();
                    genderTxt.setError("Choose a Gender");
                    error = true;
                } else {
                    genderTxt.setError(null);
                }

                if (error) {
                    return;
                }

                String teamName = teamNameTxt.getText().toString().trim();
                String sport = sportChoice.getText().toString();
                String age = ageChoice.getText().toString();
                String gender = genderChoice.getText().toString();
                Team team = new Team(teamName, sport, LoginActivity.getCurrentAcc().getId(), age, gender);
                mainActivity.addTeam(team);
                finish();
            }
        });
    }
}
