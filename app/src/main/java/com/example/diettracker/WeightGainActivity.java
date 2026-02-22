package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WeightGainActivity extends AppCompatActivity {

    Button viewProteinFoodsBtn, viewCarbsFoodsBtn, viewFatsFoodsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_gain); // 👈 matches your XML file name

        // Link buttons from XML
        viewProteinFoodsBtn = findViewById(R.id.viewProteinFoodsBtn);
        viewCarbsFoodsBtn   = findViewById(R.id.viewCarbsFoodsBtn);
        viewFatsFoodsBtn    = findViewById(R.id.viewFatsFoodsBtn);

        // Protein button → Protein foods page
        viewProteinFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightGainActivity.this, ProteinFoodsActivity.class);
            startActivity(intent);
        });

        // Carbs button → Carbo foods page
        viewCarbsFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightGainActivity.this, CarboFoodsActivity.class);
            startActivity(intent);
        });

        // Fats button → Fat foods page
        viewFatsFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightGainActivity.this, FatFoodsActivity.class);
            startActivity(intent);
        });
    }
}
