package com.example.babyjournal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.LinearLayout;
import java.util.List;

public class FamilyTreeActivity extends AppCompatActivity {
    private EditText motherInput, fatherInput;
    private EditText maternalGrandmaInput, maternalGrandpaInput;
    private EditText paternalGrandmaInput, paternalGrandpaInput;
    private LinearLayout siblingsContainer, auntiesUnclesContainer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familytree);

        // Get the baby name from the intent
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);

        // Set the activity title
        if (babyName != null) {
            setTitle(babyName + "'s Family Tree");

            // Set the birth details text
            TextView birthDetailsText = findViewById(R.id.FamilyDetailsText);
            birthDetailsText.setText(babyName + "'s Family");

        }



        // Initialize family member input fields
        initializeViews();
        
        // Set up add sibling button
        Button addSiblingButton = findViewById(R.id.addSiblingButton);
        addSiblingButton.setOnClickListener(v -> addNewFamilyMember(siblingsContainer, "Sibling"));

        // Set up add auntie/uncle button
        Button addAuntieUncleButton = findViewById(R.id.addAuntieUncleButton);
        addAuntieUncleButton.setOnClickListener(v -> addNewFamilyMember(auntiesUnclesContainer, "Auntie/Uncle"));

        // Set up save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveFamilyTree());

        // Set up return button
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFamilyTree();
    }

    private void initializeViews() {
        // Initialize parent inputs
        motherInput = findViewById(R.id.motherInput);
        fatherInput = findViewById(R.id.fatherInput);

        // Initialize grandparent inputs
        maternalGrandmaInput = findViewById(R.id.maternalGrandmaInput);
        maternalGrandpaInput = findViewById(R.id.maternalGrandpaInput);
        paternalGrandmaInput = findViewById(R.id.paternalGrandmaInput);
        paternalGrandpaInput = findViewById(R.id.paternalGrandpaInput);

        // Initialize containers for dynamic family members
        siblingsContainer = findViewById(R.id.siblingsContainer);
        auntiesUnclesContainer = findViewById(R.id.auntiesUnclesContainer);
    }

    private void addNewFamilyMember(LinearLayout container, String hint) {
        EditText newMemberInput = new EditText(this);
        newMemberInput.setHint(hint + " Name");
        container.addView(newMemberInput);
    }

    private void saveFamilyTree() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);
        
        // Delete existing family members before saving new ones
        dbHelper.deleteFamilyMembers(babyName);
        
        // Save parents
        if (!motherInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Mother", motherInput.getText().toString());
        }
        if (!fatherInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Father", fatherInput.getText().toString());
        }
        
        // Save maternal grandparents
        if (!maternalGrandmaInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Maternal Grandmother", maternalGrandmaInput.getText().toString());
        }
        if (!maternalGrandpaInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Maternal Grandfather", maternalGrandpaInput.getText().toString());
        }
        
        // Save paternal grandparents
        if (!paternalGrandmaInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Paternal Grandmother", paternalGrandmaInput.getText().toString());
        }
        if (!paternalGrandpaInput.getText().toString().isEmpty()) {
            dbHelper.addFamilyMember(babyName, "Paternal Grandfather", paternalGrandpaInput.getText().toString());
        }
        
        // Save siblings
        for (int i = 0; i < siblingsContainer.getChildCount(); i++) {
            View view = siblingsContainer.getChildAt(i);
            if (view instanceof EditText) {
                String siblingName = ((EditText) view).getText().toString();
                if (!siblingName.isEmpty()) {
                    dbHelper.addFamilyMember(babyName, "Sibling", siblingName);
                }
            }
        }
        
        // Save aunties/uncles
        for (int i = 0; i < auntiesUnclesContainer.getChildCount(); i++) {
            View view = auntiesUnclesContainer.getChildAt(i);
            if (view instanceof EditText) {
                String auntieUncleName = ((EditText) view).getText().toString();
                if (!auntieUncleName.isEmpty()) {
                    dbHelper.addFamilyMember(babyName, "Auntie/Uncle", auntieUncleName);
                }
            }
        }
        
        // Show success message
        android.widget.Toast.makeText(this, "Family tree saved successfully!", 
            android.widget.Toast.LENGTH_SHORT).show();
    }

    private void loadFamilyTree() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String babyName = getIntent().getStringExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME);
        List<FamilyMember> familyMembers = dbHelper.getFamilyMembers(babyName);

        // Clear existing dynamic views
        siblingsContainer.removeAllViews();
        auntiesUnclesContainer.removeAllViews();

        for (FamilyMember member : familyMembers) {
            switch (member.getRelation()) {
                case "Mother":
                    motherInput.setText(member.getName());
                    break;
                case "Father":
                    fatherInput.setText(member.getName());
                    break;
                case "Maternal Grandmother":
                    maternalGrandmaInput.setText(member.getName());
                    break;
                case "Maternal Grandfather":
                    maternalGrandpaInput.setText(member.getName());
                    break;
                case "Paternal Grandmother":
                    paternalGrandmaInput.setText(member.getName());
                    break;
                case "Paternal Grandfather":
                    paternalGrandpaInput.setText(member.getName());
                    break;
                case "Sibling":
                    addNewFamilyMember(siblingsContainer, "Sibling");
                    ((EditText) siblingsContainer.getChildAt(siblingsContainer.getChildCount() - 1))
                        .setText(member.getName());
                    break;
                case "Auntie/Uncle":
                    addNewFamilyMember(auntiesUnclesContainer, "Auntie/Uncle");
                    ((EditText) auntiesUnclesContainer.getChildAt(auntiesUnclesContainer.getChildCount() - 1))
                        .setText(member.getName());
                    break;
            }
        }
        dbHelper.close();
    }
}
