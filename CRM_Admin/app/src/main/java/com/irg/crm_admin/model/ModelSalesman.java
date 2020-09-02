package com.irg.crm_admin.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irg.crm_admin.R;

import java.io.Serializable;

public class ModelSalesman  implements Serializable {

            private String reg_id;
            private String user_name;
            private String user_role;
            private String mobile_no;
            private String email_id;
            private String address;
            private String user_image;
            private String password;
            private String conPassword;
            private String isActive;
            private String registeredOn;
            private String area_id;
            private String state_id;
            private String dist_id;
            private String city_id;
            private String state_name;
            private String dist_name;
            private String city_name;
            private String area_name;
            private String zone_name;

  /*  public ModelSalesman(String reg_id, String user_name, String user_role, String mobile_no, String email_id, String address, String user_image, String password, String isActive, String registeredOn, String area_id, String state_id, String dist_id, String city_id, String state_name, String dist_name, String city_name, String area_name, String zone_name) {
        this.reg_id = reg_id;
        this.user_name = user_name;
        this.user_role = user_role;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.address = address;
        this.user_image = user_image;
        this.password = password;
        this.isActive = isActive;
        this.registeredOn = registeredOn;
        this.area_id = area_id;
        this.state_id = state_id;
        this.dist_id = dist_id;
        this.city_id = city_id;
        this.state_name = state_name;
        this.dist_name = dist_name;
        this.city_name = city_name;
        this.area_name = area_name;
        this.zone_name = zone_name;
    }*/

    public ModelSalesman(String reg_id, String user_name, String user_role, String mobile_no, String email_id, String address, String user_image, String password, String conPassword, String isActive, String registeredOn, String area_id, String state_id, String dist_id, String city_id, String state_name, String dist_name, String city_name, String area_name, String zone_name) {
        this.reg_id = reg_id;
        this.user_name = user_name;
        this.user_role = user_role;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.address = address;
        this.user_image = user_image;
        this.password = password;
        this.conPassword = conPassword;
        this.isActive = isActive;
        this.registeredOn = registeredOn;
        this.area_id = area_id;
        this.state_id = state_id;
        this.dist_id = dist_id;
        this.city_id = city_id;
        this.state_name = state_name;
        this.dist_name = dist_name;
        this.city_name = city_name;
        this.area_name = area_name;
        this.zone_name = zone_name;
    }

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

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(view.getContext().getResources().getDrawable(R.drawable.app_logo)).apply(new RequestOptions().circleCrop())
                .into(view);
    }

}
