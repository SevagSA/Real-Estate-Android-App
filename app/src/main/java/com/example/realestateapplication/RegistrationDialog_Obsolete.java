package com.example.realestateapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.realestateapplication.Controllers.HomeActivity;

public class RegistrationDialog_Obsolete extends AppCompatDialogFragment {

    private Toolbar toolbar;
    private EditText regUsername;
    private EditText regPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_registration, null);

        builder.setView(view)
                .setTitle("Register")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getContext(), HomeActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        regUsername = view.findViewById(R.id.reg_username);
        regPassword = view.findViewById(R.id.reg_password);

        return builder.create();
    }
}
