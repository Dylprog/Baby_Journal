package com.example.babyjournal;

public class BathtimeData {
    private String firstBath;
    private String bathExperience;
    private String bathGames;
    private String bathToys;

    public BathtimeData(String firstBath, String bathExperience, String bathGames, String bathToys) {
        this.firstBath = firstBath;
        this.bathExperience = bathExperience;
        this.bathGames = bathGames;
        this.bathToys = bathToys;
    }

    public String getFirstBath() { return firstBath; }
    public String getBathExperience() { return bathExperience; }
    public String getBathGames() { return bathGames; }
    public String getBathToys() { return bathToys; }
} 