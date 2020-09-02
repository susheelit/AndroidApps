package com.irg.crm_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.irg.crm_admin.R;
import com.irg.crm_admin.model.ModelDistrict;

import java.util.ArrayList;

public class DistrictListAdapter extends ArrayAdapter<ModelDistrict> {

    Context context;
    ArrayList<ModelDistrict> districtList;


    public DistrictListAdapter(Context context,ArrayList<ModelDistrict> districtList) {
        super(context, 0,districtList);
        this.context = context;
        this.districtList = districtList;
    }

    @Override
    public int getCount(){
        return districtList.size();
    }

    @Override
    public ModelDistrict getItem(int position){
        return districtList.get(position);
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
        ModelDistrict modelDistrict = districtList.get(position);
        label.setText(""+modelDistrict.getDist_name());
        return row;
    }


}
