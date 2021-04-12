package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realestateapplication.Adapters.PropertyGalleryRecyclerViewAdapter;
import com.example.realestateapplication.Fragments.ContactPropertyAgentFragment;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;

public class PropertyDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ImageButton agentButton;
    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);


        property = getIntent().getParcelableExtra("property");
        property.setContext(this);

        agentButton = findViewById(R.id.propertyAgentImageView);
        agentButton.setOnClickListener(view -> {
            startActivity(new Intent(PropertyDetailActivity.this, AgentActivity.class));
        });

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(property.getPropertyMainImgURL())
                .into((ImageView)findViewById(R.id.propertyMainImageView));

        // TODO: After creating the Agent Model
        //  findViewById(R.id.propertyAgentImageView);
        //  findViewById(R.id.propertyAgentNameText);
        ((TextView)findViewById(R.id.propertyAddressText)).setText(property.getPropertyAddress());
        ((TextView)findViewById(R.id.propertyPriceText)).setText(property.getPropertyPrice());
        ((TextView)findViewById(R.id.propertyNumOfBathText)).setText(property.getPropertyNumOfBath());
        ((TextView)findViewById(R.id.propertyNumOfBedText)).setText(property.getPropertyNumOfBed());
        ((TextView)findViewById(R.id.propertySQFTText)).setText(property.getPropertySquareFoot());

        populateRecyclerViewListings();

        (findViewById(R.id.propertyAgentContactBtn)).setOnClickListener(e -> {

            // TODO: you can pass the agent in the constructor and use it in the form (for the title maybe)
            ContactPropertyAgentFragment dialogFragment = new ContactPropertyAgentFragment();
            dialogFragment.show(getSupportFragmentManager(), "ContactPropertyAgentFragment");
        });
    }

    private void populateRecyclerViewListings() {
        LinearLayoutManager propertyGalleryManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        );

        RecyclerView propertyGalleryRecyclerView = findViewById(R.id.propertyGalleryRecyclerView);

        propertyGalleryRecyclerView.setLayoutManager(propertyGalleryManager);

        PropertyGalleryRecyclerViewAdapter propertyGalleryAdapter =
                new PropertyGalleryRecyclerViewAdapter(
                        this, property.getGalleryImagesURLs());

        propertyGalleryRecyclerView.setAdapter(propertyGalleryAdapter);
    }
}


//    ImageView propertyMainImageView;
//    ImageView propertyAgentImageView;
//
//    TextView propertyAgentNameText;
//    TextView propertyAddressText;
//    TextView propertyPriceText;
//    TextView propertyNumOfBathText;
//    TextView propertyNumOfBedText;
//    TextView propertySQFTText;
//
//    Button propertyAgentContactBtn;
//        propertyMainImageView = findViewById(R.id.propertyMainImageView);
//        propertyAgentImageView = findViewById(R.id.propertyAgentImageView);
//        propertyAgentNameText = findViewById(R.id.propertyAgentNameText);
//        propertyAddressText = findViewById(R.id.propertyAddressText);
//        propertyPriceText = findViewById(R.id.propertyPriceText);
//        propertyNumOfBathText = findViewById(R.id.propertyNumOfBathText);
//        propertyNumOfBedText = findViewById(R.id.propertyNumOfBedText);
//        propertySQFTText = findViewById(R.id.propertySQFTText);
//        propertyAgentContactBtn = findViewById(R.id.propertyAgentContactBtn);

//        findViewById(R.id.propertyMainImageView).setBackground(
//                ContextCompat.getDrawable(getApplicationContext(), R.drawable.property_1));
