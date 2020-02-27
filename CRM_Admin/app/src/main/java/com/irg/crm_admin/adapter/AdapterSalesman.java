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
import com.irg.crm_admin.model.ModelSalesman;

import java.util.List;

public class AdapterSalesman extends RecyclerView.Adapter<AdapterSalesman.ViewHolder>{

    private List<ModelSalesman> dataModelList;
    private Context context;

    public AdapterSalesman(List<ModelSalesman> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterSalesman.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_salesman, parent, false);

        return new AdapterSalesman.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AdapterSalesman.ViewHolder holder, int position) {
        final ModelSalesman dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        //view details
        holder.itemRowBinding.getRoot().findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewDetails(dataModel);
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
            itemRowBinding.setVariable(BR.salesman, obj);
            itemRowBinding.executePendingBindings();
        }
    }


    public void onUpdateProd(ModelSalesman f) {
        Config.toastShow("Update salesman", context);
    }

    public void onDeleteProd(ModelSalesman f) {
        Config.toastShow("Delete salesman", context);
    }

    private void onViewDetails(ModelSalesman f) {Config.toastShow("View salesman", context);
    }
}
