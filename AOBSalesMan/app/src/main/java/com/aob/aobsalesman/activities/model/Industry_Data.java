package com.aob.aobsalesman.activities.model;

import com.google.gson.annotations.SerializedName;

public class Industry_Data {

    private String industry_name;

    private boolean selected;

    public String getIndustry_name ()
    {
        return industry_name;
    }

    public void setIndustry_name (String industry_name)
    {
        this.industry_name = industry_name;
    }

    public Boolean getSelected ()
    {
        return selected;
    }

    public void setName (Boolean selected)
    {
        this.selected = selected;
    }



}
