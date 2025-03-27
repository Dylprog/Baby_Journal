package com.example.babyjournal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.content.Intent;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import android.view.View;

public class BabyProfileDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_BABY_NAME = "baby_name";
    public static final String EXTRA_BABY_ID = "baby_id";
    public static final String EXTRA_BABY_GENDER = "baby_gender";

    private final String[] categories = {
        "Parents",
        "Family tree",
        "Name",
        "Birth",
        "Telling everyone",
        "Baby's first week",
        "Bathtime",
        "On the move",
        "First words",
        "Foods"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_profile_details);

        String babyName = getIntent().getStringExtra(EXTRA_BABY_NAME);
        String babyGender = getIntent().getStringExtra(EXTRA_BABY_GENDER);
        TextView titleTextView = findViewById(R.id.babyNameTitle);
        titleTextView.setText(babyName);

        // Set background color based on gender
        View rootView = findViewById(android.R.id.content);
        if ("Boy".equalsIgnoreCase(babyGender)) {
            rootView.setBackgroundColor(0xFF89CFF0); // Baby blue color
        } else if ("Girl".equalsIgnoreCase(babyGender)) {
            rootView.setBackgroundColor(0xFFF4C2C2); // Baby pink color
        }

        // Initialize ListView with custom adapter
        ListView categoryList = findViewById(R.id.categoryListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                
                // Set text color to make it more readable against colored background
                ((TextView) view).setTextColor(0xFF000000); // Black color
                
                // Match the background color of list items with the main background
                if ("Boy".equalsIgnoreCase(babyGender)) {
                    view.setBackgroundColor(0xFF89CFF0); // Baby blue color
                } else if ("Girl".equalsIgnoreCase(babyGender)) {
                    view.setBackgroundColor(0xFFF4C2C2); // Baby pink color
                }
                
                return view;
            }
        };
        categoryList.setAdapter(adapter);

        // Handle item clicks
        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories[position];
            
            try {
                // Convert category name to activity class name (capitalize each word, remove special characters)
                String className = Stream.of(selectedCategory.split(" "))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining(""))
                    .replaceAll("[^a-zA-Z]", "") + "Activity";
                
                Class<?> activityClass = Class.forName(getPackageName() + "." + className);
                
                Intent intent = new Intent(this, activityClass);
                intent.putExtra(EXTRA_BABY_NAME, babyName);
                intent.putExtra(EXTRA_BABY_GENDER, babyGender);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                Toast.makeText(this, "Selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
            }
        });

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());
    }
}