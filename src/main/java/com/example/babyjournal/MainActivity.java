package com.example.babyjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.content.Context;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private ListView babyListView;
    private BabyProfileAdapter adapter;
    private List<BabyProfile> babyProfiles;
    private List<BabyProfile> displayedProfiles;
    private boolean showingAllProfiles = false;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        dbHelper = new DatabaseHelper(this);
        babyListView = findViewById(R.id.babyListView);
        babyProfiles = new ArrayList<>();
        displayedProfiles = new ArrayList<>();
        adapter = new BabyProfileAdapter(this, displayedProfiles);
        babyListView.setAdapter(adapter);

        // Add button click listener
        Button newBabyButton = findViewById(R.id.newBabyButton);
        newBabyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewBabyProfileActivity.class);
                startActivity(intent);
            }
        });

        babyListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 5 && !showingAllProfiles) {
                // Clicked on "More" item
                showingAllProfiles = true;
                updateDisplayedProfiles();
            } else {
                BabyProfile selectedBaby = displayedProfiles.get(position);
                Intent intent = new Intent(MainActivity.this, BabyProfileDetailsActivity.class);
                intent.putExtra(BabyProfileDetailsActivity.EXTRA_BABY_NAME, selectedBaby.getName());
                intent.putExtra(BabyProfileDetailsActivity.EXTRA_BABY_ID, selectedBaby.getId());
                intent.putExtra(BabyProfileDetailsActivity.EXTRA_BABY_GENDER, selectedBaby.getGender());
                startActivity(intent);
            }
        });

        // Add long click listener for deletion
        babyListView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position < displayedProfiles.size() && displayedProfiles.get(position).getId() != -1) {
                BabyProfile selectedBaby = displayedProfiles.get(position);
                // Show confirmation dialog
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Profile")
                    .setMessage("Are you sure you want to delete " + selectedBaby.getName() + "'s profile?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.deleteBabyProfile(selectedBaby.getId());
                        updateBabyList();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
                return true; // Consume the long click
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the baby list when ret›urning to this activity
        updateBabyList();
    }

    private void updateBabyList() {
        babyProfiles.clear();
        babyProfiles.addAll(dbHelper.getAllBabyProfiles());
        showingAllProfiles = false;
        updateDisplayedProfiles();
    }

    private void updateDisplayedProfiles() {
        displayedProfiles.clear();
        if (!showingAllProfiles && babyProfiles.size() > 5) {
            // Show only last 5 profiles plus "More" option
            for (int i = Math.max(0, babyProfiles.size() - 5); i < babyProfiles.size(); i++) {
                displayedProfiles.add(babyProfiles.get(i));
            }
            displayedProfiles.add(new BabyProfile(-1, "More...", "")); // Add "More" option
        } else {
            // Show all profiles
            displayedProfiles.addAll(babyProfiles);
        }
        adapter.notifyDataSetChanged();
    }

    private class BabyProfileAdapter extends ArrayAdapter<BabyProfile> {
        public BabyProfileAdapter(Context context, List<BabyProfile> profiles) {
            super(context, android.R.layout.simple_list_item_1, profiles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            BabyProfile profile = getItem(position);

            // Set background colour based on gender
            if (profile != null && profile.getId() != -1) {  // Check if not "More" item
                if ("Boy".equalsIgnoreCase(profile.getGender())) {
                    view.setBackgroundColor(0xFF89CFF0); // Baby blue colour
                } else if ("Girl".equalsIgnoreCase(profile.getGender())) {
                    view.setBackgroundColor(0xFFF4C2C2);  // Baby pink colour
                }
            } else {
                // Reset background for "More" item
                view.setBackgroundColor(0xFFFFFFFF);
            }

            return view;
        }
    }
}