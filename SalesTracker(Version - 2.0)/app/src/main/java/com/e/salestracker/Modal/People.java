package com.e.salestracker.Modal;

import java.io.Serializable;

public class People implements Serializable {

   private String agent_id;
   private String ccid;
   private String checkin_time;
   private String checkin_date;
   private String checkout_time;
   private String checkout_date;
   private String project_name_form;
   private String department;

    public String getProject_name_form() {
        return project_name_form;
    }

    public String getDepartment() {
        return department;
    }

    public String getDsr_status() {
        return dsr_status;
    }

    private String visited;
    private String dsr_status;

    public String getAgent_id() {
        return agent_id;
    }

    public String getCcid() {
        return ccid;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public String getVisited() {
        return visited;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getAgent_cc_status() {
        return agent_cc_status;
    }

    public String getImage() {
        return image;
    }

    public People(String agent_id, String ccid, String checkin_time, String checkin_date, String checkout_time, String checkout_date, String visited, String client_name, String agent_cc_status, String image,String dsr_status,String project_name_form,String department) {
        this.agent_id = agent_id;
        this.ccid = ccid;
        this.checkin_time = checkin_time;
        this.checkin_date = checkin_date;
        this.checkout_time = checkout_time;
        this.checkout_date = checkout_date;
        this.visited = visited;
        this.client_name = client_name;
        this.agent_cc_status = agent_cc_status;
        this.image = image;
        this.dsr_status = dsr_status;
        this.project_name_form = project_name_form;
        this.department = department;
    }
    private String client_name;
    private String agent_cc_status;
    private String image;

}
