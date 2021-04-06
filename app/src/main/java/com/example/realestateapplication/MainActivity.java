package com.example.realestateapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// each activity is a controller for the respective model(s) and view(s);

// instantiate the "language select" view from here, since it will be the first view that the
// user will see
public class MainActivity extends AppCompatActivity{

    Button goToHomePageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToHomePageBtn = findViewById(R.id.goToHomePageBtn);
        goToHomePageBtn.setOnClickListener(e -> {
            Intent myIntent = new Intent(this, HomeActivity.class);
            startActivity(myIntent);
        });
    }
}