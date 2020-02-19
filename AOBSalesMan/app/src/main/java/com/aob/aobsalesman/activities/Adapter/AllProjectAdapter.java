/*
package com.aob.aobsalesman.activities.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aob.aobsalesman.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aob.aobsalesman.activities.activities.HomeActivity;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.ProjectDescriptionActivity;
import com.aob.aobsalesman.activities.model.Company_Data;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.aob.aobsalesman.activities.utility.Tools;

public class AllProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Company_Data> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mOnItemClickListenerApply;
    public View v;

    private List<Company_Data> ListFiltered;
    private ContactsAdapterListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, final Company_Data obj, int position, RecyclerView.ViewHolder holder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AllProjectAdapter(Context context, List<Company_Data> items ) {

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
        public Button apply_rv;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            company_name =  v.findViewById(R.id.company_name);
            address =  v.findViewById(R.id.address);
            apply_rv = v.findViewById(R.id.apply_rv);
            price = v.findViewById(R.id.price);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;


            view.company_name.setText(items.get(position).getCompany_name());
            view.address.setText(items.get(position).getCompany_address());
            view.price.setText(items.get(position).getPrice());

            Tools.displayImageOriginal(ctx,view.image,items.get(position).getImage());

            if(items.get(position).getApplied() == false) {

                view.apply_rv.setEnabled(true);

                view.apply_rv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("aobsales", "full clicked");

                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(view, items.get(position), position, holder);
                        }
                    }
                });

            }
            else
            {
                view.apply_rv.setEnabled(false);
                view.apply_rv.setText("Applied");
            }
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ctx, ProjectDescriptionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MyClass", (Serializable) items.get(position));
                    intent.putExtras(bundle);
                    ctx.startActivity(intent);

                }

            });
        }

        }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void showRegistrationDialog()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
        builder1.setMessage("Please fill Registration details to apply to the projects.");
        builder1.setCancelable(true);
        builder1.setTitle("Registratio0n is pending");
        builder1.setPositiveButton(
                "KYC",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(ctx, KYCActivity.class);
                        ctx.startActivity(i);
                    }
                });
        builder1.setNegativeButton(
                "Registration",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showKYCDialog()
    {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(ctx);
        builder2.setMessage("Please fill your KYC details to apply to the projects.");
        builder2.setCancelable(true);
        builder2.setTitle("KYC is pending");
        builder2.setPositiveButton(
                "KYC",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(ctx, KYCActivity.class);
                        ctx.startActivity(i);
                    }
                });
        builder2.setNegativeButton(
                "Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert2 = builder2.create();
        alert2.show();

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ListFiltered = items;
                } else {
                    List<Company_Data> filteredList = new ArrayList<>();
                    for (Company_Data row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCompany_address().toLowerCase().contains(charString.toLowerCase()) || row.getCompany_name().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    ListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ListFiltered = (ArrayList<Company_Data>) filterResults.values;
                Log.e("aobsales","lol "+ListFiltered.size());
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Company_Data contact);
    }

}*/
