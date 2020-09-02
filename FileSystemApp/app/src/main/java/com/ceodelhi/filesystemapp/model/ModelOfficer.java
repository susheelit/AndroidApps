package com.ceodelhi.filesystemapp.model;

import java.io.Serializable;

public class ModelOfficer implements Serializable {

    private String Officer_Name;
    private String Actual_Designation;
    private String Mobile_Number;
    private boolean isSelected;

    public ModelOfficer(String officer_Name, String actual_Designation, String mobile_Number) {
        Officer_Name = officer_Name;
        Actual_Designation = actual_Designation;
        Mobile_Number = mobile_Number;
    }

    public String getOfficer_Name() {
        return Officer_Name;
    }

    public void setOfficer_Name(String officer_Name) {
        Officer_Name = officer_Name;
    }

    public String getActual_Designation() {
        return Actual_Designation;
    }

    public void setActual_Designation(String actual_Designation) {
        Actual_Designation = actual_Designation;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
