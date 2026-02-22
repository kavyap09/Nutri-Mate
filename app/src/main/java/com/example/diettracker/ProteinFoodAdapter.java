package com.example.diettracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProteinFoodAdapter extends BaseAdapter {

    Context context;
    String[] foodNames;
    TypedArray foodImages;
    LayoutInflater inflater;

    public ProteinFoodAdapter(Context context, String[] foodNames, TypedArray foodImages) {
        this.context = context;
        this.foodNames = foodNames;
        this.foodImages = foodImages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodNames.length;
    }

    @Override
    public Object getItem(int position) {
        return foodNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_protein_food, parent, false);
        }

        ImageView foodImage = convertView.findViewById(R.id.foodImage);
        TextView foodName = convertView.findViewById(R.id.foodName);

        foodName.setText(foodNames[position]);
        foodImage.setImageResource(foodImages.getResourceId(position, -1));

        return convertView;
    }
}
