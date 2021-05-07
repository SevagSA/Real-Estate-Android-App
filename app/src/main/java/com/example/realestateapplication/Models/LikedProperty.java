package com.example.realestateapplication.Models;

import android.content.Context;
import android.database.Cursor;

import com.example.realestateapplication.Models.DBHelpers.LikedPropertyDBHelper;
import com.example.realestateapplication.Models.DBHelpers.PropertyDBHelper;

import java.util.ArrayList;

public class LikedProperty {

    private String userId;
//    TODO if you are going to use address, then use address as the ID for the Property table aswell.
//     Also, address will need to be UNIQUE (w/Constraint) and enforece the uniquenes on the app
//     (if a user chooses a same address, notify it to them.)
    private String propertyAddress;
    private LikedPropertyDBHelper db;
    private Context context;

    public LikedProperty(String userId, String propertyAddress, Context context) {
        this.userId = userId;
        this.propertyAddress = propertyAddress;
        this.context = context;
        db = new LikedPropertyDBHelper(context);
    }

    public LikedProperty(String userId, Context context) {
        this.userId = userId;
        this.context = context;
        db = new LikedPropertyDBHelper(context);
    }

    public boolean handleLikedBtnClick() {
        Cursor cursor = db.runQuery("SELECT * FROM " + LikedPropertyDBHelper.TABLE_NAME +
                        " WHERE user_id = ? AND property_id = ?",
                new String[]{getUserId(), getPropertyAddress()});
        boolean has_liked = cursor.moveToFirst();
        if (has_liked) {
            long result = (long) db.deleteValue(cursor.getString(0));
            return result != -1;
        } else {
            Long result = db.addData(getUserId(), getPropertyAddress());
            return result != -1;
        }
    }

    /**
     * To get all of the liked properties of the current user from the DB with all of their attributes.
     *
     * @return An ArrayList<Property> of the all of the liked properties of the user.
     */
    public ArrayList<Property> getLikedListingsForUser() {
        ArrayList<Property> properties = new ArrayList<>();
        try (Cursor cursor = db.runQuery(
                "SELECT property.* " +
                "FROM " + LikedPropertyDBHelper.TABLE_NAME + " " +
                "JOIN " + PropertyDBHelper.TABLE_NAME + " " +
                "ON " + LikedPropertyDBHelper.TABLE_NAME + "." + LikedPropertyDBHelper.COL_PROPERTY_ID +
                    " = " + PropertyDBHelper.TABLE_NAME + "." + PropertyDBHelper.COL_PROPERTY_ADDRESS + " " +
                "WHERE " + LikedPropertyDBHelper.COL_USER_ID + " = ?",
                new String[]{getUserId()})) {
            while (cursor.moveToNext()) {
                Property property = new Property(
                        context,
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
//                       type
                        cursor.getString(8),
//                        price
                        cursor.getString(9),
//                        address
                        cursor.getString(10),
//                        bed
                        cursor.getInt(11),
//                        bath
                        cursor.getInt(12),
//                        sqft
                        cursor.getInt(13),
//                        agentid
                        cursor.getInt(7)
                );
                properties.add(property);
            }
        }
        return properties;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }
}
