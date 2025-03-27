package com.example.babyjournal;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewBabyProfileActivity extends AppCompatActivity {
    private Spinner genderSpinner;
    private EditText nameEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_baby_profile);

        dbHelper = new DatabaseHelper(this);

        // Initialize views
        genderSpinner = findViewById(R.id.genderSpinner);
        nameEditText = findViewById(R.id.babyNameInput);

        // Set up gender spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Boy", "Girl"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Add save button click listener
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBabyProfile();
            }
        });

        // Add return button click listener
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveBabyProfile() {
        String name = nameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addBabyProfile(name, gender);
        if (result != -1) {
            Toast.makeText(this, "Baby profile saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving baby profile", Toast.LENGTH_SHORT).show();
        }
    }
} 