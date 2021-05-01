package com.example.realestateapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.example.realestateapplication.Controllers.HomeActivity;
import com.example.realestateapplication.Controllers.PropertyDetailActivity;
import com.example.realestateapplication.Models.Property;
import com.example.realestateapplication.Models.User;
import com.example.realestateapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PropertyCardRecyclerViewAdapter extends RecyclerView.Adapter<PropertyCardRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Property> properties;
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
        properties.get(position).setContext(context);
        Glide.with(context)
                .asBitmap()
                .load(properties.get(position).getPropertyMainImg())
                .into(holder.recentPropertyImg);
        String propertyType = properties.get(position).getPropertyType();
        holder.recentPropertyTypeTextView.setText(propertyType);
        holder.recentPropertyPrice.setText(properties.get(position).getPropertyPrice());
        holder.recentPropertyAddress.setText(properties.get(position).getPropertyAddress());
        holder.recentPropertyNumOfBed.setText(properties.get(position).getPropertyNumOfBed());
        holder.recentPropertyNumOfBath.setText(properties.get(position).getPropertyNumOfBath());
        holder.recentPropertySquareFoot.setText(properties.get(position).getPropertySquareFoot());

        holder.exploreRecentPropertyBtn.setOnClickListener(e -> {
            Intent intent = new Intent(context, PropertyDetailActivity.class);
            intent.putExtra("property", properties.get(position));
            context.startActivity(intent);
        });

        holder.likedIconImg.setOnClickListener(e -> {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            User.handleLikedBtnClick(fAuth.getCurrentUser().getEmail(), holder.recentPropertyAddress.getText().toString());
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
        ImageView likedIconImg;

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
            likedIconImg = itemView.findViewById(R.id.likedIconImg);

        }
    }
}
