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

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button loginBtn;
    TextView tvGoSignup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.loginEmail);
        passwordEt = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.btnLogin);
        tvGoSignup = findViewById(R.id.tvGoSignup);

        loginBtn.setOnClickListener(v -> loginUser());

        // 🔁 Go to Signup screen
        tvGoSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);

            // Preserve TARGET_PAGE if user came from BMI
            String targetPage = getIntent().getStringExtra("TARGET_PAGE");
            if (targetPage != null) {
                intent.putExtra("TARGET_PAGE", targetPage);
            }

            startActivity(intent);
            finish();
        });
    }

    private void loginUser() {

        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        redirectAfterAuth();

                    } else {
                        Toast.makeText(this,
                                "Login failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // 🔁 Redirect based on where user came from
    private void redirectAfterAuth() {

        String targetPage = getIntent().getStringExtra("TARGET_PAGE");
        Intent intent;

        if ("LOSS".equals(targetPage)) {
            intent = new Intent(this, WeightLossActivity.class);
        } else if ("GAIN".equals(targetPage)) {
            intent = new Intent(this, WeightGainActivity.class);
        } else {
            // ✅ Default after login → Landing page
            intent = new Intent(this, LandingActivity.class);
        }

        // 🔥 Clear back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
