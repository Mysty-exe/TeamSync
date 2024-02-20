package com.example.teamsync.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teamsync.R;
import com.example.teamsync.models.Account;
import com.google.android.material.button.MaterialButton;

public class userAvatarFragment extends Fragment {

    View view;
    ImageView addAvatarImg;
    MaterialButton nextBtn;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_avatar, container, false);

        Bundle bundle = getArguments();
        Account account = bundle.getParcelable("Account");

        addAvatarImg = view.findViewById(R.id.addAvatarImg);
        nextBtn = view.findViewById(R.id.nextBtn);
        
        addAvatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Setting Avatar Image", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new userTypeFragment();
                Bundle args = new Bundle();
                args.putParcelable("Account", account);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.userAvatarFragment, fragment);
                fragmentTransaction.commit();
            }
        });
        
        return view;
    }
}