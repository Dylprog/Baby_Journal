package com.example.babyjournal;

public class BirthDetails {
    private String birthDate;
    private String birthWeight;
    private String birthFeatures;
    private String birthAttendees;

    public BirthDetails(String birthDate, String birthWeight, String birthFeatures, String birthAttendees) {
        this.birthDate = birthDate;
        this.birthWeight = birthWeight;
        this.birthFeatures = birthFeatures;
        this.birthAttendees = birthAttendees;
    }

    public String getBirthDate() { return birthDate; }
    public String getBirthWeight() { return birthWeight; }
    public String getBirthFeatures() { return birthFeatures; }
    public String getBirthAttendees() { return birthAttendees; }
} 