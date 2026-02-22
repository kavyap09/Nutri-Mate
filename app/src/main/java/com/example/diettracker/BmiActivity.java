package com.example.diettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BmiActivity extends AppCompatActivity {

    EditText ageEt, heightEt, weightEt;
    Button calculateBtn;
    FirebaseAuth auth;

    float calculatedBmi;
    String bmiStatus;
    String targetPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        auth = FirebaseAuth.getInstance();

        ageEt = findViewById(R.id.ageEt);
        heightEt = findViewById(R.id.heightEt);
        weightEt = findViewById(R.id.weightEt);
        calculateBtn = findViewById(R.id.calculateBtn);


        calculateBtn.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {

        String heightStr = heightEt.getText().toString().trim();
        String weightStr = weightEt.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        float heightCm = Float.parseFloat(heightStr);
        float weightKg = Float.parseFloat(weightStr);

        float heightM = heightCm / 100;
        calculatedBmi = weightKg / (heightM * heightM);

        if (calculatedBmi < 18.5f) {
            bmiStatus = "Lower than recommended range";
            targetPage = "GAIN";
        } else if (calculatedBmi >= 25f) {
            bmiStatus = "Higher than recommended range";
            targetPage = "LOSS";
        } else {
            bmiStatus = "Normal";
            targetPage = null;
        }

        showBmiDialog();
    }

    // ✅ BMI RESULT POPUP
    private void showBmiDialog() {

        FirebaseUser currentUser = auth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();

        View view = getLayoutInflater().inflate(R.layout.dialog_bmi_result, null);
        dialog.setView(view);
        dialog.setCancelable(false);

        TextView bmiValueTv = view.findViewById(R.id.dialogBmiValue);
        TextView bmiStatusTv = view.findViewById(R.id.dialogBmiStatus);
        TextView titleTv = view.findViewById(R.id.dialogTitle);

        Button primaryBtn = view.findViewById(R.id.primaryBtn);
        Button secondaryBtn = view.findViewById(R.id.secondaryBtn);

        // Set values
        bmiValueTv.setText(String.format("%.2f", calculatedBmi));
        bmiStatusTv.setText(bmiStatus);

        // NORMAL BMI
        if (targetPage == null) {

            primaryBtn.setVisibility(View.GONE);
            secondaryBtn.setText("Okay");

            secondaryBtn.setOnClickListener(v -> dialog.dismiss());
        }
        // LOGGED IN USER
        else if (currentUser != null) {

            primaryBtn.setText("View Diet Plan");
            secondaryBtn.setText("Close");

            primaryBtn.setOnClickListener(v -> {
                dialog.dismiss();
                if ("LOSS".equals(targetPage)) {
                    startActivity(new Intent(this, WeightLossActivity.class));
                } else {
                    startActivity(new Intent(this, WeightGainActivity.class));
                }
            });

            secondaryBtn.setOnClickListener(v -> dialog.dismiss());
        }
        // NOT LOGGED IN
        else {

            titleTv.setText("Explore personalized diet plans");

            primaryBtn.setText("Login");
            secondaryBtn.setText("Sign Up");

            // LOGIN
            primaryBtn.setOnClickListener(v -> {
                dialog.dismiss();
                Intent i = new Intent(this, LoginActivity.class);
                i.putExtra("TARGET_PAGE", targetPage);
                startActivity(i);
            });

            // SIGN UP
            secondaryBtn.setOnClickListener(v -> {
                dialog.dismiss();
                Intent i = new Intent(this, SignupActivity.class);
                i.putExtra("TARGET_PAGE", targetPage);
                startActivity(i);
            });
        }

        dialog.show();
    }

}
