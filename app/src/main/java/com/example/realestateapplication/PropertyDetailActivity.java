package com.example.realestateapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realestateapplication.Models.Property;

public class PropertyDetailActivity extends AppCompatActivity {

    Property property;
    GridView propertyGalleryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        property = getIntent().getParcelableExtra("property");
        Log.d("In the detail activity", property.toString());
        property.setContext(this);

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(property.getPropertyMainImgURL())
                .into((ImageView)findViewById(R.id.propertyMainImageView));

        // TODO
        //  findViewById(R.id.propertyAgentImageView);
        //  findViewById(R.id.propertyAgentNameText);
        ((TextView)findViewById(R.id.propertyAddressText)).setText(property.getPropertyAddress());
        ((TextView)findViewById(R.id.propertyPriceText)).setText(property.getPropertyPrice());
        ((TextView)findViewById(R.id.propertyNumOfBathText)).setText(property.getPropertyNumOfBath());
        ((TextView)findViewById(R.id.propertyNumOfBedText)).setText(property.getPropertyNumOfBed());
        ((TextView)findViewById(R.id.propertySQFTText)).setText(property.getPropertySquareFoot());
        findViewById(R.id.propertyAgentContactBtn);

        propertyGalleryGridView = (GridView)findViewById(R.id.propertyGalleryGridView);
        propertyGalleryGridView.setAdapter(new PropertyGalleryAdapter(this, property.getGalleryImagesURLs()));
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
