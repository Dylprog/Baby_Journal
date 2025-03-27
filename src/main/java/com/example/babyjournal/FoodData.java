package com.example.babyjournal;

public class FoodData {
    private String foodName;
    private boolean tried;

    public FoodData(String foodName, boolean tried) {
        this.foodName = foodName;
        this.tried = tried;
    }

    public String getFoodName() { return foodName; }
    public boolean hasTried() { return tried; }
} 