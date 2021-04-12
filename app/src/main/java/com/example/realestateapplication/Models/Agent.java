package com.example.realestateapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class Agent implements Parcelable {

    private String fullName;
    private String companyName;
    private String email;
    @DrawableRes
//    see https://stackoverflow.com/questions/62343576/is-it-possible-to-add-an-image-as-a-class-attribute
    private int profileImgId;
    private int numOfSoldListings;
    private String serviceLocation;
//    private ArrayList<Property> listedProperties;
    private String phoneNumber;

    public Agent(String fullName, String companyName,
                 @DrawableRes int profileImgId, int numOfSoldListings,
                 String serviceLocation, String email, String phoneNumber /**ArrayList<Property> listedProperties*/) {
        this.fullName = fullName;
        this.companyName = companyName;
        this.profileImgId = profileImgId;
        this.numOfSoldListings = numOfSoldListings;
        this.serviceLocation = serviceLocation;
        this.email = email;
        this.phoneNumber = phoneNumber;
//        this.listedProperties = listedProperties;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "fullName='" + fullName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", profileImgId=" + profileImgId +
                ", numOfSoldListings=" + numOfSoldListings +
                ", serviceLocation='" + serviceLocation + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getProfileImgId() {
        return profileImgId;
    }

    public void setProfileImgId(@DrawableRes int profileImgId) {
        this.profileImgId = profileImgId;
    }

    @DrawableRes
    public int getNumOfSoldListings() {
        return numOfSoldListings;
    }

    public void setNumOfSoldListings(int numOfSoldListings) {
        this.numOfSoldListings = numOfSoldListings;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

//    public ArrayList<Property> getListedProperties() {
//        return listedProperties;
//    }
//
//    public void setListedProperties(ArrayList<Property> listedProperties) {
//        this.listedProperties = listedProperties;
//    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(companyName);
        parcel.writeInt(profileImgId);
        parcel.writeInt(numOfSoldListings);
        parcel.writeString(serviceLocation);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
    }

    public static final Parcelable.Creator<Agent> CREATOR
            = new Parcelable.Creator<Agent>() {
        public Agent createFromParcel(Parcel in) {
            return new Agent(in);
        }

        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };

    /** reads back fields IN THE ORDER they were written */
    private Agent(Parcel pc) {
        fullName = pc.readString();
        companyName = pc.readString();
        profileImgId = pc.readInt();
        numOfSoldListings = pc.readInt();
        serviceLocation = pc.readString();
        email = pc.readString();
        phoneNumber = pc.readString();
    }
}

