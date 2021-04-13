package com.example.realestateapplication.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import com.example.realestateapplication.R;

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
    private Context context;


    public Agent(Context context) { this.context = context; }

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
                ", context=" + context +
                '}';
    }

    /**
     * To get all of the properties of the current agent from the DB with all of their attributes.
     * @return An ArrayList<Property> of all of the properties of the current agent.
     */
    public ArrayList<Property> getAllPropertiesOfAgent() {
        // TODO: this will be later queried from the DB, not hardcoded.
        //  and it will be agent specific

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
        return properties;
    }

    /**
     * To get all of the names of the agents from the DB.
     * @return An ArrayList<Agent> of all of the agents' names.
     */
    public static ArrayList<String> getAllAgentsNames() {
        ArrayList<String> agents = new ArrayList<>();
        agents.add(new Agent(
                "Raphael Jones",
                "High Rise Estates",
                R.drawable.raphael_jones,
                78,
                "Toronto, On, and Montreal, Qc",
                "jones@agent.com",
                "(435) 547-868").getFullName());
        agents.add(new Agent(
                "Alex Mason",
                "Alberta Property Agents",
                R.drawable.alex_mason,
                78,
                "Calgary, Ab",
                "mason@agent.com",
                "(356) 346-8288").getFullName());
        agents.add(new Agent(
                "Olivia James",
                "Redwood Realty Group",
                R.drawable.olivia_james,
                78,
                "Vancouver, Bc",
                "james@agent.com",
                "(948) 368-8276").getFullName());
        return agents;
    }

    /**
     * To get all of the agents from the DB with all of their properies.
     * @return An ArrayList<Agent> of all of the agents.
     */
    public static ArrayList<Agent> getAllAgents() {
        ArrayList<Agent> agents = new ArrayList<>();
        agents.add(new Agent(
                "Raphael Jones",
                "High Rise Estates",
                R.drawable.raphael_jones,
                78,
                "Toronto, On, and Montreal, Qc",
                "jones@agent.com",
                "(435) 547-868"));
        agents.add(new Agent(
                "Alex Mason",
                "Alberta Property Agents",
                R.drawable.alex_mason,
                78,
                "Calgary, Ab",
                "mason@agent.com",
                "(356) 346-8288"));
        agents.add(new Agent(
                "Olivia James",
                "Redwood Realty Group",
                R.drawable.olivia_james,
                78,
                "Vancouver, Bc",
                "james@agent.com",
                "(948) 368-8276"));
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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

