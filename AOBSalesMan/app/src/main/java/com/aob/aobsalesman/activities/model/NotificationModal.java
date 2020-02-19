package com.aob.aobsalesman.activities.model;

public class NotificationModal {
    private String header;
    private String body;

    public NotificationModal(String header, String body, String date) {
        this.header = header;
        this.body = body;
        Date = date;
    }

    private String Date;

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return Date;
    }
}
