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

public class PlayersRecViewAdapter extends RecyclerView.Adapter<PlayersRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<String> players = new ArrayList<>();
    private Context context;

    public ArrayList<String> getPlayers() {
        return players;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlayers(ArrayList<String> players) {
        this.players.clear();
        this.players.addAll(players);
        notifyDataSetChanged();
    }

    public PlayersRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.personTxt.setText(LoginActivity.getPersonObj(players.get(position)).getFullName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView personTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.playersRecView);
            personTxt = itemView.findViewById(R.id.personTxt);
        }
    }
}
