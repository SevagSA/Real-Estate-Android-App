package com.example.realestateapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Models.Property;

public class PropertyActionAlertDialog extends DialogFragment {

    private Property property;

    public PropertyActionAlertDialog() {
        // Required empty public constructor
    }

    public PropertyActionAlertDialog(String propertyId, Context context) {
        this.property = new Property(context);
        this.property.setPropertyId(propertyId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_property_action_alert_dialog, container, false);
        root.findViewById(R.id.propActionCancelBtn).setOnClickListener(e -> dismiss());
        root.findViewById(R.id.propActionDeleteBtn).setOnClickListener(e -> {
            property.delete();
            dismiss();
            Intent intent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(intent);
            getActivity().finish();
        });
        return root;
    }
}