package com.example.babyjournal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParentsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String babyName;
    private EditText motherEditText;
    private EditText fatherEditText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);
        
        dbHelper = new DatabaseHelper(this);
        
        // Get the baby name from the intent
        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Set the baby's name before "Parents"
        TextView titleTextView = findViewById(R.id.parentsTitle);
        titleTextView.setText(babyName + "'s Parents");

        // Set the labels for mother and father
        TextView motherLabel = findViewById(R.id.motherLabel);
        TextView fatherLabel = findViewById(R.id.fatherLabel);
        motherLabel.setText(babyName + "'s mam is called...");
        fatherLabel.setText(babyName + "'s dad is called...");

        // Initialize parent input fields
        motherEditText = findViewById(R.id.motherEditText);
        fatherEditText = findViewById(R.id.fatherEditText);

        // Load existing data
        loadExistingData();

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveParentsData());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void loadExistingData() {
        ParentsData data = dbHelper.getParentsData(babyName);
        if (data != null) {
            motherEditText.setText(data.getMotherName());
            fatherEditText.setText(data.getFatherName());
        }
    }

    private void saveParentsData() {
        String motherName = motherEditText.getText().toString().trim();
        String fatherName = fatherEditText.getText().toString().trim();
        
        if (motherName.isEmpty() && fatherName.isEmpty()) {
            Toast.makeText(this, "Please enter at least one parent's name", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.saveParentsData(babyName, motherName, fatherName);
        
        if (result > 0) {
            Toast.makeText(this, "Parents details saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving parents details", Toast.LENGTH_SHORT).show();
        }
    }
} 