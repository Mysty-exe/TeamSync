package com.example.teamsync.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.activities.HomePageActivity;
import com.example.teamsync.activities.MainActivity;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class settingsFragment extends Fragment {
    View view;
    private TextInputLayout teamName;
    private TextInputEditText teamNameTxt;
    private TextView ageTxt;
    private RadioGroup ageGroup;
    private RadioButton male, female, mixed, juniors, seniors;
    private TextView genderTxt;
    private RadioGroup genderGroup;
    private MaterialButton editTeamBtn;
    private final MainActivity mainActivity = new MainActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        Team team = HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam());

        teamName = view.findViewById(R.id.teamNameInputEdit);
        teamNameTxt = view.findViewById(R.id.teamNameInputTxtEdit);
        ageGroup = view.findViewById(R.id.ageRadioGroupEdit);
        genderGroup = view.findViewById(R.id.genderRadioGroupEdit);
        editTeamBtn = view.findViewById(R.id.editTeamBtn);

        teamNameTxt.setText(team.getName());
        RadioButton ageBtn = getAge(view, team);
        ageBtn.setChecked(true);

        RadioButton genderBtn = getGender(view, team);
        genderBtn.setChecked(true);

        teamNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!team.getName().equals(teamNameTxt.getText().toString())) {
                    editTeamBtn.setVisibility(View.VISIBLE);
                } else {
                    if (ageBtn.getId() == ageGroup.getCheckedRadioButtonId() && genderBtn.getId() == genderGroup.getCheckedRadioButtonId()) {
                        editTeamBtn.setVisibility(View.GONE);
                    }
                }
            }
        });

        ageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (ageBtn.getId() != checkedId) {
                    editTeamBtn.setVisibility(View.VISIBLE);
                } else {
                    if (team.getName().equals(teamNameTxt.getText().toString()) && genderBtn.getId() == genderGroup.getCheckedRadioButtonId()) {
                        editTeamBtn.setVisibility(View.GONE);
                    }
                }
            }
        });

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (genderBtn.getId() != checkedId) {
                    editTeamBtn.setVisibility(View.VISIBLE);
                } else {
                    if (ageBtn.getId() == ageGroup.getCheckedRadioButtonId() && team.getName().equals(teamNameTxt.getText().toString())) {
                        editTeamBtn.setVisibility(View.GONE);
                    }
                }
            }
        });

        editTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (teamNameTxt.getText().toString().isEmpty()) {
                    teamNameTxt.setError("Enter Team Name");
                    error = true;
                } else {
                    teamNameTxt.setError(null);
                }

                if (error) {
                    return;
                }

                RadioButton age = view.findViewById(ageGroup.getCheckedRadioButtonId());
                RadioButton gender = view.findViewById(genderGroup.getCheckedRadioButtonId());

                team.setName(teamNameTxt.getText().toString());
                team.setAge(age.getText().toString());
                team.setGender(gender.getText().toString());
                mainActivity.editTeam(team);
                editTeamBtn.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private RadioButton getAge(View view, Team team) {
        juniors = view.findViewById(R.id.junioursChoiceEdit);
        seniors = view.findViewById(R.id.senioursChoiceEdit);

        if (juniors.getText().toString().equals(team.getAge())) {
            return juniors;
        } else {
            return seniors;
        }
    }

    private RadioButton getGender(View view, Team team) {
        male = view.findViewById(R.id.maleChoiceEdit);
        female = view.findViewById(R.id.femaleChoiceEdit);
        mixed = view.findViewById(R.id.mixedChoiceEdit);

        if (male.getText().toString().equals(team.getGender())) {
            return male;
        } else if (female.getText().toString().equals(team.getGender())) {
            return female;
        } else {
            return mixed;
        }
    }

}