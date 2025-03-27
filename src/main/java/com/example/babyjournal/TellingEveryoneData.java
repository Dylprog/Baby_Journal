package com.example.babyjournal;

public class TellingEveryoneData {
    private String mamFoundOut;
    private String dadFoundOut;
    private String mamFirstTold;
    private String dadFirstTold;
    private String peopleReactions;

    public TellingEveryoneData(String mamFoundOut, String dadFoundOut, String mamFirstTold,
                              String dadFirstTold, String peopleReactions) {
        this.mamFoundOut = mamFoundOut;
        this.dadFoundOut = dadFoundOut;
        this.mamFirstTold = mamFirstTold;
        this.dadFirstTold = dadFirstTold;
        this.peopleReactions = peopleReactions;
    }


    // Getters
    public String getMamFoundOut() { return mamFoundOut; }
    public String getDadFoundOut() { return dadFoundOut; }
    public String getMamFirstTold() { return mamFirstTold; }
    public String getDadFirstTold() { return dadFirstTold; }
    public String getPeopleReactions() { return peopleReactions; }

    // Setters
    public void setMamFoundOut(String mamFoundOut) { this.mamFoundOut = mamFoundOut; }
    public void setDadFoundOut(String dadFoundOut) { this.dadFoundOut = dadFoundOut; }
    public void setMamFirstTold(String mamFirstTold) { this.mamFirstTold = mamFirstTold; }
    public void setDadFirstTold(String dadFirstTold) { this.dadFirstTold = dadFirstTold; }
    public void setPeopleReactions(String peopleReactions) { this.peopleReactions = peopleReactions; }
} 