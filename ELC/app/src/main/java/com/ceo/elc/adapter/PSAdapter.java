package com.ceo.elc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ceo.elc.R;
import com.ceo.elc.model.ModelPS;

import java.util.ArrayList;
import java.util.List;

public class PSAdapter extends BaseAdapter implements SpinnerAdapter {

    private List<ModelPS> modelPSList;
    Context mContext;
    public PSAdapter(List<ModelPS> list, Context context)
    {
        this.modelPSList=list;
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return modelPSList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelPSList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.custom_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
//        if (view != null){
//            text = (TextView) view;
//        } else {
//            text = (TextView)inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
//        }
//        text.setTextColor(Color.BLACK);
        names.setText(modelPSList.get(position).getPSNAME());
        return view;
    }

}
