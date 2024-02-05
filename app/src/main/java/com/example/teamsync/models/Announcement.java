package com.example.teamsync.models;

import java.util.UUID;

public class Announcement {
    private String id = UUID.randomUUID().toString();
    private String sender, time, message;

    public Announcement(String sender, String time, String message) {
        this.sender = sender;
        this.time = time;
        this.message = message;
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
}
