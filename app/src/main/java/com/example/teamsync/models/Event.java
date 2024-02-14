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

    public static ArrayList<Event> sortEvents(ArrayList<Event> events) {
//        int size = events.length;
//
//        for (int i = 0; i < (size-1); i++) {
//
//            boolean swapped = false;
//
//            for (int j = 0; j < (size - i - 1); j++) {
//
//                if (events[j] > events[j + 1]) {
//
//                    int temp = events[j];
//                    events[j] = events[j + 1];
//                    events[j + 1] = temp;
//
//                    swapped = true;
//                }
//            }
//            if (!swapped)
//                break;
//        }
        return events;
    }

    public static String timeAfter(String timeOne, String timeTwo) {
        String[] timeOneParts = timeOne.split(" ");
        String[] timeTwoParts = timeTwo.split(" ");
        String timeOneTime = timeOneParts[0];
        String timeOneAMPM = timeOneParts[1];
        String timeTwoTime = timeTwoParts[0];
        String timeTwoAMPM = timeTwoParts[1];

        if (!timeOneAMPM.equals(timeTwoAMPM)) {
            String[] timeOneTimeParts = timeOneTime.split(" ");
            String[] timeTwoTimeParts = timeTwoTime.split(" ");
            String timeOneHours = timeOneTimeParts[0];
            String timeOneMinutes = timeOneTimeParts[1];
            String timeTwoHours = timeTwoTimeParts[0];
            String timeTwoMinutes = timeTwoTimeParts[1];

        } else {
            if (timeOneAMPM.equals("AM")) {
                return timeOne;
            }
            return timeTwo;
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
