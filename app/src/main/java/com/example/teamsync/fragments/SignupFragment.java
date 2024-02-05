package com.example.teamsync.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamsync.R;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.fragments.LoginFragment;
import com.example.teamsync.models.Account;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    private TextInputLayout password, rePassword;
    private TextInputEditText fullNameTxt, emailTxt, passwordTxt, rePasswordTxt;
    private MaterialButton signupBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        fullNameTxt = view.findViewById(R.id.fullNameInputTxt);
        emailTxt = view.findViewById(R.id.emailInputTxt);
        password = view.findViewById(R.id.passwordInput);
        passwordTxt = view.findViewById(R.id.passwordInputTxt);
        rePassword = view.findViewById(R.id.rePasswordInput);
        rePasswordTxt = view.findViewById(R.id.rePasswordInputTxt);
        signupBtn = view.findViewById(R.id.createAccountBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (Objects.requireNonNull(fullNameTxt.getText()).toString().trim().isEmpty() || !validName(fullNameTxt.getText().toString().trim())) {
                    fullNameTxt.setError("Invalid Name");
                    error = true;
                } else {
                    fullNameTxt.setError(null);
                }

                if (Objects.requireNonNull(emailTxt.getText()).toString().isEmpty() || !validEmail(emailTxt.getText().toString())) {
                    emailTxt.setError("Invalid Email");
                    error = true;
                } else if (LoginActivity.emailAlreadyRegistered(emailTxt.getText().toString())) {
                    emailTxt.setError("This Email is already registered");
                    error = true;
                } else {
                    emailTxt.setError(null);
                }

                if ((Objects.requireNonNull(passwordTxt.getText()).toString().isEmpty()) || !validPassword(passwordTxt.getText().toString())) {
                    passwordTxt.setError("Invalid Password");
                    error = true;
                } else {
                    passwordTxt.setError(null);
                    password.setEndIconVisible(true);
                }

                if (!Objects.requireNonNull(rePasswordTxt.getText()).toString().equals(passwordTxt.getText().toString())) {
                    rePasswordTxt.setError("You must re-enter your Password");
                    error = true;
                }
                else {
                    rePasswordTxt.setError(null);
                    rePassword.setEndIconVisible(true);
                }

                if (error) {
                    return;
                }

                LoginActivity.addAccount(new Account(fullNameTxt.getText().toString(), emailTxt.getText().toString(), passwordTxt.getText().toString()));

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.signupFragment, new LoginFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private boolean validName(String name) {
        String regex = "^[ a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        return pattern.matcher(name).matches();
    }

    private boolean validEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(regex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    private boolean validPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}