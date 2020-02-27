package com.irgsol.irg_crm.models;

public class ModelShopList {

        private String shop_id;
        private String shop_img;
        private String shop_name;
        private String owner_name;
        private String owner_email_id;
        private String owner_mobile;
        private String owner_landline_no;
        private String address;
        private String city;
        private String district;
        private String state;
        private String pincode;
        private String active_status;

    public ModelShopList(String shop_id,String shop_img, String shop_name, String owner_name, String owner_email_id, String owner_mobile, String owner_landline_no, String address, String city, String district, String state, String pincode, String active_status) {
        this.shop_id = shop_id;
        this.shop_img = shop_img;
        this.shop_name = shop_name;
        this.owner_name = owner_name;
        this.owner_email_id = owner_email_id;
        this.owner_mobile = owner_mobile;
        this.owner_landline_no = owner_landline_no;
        this.address = address;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.active_status = active_status;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_email_id() {
        return owner_email_id;
    }

    public void setOwner_email_id(String owner_email_id) {
        this.owner_email_id = owner_email_id;
    }

    public String getOwner_mobile() {
        return owner_mobile;
    }

    public void setOwner_mobile(String owner_mobile) {
        this.owner_mobile = owner_mobile;
    }

    public String getOwner_landline_no() {
        return owner_landline_no;
    }

    public void setOwner_landline_no(String owner_landline_no) {
        this.owner_landline_no = owner_landline_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }
}
