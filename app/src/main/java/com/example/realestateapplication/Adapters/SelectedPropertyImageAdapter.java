package com.example.realestateapplication.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.realestateapplication.R;

import java.util.ArrayList;

public class SelectedPropertyImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Drawable> drawables;

    public SelectedPropertyImageAdapter(Context context, ArrayList<Drawable> drawables) {
        this.context = context;
        this.drawables = drawables;
    }

    @Override
    public int getCount() {
        return drawables.size();
    }

    @Override
    public Object getItem(int position) {
        return drawables.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.selected_property_image_item, parent, false);
        }
        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.selectedPropertyImage);
        imageView.setImageDrawable(drawables.get(position));
        return convertView;
    }
}