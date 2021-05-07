package com.example.realestateapplication.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapplication.Fragments.RegistrationDialogFragment;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginBtn;
    Button registerBtn;
    TextView forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        String sharedPreRes = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.login_shared_pref), null);
        if (sharedPreRes != null && sharedPreRes.equals("true")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            RegistrationDialogFragment dialogFragment = new RegistrationDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "RegistrationDialogFragment");
        });

        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

//    TODO this is not MVC: The method should be in the User model
        loginBtn.setOnClickListener(view -> {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            boolean isValid = true;

            if (emailStr.isEmpty()) {
                email.setError("Email is required");
                isValid = false;
            }

            if (passwordStr.isEmpty()) {
                password.setError("Password is required");
                isValid = false;
            }

            if (isValid) {
                User user = new User(this);
                user.setEmail(emailStr);
                user.setPassword(passwordStr);
                Long result = user.login();
                if (result != -1) {
                    SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.putString(getString(R.string.login_shared_pref), "true");
                    Ed.putString(getString(R.string.user_id_shared_pref), result.toString());
                    Ed.apply();
                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Account does not exist.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Please fix the errors", Toast.LENGTH_LONG).show();
            }
        });
    }
}
