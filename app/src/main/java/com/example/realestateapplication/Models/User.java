package com.example.realestateapplication.Models;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.realestateapplication.Controllers.LikedListingsActivity;
import com.example.realestateapplication.Models.DBHelpers.UserDBHelper;

import java.util.ArrayList;

public class User {
    private String fullName;
    private String email;
    private String password;
//    private ArrayList<Property> likedListings;
//    private LikedListingsActivity likedListingsActivity;

    private UserDBHelper db;


    public User() {

    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public User(Context context) {
        db = new UserDBHelper(context);
    }

//    public User(LikedListingsActivity likedListingsActivity) {
//        this.likedListingsActivity = likedListingsActivity;
//    }

    public Long insert() {
        return db.addData(this.getEmail(), this.getFullName(), this.getPassword());
    }

    public Long login() {
        Cursor cursor = db.runQuery(
                "SELECT * FROM " + UserDBHelper.TABLE_NAME +
                        " WHERE email = ? AND password = ?",
                new String[]{getEmail(), getPassword()});
//        TODO email must be unique in the DB for this to work.
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        } else {
            return (long) -1;
        }
    }

    public User getUserById(String userId) {
        User user;
        try (Cursor cursor = db.runQuery("SELECT * FROM User WHERE id = ?", new String[]{userId})) {
            if (cursor.moveToFirst()) {
                user = new User(cursor.getString(1), cursor.getString(2));
            } else {
                return null;
            }
        }
        return user;
    }

    public int update(String id, String email, String fullName, String password) {
        Log.d("userId", id);
        return db.updateData(id, email, fullName, password);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", db=" + db +
                '}';
    }
}
