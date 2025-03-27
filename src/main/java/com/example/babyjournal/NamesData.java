package com.example.babyjournal;

public class NamesData {
    private String nameReason;
    private String altName1;
    private String altName2;
    private String altName3;

    public NamesData(String nameReason, String altName1, String altName2, String altName3) {
        this.nameReason = nameReason;
        this.altName1 = altName1;
        this.altName2 = altName2;
        this.altName3 = altName3;
    }

    // Getters
    public String getNameReason() { return nameReason; }
    public String getAltName1() { return altName1; }
    public String getAltName2() { return altName2; }
    public String getAltName3() { return altName3; }
} 