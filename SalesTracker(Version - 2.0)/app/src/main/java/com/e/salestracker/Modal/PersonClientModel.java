package com.e.salestracker.Modal;

import java.util.List;

public class PersonClientModel {

    private String agent_id;
    private String agent_name;
    private String cona_cateogry;
    private String cona_value;
    private String collection_value;
    private String order_value;
    private String first_checkin;
    private String last_checkout;
    private String last_location;
    private String date_of_checkin;
    private String visit_total;
    private String mobile_no;
    private String agent_profile;

    private String total_collection_value;
    private String total_order_value;

    private List<Model_cona_cateogry> items_cona_cateogry;


    public PersonClientModel(
            String agent_id,
            String agent_name,
            String first_checkin,
            String last_checkout,
            String last_location,
            String date_of_checkin,
            String visit_total,
            String mobile_no,
            String agent_profile,
            String total_collection_value,
            String total_order_value,
            List<Model_cona_cateogry> items_cona_cateogry) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.cona_cateogry = cona_cateogry;
        this.cona_value = cona_value;
        this.collection_value = collection_value;
        this.order_value = order_value;
        this.first_checkin = first_checkin;
        this.last_checkout = last_checkout;
        this.last_location = last_location;
        this.date_of_checkin = date_of_checkin;
        this.visit_total = visit_total;
        this.mobile_no = mobile_no;
        this.agent_profile = agent_profile;
        this.items_cona_cateogry = items_cona_cateogry;
        this.total_collection_value= total_collection_value;
         this.total_order_value= total_order_value;
    }

    public String getTotal_collection_value() {
        return total_collection_value;
    }

    public void setTotal_collection_value(String total_collection_value) {
        this.total_collection_value = total_collection_value;
    }

    public String getTotal_order_value() {
        return total_order_value;
    }

    public void setTotal_order_value(String total_order_value) {
        this.total_order_value = total_order_value;
    }

    public List<Model_cona_cateogry> getItems_cona_cateogry() {
        return items_cona_cateogry;
    }

    public void setItems_cona_cateogry(List<Model_cona_cateogry> items_cona_cateogry) {
        this.items_cona_cateogry = items_cona_cateogry;
    }

    public void PersonClientModel(String agent_id,
                                  String agent_name,
                                  String first_checkin,
                                  String last_checkout,
                                  String last_location,
                                  String date_of_checkin,
                                  String visit_total,
                                  String mobile_no,
                                  String agent_profile
                                  ) {

        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.first_checkin = first_checkin;
        this.last_checkout = last_checkout;
        this.last_location = last_location;
        this.date_of_checkin = date_of_checkin;
        this.visit_total = visit_total;
        this.mobile_no = mobile_no;
        this.agent_profile = agent_profile;


    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getCona_cateogry() {
        return cona_cateogry;
    }

    public void setCona_cateogry(String cona_cateogry) {
        this.cona_cateogry = cona_cateogry;
    }

    public String getCona_value() {
        return cona_value;
    }

    public void setCona_value(String cona_value) {
        this.cona_value = cona_value;
    }

    public String getCollection_value() {
        return collection_value;
    }

    public void setCollection_value(String collection_value) {
        this.collection_value = collection_value;
    }

    public String getOrder_value() {
        return order_value;
    }

    public void setOrder_value(String order_value) {
        this.order_value = order_value;
    }

    public String getFirst_checkin() {
        return first_checkin;
    }

    public void setFirst_checkin(String first_checkin) {
        this.first_checkin = first_checkin;
    }

    public String getLast_checkout() {
        return last_checkout;
    }

    public void setLast_checkout(String last_checkout) {
        this.last_checkout = last_checkout;
    }

    public String getLast_location() {
        return last_location;
    }

    public void setLast_location(String last_location) {
        this.last_location = last_location;
    }

    public String getDate_of_checkin() {
        return date_of_checkin;
    }

    public void setDate_of_checkin(String date_of_checkin) {
        this.date_of_checkin = date_of_checkin;
    }

    public String getVisit_total() {
        return visit_total;
    }

    public void setVisit_total(String visit_total) {
        this.visit_total = visit_total;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAgent_profile() {
        return agent_profile;
    }

    public void setAgent_profile(String agent_profile) {
        this.agent_profile = agent_profile;
    }
}
