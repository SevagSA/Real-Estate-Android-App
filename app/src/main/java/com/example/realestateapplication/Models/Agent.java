package com.example.realestateapplication.Models;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.DrawableRes;

import com.example.realestateapplication.Models.DBHelpers.AgentDBHelper;
import com.example.realestateapplication.Models.DBHelpers.PropertyDBHelper;
import com.example.realestateapplication.Models.DBHelpers.UserDBHelper;
import com.example.realestateapplication.R;

import java.util.ArrayList;

public class Agent implements Parcelable {

    private String fullName;
    private String companyName;
    private String email;
    @DrawableRes
    private int profileImgId;
    private int numOfSoldListings;
    private String serviceLocation;
    private String phoneNumber;
    //    TODO remove the context attribute; it was used for dummy data before DB config.
    private Context context;

    private String agentId;

    private AgentDBHelper db;

    public Agent(Context context) {
        db = new AgentDBHelper(context);
        this.context = context;
    }

    public Agent(String agentId, String fullName, String companyName,
                 @DrawableRes int profileImgId, int numOfSoldListings,
                 String serviceLocation, String email, String phoneNumber) {
        this.agentId = agentId;
        this.fullName = fullName;
        this.companyName = companyName;
        this.profileImgId = profileImgId;
        this.numOfSoldListings = numOfSoldListings;
        this.serviceLocation = serviceLocation;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected Agent(Parcel in) {
        fullName = in.readString();
        companyName = in.readString();
        email = in.readString();
        profileImgId = in.readInt();
        numOfSoldListings = in.readInt();
        serviceLocation = in.readString();
        phoneNumber = in.readString();
        agentId = in.readString();
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        @Override
        public Agent createFromParcel(Parcel in) {
            return new Agent(in);
        }

        @Override
        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };

    public Agent getAgentById(int agentId) {
        Agent agent = null;
        Cursor cursor = db.runQuery("SELECT * FROM " + AgentDBHelper.TABLE_NAME + " " +
                "WHERE " + AgentDBHelper.COL_ID + " = ? ", new String[]{Integer.toString(agentId)});
        if (cursor.moveToFirst()) {
            agent = new Agent(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(3),
                    cursor.getString(7));
        }
        return agent;
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
                ", agentId='" + agentId + '\'' +
                '}';
    }

    /**
     * To get all of the names of the agents from the DB.
     *
     * @return An ArrayList<Agent> of all of the agents' names.
     */
    public ArrayList<String> getAllAgentsNames() {
        ArrayList<String> agentNames = new ArrayList<>();
        try (Cursor cursor = db.runQuery("SELECT full_name FROM Agent")) {
            while (cursor.moveToNext()) {
                agentNames.add(cursor.getString(0));
            }
        }
        return agentNames;
    }

    public int getIdOfAgentByName() {
        int id = -1;
        try (Cursor cursor = db.runQuery("SELECT id FROM Agent WHERE full_name = ?",
                new String[]{this.getFullName()})) {
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
            }
        }
        return id;
    }

    /**
     * To get all of the agents from the DB with all of their properies.
     *
     * @return An ArrayList<Agent> of all of the agents.
     */
    public ArrayList<Agent> getAllAgents() {
        ArrayList<Agent> agents = new ArrayList<>();
        try (Cursor cursor = db.runQuery("SELECT * FROM " + AgentDBHelper.TABLE_NAME)) {
            while (cursor.moveToNext()) {
                Agent agent = new Agent(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(3),
                        cursor.getString(7));
                agents.add(agent);
            }
        }
        return agents;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(companyName);
        dest.writeString(email);
        dest.writeInt(profileImgId);
        dest.writeInt(numOfSoldListings);
        dest.writeString(serviceLocation);
        dest.writeString(phoneNumber);
        dest.writeString(agentId);
    }
}

