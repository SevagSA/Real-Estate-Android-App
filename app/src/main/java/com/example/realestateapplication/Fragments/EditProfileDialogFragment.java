package com.example.realestateapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;

import java.util.regex.Pattern;

public class EditProfileDialogFragment extends DialogFragment {

    User user;
    String userId;
    private Communication comm;

    public EditProfileDialogFragment() {
        // Required empty public constructor
    }

    public EditProfileDialogFragment(Context context) {
        this.user = new User(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile_dialog, container, false);
        userId = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.user_id_shared_pref), null);
        User currentUser = user.getUserById(userId);
        EditText email = root.findViewById(R.id.editEmailInput);
        EditText fullName = root.findViewById(R.id.editFullNameInput);

        email.setText(currentUser.getEmail());
        fullName.setText(currentUser.getFullName());


        EditText password = root.findViewById(R.id.editPasswordInput);
        EditText password2 = root.findViewById(R.id.editPassword2Input);

        root.findViewById(R.id.editUserBtn).setOnClickListener(e -> {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            String password2Str = password2.getText().toString().trim();

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

            if (passwordStr.length() < 6) {
                password.setError(getString(R.string.pass_must_be_longer));
                isValid = false;
            }

            if (!passwordStr.equals(password2Str)) {
                password2.setError(getString(R.string.passwords_dont_match));
                isValid = false;
            }

            if (isValid) {
                int result = user.update(userId, email.getText().toString(), fullName.getText().toString(),
                        password.getText().toString());

//                TODO there might be opppruitun to do 2 way communication here
//                 (maybe like a success message? e.g. after edditing prfile, a msg
//                  is sent to the ProfileDialogFragment -> "Profile edited succesfully").
//                 OK but what will ProfileDialogFragment send to EditProfileDialogFragment?
//                  Maybe, it can send the user info: email and full name, instead of getting
//                      the email and full name from the DB in this fragment.

                dismiss();
                ProfileDialogFragment dialogFragment = new ProfileDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "ProfileDialogFragment");
                if (result == 0) {
                    Toast.makeText(getActivity(),
                            R.string.error_while_updating_your_account, Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        comm = (Communication)getActivity();
//        getActivity().findViewById(R.id.userEditBtn).setOnClickListener(e -> {
//            comm.handleData2("this is the message");
//        });
    }

    public void handleData(String email, String fullName) {
        Toast.makeText(getActivity(), email + " " + fullName, Toast.LENGTH_LONG).show();
    }
}