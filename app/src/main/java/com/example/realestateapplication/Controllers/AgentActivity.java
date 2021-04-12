package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.TextView;

import com.example.realestateapplication.R;

public class AgentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_page);

        ((TextView)findViewById(R.id.agentNameText)).setText("TestName");
        ((TextView)findViewById(R.id.agentEmalText)).setText("TestEmail");
        ((TextView)findViewById(R.id.agentPhoneText)).setText("TestPhone");
        ((TextView)findViewById(R.id.agentSalesText)).setText("TestSales");
    }
}