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
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button armenianLanguageBtn;
    Button englishLanguageBtn;

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

        LocaleHelper localeHelper = new LocaleHelper(this);

        String chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);
        if (chosenLang != null && (chosenLang.equals("hy") || chosenLang.equals("en"))) {
            localeHelper.changeLocale(chosenLang, "User", R.string.selected_language);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        armenianLanguageBtn.setOnClickListener(e -> {
            localeHelper.changeLocale("hy", "User", R.string.selected_language);
            startActivity(new Intent(this, LoginActivity.class));
        });

        englishLanguageBtn = findViewById(R.id.englishLanguageBtn);
        englishLanguageBtn.setOnClickListener(e -> {
            localeHelper.changeLocale("en", "User", R.string.selected_language);
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}