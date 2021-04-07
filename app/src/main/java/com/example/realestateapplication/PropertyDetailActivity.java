package com.example.realestateapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.realestateapplication.Models.Property;

public class PropertyDetailActivity extends AppCompatActivity {

    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        property = getIntent().getParcelableExtra("property");

        Toast.makeText(getApplicationContext(), property.getPropertyAddress() + " | " + property.getPropertyAddress()
                , Toast.LENGTH_SHORT).show();

    }
}