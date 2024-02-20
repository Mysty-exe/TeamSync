package com.example.teamsync.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamsync.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class credentialsFragment extends Fragment {

    View view;
    private TextInputLayout password, rePassword;
    private TextInputEditText fullNameTxt, emailTxt, passwordTxt, rePasswordTxt;
    private MaterialButton signupBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_credentials, container, false);
        fullNameTxt = view.findViewById(R.id.fullNameInputTxt);
        emailTxt = view.findViewById(R.id.emailInputTxt);
        password = view.findViewById(R.id.passwordInput);
        passwordTxt = view.findViewById(R.id.passwordInputTxt);
        rePassword = view.findViewById(R.id.rePasswordInput);
        rePasswordTxt = view.findViewById(R.id.rePasswordInputTxt);
        signupBtn = view.findViewById(R.id.createAccountBtn);

        return view;
    }
}