/*
package com.aob.aobsalesman.activities.Adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.model.LeadModal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.ViewHolder>  {
    private Context context;
    private List<LeadModal> itoms=new ArrayList();
    private List<LeadModal> items_filtered=new ArrayList();
    private OnItemClickListener mOnItemClickListener;
    private long mLastClickTime = 0;
    LeadModal historyModal;
    public LeadAdapter(Context context, List<LeadModal> itoms) {
        this.context = context;
        this.itoms = itoms;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position, LeadModal leadModal, RecyclerView.ViewHolder holder);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.lead_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
       historyModal=itoms.get(i);

        viewHolder.company_name.setText(historyModal.getCompany_name());
        viewHolder.person_name.setText(historyModal.getContact_person());
        viewHolder.date.setText(historyModal.getDate());
        viewHolder.city.setText(historyModal.getCity());
        viewHolder.status.setText(historyModal.getLead_status());
        viewHolder.project_name.setText(historyModal.getProject_id());

        viewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, i,itoms.get(i), viewHolder);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itoms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView company_name,person_name,city,date,status,project_name;
        LinearLayout lyt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            company_name=itemView.findViewById(R.id.company_name);
            person_name=itemView.findViewById(R.id.person_name);
            city=itemView.findViewById(R.id.city);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            lyt_parent=itemView.findViewById(R.id.lyt_parent);
            project_name=itemView.findViewById(R.id.project_name);
        }
    }



}*/
