package com.example.realestateapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.realestateapplication.Controllers.SearchPropertyActivity;
import com.example.realestateapplication.R;

public class SearchBarFragment extends Fragment {


    public SearchBarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_search_bar, container, false);
        root.findViewById(R.id.searchInputBarSearchView).setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), SearchPropertyActivity.class);
            getContext().startActivity(intent);
        });
        return root;
    }
}