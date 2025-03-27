package com.example.babyjournal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BirthActivity extends AppCompatActivity {
    private EditText birthDateInput, birthWeightInput, birthFeaturesInput, birthAttendeesInput;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth);

        // Initialize calendar
        calendar = Calendar.getInstance();

        // Get the baby name from the intent
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Set the activity title
        if (babyName != null) {
            setTitle(babyName + "'s Birth");
            
            // Set the birth details text
            TextView birthDetailsText = findViewById(R.id.birthDetailsText);
            birthDetailsText.setText("When " + babyName + " was born:");
        }

        // Initialize EditText fields
        birthDateInput = findViewById(R.id.birthDateInput);
        birthWeightInput = findViewById(R.id.birthWeightInput);
        birthFeaturesInput = findViewById(R.id.birthFeaturesInput);
        birthAttendeesInput = findViewById(R.id.birthAttendeesInput);

        // Load saved birth details
        if (babyName != null) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            BirthDetails birthDetails = dbHelper.getBirthDetails(babyName);
            if (birthDetails != null) {
                birthDateInput.setText(birthDetails.getBirthDate());
                birthWeightInput.setText(birthDetails.getBirthWeight());
                birthFeaturesInput.setText(birthDetails.getBirthFeatures());
                birthAttendeesInput.setText(birthDetails.getBirthAttendees());
                
                // Update calendar with saved date
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    calendar.setTime(sdf.parse(birthDetails.getBirthDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Set up date picker
        setupDatePicker();

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveBirthDetails());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateLabel();
            }
        };

        birthDateInput.setOnClickListener(v -> {
            new DatePickerDialog(BirthActivity.this, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Make the EditText not editable directly
        birthDateInput.setFocusable(false);
        birthDateInput.setClickable(true);
    }

    private void updateDateLabel() {
        String dateFormat = "dd/MM/yyyy"; // You can change this format as needed
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        birthDateInput.setText(sdf.format(calendar.getTime()));
    }

    
    private void saveBirthDetails() {
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);
        String birthDate = birthDateInput.getText().toString();
        String birthWeight = birthWeightInput.getText().toString();
        String birthFeatures = birthFeaturesInput.getText().toString();
        String birthAttendees = birthAttendeesInput.getText().toString();
    
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long result = dbHelper.saveBirthDetails(babyName, birthDate, birthWeight, 
                                              birthFeatures, birthAttendees);
    
        if (result > 0) {
            // Successfully saved
            finish();
        } else {
            // Handle error
            Toast.makeText(this, "Error saving birth details", Toast.LENGTH_SHORT).show();
        }
    }
}
