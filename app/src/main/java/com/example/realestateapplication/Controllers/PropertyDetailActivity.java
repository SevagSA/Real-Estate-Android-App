package com.example.realestateapplication.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapplication.Adapters.PropertyGalleryRecyclerViewAdapter;
import com.example.realestateapplication.Fragments.ContactPropertyAgentFragment;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

public class PropertyDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private Toolbar toolbar;

    ImageButton agentButton;
    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

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

        property = getIntent().getParcelableExtra("property");
        property.setContext(this);

        agentButton = findViewById(R.id.propertyAgentImageView);
        agentButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AgentActivity.class);
            Agent agent = new Agent(
                    property.getAgent().getFullName(),
                    property.getAgent().getCompanyName(),
                    property.getAgent().getProfileImgId(),
                    property.getAgent().getNumOfSoldListings(),
                    property.getAgent().getServiceLocation(),
                    property.getAgent().getEmail(),
                    property.getAgent().getPhoneNumber()
            );
            Log.d("in the on click", agent.toString());
            intent.putExtra("agent", agent);
            startActivity(intent);
        });

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(property.getPropertyMainImgURL())
                .into((ImageView)findViewById(R.id.propertyMainImageView));

        ((ImageView)findViewById(R.id.propertyAgentImageView)).setImageResource(property.getAgent().getProfileImgId());
        ((TextView)findViewById(R.id.propertyAgentNameText)).setText(property.getAgent().getFullName());
        ((TextView)findViewById(R.id.propertyAddressText)).setText(property.getPropertyAddress());
        ((TextView)findViewById(R.id.propertyPriceText)).setText(property.getPropertyPrice());
        ((TextView)findViewById(R.id.propertyNumOfBathText)).setText(property.getPropertyNumOfBath());
        ((TextView)findViewById(R.id.propertyNumOfBedText)).setText(property.getPropertyNumOfBed());
        ((TextView)findViewById(R.id.propertySQFTText)).setText(property.getPropertySquareFoot());

        populateRecyclerViewListings();

        (findViewById(R.id.propertyAgentContactBtn)).setOnClickListener(e -> {
            // TODO: you can pass the agent in the constructor and use it in the form (for the email)
            ContactPropertyAgentFragment dialogFragment = new ContactPropertyAgentFragment();
            dialogFragment.show(getSupportFragmentManager(), "ContactPropertyAgentFragment");
        });
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
            startActivity(new Intent(this, LoginActivity.class));
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
