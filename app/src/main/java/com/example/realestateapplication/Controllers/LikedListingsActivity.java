package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.realestateapplication.Adapters.PropertyCardRecyclerViewAdapter;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;

import java.util.ArrayList;

public class LikedListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_listings);
        populateRecyclerViewListings();
    }

    /**
     * Render the liked listings by the current user
     */
    private void populateRecyclerViewListings() {
        Property property = new Property(this);
        ArrayList<Property> likedProperties = property.getAllLikedProperties();

        LinearLayoutManager likedPropertyLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        );
        RecyclerView likedPropertyRecyclerView = findViewById(R.id.likedRecycler);
        likedPropertyRecyclerView.setLayoutManager(likedPropertyLayoutManager);
        PropertyCardRecyclerViewAdapter likedPropertyAdapter =
                new PropertyCardRecyclerViewAdapter(likedProperties, this);
        likedPropertyRecyclerView.setAdapter(likedPropertyAdapter);
    }
}