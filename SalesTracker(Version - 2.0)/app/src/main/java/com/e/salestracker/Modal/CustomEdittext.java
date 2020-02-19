package com.e.salestracker.Modal;

import java.util.ArrayList;

public class CustomEdittext {

    public String type;
    public int number;
    public ArrayList<String> values;

    public CustomEdittext( String type,int number, ArrayList<String> values) {
        this.type = type;
        this.number = number;
        this.values = new ArrayList<>(values);
    }



}
