package com.example.realestateapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Adapters.AgentAdapter;
import com.example.realestateapplication.Fragments.AboutDialogFragment;
import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

public class AgentListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GridView agentGridLayout;
    private DrawerLayout drawer;

    private String chosenLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

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

        agentGridLayout = findViewById(R.id.agentGridLayout);
        Agent agent = new Agent(this);
        AgentAdapter agentAdapter = new AgentAdapter(
                this, R.layout.agent_item, agent.getAllAgents());
        agentGridLayout.setAdapter(agentAdapter);
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
//            case R.id.nav_see_sent_messages:
//                Toast.makeText(this, "Sent msg", Toast.LENGTH_LONG).show();
//                break;
            case R.id.nav_share_fb:
                Toast.makeText(this, "nav_share_fb", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share_tweet:
                Toast.makeText(this, "nav_share_tweet", Toast.LENGTH_LONG).show();
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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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