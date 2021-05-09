package com.example.realestateapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;

import java.util.regex.Pattern;

public class RegistrationDialogFragment extends DialogFragment {

    EditText fullName;
    EditText email;
    EditText password;
    EditText password2;
    Button register_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

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

            if (fullNameStr.isEmpty()) {
                fullName.setError(getString(R.string.full_name_required));
                isValid = false;
            }

            if (emailStr.isEmpty()) {
                email.setError(getString(R.string.email_is_required));
                isValid = false;
            }

            if (passwordStr.isEmpty()) {
                password.setError(getString(R.string.password_is_required));
                isValid = false;
            }

            if (password2Str.isEmpty()) {
                password2.setError(getString(R.string.password2_is_required));
                isValid = false;
            }


            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", emailStr)) {
                email.setError(getString(R.string.email_not_valid));
                isValid = false;
            }

            if (passwordStr.length() < 6) {
                password.setError(getString(R.string.pass_must_be_longer));
                isValid = false;
            }

            if (!passwordStr.equals(password2Str)) {
                password2.setError(getString(R.string.passwords_dont_match));
                isValid = false;
            }

            if (isValid) {
                User user = new User(getContext());
                user.setFullName(fullNameStr);
                user.setEmail(emailStr);
                user.setPassword(passwordStr);
                Long result = user.insert();
                if (result != -1) {
                    SharedPreferences sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.putString(getString(R.string.login_shared_pref), "true");
                    Ed.putString(getString(R.string.user_id_shared_pref), result.toString());
                    Ed.apply();
                    Toast.makeText(getActivity(), getString(R.string.account_registered), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(),
                            R.string.error_while_registering_your_account, Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
}
