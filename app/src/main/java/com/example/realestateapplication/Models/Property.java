package com.example.realestateapplication.Models;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Property extends Observable implements Parcelable {

    private Context context;
    private String propertyMainImg;
    private String propertySecondImg;
    private String propertyThirdImg;
    private String propertyFourthImg;
    private String propertyFifthImg;
    private String propertySixthImg;
    //    TODO: Create this class when you work on the Agent page
//     The agents will be pre filled and users can't add agents
//     so have the profile picture of the agent as a drawable
//     (take screenshots of the imgs so all have the same dimens)
    private Agent agent;
    private String propertyType;
    private double propertyPrice;
    //    TODO: this will be the address provide by Google Places API.
//     it will be used by getListingsBySearchQuery(). It is still not implemented
//     Decide to use either getAddress(), or getName(). getName() seems more concise.
    private String propertyAddress;
    private int propertyNumOfBed;
    private int propertyNumOfBath;
    private int propertySquareFoot;
    private HomeActivity homeActivity;

    private ArrayList<Property> allProperties;

    public static final String[] PROPERTY_TYPES = {"House", "Apartment", "Land", "Duplex"};

    public Property(Context context) {
        this.context = context;
    }

    public Property(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

//    /**
//     * Constructor with all parameters excluding `context`. Used for storing properties in the database.
//     *
//     * @param propertyMainImg
//     * @param propertySecondImg
//     * @param propertyThirdImg
//     * @param propertyFourthImg
//     * @param propertyFifthImg
//     * @param propertySixthImg
//     * @param agent
//     * @param propertyType
//     * @param propertyPrice
//     * @param propertyAddress
//     * @param propertyNumOfBed
//     * @param propertyNumOfBath
//     * @param propertySquareFoot
//     */
//    public Property(String propertyMainImg, String propertySecondImg, String propertyThirdImg, String propertyFourthImg, String propertyFifthImg, String propertySixthImg, Agent agent, String propertyType, double propertyPrice, String propertyAddress, int propertyNumOfBed, int propertyNumOfBath, int propertySquareFoot) {
//        this.propertyMainImg = propertyMainImg;
//        this.propertySecondImg = propertySecondImg;
//        this.propertyThirdImg = propertyThirdImg;
//        this.propertyFourthImg = propertyFourthImg;
//        this.propertyFifthImg = propertyFifthImg;
//        this.propertySixthImg = propertySixthImg;
//        this.agent = agent;
//        this.propertyType = propertyType;
//        this.propertyPrice = propertyPrice;
//        this.propertyAddress = propertyAddress;
//        this.propertyNumOfBed = propertyNumOfBed;
//        this.propertyNumOfBath = propertyNumOfBath;
//        this.propertySquareFoot = propertySquareFoot;
//    }

    public Property(Context context, String propertyMainImg, String propertySecondImg,
                    String propertyThirdImg, String propertyFourthImg, String propertyFifthImg,
                    String propertySixthImg, String propertyType, double propertyPrice, String propertyAddress,
                    int propertyNumOfBed, int propertyNumOfBath, int propertySquareFoot, Agent agent) {
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
        this.agent = agent;
    }

    public Property() {
    }

    /**
     * To store a property object in the database.
     */
    public void insertProperty() {
        Map<String, Object> property = new HashMap<>();
//        TODO change to the real input address, not Calgary
        property.put("propertyAddress", "Calgary, AB, Canada");
        property.put("propertyPrice", this.propertyPrice);
        property.put("propertyNumOfBed", this.propertyNumOfBed);
        property.put("propertyNumOfBath", this.propertyNumOfBath);
        property.put("propertySquareFoot", this.propertySquareFoot);

        property.put("propertyMainImg", this.getPropertyMainImg());
        property.put("propertySecondImg", this.getPropertySecondImg());
        property.put("propertyThirdImg", this.getPropertyThirdImg());
        property.put("propertyFourthImg", this.getPropertyFourthImg());
        property.put("propertyFifthImg", this.getPropertyFifthImg());
        property.put("propertySixthImg", this.getPropertySixthImg());

        property.put("agent", this.agent.getFullName());
        property.put("propertyType", this.propertyType);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Log.d("date: ", dtf.format(now));

        property.put("dateAdded", dtf.format(now));

        FirebaseDatabase.getInstance().getReference().child("Property")
                .push().setValue(property)
                .addOnSuccessListener(e -> {
                    Toast.makeText(context, "Property was successfully listed", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error: Property was not successfully listed", Toast.LENGTH_LONG).show();
                });
    }

    /**
     * To set the 5 most recent properties from the DB with all of their attributes into the
     * HomeActivity's "Recent Properties"'s sections's recyclerView.
     */
    public void setAllPropertiesFromDB(boolean populateRecentProperties) {
        ArrayList<Property> properties = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Property")
                .orderByChild("dateAdded")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            HashMap<Object, Object> propHash = (HashMap<Object, Object>) snap.getValue();
                            Property property = new Property();

                            property.setPropertyType(propHash.get("propertyType").toString());

                            property.setPropertyPrice(Double.parseDouble(propHash.get("propertyPrice").toString()));
                            property.setPropertyAddress(propHash.get("propertyAddress").toString());
                            property.setPropertyNumOfBed(Integer.parseInt(propHash.get("propertyNumOfBed").toString()));
                            property.setPropertyNumOfBath(Integer.parseInt(propHash.get("propertyNumOfBath").toString()));
                            property.setPropertySquareFoot(Integer.parseInt(propHash.get("propertySquareFoot").toString()));

                            property.setPropertyMainImg(propHash.get("propertyMainImg").toString());
                            property.setPropertySecondImg(propHash.get("propertySecondImg").toString());
                            property.setPropertyThirdImg(propHash.get("propertyThirdImg").toString());
                            property.setPropertyFourthImg(propHash.get("propertyFourthImg").toString());
                            property.setPropertyFifthImg(propHash.get("propertyFifthImg").toString());
                            property.setPropertySixthImg(propHash.get("propertySixthImg").toString());

                            property.setAgent(Agent.getAgentByName(propHash.get("agent").toString()));
                            properties.add(property);
                        }
                        setAllProperties(properties);
                        if (populateRecentProperties) {
                            homeActivity.populateRecyclerViewListings(LinearLayoutManager.HORIZONTAL, properties);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    /**
     * To get all of the liked properties of the current user from the DB with all of their attributes.
     *
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
                        "jones@agent.com",
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
                        "mason@agent.com",
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
                        "james@agent.com",
                        "(948) 368-8276")
        ));
        setChanged();
        notifyObservers();
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
                        "jones@agent.com",
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
                        "mason@agent.com",
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
                        "james@agent.com",
                        "(948) 368-8276")
        ));
        setChanged();
        notifyObservers();
        return properties;
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

    @Override
    public String toString() {
        return "Property{" +
                "context=" + context +
                ", propertyMainImg='" + propertyMainImg + '\'' +
                ", propertySecondImg='" + propertySecondImg + '\'' +
                ", propertyThirdImg='" + propertyThirdImg + '\'' +
                ", propertyFourthImg='" + propertyFourthImg + '\'' +
                ", propertyFifthImg='" + propertyFifthImg + '\'' +
                ", propertySixthImg='" + propertySixthImg + '\'' +
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

    public String getPropertyMainImg() {
        return propertyMainImg;
    }

    public void setPropertyMainImg(String propertyMainImg) {
        this.propertyMainImg = propertyMainImg;
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
        Log.d("getPropertyNumOfBed", propertyNumOfBed + "");
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public ArrayList<Property> getAllProperties() {
        return allProperties;
    }

    public void setAllProperties(ArrayList<Property> allProperties) {
        this.allProperties = allProperties;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(propertyMainImg);
        parcel.writeString(propertySecondImg);
        parcel.writeString(propertyThirdImg);
        parcel.writeString(propertyFourthImg);
        parcel.writeString(propertyFifthImg);
        parcel.writeString(propertySixthImg);
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

    /**
     * reads back fields IN THE ORDER they were written
     */
    private Property(Parcel pc) {
        propertyMainImg = pc.readString();
        propertySecondImg = pc.readString();
        propertyThirdImg = pc.readString();
        propertyFourthImg = pc.readString();
        propertyFifthImg = pc.readString();
        propertySixthImg = pc.readString();
        propertyType = pc.readString();
        propertyPrice = pc.readDouble();
        propertyAddress = pc.readString();
        propertyNumOfBed = pc.readInt();
        propertyNumOfBath = pc.readInt();
        propertySquareFoot = pc.readInt();
        agent = (Agent) pc.readValue(Agent.class.getClassLoader());
    }
}
