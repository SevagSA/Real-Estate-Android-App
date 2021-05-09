package com.example.realestateapplication.Models;

import android.content.Context;
import android.database.Cursor;

import com.example.realestateapplication.Models.DBHelpers.RegionDBHelper;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Observable;

public class Region {

    private String imgURL;
    private String locationTitle;
    private RegionDBHelper db;

    public Region(Context context) {
        db = new RegionDBHelper(context);
    }

    public Region(String imgURL, String locationTitle) {
        this.imgURL = imgURL;
        this.locationTitle = locationTitle;
    }

    /**
     * To get all of the regions from the DB with all of their attributes.
     *
     * @return An ArrayList<Region> of all of the regions.
     */
    public ArrayList<Region> getAllRegions() {
        ArrayList<Region> regions = new ArrayList<>();
        try (Cursor cursor = db.runQuery("SELECT * FROM " + RegionDBHelper.TABLE_NAME)) {
            while (cursor.moveToNext()) {
                Region region = new Region(cursor.getString(1), cursor.getString(2));
                regions.add(region);
            }
        }
        return regions;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getImgURL() {
        return imgURL;
    }
}
