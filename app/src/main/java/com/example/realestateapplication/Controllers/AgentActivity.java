package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import com.example.realestateapplication.R;

public class AgentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_page);
        toolbar = findViewById(R.id.appToolbar);

        setSupportActionBar(toolbar);
    }
}