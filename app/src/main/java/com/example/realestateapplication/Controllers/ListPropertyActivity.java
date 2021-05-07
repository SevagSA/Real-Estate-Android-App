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
import android.widget.EditText;
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


    Agent agent;
    ArrayList<String> agents;

    GridView imageGridView;
    Button selectImgBtn;

    ArrayList<String> propertyImages = new ArrayList<>();

    String agentName;

    String propertyType;
    EditText propertyPrice;
    EditText propertyNumOfBed;
    EditText propertyNumOfBath;
    EditText propertySquareFoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_property);

        agent = new Agent(this);
        agents = agent.getAllAgentsNames();

        imageGridView = findViewById(R.id.selectedPropertyImagesGridView);
        propertyPrice = findViewById(R.id.listPropertyPrice);
        propertyAddressSelectBtn = findViewById(R.id.propertyAddressSelectBtn);
        propertySquareFoot = findViewById(R.id.listPropertySQFT);
        propertyNumOfBed = findViewById(R.id.listPropertyBed);
        propertyNumOfBath = findViewById(R.id.listPropertyBath);


        findViewById(R.id.listPropertyForSaleBtn).setOnClickListener(e -> {
            if (
                    !propertyPrice.getText().toString().isEmpty() &&
//                    !propertyAddressSelectBtn.getText().toString().isEmpty() &&
                    !propertyNumOfBed.getText().toString().isEmpty() &&
                    !propertyNumOfBath.getText().toString().isEmpty() &&
                    !propertySquareFoot.getText().toString().isEmpty() &&
                    imageGridView.getChildCount() == 6
            ) {
                Property property = new Property(this);
                property.setContext(getApplicationContext());
                property.setPropertyPrice(((EditText)findViewById(R.id.listPropertyPrice)).getText().toString());
//                TODO uncomment during presentations.
//                property.setPropertyAddress(propertyAddressSelectBtn.getText().toString());
//                TODO comment during presentations
                property.setPropertyAddress("8 Av. Forden Westmount, Quebec H3G1K4 Canada");
                property.setPropertyNumOfBed(Integer.parseInt
                        (((EditText)findViewById(R.id.listPropertyBed)).getText().toString()));
                property.setPropertyNumOfBath(Integer.parseInt
                        (((EditText)findViewById(R.id.listPropertyBath)).getText().toString()));
                property.setPropertySquareFoot(
                        Integer.parseInt(
                                ((EditText)findViewById(R.id.listPropertySQFT)).getText().toString()));
                property.setPropertyMainImg(propertyImages.get(0));
                property.setPropertySecondImg(propertyImages.get(1));
                property.setPropertyThirdImg(propertyImages.get(2));
                property.setPropertyFourthImg(propertyImages.get(3));
                property.setPropertyFifthImg(propertyImages.get(4));
                property.setPropertySixthImg(propertyImages.get(5));

                agent.setFullName(agentName);
                property.setAgentId(agent.getIdOfAgentByName());
                property.setPropertyType(propertyType);
                Long result = property.insertProperty();
                if (result != -1) {
                    Intent intent = new Intent(this, PropertyDetailActivity.class);
                    intent.putExtra("property", property);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill in all the fields " +
                        "(6 images are required)", Toast.LENGTH_LONG).show();
            }
        });

        propertyAddressSelectBtn.setFocusable(false);
        Places.initialize(getApplicationContext(), "AIzaSyB4Iy3hfKjH3SudYoP1TU_uDF83bvHGMq4");

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
        // Setting the ArrayAdapter data on the Spinner
        agentSpin.setAdapter(agentAdapter);
        agentSpin.setOnItemSelectedListener(new AgentAdapterSpinnerClass());


        // Property type spinner
        Spinner propertyTypeSpin = (Spinner) findViewById(R.id.listPropertySelectPropertyType);

        ArrayAdapter propertyTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                Property.PROPERTY_TYPES);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTypeSpin.setAdapter(propertyTypeAdapter);
        propertyTypeSpin.setOnItemSelectedListener(new PropertyTypeAdapterSpinnerClass());

        selectImgBtn = findViewById(R.id.selectImgBtn);
        selectImgBtn.setOnClickListener(e -> {
            imageGridView.setBackground(null);
            startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 3);

//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(intent, 3);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
//            TODO the "replaceAll" has not been tested.
            propertyAddressSelectBtn.setText(place.getAddress().replaceAll(",", ""));
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            propertyImages.add(selectedImage.toString());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                BitmapDrawable drawableImg = new BitmapDrawable(getResources(), bitmap);
                handlePropertyImgSelected(drawableImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

    class PropertyTypeAdapterSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            {
                propertyType = Property.PROPERTY_TYPES[position];
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
                agentName = agents.get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
