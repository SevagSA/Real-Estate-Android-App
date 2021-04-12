package com.example.realestateapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.R;

public class RegistrationDialogFragment extends DialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_registration, container, false);
        root.findViewById(R.id.register_btn).setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(intent);
        });
        return root;
    }
}
