package com.example.realestateapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;


public class ProfileDialogFragment extends DialogFragment {

    public ProfileDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        root.findViewById(R.id.userCancelBtn).setOnClickListener(e -> dismiss());

        root.findViewById(R.id.userEditBtn).setOnClickListener(e -> {
            dismiss();
            EditProfileDialogFragment dialogFragment = new EditProfileDialogFragment(getActivity());
            dialogFragment.show(getActivity().getSupportFragmentManager(), "EditProfileDialogFragment");
        });

        String userId = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.user_id_shared_pref), null);
        User user = new User(getActivity());
        user = user.getUserById(userId);

        TextView email = root.findViewById(R.id.your_email);
        TextView fullName = root.findViewById(R.id.your_full_name);

        email.setText(user.getEmail());
        fullName.setText(user.getFullName());
        
        return root;
    }
}