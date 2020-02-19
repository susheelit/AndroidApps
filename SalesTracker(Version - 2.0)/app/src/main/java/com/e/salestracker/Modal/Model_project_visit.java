package com.e.salestracker.Modal;

public class Model_project_visit {

    private String project_name;
    private String visit_count;

    public Model_project_visit(String project_name, String visit_count) {
        this.project_name = project_name;
        this.visit_count = visit_count;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }
}
