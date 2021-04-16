package com.example.realestateapplication.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapplication.Adapters.SelectedPropertyImageAdapter;
import com.example.realestateapplication.Models.Agent;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPropertyActivity extends AppCompatActivity {

    Button propertyAddressSelectBtn;

    ArrayList<String> agents = Agent.getAllAgentsNames();
    GridView imageGridView;
    Button selectImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_property);


        propertyAddressSelectBtn = findViewById(R.id.propertyAddressSelectBtn);
        propertyAddressSelectBtn.setFocusable(false);
        Places.initialize(getApplicationContext(), "AIzaSyBUUmmyiGdCIlDhGyEvI38S6fExzomHYlE");

        propertyAddressSelectBtn.setOnClickListener(e -> {
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

        imageGridView = findViewById(R.id.selectedPropertyImagesGridView);
        selectImgBtn = findViewById(R.id.selectImgBtn);
        selectImgBtn.setOnClickListener(e -> {
            imageGridView.setBackground(null);
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            propertyAddressSelectBtn.setText(place.getAddress());
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                BitmapDrawable drawableImg = new BitmapDrawable(getResources(), bitmap);
                handlePropertyImgSelected(drawableImg);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void handlePropertyImgSelected(BitmapDrawable newImg) {
//        TODO: add a functionality to delete the selected images.
//         While doing so, check if getChildCount == 0, if so, set grid.setBackground
//         to the default img
        ArrayList<Drawable> images = new ArrayList<>();
        for (int i = 0; i < imageGridView.getChildCount(); i++) {
            ImageView img = ((ImageView) ((RelativeLayout) imageGridView.getChildAt(i)).getChildAt(0));
            images.add((img).getDrawable());
        }
        images.add(newImg);
        SelectedPropertyImageAdapter adapter = new SelectedPropertyImageAdapter(
                getApplicationContext(), images);
        Log.d("imageGridView.", (imageGridView.getChildCount() + 1) + "");
        if (imageGridView.getChildCount() + 1 == 6) {
            selectImgBtn.setEnabled(false);
        }

//        TODO onClick = delete image and update the btn enabled
//            - If there's no img, set bg
//        imageGridView.setOnItemClickListener((parent, view, position, id) -> {
//            parent.removeViewInLayout(view);
//        });
        imageGridView.setAdapter(adapter);
    }


    static class PropertyTypeAdapterSpinnerClass implements AdapterView.OnItemSelectedListener {
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
                Toast.makeText(arg1.getContext(), agents.get(position), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
