package com.example.realestateapplication.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.realestateapplication.Controllers.PropertyDetailActivity;
import com.example.realestateapplication.Controllers.SearchPropertyActivity;
import com.example.realestateapplication.Models.Region;
import com.example.realestateapplication.R;

import java.util.ArrayList;

public class RegionsRecyclerViewAdapter extends RecyclerView.Adapter<RegionsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Region> regions;
    private final Context context;

    public RegionsRecyclerViewAdapter(ArrayList<Region> regions, Context context) {
        this.regions = regions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_card_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // this is were all of the views will be attached to the View
        Glide.with(context)
                .asBitmap()
                .load(regions.get(position).getImgURL())
                .into(holder.locationImg);
        holder.locationTitle.setText(regions.get(position).getLocationTitle());
        holder.exploreBtn.setOnClickListener(e -> {
            Intent intent = new Intent(context, SearchPropertyActivity.class);
            intent.putExtra("region", regions.get(position).getLocationTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView locationImg;
        TextView locationTitle;
        Button exploreBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationImg = itemView.findViewById(R.id.locationImg);
            locationTitle = itemView.findViewById(R.id.locationTitle);
            exploreBtn = itemView.findViewById(R.id.exploreRegionBtn);

        }
    }
}
