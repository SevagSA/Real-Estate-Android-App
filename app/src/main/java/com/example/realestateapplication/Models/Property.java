package com.example.realestateapplication.Models;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.realestateapplication.R;

import java.util.ArrayList;
import java.util.Observable;

public class Property extends Observable implements Parcelable {

    private Context context;
    private String propertyMainImgURL;
    private String propertySecondImgURL;
    private String propertyThirdImgURL;
    private String propertyFourthImgURL;
    private String propertyFifthImgURL;
    private String propertySixthImgURL;
//    TODO: Create this class when you work on the Agent page
//     The agents will be pre filled and users can't add agents
//     so have the profile picture of the agent as a drawable
//     (take screenshots of the imgs so all have the same dimens)
    private Agent agent;
    private String propertyType;
    private double propertyPrice;
    private String propertyAddress;
    private int propertyNumOfBed;
    private int propertyNumOfBath;
    private int propertySquareFoot;

    public Property(Context context) { this.context = context; }

    public Property(Context context, String propertyMainImgURL, String propertySecondImgURL,
                    String propertyThirdImgURL, String propertyFourthImgURL, String propertyFifthImgURL,
                    String propertySixthImgURL, String propertyType, double propertyPrice, String propertyAddress,
                    int propertyNumOfBed, int propertyNumOfBath, int propertySquareFoot, Agent agent) {
        this.context = context;
        this.propertyMainImgURL = propertyMainImgURL;
        this.propertySecondImgURL = propertySecondImgURL;
        this.propertyThirdImgURL = propertyThirdImgURL;
        this.propertyFourthImgURL = propertyFourthImgURL;
        this.propertyFifthImgURL = propertyFifthImgURL;
        this.propertySixthImgURL = propertySixthImgURL;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
        this.propertyAddress = propertyAddress;
        this.propertyNumOfBed = propertyNumOfBed;
        this.propertyNumOfBath = propertyNumOfBath;
        this.propertySquareFoot = propertySquareFoot;
        this.agent = agent;
    }

    /**
     * To get the 5 most recent properties from the DB with all of their attributes.
     * @return An ArrayList<Property> of the 5 most recent properties.
     */
    public ArrayList<Property> getAllProperties() {
        // TODO: this will be later queried from the DB, not hardcoded.

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(new Property(
            context,
            "https://m.foolcdn.com/media/millionacres/original_images/colonial_house.jpg",
            "https://i.pinimg.com/originals/e6/81/8f/e6818f770c45e5020f955a0c90a2a0a3.jpg",
            "https://i.pinimg.com/originals/5a/4a/c1/5a4ac16eb9fd004fe98468e2b50a7569.jpg",
            "https://livinator.com/wp-content/uploads/2018/11/home-minimalism.jpg",
            "https://lh3.googleusercontent.com/proxy/sH3PscMIUWOt3BPZpMcqdSQYTsc7g0_OO1JDQeaiS26W749IfBWI5vn3x3fWY133i85b6m_Y-8R4WQYP4IffxLmikXcbhP9F1zCc_f6FZa-7WVliRbh1e0wpi_Tn0-CO",
            "https://i.pinimg.com/originals/08/9c/65/089c659949c103c7dced21dd057f5f35.jpg",
            "House",
            567_000,
            "7365, McDonald's Street, P0V K9G",
            2,
            3,
            2500,
                new Agent(
                        "Raphael Jones",
                        "High Rise Estates",
                        R.drawable.raphael_jones,
                        78,
                        "Toronto, On, and Montreal, Qc",
                        "raphael.jones@agent.com",
                        "(435) 547-868")
        ));

        properties.add(new Property(
            context,
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
            "Apartment",
            1_300,
            "3646, 42nd Road, V0R H95",
            2,
            1,
            1100,
                new Agent(
                        "Alex Mason",
                        "Alberta Property Agents",
                        R.drawable.alex_mason,
                        78,
                        "Calgary, Ab",
                        "alex.mason@agent.com",
                        "(356) 346-8288")

        ));

        properties.add(new Property(
            context,
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
            "Apartment",
            1_550,
            "288, Rue de la Vallée",
            3,
            3,
            1300,
                new Agent(
                        "Olivia James",
                        "Redwood Realty Group",
                        R.drawable.olivia_james,
                        78,
                        "Vancouver, Bc",
                        "olivia.james@agent.com",
                        "(948) 368-8276")
        ));
        setChanged();
        notifyObservers();
        return properties;
    }

    /**
     * To get all of the liked properties of the current user from the DB with all of their attributes.
     * @return An ArrayList<Property> of the all of the liked properties of the user.
     */
    public ArrayList<Property> getAllLikedProperties() {
        // TODO: this will be later queried from the DB, not hardcoded.

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(new Property(
                context,
                "https://m.foolcdn.com/media/millionacres/original_images/colonial_house.jpg",
                "https://i.pinimg.com/originals/e6/81/8f/e6818f770c45e5020f955a0c90a2a0a3.jpg",
                "https://i.pinimg.com/originals/5a/4a/c1/5a4ac16eb9fd004fe98468e2b50a7569.jpg",
                "https://livinator.com/wp-content/uploads/2018/11/home-minimalism.jpg",
                "https://lh3.googleusercontent.com/proxy/sH3PscMIUWOt3BPZpMcqdSQYTsc7g0_OO1JDQeaiS26W749IfBWI5vn3x3fWY133i85b6m_Y-8R4WQYP4IffxLmikXcbhP9F1zCc_f6FZa-7WVliRbh1e0wpi_Tn0-CO",
                "https://i.pinimg.com/originals/08/9c/65/089c659949c103c7dced21dd057f5f35.jpg",
                "House",
                567_000,
                "7365, McDonald's Street, P0V K9G",
                2,
                3,
                2500,
                new Agent(
                        "Raphael Jones",
                        "High Rise Estates",
                        R.drawable.raphael_jones,
                        78,
                        "Toronto, On, and Montreal, Qc",
                        "raphael.jones@agent.com",
                        "(435) 547-868")
        ));

        properties.add(new Property(
                context,
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "https://images.rentals.ca/property-pictures/medium/oshawa-on/313877/apartment-1954812.jpg",
                "Apartment",
                1_300,
                "3646, 42nd Road, V0R H95",
                2,
                1,
                1100,
                new Agent(
                        "Alex Mason",
                        "Alberta Property Agents",
                        R.drawable.alex_mason,
                        78,
                        "Calgary, Ab",
                        "alex.mason@agent.com",
                        "(356) 346-8288")

        ));

        properties.add(new Property(
                context,
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "https://www.orizoncondo.com/wp-content/uploads/2017/10/perspective_orizon.jpg",
                "Apartment",
                1_550,
                "288, Rue de la Vallée",
                3,
                3,
                1300,
                new Agent(
                        "Olivia James",
                        "Redwood Realty Group",
                        R.drawable.olivia_james,
                        78,
                        "Vancouver, Bc",
                        "olivia.james@agent.com",
                        "(948) 368-8276")
        ));
        setChanged();
        notifyObservers();
        return properties;
    }

    /**
     * Get all of the images related to this property in a String[].
     * @return A String[] containing the URLs of all of the images of this property.
     */
    public String[] getGalleryImagesURLs() {
        return new String[] {getPropertyMainImgURL(), getPropertySecondImgURL(),
                getPropertyThirdImgURL(), getPropertyFourthImgURL(), getPropertyFifthImgURL(),
                getPropertySixthImgURL()};
    }

    @Override
    public String toString() {
        return "Property{" +
                "context=" + context +
                ", propertyMainImgURL='" + propertyMainImgURL + '\'' +
                ", propertySecondImgURL='" + propertySecondImgURL + '\'' +
                ", propertyThirdImgURL='" + propertyThirdImgURL + '\'' +
                ", propertyFourthImgURL='" + propertyFourthImgURL + '\'' +
                ", propertyFifthImgURL='" + propertyFifthImgURL + '\'' +
                ", propertySixthImgURL='" + propertySixthImgURL + '\'' +
                ", agent=" + agent +
                ", propertyType='" + propertyType + '\'' +
                ", propertyPrice=" + propertyPrice +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", propertyNumOfBed=" + propertyNumOfBed +
                ", propertyNumOfBath=" + propertyNumOfBath +
                ", propertySquareFoot=" + propertySquareFoot +
                '}';
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPropertyMainImgURL() {
        return propertyMainImgURL;
    }

    public void setPropertyMainImgURL(String propertyMainImgURL) {
        this.propertyMainImgURL = propertyMainImgURL;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyPrice() {
        String dollarSymbol = propertyType.equalsIgnoreCase("Apartment") ? "$ / Month" : "$";
        return propertyPrice + dollarSymbol;
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

    public String getPropertyNumOfBed() {
        return propertyNumOfBed + " " + context.getResources().getString(R.string.bed);
    }

    public void setPropertyNumOfBed(int propertyNumOfBed) {
        this.propertyNumOfBed = propertyNumOfBed;
    }

    public String getPropertyNumOfBath() {
        return propertyNumOfBath + " " + context.getResources().getString(R.string.bath);
    }

    public void setPropertyNumOfBath(int propertyNumOfBath) {
        this.propertyNumOfBath = propertyNumOfBath;
    }

    public String getPropertySquareFoot() {
        return propertySquareFoot + " " + context.getResources().getString(R.string.sqft);
    }

    public void setPropertySquareFoot(int propertySquareFoot) {
        this.propertySquareFoot = propertySquareFoot;
    }

    public String getPropertySecondImgURL() {
        return propertySecondImgURL;
    }

    public void setPropertySecondImgURL(String propertySecondImgURL) {
        this.propertySecondImgURL = propertySecondImgURL;
    }

    public String getPropertyThirdImgURL() {
        return propertyThirdImgURL;
    }

    public void setPropertyThirdImgURL(String propertyThirdImgURL) {
        this.propertyThirdImgURL = propertyThirdImgURL;
    }

    public String getPropertyFourthImgURL() {
        return propertyFourthImgURL;
    }

    public void setPropertyFourthImgURL(String propertyFourthImgURL) {
        this.propertyFourthImgURL = propertyFourthImgURL;
    }

    public String getPropertyFifthImgURL() {
        return propertyFifthImgURL;
    }

    public void setPropertyFifthImgURL(String propertyFifthImgURL) {
        this.propertyFifthImgURL = propertyFifthImgURL;
    }

    public String getPropertySixthImgURL() {
        return propertySixthImgURL;
    }

    public void setPropertySixthImgURL(String propertySixthImgURL) {
        this.propertySixthImgURL = propertySixthImgURL;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(propertyMainImgURL);
        parcel.writeString(propertySecondImgURL);
        parcel.writeString(propertyThirdImgURL);
        parcel.writeString(propertyFourthImgURL);
        parcel.writeString(propertyFifthImgURL);
        parcel.writeString(propertySixthImgURL);
        parcel.writeString(propertyType);
        parcel.writeDouble(propertyPrice);
        parcel.writeString(propertyAddress);
        parcel.writeInt(propertyNumOfBed);
        parcel.writeInt(propertyNumOfBath);
        parcel.writeInt(propertySquareFoot);
        parcel.writeValue(agent);
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
        propertyMainImgURL = pc.readString();
        propertySecondImgURL = pc.readString();
        propertyThirdImgURL = pc.readString();
        propertyFourthImgURL = pc.readString();
        propertyFifthImgURL = pc.readString();
        propertySixthImgURL = pc.readString();
        propertyType = pc.readString();
        propertyPrice = pc.readDouble();
        propertyAddress = pc.readString();
        propertyNumOfBed = pc.readInt();
        propertyNumOfBath = pc.readInt();
        propertySquareFoot = pc.readInt();
        agent = (Agent) pc.readValue(Agent.class.getClassLoader());
    }
}
