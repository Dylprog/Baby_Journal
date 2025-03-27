package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BabysFirstWeekActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String babyName;
    private EditText homecomingEdit;
    private EditText addressEdit;
    private EditText visitorsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysfirstweek);

        dbHelper = new DatabaseHelper(this);
        
        // Get the baby name from the intent
        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Initialize EditText fields
        homecomingEdit = findViewById(R.id.homecomingEdit);
        addressEdit = findViewById(R.id.addressEdit);
        visitorsEdit = findViewById(R.id.visitorsEdit);

        // Set the activity title and labels
        if (babyName != null) {
            setTitle(babyName + "'s First Week");

            TextView firstWeekText = findViewById(R.id.FirstWeekText);
            firstWeekText.setText(babyName + "'s first week");
            
            TextView homecomingLabel = findViewById(R.id.homecomingLabel);
            homecomingLabel.setText(babyName + " came home on:");
            
            TextView addressLabel = findViewById(R.id.addressLabel);
            addressLabel.setText(babyName + "'s first address was:");
            
            TextView visitorsLabel = findViewById(R.id.visitorsLabel);
            visitorsLabel.setText(babyName + "'s first visitors were:");

            // Load existing data
            loadExistingData();
        }

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveData());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void loadExistingData() {
        FirstWeekData data = dbHelper.getFirstWeekData(babyName);
        if (data != null) {
            homecomingEdit.setText(data.getHomecomingDate());
            addressEdit.setText(data.getFirstAddress());
            visitorsEdit.setText(data.getFirstVisitors());
        }
    }

    private void saveData() {
        String homecoming = homecomingEdit.getText().toString().trim();
        String address = addressEdit.getText().toString().trim();
        String visitors = visitorsEdit.getText().toString().trim();

        long result = dbHelper.saveFirstWeekData(babyName, homecoming, address, visitors);
        
        if (result > 0) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}

