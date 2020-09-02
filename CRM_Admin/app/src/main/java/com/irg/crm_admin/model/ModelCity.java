package com.irg.crm_admin.model;

public class ModelCity {
    private String city_id;
    private String city_name;
    private String state_id;
    private String  dist_id;
    private String  isActive;

    public ModelCity(String city_id, String city_name, String state_id, String dist_id) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.state_id = state_id;
        this.dist_id = dist_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
