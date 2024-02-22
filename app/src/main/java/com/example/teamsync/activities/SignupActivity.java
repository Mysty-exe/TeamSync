package com.example.teamsync.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.fragments.credentialsFragment;
import com.example.teamsync.fragments.homeFragment;
import com.example.teamsync.models.Account;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        goCredentials();
    }

    public void replaceFragment(Fragment fragment, boolean addBack) {
        if (addBack) {
            fragmentManager.beginTransaction().replace(R.id.signupFragment, fragment).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.signupFragment, fragment).commit();
        }
    }

    private void goCredentials() {
        Fragment credentialsFragment = new credentialsFragment();
        replaceFragment(credentialsFragment, false);
    }
}