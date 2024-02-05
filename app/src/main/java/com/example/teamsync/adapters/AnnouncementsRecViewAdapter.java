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
import com.example.teamsync.models.Announcement;
import com.example.teamsync.models.Team;

import java.util.ArrayList;

public class AnnouncementsRecViewAdapter extends RecyclerView.Adapter<AnnouncementsRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<Announcement> announcements = new ArrayList<>();
    private Context context;

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements.clear();
        this.announcements.addAll(announcements);
        notifyDataSetChanged();
    }

    public AnnouncementsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcements_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.personTxt.setText(announcements.get(position).getSender());
        holder.timeTxt.setText(announcements.get(position).getTime());
        holder.announcementTxt.setText(announcements.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView personTxt, timeTxt, announcementTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("NAHH", "setAnnolkdfjsdfkuncements");
            recyclerView = itemView.findViewById(R.id.announcementsRecView);
            personTxt = itemView.findViewById(R.id.personTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            announcementTxt = itemView.findViewById(R.id.announcementTxt);
        }
    }
}
