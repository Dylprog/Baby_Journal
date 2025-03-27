package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BathtimeActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String babyName;
    private EditText firstBathEdit;
    private EditText bathExperienceEdit;
    private EditText bathGamesEdit;
    private EditText bathToysEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathtime);

        dbHelper = new DatabaseHelper(this);
        
        // Get the baby name from the intent
        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Initialize EditText fields
        firstBathEdit = findViewById(R.id.firstBathEdit);
        bathExperienceEdit = findViewById(R.id.bathExperienceEdit);
        bathGamesEdit = findViewById(R.id.bathGamesEdit);
        bathToysEdit = findViewById(R.id.bathToysEdit);

        // Set the activity title and labels
        if (babyName != null) {
            setTitle(babyName + "'s Bathtime");
            
            TextView firstBathLabel = findViewById(R.id.firstBathLabel);
            firstBathLabel.setText("When " + babyName + " had their first bath:");
            
            TextView bathExperienceLabel = findViewById(R.id.bathExperienceLabel);
            bathExperienceLabel.setText("How " + babyName + "'s bath went:");
            
            TextView bathGamesLabel = findViewById(R.id.bathGamesLabel);
            bathGamesLabel.setText("Games " + babyName + " played in the bath:");
            
            TextView bathToysLabel = findViewById(R.id.bathToysLabel);
            bathToysLabel.setText(babyName + "'s favourite toys in the bath:");

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
        BathtimeData data = dbHelper.getBathtimeData(babyName);
        if (data != null) {
            firstBathEdit.setText(data.getFirstBath());
            bathExperienceEdit.setText(data.getBathExperience());
            bathGamesEdit.setText(data.getBathGames());
            bathToysEdit.setText(data.getBathToys());
        }
    }

    private void saveData() {
        String firstBath = firstBathEdit.getText().toString().trim();
        String bathExperience = bathExperienceEdit.getText().toString().trim();
        String bathGames = bathGamesEdit.getText().toString().trim();
        String bathToys = bathToysEdit.getText().toString().trim();

        long result = dbHelper.saveBathtimeData(babyName, firstBath, bathExperience, 
                                              bathGames, bathToys);
        
        if (result > 0) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}
