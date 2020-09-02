package com.irg.crm_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.irg.crm_admin.R;
import com.irg.crm_admin.model.ModelState;

import java.util.ArrayList;

public class StateListAdapter extends ArrayAdapter<ModelState> {

     Context context;
    ArrayList<ModelState> stateList;


    public StateListAdapter(Context context,ArrayList<ModelState> stateList) {
        super(context, 0,stateList);
        this.context = context;
        this.stateList = stateList;
    }

    @Override
    public int getCount(){
        return stateList.size();
    }

    @Override
    public ModelState getItem(int position){
        return stateList.get(position);
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
        ModelState modelState = stateList.get(position);
        label.setText(""+modelState.getState_name());
        return row;
    }


}