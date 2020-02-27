package com.irg.crm_admin.adapter;

import android.content.Context;
import android.util.Log;
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
import com.irg.crm_admin.model.ModelShopList;
import java.util.List;

public class AdapterShopList extends RecyclerView.Adapter<AdapterShopList.ViewHolder> {

    private List<ModelShopList> dataModelList;
    private Context context;

    public AdapterShopList(List<ModelShopList> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterShopList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_shop, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelShopList dataModel = dataModelList.get(position);
        Log.e("data : ", ""+dataModel.getOwner_name());
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
            itemRowBinding.setVariable(BR.shop, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public void onUpdateProd(ModelShopList f) {
        Config.toastShow("Update Shop", context);
    }

    public void onDeleteProd(ModelShopList f) {
        Config.toastShow("Delete Shop", context);
    }

    private void onViewDetails(ModelShopList f) {Config.toastShow("View Shop", context);
    }
}