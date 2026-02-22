package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {

    Button startBmiBtn, nutriTrackerBtn, logoutBtn;
    TextView welcomeUserTv;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        auth = FirebaseAuth.getInstance();

        startBmiBtn = findViewById(R.id.startBmiBtn);
        nutriTrackerBtn = findViewById(R.id.nutriTrackerBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        welcomeUserTv = findViewById(R.id.welcomeUserTv);

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {

            String email = currentUser.getEmail();

            if (email != null) {

                // Remove @gmail.com
                String name = email.replace("@gmail.com", "");

                welcomeUserTv.setText("Hello!! " + name + " 👋");
            }
        }


        // BMI Page
        startBmiBtn.setOnClickListener(v ->
                startActivity(new Intent(this, BmiActivity.class))
        );

        // Nutri Tracker Page
        nutriTrackerBtn.setOnClickListener(v ->
                startActivity(new Intent(this, NutriTrackerActivity.class))
        );

        // Logout
        logoutBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
