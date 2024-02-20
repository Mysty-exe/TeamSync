package com.example.teamsync.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.teamsync.models.Team;

import java.util.ArrayList;
import java.util.UUID;

public class Account implements Parcelable {
    private String id = UUID.randomUUID().toString();
    private String fullName;
    private String email;
    private String password;
    private String activeTeam;
    private String type;
    private ArrayList<String> teamIds = new ArrayList<>();

    public Account(String name, String email, String password) {
        this.fullName = name;
        this.email = email;
        this.password = password;
        this.type = "";
    }

    protected Account(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        email = in.readString();
        password = in.readString();
        activeTeam = in.readString();
        type = in.readString();
        teamIds = in.createStringArrayList();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActiveTeam() {
        return activeTeam;
    }

    public void setActiveTeam(String activeTeam) {
        this.activeTeam = activeTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equals("Parent") || type.equals("Coach") || type.equals("Student")) {
            this.type = type;
        }
    }

    public ArrayList<String> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(ArrayList<String> teamIds) {
        this.teamIds = teamIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(activeTeam);
        dest.writeString(type);
        dest.writeList(teamIds);
    }
}
