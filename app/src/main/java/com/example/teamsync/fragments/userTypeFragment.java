package com.example.teamsync.fragments;

import android.content.Intent;
import android.media.metrics.PlaybackErrorEvent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.activities.HomePageActivity;
import com.example.teamsync.activities.LoginActivity;
import com.example.teamsync.activities.MainActivity;
import com.example.teamsync.models.Account;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class userTypeFragment extends Fragment {
    View view;
    RelativeLayout coachCardLayout, playerCardLayout, parentCardLayout;
    MaterialCardView coachCard, playerCard, parentCard;
    MaterialButton createAccBtn;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_type, container, false);

        Bundle bundle = getArguments();
        Account account = bundle.getParcelable("Account");

        coachCardLayout = view.findViewById(R.id.coachCardLayout);
        playerCardLayout = view.findViewById(R.id.playerCardLayout);
        parentCardLayout = view.findViewById(R.id.parentCardLayout);
        coachCard = view.findViewById(R.id.coachCard);
        playerCard = view.findViewById(R.id.playerCard);
        parentCard = view.findViewById(R.id.parentCard);
        createAccBtn = view.findViewById(R.id.createAccBtn);

        coachCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coachCard.setChecked(true);
                type = "Coach";
                coachCardLayout.setBackgroundResource(R.drawable.gradient_checked);
                uncheckOtherCards(coachCard);
            }
        });
        playerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerCard.setChecked(true);
                type = "Player";
                playerCardLayout.setBackgroundResource(R.drawable.gradient_checked);
                uncheckOtherCards(playerCard);
            }
        });
        parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentCard.setChecked(true);
                type = "Parent";
                parentCardLayout.setBackgroundResource(R.drawable.gradient_checked);
                uncheckOtherCards(parentCard);
            }
        });
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.isEmpty()) {
                    Toast.makeText(getContext(), "Choose an Account Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                account.setType(type);
                HomePageActivity.addAccount(account);

                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    private void uncheckOtherCards(MaterialCardView cardview) {
        if (cardview == coachCard) {
            playerCard.setChecked(false);
            playerCardLayout.setBackgroundResource(R.drawable.gradient);
            parentCard.setChecked(false);
            parentCardLayout.setBackgroundResource(R.drawable.gradient);
        } else if (cardview == playerCard) {
            coachCard.setChecked(false);
            coachCardLayout.setBackgroundResource(R.drawable.gradient);
            parentCard.setChecked(false);
            parentCardLayout.setBackgroundResource(R.drawable.gradient);
        } else if (cardview == parentCard) {
            coachCard.setChecked(false);
            coachCardLayout.setBackgroundResource(R.drawable.gradient);
            playerCard.setChecked(false);
            playerCardLayout.setBackgroundResource(R.drawable.gradient);
        }
    }
}
