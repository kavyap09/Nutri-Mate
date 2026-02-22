package com.example.diettracker;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ProteinFoodsActivity extends AppCompatActivity {

    ListView proteinListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protein_foods);

        ListView proteinListView = findViewById(R.id.proteinListView);

        String[] foodNames = getResources().getStringArray(R.array.protein_food_names);
        TypedArray foodImages = getResources().obtainTypedArray(R.array.protein_food_images);

        ProteinFoodAdapter adapter =
                new ProteinFoodAdapter(this, foodNames, foodImages);

        proteinListView.setAdapter(adapter);
    }
}
