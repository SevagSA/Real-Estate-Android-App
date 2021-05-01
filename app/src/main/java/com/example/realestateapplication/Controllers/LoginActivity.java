package com.example.realestateapplication.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.realestateapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginBtn;
    Button registerBtn;
    TextView forgotPasswordText;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            RegistrationDialogFragment dialogFragment = new RegistrationDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "RegistrationDialogFragment");
        });

        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        forgotPasswordText.setOnClickListener(this::resetPassword);

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
                fAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Please fix the errors", Toast.LENGTH_LONG).show();
            }
        });
    }

//    TODO this is not MVC: The method should be in the User model
    public void resetPassword(View v) {
        EditText newEmail = new EditText(v.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter your email to receive a reset link");
        passwordResetDialog.setView(newEmail);

        passwordResetDialog.setPositiveButton("Reset", (dialog, which) -> {
            String email = newEmail.getText().toString();
            fAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Reset link sent to your email", Toast.LENGTH_LONG).show();
                })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
        passwordResetDialog.setNegativeButton("Go back", (dialog, which) -> {

        });
        passwordResetDialog.create().show();
    }

}
