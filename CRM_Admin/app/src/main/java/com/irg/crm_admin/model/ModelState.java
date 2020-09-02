package com.irg.crm_admin.model;

public class ModelState {

    private String state_id;
    private String state_name;
    private String isActive;

    public ModelState(String state_id, String state_name) {
        this.state_id = state_id;
        this.state_name = state_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
