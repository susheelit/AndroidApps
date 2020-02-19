package com.aob.aobsalesman.activities.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company_Data implements Serializable{


    @SerializedName("project_id")
  private String project_id;

    @SerializedName("project_name")
  private String project_name;

    @SerializedName("description")
  private String description;

    @SerializedName("views")
  private String views;

  @SerializedName("incentive")
  private int incentive;

    @SerializedName("incentive_earned_total")
    private int incentive_earned_total;


    @SerializedName("sales_criteria")
    private String sales_criteria;

    @SerializedName("salesman_working_on")
    private String salesman_working_on;

    @SerializedName("project_status")
    private String project_status;

    @SerializedName("city")
    private List<String> city = new ArrayList<String>();

    @SerializedName("state")
    private List<String> state = new ArrayList<String>();

    @SerializedName("industries")
    private List<String> industries = new ArrayList<String>();

    @SerializedName("logo_url")
    private String logo_url;


    @SerializedName("project_title")
    private String project_title;


    @SerializedName("sales_type")
    private List<String> sales_type = new ArrayList<String>();

    @SerializedName("project_manager")
    private String project_manager;


    @SerializedName("price")
    private String price;

    @SerializedName("where_sell")
    private String where_sell;

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setWhere_sell(String where_sell) {
        this.where_sell = where_sell;
    }

    public void setSale_commission_freelancer(String sale_commission_freelancer) {
        this.sale_commission_freelancer = sale_commission_freelancer;
    }

    public void setLead_commission_freelancer(String lead_commission_freelancer) {
        this.lead_commission_freelancer = lead_commission_freelancer;
    }

    @SerializedName("sale_commission_freelancer")
    private String sale_commission_freelancer;

    @SerializedName("lead_commission_freelancer")
    private String lead_commission_freelancer;

    public String getProject_title() {
        return project_title;
    }

    public String getPrice() {
        return price;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getWhere_sell() {
        return where_sell;
    }

    public String getSale_commission_freelancer() {
        return sale_commission_freelancer;
    }

    public String getLead_commission_freelancer() {
        return lead_commission_freelancer;
    }

    public int getIncentive ()
    {
        return incentive;
    }

    public void setIncentive (int incentive)
    {
        this.incentive = incentive;
    }


    public String getProjectTitle ()
    {
        return project_title;
    }

    public void setProjectTitle (String project_title)
    {
        this.project_title = project_title;
    }


    public String getSales_criteria ()
    {
        return sales_criteria;
    }

    public void setSales_criteria (String sales_criteria)
    {
        this.sales_criteria = sales_criteria;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public int getIncentive_earned_total ()
    {
        return incentive_earned_total;
    }

    public void setIncentive_earned_total (int incentive_earned_total)
    {
        this.incentive_earned_total = incentive_earned_total;
    }

    public String getSalesman_working_on ()
    {
        return salesman_working_on;
    }

    public void setSalesman_working_on (String salesman_working_on)
    {
        this.salesman_working_on = salesman_working_on;
    }

    public String getProject_id ()
    {
        return project_id;
    }

    public void setProject_id (String project_id)
    {
        this.project_id = project_id;
    }

    public String getProject_status ()
    {
        return project_status;
    }

    public void setProject_status (String project_status)
    {
        this.project_status = project_status;
    }

    public String getProject_name ()
    {
        return project_name;
    }

    public void setProject_name (String project_name)
    {
        this.project_name = project_name;
    }

    public String getViews ()
    {
        return views;
    }

    public void setViews (String views)
    {
        this.views = views;
    }


    public void setCity(List<String> city) {
        this.city = city;
    }

    public List<String> getCity()
    {
        return city;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public List<String> getState()
    {
        return state;
    }

    public String getLogo_url ()
    {
        return logo_url;
    }

    public void setLogo_url (String incentive)
    {
        this.logo_url = logo_url;
    }

    public void setIndustries(List<String> industries) {
        this.industries = industries;
    }

    public List<String> getIndustries()
    {
        return industries;
    }


    public void setSales_type(List<String> sales_type) {
        this.sales_type = sales_type;
    }

    public List<String> getSales_type()
    {
        return sales_type;
    }

}
