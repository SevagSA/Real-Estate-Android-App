package com.example.realestateapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Adapters.PropertyCardRecyclerViewAdapter;
import com.example.realestateapplication.Fragments.AboutDialogFragment;
import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AgentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String chosenLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_page);
        chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);
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

        Agent agent = getIntent().getParcelableExtra("agent");
        populateRecyclerViewListings(agent.getAgentId());

        ((ImageView)findViewById(R.id.agentImg)).setImageResource(agent.getProfileImgId());
        ((TextView)findViewById(R.id.agentNameText)).setText(agent.getFullName());
        ((TextView)findViewById(R.id.agentEmailText)).setText(agent.getEmail());
        ((TextView)findViewById(R.id.agentPhoneText)).setText(agent.getPhoneNumber());
        ((TextView)findViewById(R.id.agentSalesText)).setText(Integer.toString(agent.getNumOfSoldListings()));
    }

    /**
     * Render the agent's listing by the current user
     */
    private void populateRecyclerViewListings(String agentId) {
        Property property = new Property(this);
        ArrayList<Property> agentProperties = property.getAllPropertiesOfAgent(agentId);

        LinearLayoutManager agentPropertyLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView agentPropertyRecyclerView = findViewById(R.id.agentListingRecyclerView);
        agentPropertyRecyclerView.setLayoutManager(agentPropertyLayoutManager);
        PropertyCardRecyclerViewAdapter agentPropertyAdapter =
                new PropertyCardRecyclerViewAdapter(agentProperties, this);
        agentPropertyRecyclerView.setAdapter(agentPropertyAdapter);
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
            case R.id.nav_down_payment_calculator:
                startActivity(new Intent(this, DownPaymentActivity.class));
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
}