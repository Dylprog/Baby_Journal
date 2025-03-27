package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {
    private EditText altName1, altName2, altName3, nameReasonInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        // Get the baby name from the intent
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Set the activity title
        if (babyName != null) {
            setTitle(babyName + "'s Name");
            
            // Set the name reason text
            TextView nameReasonText = findViewById(R.id.nameReasonText);
            nameReasonText.setText(babyName + "'s meaning/reason why we picked it:");
            
            // Set the alternative names text
            TextView altNamesText = findViewById(R.id.alternativeNamesText);
            altNamesText.setText("What " + babyName + " was almost called:");
        }

        // Initialize EditText fields
        nameReasonInput = findViewById(R.id.nameReasonInput);
        altName1 = findViewById(R.id.altName1);
        altName2 = findViewById(R.id.altName2);
        altName3 = findViewById(R.id.altName3);

        // Load saved data
        loadSavedNames(babyName);

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveAlternativeNames());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void loadSavedNames(String babyName) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        NamesData nameData = dbHelper.getNamesData(babyName);
        
        if (nameData != null) {
            nameReasonInput.setText(nameData.getNameReason());
            altName1.setText(nameData.getAltName1());
            altName2.setText(nameData.getAltName2());
            altName3.setText(nameData.getAltName3());

            // Set the birth details text
            TextView birthDetailsText = findViewById(R.id.NameText);
            birthDetailsText.setText("The name " + babyName);
        }
    }

    private void saveAlternativeNames() {
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);
        String nameReason = nameReasonInput.getText().toString().trim();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        
        String altName1Text = altName1.getText().toString().trim();
        String altName2Text = altName2.getText().toString().trim();
        String altName3Text = altName3.getText().toString().trim();
        
        long result = dbHelper.saveNamesData(babyName, nameReason, altName1Text, altName2Text, altName3Text);
        
        if (result > 0) {
            Toast.makeText(this, "Names saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving names", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}
