package com.irg.crm_admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.irg.crm_admin.BR;
import com.irg.crm_admin.R;
import com.irg.crm_admin.common.ApiOperation;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.model.ModelServiceArea;

import java.util.List;

public class AdapterDistrict extends RecyclerView.Adapter<AdapterDistrict.ViewHolder> {

    private List<ModelServiceArea> dataModelList;
    private Context context;

    public AdapterDistrict(List<ModelServiceArea> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterDistrict.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_dist, parent, false);

        return new AdapterDistrict.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AdapterDistrict.ViewHolder holder, int position) {
        final ModelServiceArea dataModel = dataModelList.get(position);
        Log.e("data : ", ""+dataModel.getServiceArea());
        holder.bind(dataModel);

        //Update state
        Button btnUpdate = holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate);
        if (dataModel.getIsActive().equals("1")) {
            holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
            btnUpdate.setText("UPDATE");

        } else if (dataModel.getIsActive().equals("2")) {
            holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setVisibility(View.GONE);
            btnUpdate.setText("ACTIVATE");
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataModel.getIsActive().equals("1")) {
                    onUpdateArea(dataModel);
                } else if (dataModel.getIsActive().equals("2")) {
                    ApiOperation.deleteServiceArea(dataModel.getDistrictId(), 1, context, 2);
                }

            }
        }
        );
        //Delete state
        holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiOperation.deleteServiceArea(dataModel.getDistrictId(), 2, context, 2);
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
            itemRowBinding.setVariable(BR.serviceArea, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    private void onUpdateArea(final ModelServiceArea f) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_service_area);
        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

        final EditText name = dialog.findViewById(R.id.etName);
        name.setText("" + f.getDistrictName());

        dialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText().toString())) {
                    if (!name.getText().toString().equalsIgnoreCase(f.getStateName())) {
                        ApiOperation.updateArea(context, f.getDistrictId(), name.getText().toString(), dialog, 2);
                    } else {
                        Config.alertBox("Current district name and previous district name is same, Please enter different district name", context);
                    }
                } else {
                    Config.alertBox("Please enter district name", context);
                }
            }
        });

        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}