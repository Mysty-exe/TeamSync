package com.example.teamsync.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.teamsync.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private String id = UUID.randomUUID().toString();
    private String name, sport, coach, age, gender;
    private ArrayList<Announcement> announcements = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<String> coaches = new ArrayList<>();
    private ArrayList<String> players = new ArrayList<>();

    public Team(String name, String sport, String coach, String age, String gender) {
        this.name = name;
        this.sport = sport;
        this.coach = coach;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getImage() {
        switch (this.sport) {
            case "Running":
                return R.mipmap.running;
            case "Ringette":
                return R.mipmap.ringette;
            case "Football":
                return R.mipmap.football;
            case "Hockey":
                return R.mipmap.hockey;
            default:
                return 0;
        }
    };

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<String> getCoaches() {
        return coaches;
    }

    public void setCoaches(ArrayList<String> coaches) {
        this.coaches = coaches;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }
}
