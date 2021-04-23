package com.example.realestateapplication.Controllers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// each activity is a controller for their respective model(s) and view(s);
public class MainActivity extends AppCompatActivity{

    Button frenchLanguageBtn;
    Button englishLanguageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__select);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World123!");

        frenchLanguageBtn = findViewById(R.id.frenchLanguageBtn);
        frenchLanguageBtn.setOnClickListener(e -> {
//            TODO: 1. translate the app to english.
//            TODO: 2. save their translated language
//            TODO: 3. don't ask them again once they choose a language
//            TODO: 4. if they have already chosen a language, when the open the app, use that language
//            TODO: 5. there should be an option to change back the used language
            startActivity(new Intent(this, LoginActivity.class));
        });

        englishLanguageBtn = findViewById(R.id.englishLanguageBtn);
        englishLanguageBtn.setOnClickListener(e -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "The channel name";
            String description = "The channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}