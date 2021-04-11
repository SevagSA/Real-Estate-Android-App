package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.realestateapplication.R;
import com.example.realestateapplication.RegistrationDialog;

public class LoginActivity extends AppCompatActivity {

    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        regButton = findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        RegistrationDialog regDialog = new RegistrationDialog();
        regDialog.show(getSupportFragmentManager(), "registration dialog");
    }

    //for registration:
    //https://www.youtube.com/watch?v=ARezg1D9Zd0
    //9:11
}