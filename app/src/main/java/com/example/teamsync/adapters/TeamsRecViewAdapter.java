package com.example.teamsync.adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamsync.R;
import com.example.teamsync.models.Team;
import com.example.teamsync.activities.EditTeamActivity;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.activities.MainActivity;
import com.example.teamsync.activities.TeamActivity;

import java.util.ArrayList;

public class TeamsRecViewAdapter extends RecyclerView.Adapter<TeamsRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private AlertDialog.Builder builder;
    private ArrayList<String> teams = new ArrayList<>();
    private MainActivity mainActivity;
    private Context context;

    public ArrayList<String> getTeamIds() {
        return teams;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTeamIds(ArrayList<String> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    private void confirmDialog() {
        builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Delete Team");
        builder.setMessage("Are you sure you want to delete this team?");
    }

    public TeamsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DiscouragedApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeamActivity.class);
                intent.putExtra("Team", teams.get(position));
                context.startActivity(intent);
            }
        });

        Team team = LoginActivity.getTeam(teams.get(position));
        holder.txtTeam.setText(team.getName());
        holder.txtCoach.setText(LoginActivity.getCoachObj(team.getCoach()).getFullName());
        holder.imgSport.setImageResource(team.getImage());
        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.edit) {
                            Intent intent = new Intent(context, EditTeamActivity.class);
                            intent.putExtra("Team", teams.get(position));
                            context.startActivity(intent);
                        }
                        if (item.getItemId() == R.id.delete) {
                            confirmDialog();
                            builder.setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            teams.remove(holder.getAdapterPosition());
                                            setTeamIds(teams);
                                            LoginActivity.getCurrentAcc().setTeamIds(teams);
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
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTeam, txtCoach;
        private ImageView imgSport, imgMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.teamsRecView);
            txtTeam = itemView.findViewById(R.id.teamNameTxt);
            txtCoach = itemView.findViewById(R.id.coachTxt);
            imgSport = itemView.findViewById(R.id.sportImg);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
