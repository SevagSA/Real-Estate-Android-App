package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;

import com.example.realestateapplication.R;

public class ListPropertyActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_property);
        toolbar = findViewById(R.id.appToolbar);

        setSupportActionBar(toolbar);
    }
}