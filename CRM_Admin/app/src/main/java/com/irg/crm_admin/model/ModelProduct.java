package com.irg.crm_admin.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;

public class ModelProduct implements Serializable {

   // private int Id;
    private String prod_id;
    private String prod_name;
    private String prod_img;
    private String prod_mrp;
    private String prod_price;
    private String prod_qty;
    private String prod_type;
    private String total_sticks;
    private String prod_weight;
    private String prod_color;
    private String prod_sent;
    private String prod_company;
    private String prod_brand;
    private String prod_code;
    private String prod_offer;
    private String prod_instock;
    private String prod_desc;
    private String isProdActive;

    public ModelProduct(String prod_id, String prod_name, String prod_img, String prod_mrp,
                        String prod_price, String prod_qty, String prod_type, String total_sticks,
                        String prod_weight, String prod_color, String prod_sent, String prod_company,
                        String prod_brand, String prod_code, String prod_offer, String prod_instock,
                        String prod_desc, String isProdActive) {
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_img = prod_img;
        this.prod_mrp = prod_mrp;
        this.prod_price = prod_price;
        this.prod_qty = prod_qty;
        this.prod_type = prod_type;
        this.total_sticks = total_sticks;
        this.prod_weight = prod_weight;
        this.prod_color = prod_color;
        this.prod_sent = prod_sent;
        this.prod_company = prod_company;
        this.prod_brand = prod_brand;
        this.prod_code = prod_code;
        this.prod_offer = prod_offer;
        this.prod_instock = prod_instock;
        this.prod_desc = prod_desc;
        this.isProdActive = isProdActive;
    }


    public ModelProduct(String prod_name, String prod_img, String prod_mrp,
                        String prod_price, String prod_qty, String prod_type, String total_sticks,
                        String prod_weight, String prod_color, String prod_sent, String prod_company,
                        String prod_brand, String prod_code, String prod_offer, String prod_instock,
                        String prod_desc) {
        this.prod_name = prod_name;
        this.prod_img = prod_img;
        this.prod_mrp = prod_mrp;
        this.prod_price = prod_price;
        this.prod_qty = prod_qty;
        this.prod_type = prod_type;
        this.total_sticks = total_sticks;
        this.prod_weight = prod_weight;
        this.prod_color = prod_color;
        this.prod_sent = prod_sent;
        this.prod_company = prod_company;
        this.prod_brand = prod_brand;
        this.prod_code = prod_code;
        this.prod_offer = prod_offer;
        this.prod_instock = prod_instock;
        this.prod_desc = prod_desc;
    }


   /* public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }*/

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_img() {
        return prod_img;
    }

    public void setProd_img(String prod_img) {
        this.prod_img = prod_img;
    }

    public String getProd_mrp() {
        return prod_mrp;
    }

    public void setProd_mrp(String prod_mrp) {
        this.prod_mrp = prod_mrp;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_qty() {
        return prod_qty;
    }

    public void setProd_qty(String prod_qty) {
        this.prod_qty = prod_qty;
    }

    public String getProd_type() {
        return prod_type;
    }

    public void setProd_type(String prod_type) {
        this.prod_type = prod_type;
    }

    public String getTotal_sticks() {
        return total_sticks;
    }

    public void setTotal_sticks(String total_sticks) {
        this.total_sticks = total_sticks;
    }

    public String getProd_weight() {
        return prod_weight;
    }

    public void setProd_weight(String prod_weight) {
        this.prod_weight = prod_weight;
    }

    public String getProd_color() {
        return prod_color;
    }

    public void setProd_color(String prod_color) {
        this.prod_color = prod_color;
    }

    public String getProd_sent() {
        return prod_sent;
    }

    public void setProd_sent(String prod_sent) {
        this.prod_sent = prod_sent;
    }

    public String getProd_company() {
        return prod_company;
    }

    public void setProd_company(String prod_company) {
        this.prod_company = prod_company;
    }

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    public String getProd_code() {
        return prod_code;
    }

    public void setProd_code(String prod_code) {
        this.prod_code = prod_code;
    }

    public String getProd_offer() {
        return prod_offer;
    }

    public void setProd_offer(String prod_offer) {
        this.prod_offer = prod_offer;
    }

    public String getProd_instock() {
        return prod_instock;
    }

    public void setProd_instock(String prod_instock) {
        this.prod_instock = prod_instock;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public String getIsProdActive() {
        return isProdActive;
    }

    public void setIsProdActive(String isProdActive) {
        this.isProdActive = isProdActive;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        imageUrl = "https://i.pinimg.com/236x/ca/76/0b/ca760b70976b52578da88e06973af542.jpg";
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions())
                .into(view);
    }

}