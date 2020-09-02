package com.irg.crm_admin.model;

public class ModelRegistration {
    private String area_id;
    private String userName;
    private String userEmail;
    private String mobileNo;
    private String userPhoto;
    private String password;
    private String conPassword;
    private String userRole;
    private String address;

    public ModelRegistration(String mobileNo, String password) {
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public ModelRegistration(String area_id, String userName, String userEmail, String mobileNo,
                             String userPhoto, String password, String conPassword, String userRole
    , String address) {
        this.area_id = area_id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.mobileNo = mobileNo;
        this.userPhoto = userPhoto;
        this.password = password;
        this.conPassword = conPassword;
        this.userRole = userRole;
        this.address = address;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
