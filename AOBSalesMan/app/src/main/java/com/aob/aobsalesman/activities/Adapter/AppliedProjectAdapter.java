/*
package com.aob.aobsalesman.activities.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aob.aobsalesman.R;

import java.util.ArrayList;
import java.util.List;

import com.aob.aobsalesman.activities.activities.HomeActivity;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.ProjectDescriptionActivity;
import com.aob.aobsalesman.activities.model.Company_Data;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.aob.aobsalesman.activities.utility.Tools;

public class AppliedProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Company_Data> items = new ArrayList<>();
    private List<Company_Data> filteredList = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mOnItemClickListenerApply;
    public View v;

    public interface OnItemClickListener {
        void onItemClick(View view, final Company_Data obj, int position, RecyclerView.ViewHolder holder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AppliedProjectAdapter(Context context, List<Company_Data> items ) {

        ctx = context;
        this.items=items;

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView action;
        public TextView company_name;
        public TextView address;
        public TextView price;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            company_name =  v.findViewById(R.id.company_name);
            address =  v.findViewById(R.id.address);
            price = v.findViewById(R.id.price);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_applied, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;


            view.company_name.setText(items.get(position).getCompany_name());
            view.address.setText(items.get(position).getCompany_address());
            view.price.setText(items.get(position).getPrice());

            Tools.displayImageOriginal(ctx,view.image,items.get(position).getImage());

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}*/
