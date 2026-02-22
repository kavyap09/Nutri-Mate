package com.example.diettracker;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TodayDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MealModel> mealList;
    MealsAdapter adapter;
    TextView emptyStateTv;

    FirebaseFirestore db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_details);

        recyclerView = findViewById(R.id.mealsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyStateTv = findViewById(R.id.emptyStateTv);

        mealList = new ArrayList<>();
        adapter = new MealsAdapter(mealList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadMeals();
    }

    private void loadMeals() {

        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");

        if (selectedDate == null) {
            selectedDate = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault()).format(new Date());
        }

        db.collection("users")
                .document(userId)
                .collection("meals")
                .document(selectedDate)
                .collection("foodItems")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    mealList.clear();

                    queryDocumentSnapshots.forEach(doc -> {
                        MealModel meal = doc.toObject(MealModel.class);
                        mealList.add(meal);
                    });

                    adapter.notifyDataSetChanged();

                    if (mealList.isEmpty()) {
                        emptyStateTv.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyStateTv.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }

}
