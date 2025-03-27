package com.example.babyjournal;

public class ParentsData {
    private String motherName;
    private String fatherName;

    public ParentsData(String motherName, String fatherName) {
        this.motherName = motherName;
        this.fatherName = fatherName;
    }

    public String getMotherName() { return motherName; }
    public String getFatherName() { return fatherName; }
} 