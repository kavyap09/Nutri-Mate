package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NutriTrackerActivity extends AppCompatActivity {

    EditText foodNameEt, proteinEt, carbsEt, fatsEt;
    Button addFoodBtn, viewDetailsBtn;
    TextView totalProteinTv, totalCarbsTv, totalFatsTv;
    ImageView historyIcon;

    FirebaseFirestore db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri_tracker);

        // Check login
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Bind views
        foodNameEt = findViewById(R.id.foodNameEt);
        proteinEt = findViewById(R.id.proteinEt);
        carbsEt = findViewById(R.id.carbsEt);
        fatsEt = findViewById(R.id.fatsEt);
        addFoodBtn = findViewById(R.id.addFoodBtn);
        viewDetailsBtn = findViewById(R.id.viewDetailsBtn);
        totalProteinTv = findViewById(R.id.totalProteinTv);
        totalCarbsTv = findViewById(R.id.totalCarbsTv);
        totalFatsTv = findViewById(R.id.totalFatsTv);
        historyIcon = findViewById(R.id.historyIcon);

        historyIcon.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class))
        );

        viewDetailsBtn.setOnClickListener(v ->
                startActivity(new Intent(this, TodayDetailsActivity.class))
        );

        addFoodBtn.setOnClickListener(v -> addMeal());

        loadTodayTotals();
    }

    private void addMeal() {

        String foodName = foodNameEt.getText().toString().trim();
        String proteinStr = proteinEt.getText().toString().trim();
        String carbsStr = carbsEt.getText().toString().trim();
        String fatsStr = fatsEt.getText().toString().trim();

        if (TextUtils.isEmpty(foodName) ||
                TextUtils.isEmpty(proteinStr) ||
                TextUtils.isEmpty(carbsStr) ||
                TextUtils.isEmpty(fatsStr)) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        float protein = Float.parseFloat(proteinStr);
        float carbs = Float.parseFloat(carbsStr);
        float fats = Float.parseFloat(fatsStr);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());

        // Create meal map
        Map<String, Object> meal = new HashMap<>();
        meal.put("foodName", foodName);
        meal.put("protein", protein);
        meal.put("carbs", carbs);
        meal.put("fats", fats);
        meal.put("timestamp", FieldValue.serverTimestamp());

        // 1️⃣ Ensure date document exists (without overwriting)
        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("createdAt", FieldValue.serverTimestamp());

        db.collection("users")
                .document(userId)
                .collection("meals")
                .document(currentDate)
                .set(dateMap, SetOptions.merge());

        // 2️⃣ Add meal ONLY ONCE
        db.collection("users")
                .document(userId)
                .collection("meals")
                .document(currentDate)
                .collection("foodItems")
                .add(meal)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Meal added", Toast.LENGTH_SHORT).show();
                    clearFields();
                    loadTodayTotals();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void loadTodayTotals() {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());

        db.collection("users")
                .document(userId)
                .collection("meals")
                .document(currentDate)
                .collection("foodItems")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    float totalProtein = 0;
                    float totalCarbs = 0;
                    float totalFats = 0;

                    for (var doc : queryDocumentSnapshots) {

                        Double protein = doc.getDouble("protein");
                        Double carbs = doc.getDouble("carbs");
                        Double fats = doc.getDouble("fats");

                        if (protein != null) totalProtein += protein.floatValue();
                        if (carbs != null) totalCarbs += carbs.floatValue();
                        if (fats != null) totalFats += fats.floatValue();
                    }

                    totalProteinTv.setText("Protein: " + totalProtein + " g");
                    totalCarbsTv.setText("Carbs: " + totalCarbs + " g");
                    totalFatsTv.setText("Fats: " + totalFats + " g");
                });
    }

    private void clearFields() {
        foodNameEt.setText("");
        proteinEt.setText("");
        carbsEt.setText("");
        fatsEt.setText("");
    }
}
