package com.example.teamsync.models;

import static java.lang.System.out;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Event implements Parcelable {
    private String id = UUID.randomUUID().toString();
    private String date, name, type, time, place, notes;

    public Event(String date, String name, String type, String time, String place) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.time = time;
        this.place = place;
        this.notes = "";
    }

    protected Event(Parcel in) {
        id = in.readString();
        date = in.readString();
        name = in.readString();
        type = in.readString();
        time = in.readString();
        place = in.readString();
        notes = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        ArrayList<Event> sortedEvents = sortEvents(resultEvents);
        return sortedEvents;
    }

    public static ArrayList<Event> sortEvents(ArrayList<Event> events) {
        int size = events.size();

        for (int i = 0; i < (size-1); i++) {
            boolean swapped = false;
            for (int j = 0; j < (size - i - 1); j++) {
                if (events.get(j).isAfter(events.get(j + 1))) {
                    Collections.swap(events, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
        return events;
    }

    public boolean isAfter(Event time) {
        String[] timeOneParts = this.time.split(" ");
        String[] timeTwoParts = time.time.split(" ");
        String timeOneTime = timeOneParts[0];
        String timeOneAMPM = timeOneParts[1];
        String timeTwoTime = timeTwoParts[0];
        String timeTwoAMPM = timeTwoParts[1];

        if (timeOneAMPM.equals(timeTwoAMPM)) {
            String[] timeOneTimeParts = timeOneTime.split(":");
            String[] timeTwoTimeParts = timeTwoTime.split(":");
            String timeOneHours = timeOneTimeParts[0];
            String timeOneMinutes = timeOneTimeParts[1];
            String timeTwoHours = timeTwoTimeParts[0];
            String timeTwoMinutes = timeTwoTimeParts[1];
            if (Integer.parseInt(timeOneHours) > Integer.parseInt(timeTwoHours)) {
                return true;
            } else if (Integer.parseInt(timeOneHours) < Integer.parseInt(timeTwoHours)) {
                return false;
            } else {
                if (Integer.parseInt(timeOneMinutes) > Integer.parseInt(timeTwoMinutes)) {
                    return true;
                } else if (Integer.parseInt(timeOneMinutes) < Integer.parseInt(timeTwoMinutes)) {
                    return false;
                } else {
                    return false;
                }
            }
        } else {
            if (timeOneAMPM.equals("AM")) {
                return false;
            }
            return true;
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(time);
        dest.writeString(place);
        dest.writeString(notes);
    }
}
