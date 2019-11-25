package com.irgsol.irg_crm.models;

public class ModelUser {

    private String reg_id;
    private String user_name;
    private String mobile_no;
    private String alt_mobile_no;
    private String email_id;
    private String user_image;
    private String password;

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAlt_mobile_no() {
        return alt_mobile_no;
    }

    public void setAlt_mobile_no(String alt_mobile_no) {
        this.alt_mobile_no = alt_mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}