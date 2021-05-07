package com.example.realestateapplication.Models;

import android.content.Context;
import android.database.Cursor;

import com.example.realestateapplication.Controllers.LikedListingsActivity;
import com.example.realestateapplication.Models.DBHelpers.UserDBHelper;

import java.util.ArrayList;

public class User {
    private String fullName;
    private String email;
    private String password;
    private ArrayList<Property> likedListings;
//    private LikedListingsActivity likedListingsActivity;

    private UserDBHelper db;


    public User() {

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
                new String[] {getEmail(), getPassword()});
//        TODO email must be unique in the DB for this to work.
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        } else {
            return (long) -1;
        }
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

//    public ArrayList<Property> getLikedListings() {
//        return likedListings;
//    }
//
//    public void setLikedListings(ArrayList<Property> likedListings) {
//        this.likedListings = likedListings;
//    }
}
