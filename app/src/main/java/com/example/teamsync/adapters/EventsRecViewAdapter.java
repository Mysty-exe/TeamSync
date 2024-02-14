package com.example.teamsync.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamsync.R;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.models.Account;
import com.example.teamsync.models.Announcement;
import com.example.teamsync.models.Event;
import com.example.teamsync.models.Team;

import java.util.ArrayList;

public class EventsRecViewAdapter extends RecyclerView.Adapter<EventsRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<Event> events = new ArrayList<>();
    private Context context;

    public ArrayList<Event> getEvents() {
        return events;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEvents(ArrayList<Event> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public EventsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.event.setText(events.get(position).getName());
        holder.type.setText(events.get(position).getType());
        holder.time.setText(events.get(position).getTime());
        holder.address.setText(events.get(position).getPlace());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView event, type, time, address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.eventsRecyclerView);
            event = itemView.findViewById(R.id.eventTxt);
            type = itemView.findViewById(R.id.eventTypeTxt);
            time = itemView.findViewById(R.id.timeTxt);
            address = itemView.findViewById(R.id.placeTxt);
        }
    }
}
