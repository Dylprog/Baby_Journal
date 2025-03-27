package com.example.babyjournal;

public class OnTheMoveData {
    private String liftHead;
    private String rollOver;
    private String findFeet;
    private String sitUp;
    private String clapped;
    private String stoodUp;
    private String firstSteps;

    public OnTheMoveData(String liftHead, String rollOver, String findFeet, 
                        String sitUp, String clapped, String stoodUp, String firstSteps) {
        this.liftHead = liftHead;
        this.rollOver = rollOver;
        this.findFeet = findFeet;
        this.sitUp = sitUp;
        this.clapped = clapped;
        this.stoodUp = stoodUp;
        this.firstSteps = firstSteps;
    }

    public String getLiftHead() { return liftHead; }
    public String getRollOver() { return rollOver; }
    public String getFindFeet() { return findFeet; }
    public String getSitUp() { return sitUp; }
    public String getClapped() { return clapped; }
    public String getStoodUp() { return stoodUp; }
    public String getFirstSteps() { return firstSteps; }
} 