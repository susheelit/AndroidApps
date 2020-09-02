package com.irg.crm_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.irg.crm_admin.R;
import com.irg.crm_admin.model.ModelCity;

import java.util.ArrayList;

public class CityListAdapter extends ArrayAdapter<ModelCity> {

    Context context;
    ArrayList<ModelCity> cityList;


    public CityListAdapter(Context context,ArrayList<ModelCity> cityList) {
        super(context, 0,cityList);
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount(){
        return cityList.size();
    }

    @Override
    public ModelCity getItem(int position){
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            row = inflater.inflate(R.layout.list_items, parent,
                    false);
        } else {
            row = convertView;
        }
        CheckedTextView label = row.findViewById(R.id.label);
        ModelCity modelCity = cityList.get(position);
        label.setText(""+modelCity.getCity_name());
        return row;
    }


}

