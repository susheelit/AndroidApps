package com.aob.aobsalesman.activities.model;

import java.io.Serializable;

public class LeadModal implements Serializable {
    private String lead_id;
    private String agent_email;
    private String project_id;
    private String company_name;
    private String contact_person;
    private String designation;
    private String contact_no;
    private String email_id;
    private String address;
    private String city;
    private String state;
    private String pincode;

    public String getLead_id() {
        return lead_id;
    }

    public String getAgent_email() {
        return agent_email;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getContact_person() {
        return contact_person;
    }

    public String getDesignation() {
        return designation;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getGst_no() {
        return gst_no;
    }

    public String getPan_no() {
        return pan_no;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLead_status() {
        return lead_status;
    }

    public String getType() {
        return type;
    }

    private String gst_no;
    private String pan_no;
    private String instruction;
    private String date;
    private String time;
    private String lead_status;
    private String type;

    public LeadModal(String lead_id, String agent_email, String project_id, String company_name, String contact_person, String designation, String contact_no, String email_id, String address, String city, String state, String pincode, String gst_no, String pan_no, String instruction, String date, String time, String lead_status, String type) {
        this.lead_id = lead_id;
        this.agent_email = agent_email;
        this.project_id = project_id;
        this.company_name = company_name;
        this.contact_person = contact_person;
        this.designation = designation;
        this.contact_no = contact_no;
        this.email_id = email_id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.gst_no = gst_no;
        this.pan_no = pan_no;
        this.instruction = instruction;
        this.date = date;
        this.time = time;
        this.lead_status = lead_status;
        this.type = type;
    }
}
