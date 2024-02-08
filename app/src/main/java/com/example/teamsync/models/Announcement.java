package com.example.teamsync.models;

import java.util.UUID;

public class Announcement {
    private String id = UUID.randomUUID().toString();
    private String sender, time, message, team;
    private boolean edited = false;

    public Announcement(String sender, String time, String message, String team) {
        this.sender = sender;
        this.time = time;
        this.message = message;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
