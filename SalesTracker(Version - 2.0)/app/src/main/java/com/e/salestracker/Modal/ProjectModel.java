package com.e.salestracker.Modal;

import org.json.JSONArray;

public class ProjectModel {

    private String project_id;
    private String project_name;
    private JSONArray location;

    public ProjectModel(String project_id,String project_name,JSONArray location  ){
        this.project_id = project_id;
        this.project_name = project_name;
        this.location = location;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public JSONArray getLocation() {
        return location;
    }

    public void setLocation(JSONArray location) {
        this.location = location;
    }

}
