package com.example.realestateapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.realestateapplication.Controllers.PropertyDetailActivity;
import com.example.realestateapplication.Controllers.SearchPropertyActivity;
import com.example.realestateapplication.R;

public class SearchBarFragment extends Fragment {


    public SearchBarFragment() {
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SearchBarFragment.
//     */
//    public static SearchBarFragment newInstance(String param1, String param2) {
//        SearchBarFragment fragment = new SearchBarFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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