package com.e.salestracker.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.e.salestracker.Modal.ProjectvisitModal;
import com.e.salestracker.R;
import java.util.ArrayList;

public class ProjectListVisitAdapter extends BaseAdapter {
     ArrayList<ProjectvisitModal> items;
    private int resourceLayout;
    private Context mContext;

    public ProjectListVisitAdapter(Context context, int resource, ArrayList<ProjectvisitModal> items) {

        this.resourceLayout = resource;
        this.mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        if(items.size()<=0)
            return 1;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View v =LayoutInflater.from(mContext).inflate(R.layout.project_list_item,null);
        ProjectvisitModal p = items.get(position);

        if (p != null) {
            TextView project_name = (TextView) v.findViewById(R.id.project);
            TextView project_visit = (TextView) v.findViewById(R.id.project_visit);
            // Toast.makeText(mContext, Integer.toString(position), Toast.LENGTH_SHORT).show();
            project_name.setText(p.getProject_name()+" : ");
            project_visit.setText(p.getProject_visit());
        }
        return v;
    }


}