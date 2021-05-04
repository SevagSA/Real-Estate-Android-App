package com.example.realestateapplication.Models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.realestateapplication.Controllers.LikedListingsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class User {
    private String fullName;
    private String email;
    private String uid;
    private ArrayList<Property> likedListings;
    private LikedListingsActivity likedListingsActivity;

    public User(String fullName, String email, String uuid, ArrayList<Property> likedListings) {
        this.fullName = fullName;
        this.email = email;
        this.uid = uuid;
        this.likedListings = likedListings;
    }

    public User(LikedListingsActivity likedListingsActivity) {
        this.likedListingsActivity = likedListingsActivity;
    }

    public void insert() {
        Map<String, Object> user = new HashMap<>();
        user.put("email", getEmail());
        FirebaseDatabase.getInstance().getReference().child("LikedListing").push().setValue(user);
        user.put("fullName", getFullName());
        user.put("uuid", getUid());
        String likedListingsStr = "";
        if (getLikedListings() != null) {
            for (Property listing : getLikedListings()) {
                likedListingsStr += listing.getPropertyAddress() + ", ";
            }
        }
        user.put("likedListings", likedListingsStr);
        FirebaseDatabase.getInstance().getReference().child("User").push().setValue(user);
    }

    public void getLikedListingsForUser(String userEmail) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("LikedListing");
        userRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    HashMap<Object, String> likedListingHash = (HashMap<Object, String>) snap.getValue();
                    likedListingHash.remove("email");
                    ArrayList<Property> likedListings = new ArrayList<>();
                    for (String value : likedListingHash.values()) {
//                        TODO explain waht is the num 9 as a comment
                        value = value.substring(9);
                        value = value.substring(0, value.length() - 1);

                        String agentStr = value.substring(value.indexOf("agent="));
                        agentStr = agentStr.substring(0, agentStr.lastIndexOf("}") + 1);


//                        value = value.substring(0, value.indexOf("agent="))  + value.substring(value.indexOf(value.substring(0, value.indexOf("agent="))));
                        StringBuffer valueBuff = new StringBuffer(value);
                        value = valueBuff.replace(
                                value.indexOf("agent="), value.lastIndexOf("}"), value).toString();
                        Log.d("idx of agent", value);


//                        CONTINUE FROM HERE
//                        You were trying to remove the agent object
//                        from the `value` string so that it dont' interfer with the
//                        for loop you're doing at the bottomt.
//                        Once that's done, get the Property classes' values from the
//                        propertyHash Map and create a new Propety obj with that.
//                        Then add that Propety to an ArrayList and pass that arrya List to
//                        populateRecyclerViewListings

                        String[] arr = value.split(", ");
//                        Log.d("propAttributeArray", Arrays.toString(arr));
                        HashMap<Object, Object> propertyHash = new HashMap<>();
                        for (int i = 0; i < arr.length; i++) {
//                            Log.d("arr", arr[i]);
                            String[] propAttributeArray = arr[i].split("=");
//                            Log.d("propAttributeArray", Arrays.toString(propAttributeArray));
                            if (propAttributeArray[0].equals("agent")) {
//                                Log.d("agent", propAttributeArray[1]);
                            }
                            propertyHash.put(propAttributeArray[0], propAttributeArray[1]);
                        }
//                        likedListings.add();
//                        Log.d("propertyHash", propertyHash.toString());
                    }
                    likedListingsActivity.populateRecyclerViewListings(likedListings);

//                    ArrayList<String> addresses = new ArrayList(Arrays.asList(likedListings.split(", ")));
//                    Property property = new Property(likedListingsActivity);

//                    property.getPropertyByAddress(addresses);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void handleLikedBtnClick(String userEmail, Property property) {
        DatabaseReference likedListingRef = FirebaseDatabase.getInstance().getReference().child("LikedListing");
        likedListingRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    HashMap<Object, Object> likedListingHash = (HashMap<Object, Object>) snap.getValue();

                    Map<String, Object> likedListingsUpdatesMap = new HashMap<>();
                    String key = snap.getKey();
                    if (!likedListingHash.containsKey(property.getPropertyAddress())) {
                        likedListingsUpdatesMap.put(key + "/" + property.getPropertyAddress(), property.toString());
                        likedListingRef.updateChildren(likedListingsUpdatesMap);
                    } else if (likedListingHash.containsKey(property.getPropertyAddress())) {
                        Log.d("likedListingRef", likedListingHash.toString());
                        likedListingHash.remove(property.getPropertyAddress());

                        likedListingsUpdatesMap.put(key, likedListingHash);

                        likedListingRef.updateChildren(likedListingsUpdatesMap);
                    }
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
