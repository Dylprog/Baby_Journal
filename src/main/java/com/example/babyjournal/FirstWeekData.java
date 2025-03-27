package com.example.babyjournal;

public class FirstWeekData {
    private String homecomingDate;
    private String firstAddress;
    private String firstVisitors;

    public FirstWeekData(String homecomingDate, String firstAddress, String firstVisitors) {
        this.homecomingDate = homecomingDate;
        this.firstAddress = firstAddress;
        this.firstVisitors = firstVisitors;
    }

    public String getHomecomingDate() { return homecomingDate; }
    public String getFirstAddress() { return firstAddress; }
    public String getFirstVisitors() { return firstVisitors; }
} 