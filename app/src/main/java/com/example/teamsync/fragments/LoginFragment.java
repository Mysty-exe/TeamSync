package com.example.teamsync.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamsync.R;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.activities.MainActivity;
import com.example.teamsync.models.Account;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    Gson gson = new Gson();
    private TextInputLayout email, password;
    private TextInputEditText emailTxt, passwordTxt;
    private MaterialButton loginBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.emailLoginInput);
        emailTxt = view.findViewById(R.id.emailLoginInputTxt);
        password = view.findViewById(R.id.passwordLoginInput);
        passwordTxt = view.findViewById(R.id.passwordLoginInputTxt);
        loginBtn = view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.accountExists(emailTxt.getText().toString(), passwordTxt.getText().toString())) {
                    LoginActivity.setCurrentAcc(LoginActivity.getRequestedAccount(emailTxt.getText().toString(), passwordTxt.getText().toString()));
                    SharedPreferences sharedPrefs = getActivity().getSharedPreferences("Data", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
                    String json = gson.toJson(LoginActivity.getCurrentAcc());
                    prefsEditor.putString("Account", json).apply();

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    email.setError("Invalid Email or Password");
                    password.setError("Invalid Email or Password");
                }
            }
        });

        return view;
    }
}