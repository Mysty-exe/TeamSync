package com.example.teamsync.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamsync.R;
import com.example.teamsync.activities.EditTeamActivity;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.models.Announcement;
import com.example.teamsync.models.Team;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AnnouncementsRecViewAdapter extends RecyclerView.Adapter<AnnouncementsRecViewAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<Announcement> announcements = new ArrayList<>();
    private AlertDialog.Builder builder;
    private Context context;

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
        notifyDataSetChanged();
    }

    public AnnouncementsRecViewAdapter(Context context) {
        this.context = context;
    }

    private void confirmDialog() {
        builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Delete Announcement");
        builder.setMessage("Are you sure you want to delete this announcement?");
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
        if (announcements.get(holder.getAdapterPosition()).isEdited()) {
            holder.editedTxt.setVisibility(View.VISIBLE);
        } else {
            holder.editedTxt.setVisibility(View.GONE);
        }

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.edit) {
                            holder.updateAnnouncement.setVisibility(View.VISIBLE);
                            holder.announcementTxt.setVisibility(View.GONE);
                            holder.announcementInputTxt.setText(announcements.get(position).getMessage());

                            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.announcementInputTxt.setText("");
                                    holder.updateAnnouncement.setVisibility(View.GONE);
                                    holder.announcementTxt.setVisibility(View.VISIBLE);
                                }
                            });
                            holder.updateBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String teamId = announcements.get(holder.getAdapterPosition()).getTeam();
                                    String message = holder.announcementInputTxt.getText().toString();
                                    if (message.trim().equals("")) {
                                        return;
                                    }
                                    holder.announcementTxt.setVisibility(View.VISIBLE);
                                    holder.announcementTxt.setText(message);
                                    holder.announcementInputTxt.setText("");
                                    holder.updateAnnouncement.setVisibility(View.GONE);
                                    announcements.get(holder.getAdapterPosition()).setEdited(true);
                                    announcements.get(holder.getAdapterPosition()).setMessage(message);

                                    setAnnouncements(announcements);
                                    LoginActivity.getTeam(teamId).setAnnouncements(announcements);
                                    hideKeyboardFrom(context.getApplicationContext(), v);
                                }
                            });
                        }
                        if (item.getItemId() == R.id.delete) {
                            confirmDialog();
                            builder.setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String teamId = announcements.get(holder.getAdapterPosition()).getTeam();
                                            announcements.remove(position);
                                            setAnnouncements(announcements);
                                            LoginActivity.getTeam(teamId).setAnnouncements(announcements);
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout updateAnnouncement;
        private TextInputLayout announcementInput;
        private TextInputEditText announcementInputTxt;
        private TextView personTxt, timeTxt, editedTxt, announcementTxt;
        private MaterialButton cancelBtn, updateBtn;
        private ImageView imgMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            updateAnnouncement = itemView.findViewById(R.id.updateAnnouncement);
            recyclerView = itemView.findViewById(R.id.announcementsRecView);
            announcementInput = itemView.findViewById(R.id.announcementInput);
            announcementInputTxt = itemView.findViewById(R.id.announcementInputTxt);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            personTxt = itemView.findViewById(R.id.personTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            editedTxt = itemView.findViewById(R.id.editedTxt);
            announcementTxt = itemView.findViewById(R.id.announcementTxt);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
