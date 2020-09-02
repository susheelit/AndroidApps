package com.irg.crm_admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.irg.crm_admin.BR;
import com.irg.crm_admin.R;
import com.irg.crm_admin.activity.AddSalesmanActivity;
import com.irg.crm_admin.common.ApiOperation;
import com.irg.crm_admin.databinding.DialogUserDetailBinding;
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

        Button btnDelete = holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete);
        if (dataModel.getIsActive().equals("2")) {
            btnDelete.setText("ACTIVATE");
            holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setVisibility(View.GONE);
        }else {
            btnDelete.setText("DELETE");
            holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setVisibility(View.VISIBLE);
        }

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
                        onUpdateUser(dataModel);
                    }
                }
        );

        //Delete product
        holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //onDeleteProd(dataModel);
                        if (dataModel.getIsActive().equals("1")) {
                            deleteUser(""+dataModel.getReg_id(), "2", "delete", null);
                        } else if (dataModel.getIsActive().equals("2")) {
                            deleteUser(""+dataModel.getReg_id(), "1", "activate", null);
                        }

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

    public void onUpdateUser(ModelSalesman f) {

        Intent intent = new Intent(context, AddSalesmanActivity.class);
        intent.putExtra("userDetails", f);
        intent.putExtra("userType", "Update");
        context.startActivity(intent);
    }

    private void onViewDetails(final ModelSalesman f) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogUserDetailBinding layoutBinder = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_user_detail, null, false);
        layoutBinder.setViewModel(f);

        dialog.setContentView(layoutBinder.getRoot());
        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        if (f.getIsActive().equals("2")) {
            btnDelete.setText("ACTIVATE");
            dialog.findViewById(R.id.btnUpdate).setVisibility(View.GONE);
            // btnDelete.setVisibility(View.GONE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.getIsActive().equals("1")) {
                    deleteUser(""+f.getReg_id(), "2", "delete", dialog);
                } else if (f.getIsActive().equals("2")) {
                    deleteUser(""+f.getReg_id(), "1", "activate", dialog);
                }
            }
        });

        dialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onUpdateUser(f);
            }
        });
    }

    private void deleteUser(final String userId, final String updateValue, String title, final Dialog dialog1) {

        String msg = "Do you want to " + title + " this salesman?";
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(msg);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ApiOperation.deleteUser(context, userId, updateValue, dialog1);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }
}
