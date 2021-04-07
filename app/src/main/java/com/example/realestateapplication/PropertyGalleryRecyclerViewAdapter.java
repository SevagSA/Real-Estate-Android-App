package com.example.realestateapplication;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertyGalleryRecyclerViewAdapter extends RecyclerView.Adapter<PropertyGalleryRecyclerViewAdapter.ViewHolder>  {
    Context context;
    String[] propertyGalleryImageURLs;

    public PropertyGalleryRecyclerViewAdapter(Context context, String[] propertyGalleryImageURLs) {
        this.context = context;
        this.propertyGalleryImageURLs = propertyGalleryImageURLs;
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
