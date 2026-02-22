package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, signupBtn, bmiBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // ✅ If already logged in → go to Landing page
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, LandingActivity.class));
            finish();
            return;
        }

        // Show welcome screen
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        bmiBtn = findViewById(R.id.bmiBtn);

        bmiBtn.setOnClickListener(v ->
                startActivity(new Intent(this, BmiActivity.class))
        );

        loginBtn.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );

        signupBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );
    }
}
