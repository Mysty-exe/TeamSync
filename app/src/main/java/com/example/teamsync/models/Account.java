package com.example.teamsync.models;

import com.example.teamsync.models.Team;

import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private String id = UUID.randomUUID().toString();
    private String fullName;
    private String email;
    private String password;
    private String activeTeam;
    private String type;
    private ArrayList<String> teamIds = new ArrayList<>();

    public Account(String name, String email, String password, String type) {
        this.fullName = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

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
        this.type = type;
    }

    public ArrayList<String> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(ArrayList<String> teamIds) {
        this.teamIds = teamIds;
    }
}
