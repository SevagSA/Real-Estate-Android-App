package com.example.realestateapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestateapplication.Adapters.PropertyCardRecyclerViewAdapter;
import com.example.realestateapplication.Adapters.RegionsRecyclerViewAdapter;
import com.example.realestateapplication.Controllers.SearchPropertyActivity;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.Region;
import com.example.realestateapplication.R;

import java.util.ArrayList;


public class LikedListingsDialogFragment extends DialogFragment {

    public LikedListingsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_liked_listings_dialog, container, false);
        populateRecyclerViewListings(root.findViewById(R.id.likedRecycler));
        return root;
    }

    /**
     * Render the liked listings by the current user
     */
    private void populateRecyclerViewListings(RecyclerView recyclerView) {
        Property property = new Property(getContext());
        ArrayList<Property> likedProperties = property.getAllLikedProperties();

        LinearLayoutManager likedPropertyLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        RecyclerView likedPropertyRecyclerView = recyclerView;
        likedPropertyRecyclerView.setLayoutManager(likedPropertyLayoutManager);
        PropertyCardRecyclerViewAdapter likedPropertyAdapter =
                new PropertyCardRecyclerViewAdapter(likedProperties, getContext());
        likedPropertyRecyclerView.setAdapter(likedPropertyAdapter);
    }

}