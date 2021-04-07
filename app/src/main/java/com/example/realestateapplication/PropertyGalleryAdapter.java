package com.example.realestateapplication;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertyGalleryAdapter extends BaseAdapter {
    Context context;
    String[] propertyGalleryImageURLs;

    public PropertyGalleryAdapter(Context context, String[] propertyGalleryImageURLs) {
        this.context = context;
        this.propertyGalleryImageURLs = propertyGalleryImageURLs;
        Log.d("In the constructor", Arrays.toString(propertyGalleryImageURLs));
    }

    @Override
    public int getCount() {
        return propertyGalleryImageURLs.length;
    }

    @Override
    public Object getItem(int position) {
        return propertyGalleryImageURLs[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView propertyImage;
        if(convertView == null) {
            propertyImage = new ImageView(context);
            propertyImage.setLayoutParams(new GridView.LayoutParams(270, 170));
            propertyImage.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            propertyImage = (ImageView) convertView;
        }
        Glide.with(context)
                .asBitmap()
                .load(propertyGalleryImageURLs[position])
                .into(propertyImage);
        Log.d("Right over here", propertyGalleryImageURLs[position] + " | " + position + " | " + propertyGalleryImageURLs);
//        imageView.setImageResource(imagesPhoto[position]);
        return propertyImage;
    }
}
