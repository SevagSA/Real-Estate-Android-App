package com.example.realestateapplication.Models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Observable;

public class Property extends Observable implements Parcelable {

    private String propertyImgURL;
    private String propertyType;
    private double propertyPrice;
    private String propertyAddress;
    private int propertyNumOfBed;
    private int propertyNumOfBath;
    private int propertySquareFoot;

    public Property() { }

    public Property(String propertyImgURL, String propertyType, double propertyPrice, String propertyAddress, int propertyNumOfBed, int propertyNumOfBath, int propertySquareFoot) {
        this.propertyImgURL = propertyImgURL;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
        this.propertyAddress = propertyAddress;
        this.propertyNumOfBed = propertyNumOfBed;
        this.propertyNumOfBath = propertyNumOfBath;
        this.propertySquareFoot = propertySquareFoot;
    }

    /**
     * To get the 5 most recent properties from the DB with all of their attributes.
     * @return An ArrayList<Property> of the 5 most recent properties.
     */
    public ArrayList<Property> getAllProperties() {
        // TODO: this will be later queries from the DB, not hardcoded.

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(new Property(
            "https://image.cnbcfm.com/api/v1/image/104548349-Large_house_suburb.jpg?v=1532563813&w=1600&h=900",
            "House",
            567_000,
            "7365, McDonald's Street, P0V K9G",
            2,
            3,
            2500
        ));

        properties.add(new Property(
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "Apartment",
            1_300,
            "3646, 42nd Road, V0R H95",
            2,
            1,
            1100

        ));

        properties.add(new Property(
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "Apartment",
            1_550,
            "288, Rue de la Vallée",
            3,
            3,
            1300
        ));
        setChanged();
        notifyObservers();
        return properties;
    }


    public String getPropertyImgURL() {
        return propertyImgURL;
    }

    public void setPropertyImgURL(String propertyImgURL) {
        this.propertyImgURL = propertyImgURL;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public int getPropertyNumOfBed() {
        return propertyNumOfBed;
    }

    public void setPropertyNumOfBed(int propertyNumOfBed) {
        this.propertyNumOfBed = propertyNumOfBed;
    }

    public int getPropertyNumOfBath() {
        return propertyNumOfBath;
    }

    public void setPropertyNumOfBath(int propertyNumOfBath) {
        this.propertyNumOfBath = propertyNumOfBath;
    }

    public int getPropertySquareFoot() {
        return propertySquareFoot;
    }

    public void setPropertySquareFoot(int propertySquareFoot) {
        this.propertySquareFoot = propertySquareFoot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(propertyImgURL);
        parcel.writeString(propertyType);
        parcel.writeDouble(propertyPrice);
        parcel.writeString(propertyAddress);
        parcel.writeInt(propertyNumOfBed);
        parcel.writeInt(propertyNumOfBath);
        parcel.writeInt(propertySquareFoot);
    }

    public static final Parcelable.Creator<Property> CREATOR
            = new Parcelable.Creator<Property>() {
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    /** reads back fields IN THE ORDER they were written */
    private Property(Parcel pc) {
        propertyImgURL = pc.readString();
        propertyType = pc.readString();
        propertyPrice = pc.readDouble();
        propertyAddress = pc.readString();
        propertyNumOfBed = pc.readInt();
        propertyNumOfBath = pc.readInt();
        propertySquareFoot = pc.readInt();
    }

}
