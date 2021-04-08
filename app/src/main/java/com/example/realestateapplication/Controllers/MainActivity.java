package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.R;

// each activity is a controller for their respective model(s) and view(s);
public class MainActivity extends AppCompatActivity{

    Button frenchLanguageBtn;
    Button englishLanguageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__select);

        frenchLanguageBtn = findViewById(R.id.frenchLanguageBtn);
        frenchLanguageBtn.setOnClickListener(e -> {
//            TODO: 1. translate the app to english.
//            TODO: 2. save their translated language
//            TODO: 3. don't ask them again once they choose a language
//            TODO: 4. if they have already chosen a language, when the open the app, use that language
//            TODO: 5. there should be an option to change back the used language
            startActivity(new Intent(this, HomeActivity.class));
        });

        englishLanguageBtn = findViewById(R.id.englishLanguageBtn);
        englishLanguageBtn.setOnClickListener(e -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}