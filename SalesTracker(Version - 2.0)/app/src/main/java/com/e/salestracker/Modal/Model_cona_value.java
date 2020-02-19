package com.e.salestracker.Modal;

public class Model_cona_value {

    private  String total_collection_value;
    private String total_order_value;

    public Model_cona_value(String total_collection_value, String total_order_value){
        this.total_collection_value =total_collection_value;
        this.total_order_value = total_order_value;
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
}
