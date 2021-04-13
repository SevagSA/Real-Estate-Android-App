package com.example.realestateapplication.Controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPropertyActivity extends AppCompatActivity{

    Button propertyAddressEditText;
    Agent agent = new Agent();
    ArrayList<String> agents = agent.getAllAgentsNames();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_property);

        propertyAddressEditText = findViewById(R.id.propertyAddressSelectBtn);
        propertyAddressEditText.setFocusable(false);
        Places.initialize(getApplicationContext(), "AIzaSyBUUmmyiGdCIlDhGyEvI38S6fExzomHYlE");

        propertyAddressEditText.setOnClickListener(e -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(this);
            startActivityForResult(intent, 100);
        });


        // Agent spinner
        Spinner agentSpin = (Spinner) findViewById(R.id.listPropertySelectAgent);

        ArrayAdapter agentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, agents);
        agentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        agentSpin.setAdapter(agentAdapter);
        agentSpin.setOnItemSelectedListener(new AgentAdapterSpinnerClass());


        // Property type spinner
        Spinner propertyTypeSpin = (Spinner) findViewById(R.id.listPropertySelectPropertyType);

        ArrayAdapter propertyTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                Property.PROPERTY_TYPES);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTypeSpin.setAdapter(propertyTypeAdapter);
        propertyTypeSpin.setOnItemSelectedListener(new PropertyTypeAdapterSpinnerClass());
    }

    class PropertyTypeAdapterSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            {
                Toast.makeText(arg1.getContext(), Property.PROPERTY_TYPES[position], Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class AgentAdapterSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            {
                Toast.makeText(getApplicationContext(), agents.get(position), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

//
//    @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == 100 && resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                propertyAddressEditText.setText(place.getName());
//            }
//        }
//
//        //Performing action onItemSelected and onNothing selected
//        @Override
//        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//            Log.d("ID: ", id + " | ");
//
//            if (arg1.getId() == R.id.listPropertySelectAgent) {
//                Toast.makeText(getApplicationContext(), agents.get(position), Toast.LENGTH_LONG).show();
//            } else if (arg1.getId() == R.id.listPropertySelectPropertyType) {
//                Toast.makeText(getApplicationContext(), Property.PROPERTY_TYPES[position], Toast.LENGTH_LONG).show();
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg0) {
//            // TODO Auto-generated method stub
//        }
    }


