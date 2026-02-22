package com.example.diettracker;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CarboFoodsActivity extends AppCompatActivity {

    ListView carboListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbo);

        carboListView = findViewById(R.id.carboListView);

        String[] foodNames = getResources().getStringArray(R.array.carb_food_names);
        TypedArray foodImages = getResources().obtainTypedArray(R.array.carb_food_images);

        CarboFoodAdapter adapter =
                new CarboFoodAdapter(this, foodNames, foodImages);

        carboListView.setAdapter(adapter);
    }
}
