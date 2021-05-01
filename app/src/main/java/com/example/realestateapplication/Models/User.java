package com.example.realestateapplication.Models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String fullName;
    private String email;
    private String uid;
    private ArrayList<Property> likedListings;


    public User(String fullName, String email, String uuid, ArrayList<Property> likedListings) {
        this.fullName = fullName;
        this.email = email;
        this.uid = uuid;
        this.likedListings = likedListings;
    }

    public void insert() {
        Map<String, Object> user = new HashMap<>();
        user.put("fullName", getFullName());
        user.put("email", getEmail());
        user.put("uuid", getUid());
        String likedListingsStr = "";
        if (getLikedListings() != null) {
            for (Property listing : getLikedListings()) {
                likedListingsStr += listing.getPropertyAddress() + "& ";
            }
        }
        user.put("likedListings", likedListingsStr);
        FirebaseDatabase.getInstance().getReference().child("User").push().setValue(user);
    }

    public static void handleLikedBtnClick(String userEmail, String propertyAddress) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");
        userRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        HashMap<Object, Object> userHash = (HashMap<Object, Object>) snap.getValue();
                        String likedListings = userHash.get("likedListings").toString();
                        Log.d("likedListings", likedListings);

                        Map<String, Object> likedListingsUpdatesMap = new HashMap<>();
                        String key = snap.getKey();
//                        String updatedLikedListing;
                        if (!likedListings.contains(propertyAddress)) {
                            Log.d("in the add", propertyAddress);
                            likedListingsUpdatesMap.put(key+"/likedListings", likedListings + propertyAddress + "& ");
                            userRef.updateChildren(likedListingsUpdatesMap);
                        } else if (likedListings.contains(propertyAddress)) {
//                            Log.d("index", likedListings.indexOf(propertyAddress + "| ") + "");

                            String addressToBeRemoved = propertyAddress + "& ";
                            String updatedLikedListing = likedListings.replaceAll(addressToBeRemoved, "");
                            Log.d("addressToBeRemoved", addressToBeRemoved);
                            Log.d("updatedLikedListing", updatedLikedListing);
                            Log.d("likedListingsUpdatesMap", likedListingsUpdatesMap.toString());
                            likedListingsUpdatesMap.put(key+"/likedListings", updatedLikedListing);
                            userRef.updateChildren(likedListingsUpdatesMap);
//                            updatedLikedListing = "";
                        }
//                        likedListingsUpdatesMap.put(key+"/likedListings", updatedLikedListing);
//                        userRef.updateChildren(likedListingsUpdatesMap);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Property> getLikedListings() {
        return likedListings;
    }

    public void setLikedListings(ArrayList<Property> likedListings) {
        this.likedListings = likedListings;
    }
}
