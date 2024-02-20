package com.example.teamsync.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamsync.models.Account;
import com.example.teamsync.R;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity {

    Gson gson = new Gson();
    SharedPreferences sharedPrefs;
    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Account> accounts = new ArrayList<>();
    static Account currentAcc;
    private MaterialButton signupBtn, loginBtn;

    public static Account getPersonObj(String id) {
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
        HomePageActivity.teams = teams;
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
        HomePageActivity.accounts = accounts;
    }

    public static Account getCurrentAcc() {
        return currentAcc;
    }

    public static void setCurrentAcc(Account currentAcc) {
        HomePageActivity.currentAcc = currentAcc;
    }
    public static void clearCurrentAcc() {
        currentAcc = null;
    }

    public static void deleteAccount(String id) {
        for (Account a: accounts) {
            if (a.getId().equals(id)) {
                accounts.remove(a);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

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
//                replaceFragment(new SignupFragment());
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
