package com.example.teamsync.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teamsync.R;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditTeamActivity extends Activity {
    private ImageView exitBtn;
    private TextInputEditText teamNameTxt;
    private TextView ageTxt;
    private RadioGroup ageGroup;
    private RadioButton male, female, mixed, juniors, seniors;
    private TextView genderTxt;
    private RadioGroup genderGroup;
    private MaterialButton editTeamBtn;
    private final MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_team_popupwindow);

        Intent intent = getIntent();
        String teamId = intent.getStringExtra("Team");
        Team team = LoginActivity.getTeam(teamId);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.7));

        teamNameTxt = findViewById(R.id.teamNameInputTxtEdit);
        ageGroup = findViewById(R.id.ageRadioGroupEdit);
        genderGroup = findViewById(R.id.genderRadioGroupEdit);
        editTeamBtn = findViewById(R.id.editTeamBtn);
        exitBtn = findViewById(R.id.exitBtnEdit);

        teamNameTxt.setText(team.getName());
        getAge(team).setChecked(true);
        getGender(team).setChecked(true);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (Objects.requireNonNull(teamNameTxt.getText()).toString().isEmpty()) {
                    teamNameTxt.setError("Enter Team Name");
                    error = true;
                } else {
                    teamNameTxt.setError(null);
                }

                if (error) {
                    return;
                }

                RadioButton age = findViewById(ageGroup.getCheckedRadioButtonId());
                RadioButton gender = findViewById(genderGroup.getCheckedRadioButtonId());

                team.setName(teamNameTxt.getText().toString());
                team.setAge(age.getText().toString());
                team.setGender(gender.getText().toString());
                mainActivity.editTeam(team);
                finish();
            }
        });
    }

    private RadioButton getAge(Team team) {
        juniors = findViewById(R.id.junioursChoiceEdit);
        seniors = findViewById(R.id.senioursChoiceEdit);

        if (juniors.getText().toString().equals(team.getAge())) {
            return juniors;
        } else {
            return seniors;
        }
    }

    private RadioButton getGender(Team team) {
        male = findViewById(R.id.maleChoiceEdit);
        female = findViewById(R.id.femaleChoiceEdit);
        mixed = findViewById(R.id.mixedChoiceEdit);

        if (male.getText().toString().equals(team.getGender())) {
            return male;
        } else if (female.getText().toString().equals(team.getGender())) {
            return female;
        } else {
            return mixed;
        }
    }
}
