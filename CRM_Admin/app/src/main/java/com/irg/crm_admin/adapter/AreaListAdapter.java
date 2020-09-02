package com.irg.crm_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.irg.crm_admin.R;
import com.irg.crm_admin.model.ModelArea;

import java.util.ArrayList;

public class AreaListAdapter extends ArrayAdapter<ModelArea> {

    Context context;
    ArrayList<ModelArea> areaArrayList;


    public AreaListAdapter(Context context,ArrayList<ModelArea> areaArrayList) {
        super(context, 0,areaArrayList);
        this.context = context;
        this.areaArrayList = areaArrayList;
    }

    @Override
    public int getCount(){
        return areaArrayList.size();
    }

    @Override
    public ModelArea getItem(int position){
        return areaArrayList.get(position);
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
        ModelArea modelArea = areaArrayList.get(position);
        label.setText(" "+modelArea.getArea_name()+"\n (Zone: "+modelArea.getZone_name() +" )");
        return row;
    }


}