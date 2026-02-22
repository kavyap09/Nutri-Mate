package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WeightLossActivity extends AppCompatActivity {

    Button viewProteinFoodsBtn, viewCarbsFoodsBtn, viewFatsFoodsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);

        viewProteinFoodsBtn = findViewById(R.id.viewProteinFoodsBtn);
        viewCarbsFoodsBtn = findViewById(R.id.viewCarbsFoodsBtn);
        viewFatsFoodsBtn = findViewById(R.id.viewFatsFoodsBtn);

        // Protein button
        viewProteinFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightLossActivity.this, ProteinFoodsActivity.class);
            startActivity(intent);
        });

        // Carbs button
        viewCarbsFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightLossActivity.this, CarboFoodsActivity.class);
            startActivity(intent);
        });

        // Fats button
        viewFatsFoodsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeightLossActivity.this, FatFoodsActivity.class);
            startActivity(intent);
        });
    }
}
