package com.example.teamsync.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamsync.R;
import com.example.teamsync.activities.EditEventActivity;
import com.example.teamsync.activities.HomePageActivity;
import com.example.teamsync.activities.ViewEventActivity;
import com.example.teamsync.models.Event;

import java.util.ArrayList;

public class EventsRecViewAdapter extends RecyclerView.Adapter<EventsRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<Event> events = new ArrayList<>();
    private AlertDialog.Builder builder;
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

    private void confirmDialog() {
        builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Delete Team");
        builder.setMessage("Are you sure you want to delete this team?");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewEventActivity.class);
                intent.putExtra("Event", (Parcelable) events.get(position));
                context.startActivity(intent);
            }
        });
        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.edit) {
                            Intent intent = new Intent(context, EditEventActivity.class);
                            intent.putExtra("Event", events.get(position));
                            context.startActivity(intent);
                        }
                        if (item.getItemId() == R.id.delete) {
                            confirmDialog();
                            builder.setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            events.remove(holder.getAdapterPosition());
                                            setEvents(events);
                                            HomePageActivity.getTeam(HomePageActivity.getCurrentAcc().getActiveTeam()).setEvents(events);
                                            Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.actions, popup.getMenu());
                popup.show();
            }
        });
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
        private ImageView imgMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.eventsRecyclerView);
            event = itemView.findViewById(R.id.eventTxt);
            type = itemView.findViewById(R.id.eventTypeTxt);
            time = itemView.findViewById(R.id.timeTxt);
            address = itemView.findViewById(R.id.placeTxt);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
