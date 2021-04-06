package com.example.realestateapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapplication.Models.Property;

import java.util.ArrayList;

public class PropertyCardRecyclerViewAdapter extends RecyclerView.Adapter<PropertyCardRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Property> properties = new ArrayList<>();
    private final Context context;

    public PropertyCardRecyclerViewAdapter(ArrayList<Property> properties, Context context) {
        this.properties = properties;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // this is were all of the views will be attached to the View
        Glide.with(context)
                .asBitmap()
                .load(properties.get(position).getPropertyImgURL())
                .into(holder.recentPropertyImg);
        String propertyType = properties.get(position).getPropertyType();
        holder.recentPropertyTypeTextView.setText(propertyType);
        String priceSymbol = propertyType.equalsIgnoreCase("Apartment") ? "$ / Month" : "$";
        holder.recentPropertyPrice.setText(properties.get(position).getPropertyPrice() + priceSymbol);
        holder.recentPropertyAddress.setText(properties.get(position).getPropertyAddress());
        holder.recentPropertyNumOfBed.setText(
                properties.get(position).getPropertyNumOfBed() + " " + holder.itemView.getContext().getString(R.string.bed));
        holder.recentPropertyNumOfBath.setText(
                properties.get(position).getPropertyNumOfBath() + " " + holder.itemView.getContext().getString(R.string.bath));
        holder.recentPropertySquareFoot.setText(
                properties.get(position).getPropertySquareFoot() + " " + holder.itemView.getContext().getString(R.string.sqft));

        holder.exploreRecentPropertyBtn.setOnClickListener(e -> {
        // go to the detail page of the property clicked.
            Toast.makeText(context, properties.get(position).getPropertyAddress(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recentPropertyImg;
        TextView recentPropertyTypeTextView;
        TextView recentPropertyPrice;
        TextView recentPropertyAddress;
        TextView recentPropertyNumOfBed;
        TextView recentPropertyNumOfBath;
        TextView recentPropertySquareFoot;
        Button exploreRecentPropertyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentPropertyImg = itemView.findViewById(R.id.recentPropertyImg);
            recentPropertyTypeTextView = itemView.findViewById(R.id.recentPropertyTypeTextView);
            recentPropertyPrice = itemView.findViewById(R.id.recentPropertyPrice);
            recentPropertyAddress = itemView.findViewById(R.id.recentPropertyAddress);
            recentPropertyNumOfBed = itemView.findViewById(R.id.recentPropertyNumOfBed);
            recentPropertyNumOfBath = itemView.findViewById(R.id.recentPropertyNumOfBath);
            recentPropertySquareFoot = itemView.findViewById(R.id.recentPropertySquareFoot);
            exploreRecentPropertyBtn = itemView.findViewById(R.id.exploreRecentPropertyBtn);

        }
    }
}
