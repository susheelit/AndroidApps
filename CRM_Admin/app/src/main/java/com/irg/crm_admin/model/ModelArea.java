package com.irg.crm_admin.model;

public class ModelArea {
    private String area_id;
    private String area_name;
    private String zone_name;
    private String state_id;
    private String dist_id;
    private String city_id;
    private String isActive;

    public ModelArea(String area_id, String area_name, String zone_name, String state_id, String dist_id, String city_id, String isActive) {
        this.area_id = area_id;
        this.area_name = area_name;
        this.zone_name = zone_name;
        this.state_id = state_id;
        this.dist_id = dist_id;
        this.city_id = city_id;
        this.isActive = isActive;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
