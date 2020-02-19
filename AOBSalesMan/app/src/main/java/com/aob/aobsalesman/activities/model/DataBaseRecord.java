package com.aob.aobsalesman.activities.model;

import java.util.ArrayList;

public class DataBaseRecord
{
    static ArrayList<Integer> count1=new ArrayList<>();
    static ArrayList<Integer> userId1=new ArrayList<>();
    static ArrayList<String> userName1=new ArrayList<>();
    static ArrayList<Boolean> status1=new ArrayList<>();
    static ArrayList<String> date1=new ArrayList<>();
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DataBaseRecord() {
    }

    public DataBaseRecord(String userId, String userName, boolean status, String date) {
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.date = date;
    }

    private boolean status;
    private String date;

}