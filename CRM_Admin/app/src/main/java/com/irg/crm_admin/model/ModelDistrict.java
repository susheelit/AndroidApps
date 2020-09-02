package com.irg.crm_admin.model;

public class ModelDistrict {

    private String dist_id;
    private String state_id;
    private String dist_name;
    private String isActive;

    public ModelDistrict(String dist_id, String dist_name, String state_id) {
        this.dist_id = dist_id;
        this.dist_name = dist_name;
        this.state_id = state_id;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
