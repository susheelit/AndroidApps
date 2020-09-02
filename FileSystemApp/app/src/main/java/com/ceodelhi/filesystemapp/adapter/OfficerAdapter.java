package com.ceodelhi.filesystemapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.model.ModelOfficer;
import com.ceodelhi.filesystemapp.utility.UserPreferences;

import java.util.ArrayList;


public class OfficerAdapter extends RecyclerView.Adapter<OfficerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ModelOfficer> officers;
    private UserPreferences userPreferences;
    MyCallBack myCallBack;


    public interface MyCallBack {
        void ListenerMethod(ModelOfficer officer);
    }

    public OfficerAdapter(Context mContext,MyCallBack myCallBack) {
        this.mContext = mContext;
        userPreferences = new UserPreferences(mContext);
        officers = userPreferences.getOfficers();
        this.myCallBack = myCallBack;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.officer_view, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ModelOfficer officer = officers.get(position);
        holder.nameTv.setText(checkforNull(officer.getOfficer_Name()));
        holder.desgnTv.setText(checkforNull(officer.getActual_Designation()));
        holder.chkbx.setChecked(officer.isSelected());
        holder.chkbx.setTag(officer);

        holder.chkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                ModelOfficer officer = (ModelOfficer) cb.getTag();
                officer.setSelected(cb.isChecked());
                officers.get(position).setSelected(cb.isChecked());
                myCallBack.ListenerMethod(officer);
            }
        });
    }

    private String checkforNull(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv, desgnTv;
        public CheckBox chkbx;


        public MyViewHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            desgnTv = (TextView) view.findViewById(R.id.desgnTv);
            chkbx = (CheckBox) view.findViewById(R.id.chkbx);
        }

    }


    @Override
    public int getItemCount() {
        return officers.size();
    }

}