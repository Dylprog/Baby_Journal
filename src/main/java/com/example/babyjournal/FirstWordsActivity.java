package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FirstWordsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstwords);

        // Get the baby name from the intent
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Set the activity title and update text fields
        if (babyName != null) {
            setTitle(babyName + "'s First words");

            // Set the birth details text
            TextView birthDetailsText = findViewById(R.id.FirstWordsText);
            birthDetailsText.setText(babyName + "'s First Words");
            
            // Find all TextViews that need to be updated
            TextView smilesText = findViewById(R.id.smilesQuestion);
            TextView wordsText = findViewById(R.id.wordsQuestion);
            TextView laughterText = findViewById(R.id.laughterQuestion);

            // Update the text with the baby's name
            smilesText.setText("When did " + babyName + " first smile?");
            wordsText.setText("What were " + babyName + "'s first words?");
            laughterText.setText("What makes " + babyName + " laugh?");
        }

        // Load saved data
        loadSavedData(babyName);

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // Get input values from EditText fields
            EditText smilesInput = findViewById(R.id.smilesAnswer);
            EditText wordsInput = findViewById(R.id.wordsAnswer);
            EditText laughterInput = findViewById(R.id.laughterAnswer);

            String smiles = smilesInput.getText().toString().trim();
            String words = wordsInput.getText().toString().trim();
            String laughter = laughterInput.getText().toString().trim();

            // Validate inputs
            if (smiles.isEmpty() || words.isEmpty() || laughter.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long result = dbHelper.saveFirstWordsData(babyName, smiles, words, laughter);

            if (result != -1) {
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void loadSavedData(String babyName) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        FirstWordsData savedData = dbHelper.getFirstWordsData(babyName);

        if (savedData != null) {
            // Find EditText fields
            EditText smilesInput = findViewById(R.id.smilesAnswer);
            EditText wordsInput = findViewById(R.id.wordsAnswer);
            EditText laughterInput = findViewById(R.id.laughterAnswer);

            // Set the saved values
            smilesInput.setText(savedData.getFirstSmile());
            wordsInput.setText(savedData.getFirstWords());
            laughterInput.setText(savedData.getMakesLaugh());
        }
    }
}
