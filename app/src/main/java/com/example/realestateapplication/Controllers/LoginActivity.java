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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        String isloggedInStr = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.login_shared_pref), null);
        if (isloggedInStr != null && isloggedInStr.equals("true")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            RegistrationDialogFragment dialogFragment = new RegistrationDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "RegistrationDialogFragment");
        });


        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        // Validating user input before passing it to the model.
        loginBtn.setOnClickListener(view -> {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            boolean isValid = true;

            if (emailStr.isEmpty()) {
                email.setError(getString(R.string.email_is_required));
                isValid = false;
            }

            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", emailStr)) {
                email.setError(getString(R.string.email_not_valid));
                isValid = false;
            }

            if (passwordStr.isEmpty()) {
                password.setError(getString(R.string.password_is_required));
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
                    Toast.makeText(getApplicationContext(), getString(R.string.logged_in), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.account_does_not_exits), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.fix_the_errors), Toast.LENGTH_LONG).show();
            }
        });
    }
}
