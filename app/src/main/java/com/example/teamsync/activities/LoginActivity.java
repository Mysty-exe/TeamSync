package com.example.teamsync.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teamsync.models.Account;
import com.example.teamsync.fragments.LoginFragment;
import com.example.teamsync.R;
import com.example.teamsync.fragments.SignupFragment;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Gson gson = new Gson();
    SharedPreferences sharedPrefs;
    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Account> accounts = new ArrayList<>();
    static Account currentAcc;
    private MaterialButton signupBtn, loginBtn;

    public static Account getCoachObj(String id) {
        for (Account a: accounts) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void setTeams(ArrayList<Team> teams) {
        LoginActivity.teams = teams;
    }

    public static Team getTeam(String id) {
        for (Team t: teams) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static boolean emailAlreadyRegistered(String email) {
        for (Account a: accounts) {
            if (a.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean accountExists(String email, String password) {
        for (Account a: accounts) {
            if (a.getEmail().equals(email)) {
                if (a.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Account getRequestedAccount(String email, String password) {
        for (Account a: accounts) {
            if (a.getEmail().equals(email)) {
                if (a.getPassword().equals(password)) {
                    return a;
                }
            }
        }
        return null;
    }

    public static void setAccounts(ArrayList<Account> accounts) {
        LoginActivity.accounts = accounts;
    }

    public static Account getCurrentAcc() {
        return currentAcc;
    }

    public static void setCurrentAcc(Account currentAcc) {
        LoginActivity.currentAcc = currentAcc;
    }
    public static void clearCurrentAcc() {
        currentAcc = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefs = getSharedPreferences("Data", MODE_PRIVATE);
        String accountJson = sharedPrefs.getString("Account", null);
        String accountsJson = sharedPrefs.getString("Accounts", null);
        String teamsJson = sharedPrefs.getString("Teams", null);
        ArrayList<Account> accounts = gson.fromJson(accountsJson, new TypeToken<ArrayList<Account>>(){}.getType());
        ArrayList<Team> teams = gson.fromJson(teamsJson, new TypeToken<ArrayList<Team>>(){}.getType());

        if (accountJson != null) {
            Account account = gson.fromJson(accountJson, Account.class);
            setCurrentAcc(account);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } if (accountsJson != null) {
            setAccounts(accounts);
        } if (teamsJson != null) {
            setTeams(teams);
        }

        signupBtn = findViewById(R.id.signupStartBtn);
        loginBtn = findViewById(R.id.loginStartBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Signup Pressed", Toast.LENGTH_SHORT).show();
                replaceFragment(new SignupFragment());
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login Pressed", Toast.LENGTH_SHORT).show();
                replaceFragment(new LoginFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
