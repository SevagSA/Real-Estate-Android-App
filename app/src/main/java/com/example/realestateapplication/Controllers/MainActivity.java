package com.example.realestateapplication.Controllers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapplication.Models.Unsplash;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;
import com.example.realestateapplication.Utils.LocalHelper;

import java.util.Locale;

// each activity is a controller for their respective model(s) and view(s);
public class MainActivity extends AppCompatActivity {

    Button armenianLanguageBtn;
    Button englishLanguageBtn;

    /**
     * An API that returns images of regions (the ones you have listed). So every time, there would
     * be a new image of that place
     * <p>
     * OR
     * <p>
     * An Activity that displays various numbers relating to the current housing market in Canada.
     * Liked avg mortgage prices, house prices, etc.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__select);
        armenianLanguageBtn = findViewById(R.id.armenianLanguageBtn);

        String isloggedInStr = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.login_shared_pref), null);
        if (isloggedInStr != null && isloggedInStr.equals("true")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        LocalHelper localHelper = new LocalHelper(this);

        String chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);
        if (chosenLang != null && (chosenLang.equals("hy") || chosenLang.equals("en"))) {
            localHelper.changeLocale(chosenLang);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        armenianLanguageBtn.setOnClickListener(e -> {
            localHelper.changeLocale("hy");
            startActivity(new Intent(this, LoginActivity.class));
        });

        englishLanguageBtn = findViewById(R.id.englishLanguageBtn);
        englishLanguageBtn.setOnClickListener(e -> {
            localHelper.changeLocale("en");
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}