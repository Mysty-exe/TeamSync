package com.example.teamsync.models;

import java.util.ArrayList;

public class Event {
    private String date, name, type, time, place, notes;

    public Event(String date, String name, String type, String time, String place) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.time = time;
        this.place = place;
        this.notes = "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public static ArrayList<Event> getEventsForDay(ArrayList<Event> events, String date) {
        ArrayList<Event> resultEvents = new ArrayList<>();
        for (Event e: events) {
            if (e.getDate().equals(date)) {
                resultEvents.add(e);
            }
        }
        return resultEvents;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
