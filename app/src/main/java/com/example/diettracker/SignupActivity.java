package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText emailEt, passwordEt;
    Button signupBtn;
    TextView tvGoLogin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.signupEmail);
        passwordEt = findViewById(R.id.signupPassword);
        signupBtn = findViewById(R.id.btnSignup);
        tvGoLogin = findViewById(R.id.tvGoLogin);

        signupBtn.setOnClickListener(v -> signupUser());

        // 🔁 Go to Login screen
        tvGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);

            // Preserve TARGET_PAGE if coming from BMI
            String targetPage = getIntent().getStringExtra("TARGET_PAGE");
            if (targetPage != null) {
                intent.putExtra("TARGET_PAGE", targetPage);
            }

            startActivity(intent);
            finish();
        });
    }

    private void signupUser() {

        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        redirectAfterAuth();
                    } else {
                        Toast.makeText(this,
                                "Signup failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // 🔁 Redirect after signup
    private void redirectAfterAuth() {

        String targetPage = getIntent().getStringExtra("TARGET_PAGE");
        Intent intent;

        if ("LOSS".equals(targetPage)) {
            intent = new Intent(this, WeightLossActivity.class);
        } else if ("GAIN".equals(targetPage)) {
            intent = new Intent(this, WeightGainActivity.class);
        } else {
            // ✅ Default after signup → Landing page
            intent = new Intent(this, LandingActivity.class);
        }

        // 🔥 Clear back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
