package com.aob.aobsalesman.activities.model;

public class Earning_Data  {

    private String heading;
    private String body_text;
    private String amount;
    private String date;
    private String time;
    private String user_name;
    private String status;

    public String getHeading() {
        return heading;
    }

    public String getBody_text() {
        return body_text;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getStatus() {
        return status;
    }

    private String user_email;

    public Earning_Data(String heading, String body_text, String amount, String date, String time, String user_name, String user_email, String status) {
        this.heading = heading;
        this.body_text = body_text;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.user_name = user_name;
        this.user_email = user_email;
        this.status = status;
    }

}
