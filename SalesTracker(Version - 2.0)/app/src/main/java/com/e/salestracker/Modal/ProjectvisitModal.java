package com.e.salestracker.Modal;

public class ProjectvisitModal {
    private String project_name;
    private String project_visit;

    public String getProject_name() {
        return project_name;
    }

    public String getProject_visit() {
        return project_visit;
    }

    public ProjectvisitModal(String project_name, String project_visit) {
        this.project_name = project_name;
        this.project_visit = project_visit;
    }

}
