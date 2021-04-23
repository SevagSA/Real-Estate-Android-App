package com.example.realestateapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapplication.R;

import java.util.ArrayList;

//  TODO you changed propertyGalleryImageURLs from a String[] to ArrayList.
//   See if re-changing it is necesarry once you put the db fetching logic in the Property
//    class.
public class PropertyGalleryRecyclerViewAdapter extends RecyclerView.Adapter<PropertyGalleryRecyclerViewAdapter.ViewHolder>  {
    Context context;
    String[] propertyGalleryImageURLs;
//    ArrayList<String> propertyGalleryImageURLs;

    public PropertyGalleryRecyclerViewAdapter(Context context, String[] propertyGalleryImageURLs) {
        this.context = context;
        this.propertyGalleryImageURLs = propertyGalleryImageURLs;
        Log.d("in bind iew holder", propertyGalleryImageURLs.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.property_gallery_image, parent, false);
        return new PropertyGalleryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(propertyGalleryImageURLs[position])
                .into(holder.galleryImage);
    }

    @Override
    public int getItemCount() {
        return propertyGalleryImageURLs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryImage = itemView.findViewById(R.id.galleryImage);
        }
    }
}
