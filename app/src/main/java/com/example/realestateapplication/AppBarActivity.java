package com.example.realestateapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.realestateapplication.Controllers.ListPropertyActivity;
import com.example.realestateapplication.Controllers.SearchPropertyActivity;

public class AppBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            startActivity(new Intent(this, SearchPropertyActivity.class));
        } else if (id == R.id.likeItem) {
            Toast.makeText(getApplicationContext(), "likeItem", Toast.LENGTH_LONG);
//            LikedListingFragment dialogFragment = new LikedListingFragment();
//            dialogFragment.show(getSupportFragmentManager(), "LikedListingFragment");
        } else if (id == R.id.about) {
            Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG);
//            AboutCompanyFragment dialogFragment = new AboutCompanyFragment();
//            dialogFragment.show(getSupportFragmentManager(), "AboutCompanyFragment");
        } else if (id == R.id.logout) {
            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG);
        }
        return true;
    }
}