package com.example.realestateapplication.Controllers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Adapters.PropertyGalleryRecyclerViewAdapter;
import com.example.realestateapplication.Fragments.ContactEmailSuccessFragment;
import com.example.realestateapplication.Fragments.AboutDialogFragment;
import com.example.realestateapplication.Fragments.ContactPropertyAgentDialogFragment;
import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Fragments.PropertyActionAlertDialog;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

public class PropertyDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    ImageButton agentButton;
    Property property;
    Agent agent;

    private String chosenLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

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

        property = getIntent().getParcelableExtra("property");
        property.setContext(this);

        agent = new Agent(this);
        agent = agent.getAgentById(property.getAgentId());
        agentButton = findViewById(R.id.propertyAgentImageView);
        agentButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AgentActivity.class);
            intent.putExtra("agent", agent);
            startActivity(intent);
        });

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(property.getPropertyMainImg())
                .into((ImageView) findViewById(R.id.propertyMainImageView));

        ((ImageView) findViewById(R.id.propertyAgentImageView)).setImageResource(agent.getProfileImgId());
        ((TextView) findViewById(R.id.propertyAgentNameText)).setText(agent.getFullName());
        ((TextView) findViewById(R.id.propertyAddressText)).setText(property.getPropertyAddress());
        ((TextView) findViewById(R.id.propertyPriceText)).setText(property.getPropertyPrice());
        ((TextView) findViewById(R.id.propertyNumOfBathText)).setText(property.getPropertyNumOfBath());
        ((TextView) findViewById(R.id.propertyNumOfBedText)).setText(property.getPropertyNumOfBed());
        ((TextView) findViewById(R.id.propertySQFTText)).setText(property.getPropertySquareFoot());

        String userId = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getResources().getString(R.string.user_id_shared_pref), null);
        if (Integer.toString(property.getOwnerId()).equals(userId)) {
            Button propertyActionBtn = findViewById(R.id.property_actions_btn);
            propertyActionBtn.setVisibility(View.VISIBLE);
            propertyActionBtn.setOnClickListener(e -> {
                        PropertyActionAlertDialog dialogFragment =
                                new PropertyActionAlertDialog(property.getPropertyId(), this);
                        dialogFragment.show(getSupportFragmentManager(), "PropertyActionAlertDialog");
                    }
            );
        }


        (findViewById(R.id.propertyAgentContactBtn)).setOnClickListener(e -> {
            ContactPropertyAgentDialogFragment dialogFragment = new ContactPropertyAgentDialogFragment(property, agent);
            dialogFragment.show(getSupportFragmentManager(), "ContactPropertyAgentDialogFragment");
        });
        populateRecyclerViewListings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this,"before if: " + requestCode, Toast.LENGTH_SHORT).show();

        if (requestCode  == 0) {
            Toast.makeText(this,"in if", Toast.LENGTH_SHORT).show();
            sendNotification();
            playSuccessSound();
            ContactEmailSuccessFragment dialogFragment = new ContactEmailSuccessFragment();
            dialogFragment.show(getSupportFragmentManager(), "ContactEmailSuccessFragment");
        }
    }

    public void playSuccessSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.caching);
        mediaPlayer.start();
    }

    public void sendNotification() {
//            TODO create a "viewAllSentMessages" activity for the owner of the proeprty and
//             put the intent there, not HomeActivity
        Intent propertyDetailIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,
                0, propertyDetailIntent, 0);

        Intent agentIntent = new Intent(this, AgentListActivity.class);
        PendingIntent agentPendingIntent = PendingIntent.getActivity(this,
                0, agentIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel ID");
        builder.setContentTitle(getString(R.string.message_has_been_sent));
        builder.setContentText(getString(R.string.your_message_for) + " " + property.getPropertyAddress() + " " + getString(R.string.was_sent));
        builder.setSmallIcon(R.drawable.house_notification_icon);
        builder.addAction(R.drawable.house_notification_icon, getString(R.string.home_page), contentPendingIntent);
        builder.addAction(R.drawable.ic_agents, getString(R.string.view_agents), agentPendingIntent);
        builder.setColor(getResources().getColor(R.color.btnColor));
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }
//

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
