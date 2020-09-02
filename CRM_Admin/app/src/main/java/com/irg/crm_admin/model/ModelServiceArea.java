package com.irg.crm_admin.model;

public class ModelServiceArea {

    private String stateName;
    private String stateId;
    private String districtName;
    private String districtId;
    private String cityName;
    private String cityId;
    private String areaname;
    private String areaId;
    private String serviceArea;
    private String zone_name;
    private String isActive;

    // state
    public ModelServiceArea(String serviceArea, String stateName, String stateId, String isActive) {
        this.serviceArea = serviceArea;
        this.stateName = stateName;
        this.stateId = stateId;
        this.isActive = isActive;
    }

    // district
    public ModelServiceArea(String stateName, String stateId, String districtName, String districtId, String isActive) {
        this.stateName = stateName;
        this.stateId = stateId;
        this.districtName = districtName;
        this.districtId = districtId;
        this.isActive = isActive;
    }

    // city
    public ModelServiceArea(String stateName, String stateId, String districtName, String districtId,
                            String cityName, String cityId, String isActive) {
        this.stateName = stateName;
        this.stateId = stateId;
        this.districtName = districtName;
        this.districtId = districtId;
        this.cityName = cityName;
        this.cityId = cityId;
        this.isActive = isActive;
    }

    // area
    public ModelServiceArea(String stateName, String stateId, String districtName, String districtId,
                            String cityName, String cityId, String areaname, String areaId, String zone_name, String isActive ) {
        this.stateName = stateName;
        this.stateId = stateId;
        this.districtName = districtName;
        this.districtId = districtId;
        this.cityName = cityName;
        this.cityId = cityId;
        this.areaname = areaname;
        this.areaId = areaId;
        this.zone_name = zone_name;
        this.isActive = isActive;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
