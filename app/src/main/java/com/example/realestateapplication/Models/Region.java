package com.example.realestateapplication.Models;

import java.util.ArrayList;
import java.util.Observable;

public class Region extends Observable {

    private String imgURL;
    private String locationTitle;

    public Region() { }

    public Region(String imgURL, String locationTitle) {
        this.imgURL = imgURL;
        this.locationTitle = locationTitle;
    }

    /**
     * To get all of the regions from the DB with all of their attributes.
     * @return An ArrayList<Region> of all of the regions.
     */
    public ArrayList<Region> getAllRegions() {
        // TODO: this will be later queries from the DB, not hardcoded.

        ArrayList<Region> regions = new ArrayList<>();
        regions.add(new Region(
                "https://daily.sevenfifty.com/app/uploads/2017/11/SFD_48hrs_Vancouver2_CR_iStock_2520x1420.jpg",
                // this name will need to be compatible with the address API (if we use it)
                "Vancouver"
        ));

        regions.add(new Region(
                "https://www.tripsavvy.com/thmb/ixrK2cT9rjqxPbLYiwsV7BwrwxQ=/1600x900/smart/filters:no_upscale()/things-to-do-downtown-montreal-mathieu-dupuis-getty-5a70d7a1ae9ab80037bba0ff.jpg",
                // this name will need to be compatible with the address API (if we use it)
                "Downtown Montreal"
        ));

        regions.add(new Region(
                "https://www.thecutlerychronicles.com/wp-content/uploads/2020/01/Toronto-Skyline-1440x836.png",
                // this name will need to be compatible with the address API (if we use it)
                "Downtown Toronto"
        ));

        regions.add(new Region(
                "https://upload.wikimedia.org/wikipedia/commons/9/92/Downtown_Calgary_2020-2.jpg",
                // this name will need to be compatible with the address API (if we use it)
                "Calgary"
        ));

        regions.add(new Region(
                "https://cdn.getyourguide.com/img/location/5bd01093659eb.jpeg/88.jpg",
                // this name will need to be compatible with the address API (if we use it)
                "Quebec City"
        ));
        setChanged();
        notifyObservers();
        return regions;
    }


    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
