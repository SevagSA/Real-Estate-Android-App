package com.example.realestateapplication.Fragments;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistrationDialogFragment extends DialogFragment {

    EditText fullName;
    EditText email;
    EditText password;
    EditText password2;
    Button register_btn;
    FirebaseAuth fAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(intent);
            getActivity().finish();
        }

        fullName = root.findViewById(R.id.fullName);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        password2 = root.findViewById(R.id.password2);
        register_btn = root.findViewById(R.id.register_btn);

//    TODO this is not MVC: The method should be in the User model
        register_btn.setOnClickListener(e -> {
            String fullNameStr = fullName.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            String password2Str = password2.getText().toString().trim();
            boolean isValid = true;

            if (emailStr.isEmpty()) {
                email.setError("Email is required");
                isValid = false;
            }


            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", emailStr)) {
                email.setError("Email is not valid");
                isValid = false;
            }

            if (passwordStr.isEmpty()) {
                password.setError("Password is required");
                isValid = false;
            }

            if (passwordStr.length() < 6) {
                password.setError("Password must be longer than 6 characters");
                isValid = false;
            }

            if (!passwordStr.equals(password2Str)) {
                password2.setError("Passwords don't match");
                isValid = false;
            }

            if (isValid) {
                fAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                User user = new User(fullNameStr, emailStr,
                                        fAuth.getCurrentUser().getUid(), null);
                                user.insert();
                                Toast.makeText(getContext(), "Account created", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                getContext().startActivity(intent);
                            } else {
                                Toast.makeText(
                                        getContext(),
                                        "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Please fix the errors", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}
