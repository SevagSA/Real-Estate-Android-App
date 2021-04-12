package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.realestateapplication.Fragments.ContactPropertyAgentFragment;
import com.example.realestateapplication.Fragments.RegistrationDialogFragment;
import com.example.realestateapplication.R;

public class LoginActivity extends AppCompatActivity {

    Button registerBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            RegistrationDialogFragment dialogFragment = new RegistrationDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "RegistrationDialogFragment");
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));
    }
}
