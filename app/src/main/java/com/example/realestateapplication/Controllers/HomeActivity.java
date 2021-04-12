package com.example.realestateapplication.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.realestateapplication.Adapters.PropertyCardRecyclerViewAdapter;
import com.example.realestateapplication.Adapters.RegionsRecyclerViewAdapter;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.Region;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity implements Observer, NavigationView.OnNavigationItemSelectedListener {


    private Region region = new Region();
    private final Property property = new Property(this);

    Button changeLayoutOrientationBtn;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = findViewById(R.id.homeDrawerMenu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // creating a relationship between the observable Model and the observer Activity
        region = new Region();
        region.addObserver(this);

        populateRecyclerViewListings(LinearLayoutManager.HORIZONTAL);

        changeLayoutOrientationBtn = findViewById(R.id.changeLayoutOrientationBtn);
        changeLayoutOrientationBtn.setOnClickListener(e -> handleChangeLayoutBtnClick());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_list_property:
                startActivity(new Intent(this, ListPropertyActivity.class));
                break;
            case R.id.nav_see_sent_messages:
                Toast.makeText(this, "Sent msg", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share_fb:
                Toast.makeText(this, "nav_share_fb", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share_tweet:
                Toast.makeText(this, "nav_share_tweet", Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            startActivity(new Intent(this, SearchPropertyActivity.class));
        } else if (id == R.id.likeItem) {
            startActivity(new Intent(this, LikedListingsActivity.class));
        } else if (id == R.id.about) {
            Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG).show();
//            AboutCompanyFragment dialogFragment = new AboutCompanyFragment();
//            dialogFragment.show(getSupportFragmentManager(), "AboutCompanyFragment");
        } else if (id == R.id.logout) {
            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    /**
     * Go to the ListPropertyActivity
     */
    private void handleGoToListPropertyPageBtnClick() {
        startActivity(new Intent(this, ListPropertyActivity.class));
    }
}

//Places.initialize(getApplicationContext(), "AIzaSyD3hdT4eY5_Dh2mCrGGV1Rkd_PM4AhcBJM");
//        PlacesClient placesClient = Places.createClient(getApplicationContext());
//
//        Log.d("here", findViewById(R.id.searchInputBarSearchView) + "");
//
//        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
//        getSupportFragmentManager().findFragmentById(R.id.searchInputBarSearchView);
//
//        autocompleteSupportFragment.setTypeFilter(TypeFilter.CITIES);
//        autocompleteSupportFragment.setLocationBias(RectangularBounds.newInstance(
//        new LatLng(48.435135, -125.615663),
//        new LatLng(69.292697, -68.509274)));
//        autocompleteSupportFragment.setCountries("CA");
//        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
//
//        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//@Override
//public void onPlaceSelected(@NonNull Place place) {
//        Log.i("onPlaceSelected",
//        place.getName() + ", "
//        + place.getAddress() + ", id: " + place.getId());
//        }
//
//@Override
//public void onError(@NonNull Status status) {
//        Log.d("onError", "An error occurred: " + status);
//        }
//        });