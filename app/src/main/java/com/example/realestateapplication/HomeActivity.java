package com.example.realestateapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.Region;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity implements Observer {

    private Region region = new Region();
    private final Property property = new Property(this);

    Button changeLayoutOrientationBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // creating a relationship between the observable Model and the observer Activity
        region = new Region();
        region.addObserver(this);

        populateRecyclerViewListings(LinearLayoutManager.HORIZONTAL);

        changeLayoutOrientationBtn = findViewById(R.id.changeLayoutOrientationBtn);
        changeLayoutOrientationBtn.setOnClickListener(e ->  handleChangeLayoutBtnClick());
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    /**
     * Render the Regions and Recent Listings in their respective RecyclerView.
     */
    private void populateRecyclerViewListings(int layoutOrientation) {
        ArrayList<Region> regions = region.getAllRegions();

        LinearLayoutManager regionsLayoutManager = new LinearLayoutManager(
                this, layoutOrientation, false
        );
        RecyclerView regionsRecyclerView = findViewById(R.id.regionsRecyclerView);
        regionsRecyclerView.setLayoutManager(regionsLayoutManager);
        RegionsRecyclerViewAdapter regionsAdapter =
                new RegionsRecyclerViewAdapter(regions, this);
        regionsRecyclerView.setAdapter(regionsAdapter);

        ArrayList<Property> properties = property.getAllProperties();
        LinearLayoutManager propertyLayoutManager = new LinearLayoutManager(
                this, layoutOrientation, false
        );

        RecyclerView propertyRecyclerView = findViewById(R.id.recentPropertiesRecyclerView);
        propertyRecyclerView.setLayoutManager(propertyLayoutManager);
        PropertyCardRecyclerViewAdapter propertyAdapter =
                new PropertyCardRecyclerViewAdapter(properties, this);
        propertyRecyclerView.setAdapter(propertyAdapter);

    }

    /**
     * Change LinearLayoutManager orientation of regionsLayoutManager and propertyLayoutManager
     * based on their current orientation
     */
    private void handleChangeLayoutBtnClick() {
        if (changeLayoutOrientationBtn.getText().equals(getString(R.string.verticalLayout))) {
            populateRecyclerViewListings(LinearLayoutManager.VERTICAL);
            changeLayoutOrientationBtn.setText(R.string.horizontalLayout);
        } else if (changeLayoutOrientationBtn.getText().equals(getString(R.string.horizontalLayout))) {
            populateRecyclerViewListings(LinearLayoutManager.HORIZONTAL);
            changeLayoutOrientationBtn.setText(R.string.verticalLayout);
        }
    }
}
