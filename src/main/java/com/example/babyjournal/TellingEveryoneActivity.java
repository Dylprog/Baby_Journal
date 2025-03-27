package com.example.babyjournal;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TellingEveryoneActivity extends AppCompatActivity {
    private EditText mamFoundOutInput, dadFoundOutInput;
    private EditText mamFirstToldInput, dadFirstToldInput;
    private EditText peopleReactionsInput;

    private DatabaseHelper dbHelper;
    private String babyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tellingeveryone);

        // Initialize dbHelper first
        dbHelper = new DatabaseHelper(this);

        // Get the baby name from the intent
        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Initialize views
        initializeViews();

        // Set the activity title
        if (babyName != null) {
            setTitle(babyName + "'s Telling Everyone");
            
            // Set the telling everyone text
            TextView tellingEveryoneText = findViewById(R.id.tellingEveryoneText);
            tellingEveryoneText.setText("When everyone found out about " + babyName + ":");

            // Load existing data
            loadExistingData();
        }

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveTellingDetails());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        // Initialize EditText fields
        mamFoundOutInput = findViewById(R.id.mamFoundOutInput);
        dadFoundOutInput = findViewById(R.id.dadFoundOutInput);
        mamFirstToldInput = findViewById(R.id.mamFirstToldInput);
        dadFirstToldInput = findViewById(R.id.dadFirstToldInput);
        peopleReactionsInput = findViewById(R.id.peopleReactionsInput);
    }

    private void saveTellingDetails() {
        String mamFoundOut = mamFoundOutInput.getText().toString().trim();
        String dadFoundOut = dadFoundOutInput.getText().toString().trim();
        String mamFirstTold = mamFirstToldInput.getText().toString().trim();
        String dadFirstTold = dadFirstToldInput.getText().toString().trim();
        String peopleReactions = peopleReactionsInput.getText().toString().trim();

        long result = dbHelper.saveTellingEveryoneData(
            babyName, mamFoundOut, dadFoundOut, mamFirstTold, dadFirstTold, peopleReactions);

        if (result > 0) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadExistingData() {
        TellingEveryoneData data = dbHelper.getTellingEveryoneData(babyName);
        if (data != null) {
            mamFoundOutInput.setText(data.getMamFoundOut());
            dadFoundOutInput.setText(data.getDadFoundOut());
            mamFirstToldInput.setText(data.getMamFirstTold());
            dadFirstToldInput.setText(data.getDadFirstTold());
            peopleReactionsInput.setText(data.getPeopleReactions());
        }
    }
}
