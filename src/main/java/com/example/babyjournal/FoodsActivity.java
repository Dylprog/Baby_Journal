package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String babyName;
    private Map<String, RadioGroup> foodRadioGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        dbHelper = new DatabaseHelper(this);
        foodRadioGroups = new HashMap<>();
        
        // Get the baby name from the intent
        babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Initialize radio groups map
        initializeFoodRadioGroups();

        // Set the activity title and update intro text
        if (babyName != null) {
            setTitle(babyName + "'s Foods");
            TextView introText = findViewById(R.id.foodsIntroText);
            introText.setText("Does " + babyName + " like these foods?");

            // Load existing food preferences
            loadFoodPreferences();
        }

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveAllFoodPreferences());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    private void initializeFoodRadioGroups() {
        foodRadioGroups.put("Banana", findViewById(R.id.bananaRadioGroup));
        foodRadioGroups.put("Yogurt", findViewById(R.id.yogurtRadioGroup));
        foodRadioGroups.put("Porridge", findViewById(R.id.porridgeRadioGroup));
        foodRadioGroups.put("Lentils", findViewById(R.id.lentilsRadioGroup));
        foodRadioGroups.put("Toast", findViewById(R.id.toastRadioGroup));
        foodRadioGroups.put("Egg", findViewById(R.id.eggRadioGroup));
        foodRadioGroups.put("Pasta", findViewById(R.id.pastaRadioGroup));
        foodRadioGroups.put("Cereal", findViewById(R.id.cerealRadioGroup));
        foodRadioGroups.put("Cheese", findViewById(R.id.cheeseRadioGroup));
        foodRadioGroups.put("Chicken", findViewById(R.id.chickenRadioGroup));
        foodRadioGroups.put("Rice", findViewById(R.id.riceRadioGroup));
    }

    private void loadFoodPreferences() {
        List<FoodData> preferences = dbHelper.getFoodPreferences(babyName);
        
        for (FoodData food : preferences) {
            RadioGroup radioGroup = foodRadioGroups.get(food.getFoodName());
            if (radioGroup != null) {
                // Select Yes if tried, No if not tried
                int radioButtonId = food.hasTried() ? 
                    radioGroup.getChildAt(0).getId() : // First radio button (Yes)
                    radioGroup.getChildAt(1).getId(); // Second radio button (No)
                radioGroup.check(radioButtonId);
            }
        }
    }

    private void saveAllFoodPreferences() {
        boolean success = true;

        for (Map.Entry<String, RadioGroup> entry : foodRadioGroups.entrySet()) {
            String foodName = entry.getKey();
            RadioGroup radioGroup = entry.getValue();
            
            // Get selected radio button ID
            int selectedId = radioGroup.getCheckedRadioButtonId();
            
            // If nothing selected, skip this food
            if (selectedId == -1) continue;
            
            // Determine if food was tried based on which radio button is selected
            boolean tried = selectedId == radioGroup.getChildAt(0).getId(); // First radio button is Yes
            
            long result = dbHelper.saveFoodPreference(babyName, foodName, tried);
            if (result <= 0) success = false;
        }

        if (success) {
            Toast.makeText(this, "Food preferences saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving some food preferences", Toast.LENGTH_SHORT).show();
        }
    }
}

