package com.irg.crm_admin.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irg.crm_admin.R;

import java.io.Serializable;

public class ModelShop implements Serializable {
    private String addBy;
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
    private String isActive;

    private String area_id;
    private String state_id;
    private String dist_id;
    private String city_id;
    private String area_name;
    private String addOn;
    private Boolean isAddShop;

    public ModelShop(String addBy, String shop_id, String shop_img, String shop_name, String owner_name,
                     String owner_email_id, String owner_mobile, String owner_landline_no, String address, String pincode, String isActive,
                     String area_id, String state_id, String dist_id, String city_id/*, Boolean isAddShop*/) {
        this.addBy = addBy;
        this.shop_id = shop_id;
        this.shop_img = shop_img;
        this.shop_name = shop_name;
        this.owner_name = owner_name;
        this.owner_email_id = owner_email_id;
        this.owner_mobile = owner_mobile;
        this.owner_landline_no = owner_landline_no;
        this.address = address;
        this.pincode = pincode;
        this.isActive = isActive;
        this.area_id = area_id;
        this.state_id = state_id;
        this.dist_id = dist_id;
        this.city_id = city_id;
      //  this.isAddShop = isAddShop;
    }

    public ModelShop(String addBy, String shop_id, String shop_img, String shop_name, String owner_name,
                     String owner_email_id, String owner_mobile, String owner_landline_no, String address,
                     String city, String district, String state, String pincode, String isActive,
                     String area_id, String state_id, String dist_id, String city_id, String area_name,
                     String addOn) {
        this.addBy = addBy;
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
        this.isActive = isActive;
        this.area_id = area_id;
        this.state_id = state_id;
        this.dist_id = dist_id;
        this.city_id = city_id;
        this.area_name = area_name;
        this.addOn = addOn;
    }

    public ModelShop() {
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }

    public Boolean getAddShop() {
        return isAddShop;
    }

    public void setAddShop(Boolean addShop) {
        isAddShop = addShop;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(view.getContext().getResources().getDrawable(R.drawable.app_logo)).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
