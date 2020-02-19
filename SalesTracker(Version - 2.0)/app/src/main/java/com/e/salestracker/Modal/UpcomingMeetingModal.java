package com.e.salestracker.Modal;

public class UpcomingMeetingModal {

    private String agent_id;
    private String client_name;
    private String next_meeting;
    private String visit_type;
    private String user_location;
    private String description;
    private String project_name_form;
    private String last_visited;

    public UpcomingMeetingModal(String agent_id, String client_name, String next_meeting, String visit_type, String user_location, String description, String project_name_form, String last_visited) {

        this.agent_id =  agent_id;
        this.client_name = client_name;
        this.next_meeting =  next_meeting;
        this.visit_type =  visit_type;
        this.user_location =  user_location;
        this.description =  description;
        this.project_name_form =  project_name_form;
        this.last_visited =  last_visited;

    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getNext_meeting() {
        return next_meeting;
    }

    public void setNext_meeting(String next_meeting) {
        this.next_meeting = next_meeting;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(String visit_type) {
        this.visit_type = visit_type;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_name_form() {
        return project_name_form;
    }

    public void setProject_name_form(String project_name_form) {
        this.project_name_form = project_name_form;
    }

    public String getLast_visited() {
        return last_visited;
    }

    public void setLast_visited(String last_visited) {
        this.last_visited = last_visited;
    }

}
