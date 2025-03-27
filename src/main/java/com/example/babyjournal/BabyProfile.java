package com.example.babyjournal;

public class BabyProfile {
    private int id;
    private String name;
    private String gender;

    public BabyProfile(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }

    @Override
    public String toString() {
        return name + " (" + gender + ")";
    }
} 