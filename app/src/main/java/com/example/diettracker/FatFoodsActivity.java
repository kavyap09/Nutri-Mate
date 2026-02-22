package com.example.diettracker;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class FatFoodsActivity extends AppCompatActivity {

    ListView fatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_foods);

        fatListView = findViewById(R.id.fatListView);

        String[] foodNames = getResources().getStringArray(R.array.fat_food_names);
        TypedArray foodImages = getResources().obtainTypedArray(R.array.fat_food_images);

        FatFoodAdapter adapter =
                new FatFoodAdapter(this, foodNames, foodImages);

        fatListView.setAdapter(adapter);
    }
}
