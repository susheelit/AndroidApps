package com.irg.crm_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.irg.crm_admin.BR;
import com.irg.crm_admin.R;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.model.ModelProduct;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> /*implements CustomClickListener*/ {

    private List<ModelProduct> dataModelList;
    private Context context;

    public AdapterProduct(List<ModelProduct> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_product, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelProduct dataModel = dataModelList.get(position);
        holder.bind(dataModel);

        // view Product
        holder.itemRowBinding.getRoot().findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewProduct(dataModel);
            }
        });
        //Update product
        holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onUpdateProd(dataModel);
                    }
                }
        );

        //Delete product
        holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDeleteProd(dataModel);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding itemRowBinding;

        ViewHolder(ViewDataBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }
           void bind(Object obj) {
            itemRowBinding.setVariable(BR.product, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public void onUpdateProd(ModelProduct f) {
        Config.toastShow("Update Product", context);
    }

    public void onDeleteProd(ModelProduct f) {
        Config.toastShow("Delete Product", context);
    }

    private void onViewProduct(ModelProduct dataModel) {
        Config.toastShow("Delete Product", context);
    }
}