package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.realestateapplication.Fragments.RegistrationFragment;
import com.example.realestateapplication.R;
import com.example.realestateapplication.RegistrationDialog_Obsolete;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;

    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        toolbar = findViewById(R.id.appToolbar);

        setSupportActionBar(toolbar);
        regButton = findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrationFragment regFrag = new RegistrationFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.loginPage, regFrag).commit();
            }
        });
    }


    //for registration:
    //https://www.youtube.com/watch?v=ARezg1D9Zd0
    //https://www.youtube.com/watch?v=GIiDNiebFt8
    //9:11
}