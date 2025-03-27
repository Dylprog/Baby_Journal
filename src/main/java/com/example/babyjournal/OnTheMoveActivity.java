package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OnTheMoveActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String babyName;
    private String babyGender;
    private EditText liftHeadEdit;
    private EditText rollOverEdit;
    private EditText findFeetEdit;
    private EditText sitUpEdit;
    private EditText clappedEdit;
    private EditText stoodUpEdit;
    private EditText firstStepsEdit;

    public static final String EXTRA_BABY_GENDER = "com.example.babyjournal.EXTRA_BABY_GENDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onthemove);

        dbHelper = new DatabaseHelper(this);
        


        // Initialize EditText fields
        liftHeadEdit = findViewById(R.id.liftHeadEdit);
        rollOverEdit = findViewById(R.id.rollOverEdit);
        findFeetEdit = findViewById(R.id.findFeetEdit);
        sitUpEdit = findViewById(R.id.sitUpEdit);
        clappedEdit = findViewById(R.id.clappedEdit);
        stoodUpEdit = findViewById(R.id.stoodUpEdit);
        firstStepsEdit = findViewById(R.id.firstStepsEdit);

        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);
        babyGender = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_GENDER);

        // Set the activity title and labels
        TextView titleTextView = findViewById(R.id.onthemovetitle);
        titleTextView.setText(babyName + " moving about");

        if (babyName != null) {
            TextView liftHeadLabel = findViewById(R.id.liftHeadLabel);
            String pronoun = (babyGender != null && babyGender.equals("Boy")) ? "his" : "her";
            liftHeadLabel.setText("First time " + babyName + " lifted " + pronoun + " head:");
            
            TextView rollOverLabel = findViewById(R.id.rollOverLabel);
            rollOverLabel.setText("First time " + babyName + " rolled over:");
            
            TextView findFeetLabel = findViewById(R.id.findFeetLabel);
            findFeetLabel.setText("When " + babyName + " found their feet:");
            
            TextView sitUpLabel = findViewById(R.id.sitUpLabel);
            sitUpLabel.setText("First time " + babyName + " sat up alone:");
            
            TextView clappedLabel = findViewById(R.id.clappedLabel);
            clappedLabel.setText("First time " + babyName + " clapped:");
            
            TextView stoodUpLabel = findViewById(R.id.stoodUpLabel);
            stoodUpLabel.setText("First time " + babyName + " stood up:");
            
            TextView firstStepsLabel = findViewById(R.id.firstStepsLabel);
            firstStepsLabel.setText("When " + babyName + " take " + pronoun + " first steps:");

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
        OnTheMoveData data = dbHelper.getOnTheMoveData(babyName);
        if (data != null) {
            liftHeadEdit.setText(data.getLiftHead());
            rollOverEdit.setText(data.getRollOver());
            findFeetEdit.setText(data.getFindFeet());
            sitUpEdit.setText(data.getSitUp());
            clappedEdit.setText(data.getClapped());
            stoodUpEdit.setText(data.getStoodUp());
            firstStepsEdit.setText(data.getFirstSteps());
        }
    }

    private void saveData() {
        String liftHead = liftHeadEdit.getText().toString().trim();
        String rollOver = rollOverEdit.getText().toString().trim();
        String findFeet = findFeetEdit.getText().toString().trim();
        String sitUp = sitUpEdit.getText().toString().trim();
        String clapped = clappedEdit.getText().toString().trim();
        String stoodUp = stoodUpEdit.getText().toString().trim();
        String firstSteps = firstStepsEdit.getText().toString().trim();

        long result = dbHelper.saveOnTheMoveData(babyName, liftHead, rollOver, findFeet, 
                                               sitUp, clapped, stoodUp, firstSteps);
        
        if (result > 0) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}
