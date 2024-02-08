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
import com.example.teamsync.models.Team;

import java.util.ArrayList;

public class CoachesRecViewAdapter extends RecyclerView.Adapter<CoachesRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<String> coaches = new ArrayList<>();
    private Context context;

    public ArrayList<String> getCoaches() {
        return coaches;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCoaches(ArrayList<String> coaches) {
        this.coaches.clear();
        this.coaches.addAll(coaches);
        notifyDataSetChanged();
    }

    public CoachesRecViewAdapter(Context context) {
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
        holder.personTxt.setText(LoginActivity.getPersonObj(coaches.get(position)).getFullName());
    }

    @Override
    public int getItemCount() {
        return coaches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView personTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.coachesRecView);
            personTxt = itemView.findViewById(R.id.personTxt);
        }
    }
}
