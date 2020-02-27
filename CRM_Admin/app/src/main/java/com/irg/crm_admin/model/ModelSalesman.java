package com.irg.crm_admin.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irg.crm_admin.R;

public class ModelSalesman {

    private String reg_id;
    private String area_id;
    private String user_name;
    private String mobile_no;
    private String email_id;
    private String user_image;
    private String password;
    private String isUserActive;

    public ModelSalesman(String reg_id, String area_id, String user_name, String mobile_no,
                         String email_id, String user_image, String password, String isUserActive) {
        this.reg_id = reg_id;
        this.area_id = area_id;
        this.user_name = user_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.user_image = user_image;
        this.password = password;
        this.isUserActive = isUserActive;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public String getIsUserActive() {
        return isUserActive;
    }

    public void setIsUserActive(String isUserActive) {
        this.isUserActive = isUserActive;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(view.getContext().getResources().getDrawable(R.drawable.app_logo)).apply(new RequestOptions().circleCrop())
                .into(view);
    }

}
