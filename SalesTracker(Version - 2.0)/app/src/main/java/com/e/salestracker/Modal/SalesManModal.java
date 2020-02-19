package com.e.salestracker.Modal;

public class SalesManModal {
    private String EmailId;
    private String Date;
    private String name;

    public String getEmailId() {
        return EmailId;
    }

    public String getDate() {
        return Date;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    public SalesManModal(String emailId, String date, String name, String status) {
        EmailId = emailId;
        Date = date;
        this.name = name;
        this.status = status;
    }

}
