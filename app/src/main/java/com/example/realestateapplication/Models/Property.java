package com.example.realestateapplication.Models;


import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.realestateapplication.Models.DBHelpers.PropertyDBHelper;
import com.example.realestateapplication.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;

public class Property implements Parcelable {

    private Context context;
    private String propertyMainImg;
    private String propertySecondImg;
    private String propertyThirdImg;
    private String propertyFourthImg;
    private String propertyFifthImg;
    private String propertySixthImg;
    private int agentId;
    private String propertyType;
    private String propertyPrice;
    private String propertyAddress;
    private int propertyNumOfBed;
    private int propertyNumOfBath;
    private int propertySquareFoot;
    private String propertyId;

    private PropertyDBHelper db;


    public static final String[] PROPERTY_TYPES = {"House", "Apartment", "Land", "Duplex"};
    private int ownerId;

    public Property(Context context) { db = new PropertyDBHelper(context); }

    public Property(
            Context context, String propertyId, String propertyMainImg, String propertySecondImg,
                    String propertyThirdImg, String propertyFourthImg, String propertyFifthImg,
                    String propertySixthImg, String propertyType, String propertyPrice, String propertyAddress,
                    int propertyNumOfBed, int propertyNumOfBath, int propertySquareFoot, int agentId, int ownerId) {
        this.context = context;
        this.propertyMainImg = propertyMainImg;
        this.propertySecondImg = propertySecondImg;
        this.propertyThirdImg = propertyThirdImg;
        this.propertyFourthImg = propertyFourthImg;
        this.propertyFifthImg = propertyFifthImg;
        this.propertySixthImg = propertySixthImg;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
        this.propertyAddress = propertyAddress;
        this.propertyNumOfBed = propertyNumOfBed;
        this.propertyNumOfBath = propertyNumOfBath;
        this.propertySquareFoot = propertySquareFoot;
        this.agentId = agentId;
        this.ownerId = ownerId;
        this.propertyId = propertyId;
    }

    public Property() {
    }


    protected Property(Parcel in) {
        propertyMainImg = in.readString();
        propertySecondImg = in.readString();
        propertyThirdImg = in.readString();
        propertyFourthImg = in.readString();
        propertyFifthImg = in.readString();
        propertySixthImg = in.readString();
        agentId = in.readInt();
        propertyType = in.readString();
        propertyPrice = in.readString();
        propertyAddress = in.readString();
        propertyNumOfBed = in.readInt();
        propertyNumOfBath = in.readInt();
        propertySquareFoot = in.readInt();
        propertyId = in.readString();
        ownerId = in.readInt();
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    /**
     * To store a property object in the database.
     */
    public Long insertProperty() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        return db.addData(
            this.propertyMainImg,
            this.propertySecondImg,
            this.propertyThirdImg,
            this.propertyFourthImg,
            this.propertyFifthImg,
            this.propertySixthImg,
            this.agentId,
            this.propertyType,
            this.propertyPrice,
            this.propertyAddress,
            this.propertyNumOfBed,
            this.propertyNumOfBath,
            this.propertySquareFoot,
            date,
                this.ownerId);
    }

    /**
     * To get all properties from the DB with all of their attributes ordered by their `date_added`
     * descending.
     */
    public ArrayList<Property> getRecentProperties() {
        ArrayList<Property> properties = new ArrayList<>();
        try (Cursor result = db.runQuery("SELECT * FROM " + PropertyDBHelper.TABLE_NAME +
                " ORDER BY date_added DESC")) {
            while (result.moveToNext()) {
                properties.add(extractPropertyFromCursor(result));
            }
        }
        return properties;
    }

    /**
     * To get all of the properties of the current agent from the DB with all of their attributes.
     *
     * @return An ArrayList<Property> of all of the properties of the current agent.
     */
    public ArrayList<Property> getAllPropertiesOfAgent(String agentId) {
        ArrayList<Property> properties = new ArrayList<>();
        try (Cursor result = db.runQuery("SELECT * FROM " + PropertyDBHelper.TABLE_NAME +
                " WHERE agent_id = ?", new String[]{agentId})) {
            while (result.moveToNext()) {
                properties.add(extractPropertyFromCursor(result));
            }
        }
        return properties;
    }

    /**
     * To get all of the properties that match the given query from the DB with all of their attributes.
     *
     * @param query The String search query provide by the user. This query will be the property's location.
     *              All properties matching this location will be returned.
     * @return An ArrayList<Property> of the all of the liked properties of the user.
     */
    public ArrayList<Property> getPropertiesBySearchQuery(String query) {
        ArrayList<Property> properties = new ArrayList<>();
        try (Cursor result = db.runQuery("SELECT * FROM " + PropertyDBHelper.TABLE_NAME +
                " WHERE property_address LIKE ?", new String[]{"%"+query+"%"})) {
            while (result.moveToNext()) {
                properties.add(extractPropertyFromCursor(result));
            }
        }
        return properties;
    }

    private Property extractPropertyFromCursor(Cursor result) {
        return new Property(
                context,
                result.getString(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
//                       type
                result.getString(8),
//                        price
                result.getString(9),
//                        address
                result.getString(10),
//                        bed
                result.getInt(11),
//                        bath
                result.getInt(12),
//                        sqft
                result.getInt(13),
//                        agentid
                result.getInt(7),
                result.getInt(15)
        );
    }

    public Integer delete() {
        return db.deleteValue(getPropertyId());
    }

    /**
     * Get all of the images related to this property in a String[].
     *
     * @return A String[] containing the URLs of all of the images of this property.
     */
    public String[] getGalleryImagesURLs() {
        return new String[]{getPropertyMainImg(), getPropertySecondImg(),
                getPropertyThirdImg(), getPropertyFourthImg(), getPropertyFifthImg(),
                getPropertySixthImg()};
    }

    public String getPropertyPrice() {
        String dollarSymbol = propertyType.equalsIgnoreCase("Apartment") ?
                context.getString(R.string.dollar_per_month) : "$";
        return propertyPrice + dollarSymbol;
    }

    public String getPropertyNumOfBed() {
        return propertyNumOfBed + " " + context.getResources().getString(R.string.bed);
    }

    public String getPropertyNumOfBath() {
        return propertyNumOfBath + " " + context.getResources().getString(R.string.bath);
    }

    public String getPropertySquareFoot() {
        return propertySquareFoot + " " + context.getResources().getString(R.string.sqft);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPropertyMainImg() {
        return propertyMainImg;
    }

    public void setPropertyMainImg(String propertyMainImg) {
        this.propertyMainImg = propertyMainImg;
    }

    public String getPropertySecondImg() {
        return propertySecondImg;
    }

    public void setPropertySecondImg(String propertySecondImg) {
        this.propertySecondImg = propertySecondImg;
    }

    public String getPropertyThirdImg() {
        return propertyThirdImg;
    }

    public void setPropertyThirdImg(String propertyThirdImg) {
        this.propertyThirdImg = propertyThirdImg;
    }

    public String getPropertyFourthImg() {
        return propertyFourthImg;
    }

    public void setPropertyFourthImg(String propertyFourthImg) {
        this.propertyFourthImg = propertyFourthImg;
    }

    public String getPropertyFifthImg() {
        return propertyFifthImg;
    }

    public void setPropertyFifthImg(String propertyFifthImg) {
        this.propertyFifthImg = propertyFifthImg;
    }

    public String getPropertySixthImg() {
        return propertySixthImg;
    }

    public void setPropertySixthImg(String propertySixthImg) {
        this.propertySixthImg = propertySixthImg;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public void setPropertyNumOfBed(int propertyNumOfBed) {
        this.propertyNumOfBed = propertyNumOfBed;
    }

    public void setPropertyNumOfBath(int propertyNumOfBath) {
        this.propertyNumOfBath = propertyNumOfBath;
    }

    public void setPropertySquareFoot(int propertySquareFoot) {
        this.propertySquareFoot = propertySquareFoot;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }


    @Override
    public String toString() {
        return "Property{" +
                ", propertyMainImg='" + propertyMainImg + '\'' +
                ", propertySecondImg='" + propertySecondImg + '\'' +
                ", propertyThirdImg='" + propertyThirdImg + '\'' +
                ", propertyFourthImg='" + propertyFourthImg + '\'' +
                ", propertyFifthImg='" + propertyFifthImg + '\'' +
                ", propertySixthImg='" + propertySixthImg + '\'' +
                ", agentId=" + agentId +
                ", propertyType='" + propertyType + '\'' +
                ", propertyPrice='" + propertyPrice + '\'' +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", propertyNumOfBed=" + propertyNumOfBed +
                ", propertyNumOfBath=" + propertyNumOfBath +
                ", propertySquareFoot=" + propertySquareFoot +
                ", propertyId='" + propertyId + '\'' +
                ", db=" + db +
                ", ownerId=" + ownerId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propertyMainImg);
        dest.writeString(propertySecondImg);
        dest.writeString(propertyThirdImg);
        dest.writeString(propertyFourthImg);
        dest.writeString(propertyFifthImg);
        dest.writeString(propertySixthImg);
        dest.writeInt(agentId);
        dest.writeString(propertyType);
        dest.writeString(propertyPrice);
        dest.writeString(propertyAddress);
        dest.writeInt(propertyNumOfBed);
        dest.writeInt(propertyNumOfBath);
        dest.writeInt(propertySquareFoot);
        dest.writeString(propertyId);
        dest.writeInt(ownerId);
    }
}
