package com.example.realestateapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Adapters.PropertyCardRecyclerViewAdapter;
import com.example.realestateapplication.Adapters.RegionsRecyclerViewAdapter;
import com.example.realestateapplication.Fragments.AboutDialogFragment;
import com.example.realestateapplication.Fragments.EditProfileDialogFragment;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.Region;
import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Models.Unsplash;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        /**Communication */
{

    private Region region;
    private Property property;
    private Button changeLayoutOrientationBtn;
    private String chosenLang;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Unsplash();
        region = new Region(this);
        property = new Property(this);
        populateRecyclerViewListings(LinearLayoutManager.HORIZONTAL);
        chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);

        int raph = this.getResources().getIdentifier("raphael_jones", "drawable", this.getPackageName());
        Log.d("raph", raph + " ");

        int alex_mason_drawable = this.getResources().getIdentifier("alex_mason", "drawable", this.getPackageName());

        Log.d("alex_mason_drawable", alex_mason_drawable + " ");
        int olivia_james_drawable = this.getResources().getIdentifier("olivia_james", "drawable", this.getPackageName());

        Log.d("olivia_james_drawable", olivia_james_drawable + " ");

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
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getString(R.string.about);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        getString(R.string.share_title));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
                break;
            case R.id.nav_list_of_agents:
                startActivity(new Intent(this, AgentListActivity.class));
                break;
            case R.id.nav_home_page:
                startActivity(new Intent(this, HomeActivity.class));
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
            AboutDialogFragment dialogFragment = new AboutDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AboutDialogFragment");
        } else if (id == R.id.logout) {
            SharedPreferences shared = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(getString(R.string.login_shared_pref), "false");
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.your_profile) {
            ProfileDialogFragment dialogFragment = new ProfileDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "ProfileDialogFragment");
        } else if (id == R.id.changeLanguage) {
            LocaleHelper localeHelper = new LocaleHelper(this);
            if (chosenLang.equals("hy")) {
                localeHelper.changeLocale("en", "User", R.string.selected_language);
            } else {
                localeHelper.changeLocale("hy", "User", R.string.selected_language);
            }
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                    .getString(getString(R.string.selected_language), null);
            Toast.makeText(this, chosenLang, Toast.LENGTH_LONG).show();
            invalidateOptionsMenu();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.changeLanguage);
        if (chosenLang.equals("hy")) {
            item.setTitle(R.string.english);
        } else {
            item.setTitle(R.string.armenian);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public void handleData(String message) {
////        pass success message notifiying the user of what their
////        old full name and email was and what their new one is now.
//        ProfileDialogFragment f1 = new ProfileDialogFragment();
//        f1.handleData2(message);
//    }

//    @Override
//    public void handleData2(String email, String fullName) {
////        pass the email and full name
//        EditProfileDialogFragment f2 = new EditProfileDialogFragment();
//        f2.handleData(email, fullName);
//    }

    /**
     * Render the Regions and Recent Listings in their respective RecyclerView.
     */
    public void populateRecyclerViewListings(int layoutOrientation) {
        ArrayList<Region> regions = region.getAllRegions();

        LinearLayoutManager regionLayoutManager = new LinearLayoutManager(
                this, layoutOrientation, false);
        RecyclerView regionsRecyclerView = findViewById(R.id.regionsRecyclerView);
        regionsRecyclerView.setLayoutManager(regionLayoutManager);
        RegionsRecyclerViewAdapter regionsAdapter =
                new RegionsRecyclerViewAdapter(regions, this);
        regionsRecyclerView.setAdapter(regionsAdapter);

        LinearLayoutManager propertyLayoutManager = new LinearLayoutManager(
                this, layoutOrientation, false
        );
        ArrayList<Property> properties = property.getRecentProperties();
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
