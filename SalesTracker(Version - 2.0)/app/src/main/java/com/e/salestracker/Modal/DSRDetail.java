package com.e.salestracker.Modal;

import java.io.Serializable;

public class DSRDetail implements Serializable {
    private String dsr_id;
    private String agent_id;
    private String ccid;
    private String contact_person;
    private String designation;
    private String phone;
    private String email;
    private String address;
    private String description;
    private String date;
    private String project_name;
    private String company_name;
    private String time;

    public String getDsr_id() {
        return dsr_id;
    }
    public String getAgent_id() {
        return agent_id;
    }

    public String getCcid() {
        return ccid;
    }

    public String getContact_person() {
        return contact_person;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public DSRDetail(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getTime() {
        return time;
    }

    public String getDsr_status() {
        return dsr_status;
    }

    public String getType() {
        return type;
    }

    public DSRDetail(String dsr_id, String agent_id, String ccid, String contact_person, String designation, String phone, String email, String address, String description, String date, String project_name, String company_name, String time, String dsr_status, String type) {
        this.dsr_id = dsr_id;
        this.agent_id = agent_id;
        this.ccid = ccid;
        this.contact_person = contact_person;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.date = date;
        this.project_name = project_name;
        this.company_name = company_name;
        this.time = time;
        this.dsr_status = dsr_status;
        this.type = type;
    }

    private String dsr_status;
    private String type;
}

