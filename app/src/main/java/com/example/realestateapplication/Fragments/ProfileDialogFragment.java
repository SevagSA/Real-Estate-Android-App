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

import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;


public class ProfileDialogFragment extends DialogFragment {

    private User user;
    private Communication comm;

    public ProfileDialogFragment() {
        // Required empty public constructor
    }

    public ProfileDialogFragment(String userId, Context context) {
        this.user = new User(context);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        comm = (Communication)getActivity();
//        getActivity().findViewById(R.id.userEditBtn).setOnClickListener(e -> {
//            comm.handleData("email", "full name");
//        });
    }

    public void handleData2(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}