package com.e.salestracker.Modal;

public class PersonModal {
    private String selectedPproject;
    private String selectedProjectVisit;
    private String agent_id;
    private String agent_name;
    private String first_checkin;
    private String last_checkout;
    private String visit_total;
    private String last_location;
    private String mobile_no;
    private String project_list;
    private String project_visit;
    private String agent_profile;

    public PersonModal(String agent_id, String agent_name, String first_checkin, String last_checkout, String visit_total, String last_location, String mobile_no, String project_list, String project_visit, String agent_profile) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.first_checkin = first_checkin;
        this.last_checkout = last_checkout;
        this.visit_total = visit_total;
        this.last_location = last_location;
        this.mobile_no = mobile_no;
        this.project_list = project_list;
        this.project_visit = project_visit;
        this.agent_profile = agent_profile;

    }

    public PersonModal(String selectedPproject,String selectedProjectVisit,String agent_id, String agent_name, String first_checkin, String last_checkout, String visit_total, String last_location, String mobile_no, String project_list, String project_visit, String agent_profile) {
        this.selectedPproject = selectedPproject;
        this.selectedProjectVisit = selectedProjectVisit;
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.first_checkin = first_checkin;
        this.last_checkout = last_checkout;
        this.visit_total = visit_total;
        this.last_location = last_location;
        this.mobile_no = mobile_no;
        this.project_list = project_list;
        this.project_visit = project_visit;
        this.agent_profile = agent_profile;

    }

    public String getSelectedPproject() {
        return selectedPproject;
    }

    public void setSelectedPproject(String selectedPproject) {
        this.selectedPproject = selectedPproject;
    }

    public String getSelectedProjectVisit() {
        return selectedProjectVisit;
    }

    public void setSelectedProjectVisit(String selectedProjectVisit) {
        this.selectedProjectVisit = selectedProjectVisit;
    }

    public String getAgent_id() {
        return agent_id;
    }


    public String getAgent_name() {
        return agent_name;
    }

    public String getFirst_checkin() {
        return first_checkin;
    }

    public String getLast_checkout() {
        return last_checkout;
    }

    public String getVisit_total() {
        return visit_total;
    }

    public String getLast_location() {
        return last_location;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getProject_list() {
        return project_list;
    }
    public String getProject_visit() {
        return project_visit;
    }

    public String getAgent_profile() {
        return agent_profile;
    }
}
