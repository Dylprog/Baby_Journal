package com.example.babyjournal;

public class FirstWordsData {
    private String firstSmile;
    private String firstWords;
    private String makesLaugh;

    public FirstWordsData(String firstSmile, String firstWords, String makesLaugh) {
        this.firstSmile = firstSmile;
        this.firstWords = firstWords;
        this.makesLaugh = makesLaugh;
    }

    public String getFirstSmile() { return firstSmile; }
    public String getFirstWords() { return firstWords; }
    public String getMakesLaugh() { return makesLaugh; }
} 