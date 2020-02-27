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
import com.irg.crm_admin.model.ModelOrderHistory;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {

    private List<ModelOrderHistory> dataModelList;
    private Context context;

    public AdapterOrder(List<ModelOrderHistory> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterOrder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_history, parent, false);

        return new AdapterOrder.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AdapterOrder.ViewHolder holder, int position) {
        final ModelOrderHistory dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        //view details
        holder.itemRowBinding.getRoot().findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewDetails(dataModel);
            }
        });

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
            itemRowBinding.setVariable(BR.orderHistory, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    private void onViewDetails(ModelOrderHistory f) {Config.toastShow("View salesman", context);
    }
}
