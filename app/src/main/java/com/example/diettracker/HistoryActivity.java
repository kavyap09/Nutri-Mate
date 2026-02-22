package com.example.diettracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> dateList;
    HistoryAdapter adapter;

    FirebaseFirestore db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dateList = new ArrayList<>();
        adapter = new HistoryAdapter(dateList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("USER ID: " + userId);

        loadDates();
    }

    private void loadDates() {

        db.collection("users")
                .document(userId)
                .collection("meals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    dateList.clear();

                    if (queryDocumentSnapshots.isEmpty()) {
                        System.out.println("No dates found");
                    }

                    queryDocumentSnapshots.forEach(doc -> {
                        dateList.add(doc.getId());
                        System.out.println("DATE: " + doc.getId());
                    });

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        System.out.println("ERROR: " + e.getMessage()));
    }


}
