package com.irg.crm_admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
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
import com.irg.crm_admin.activity.AddShopActivity;
import com.irg.crm_admin.common.ApiOperation;
import com.irg.crm_admin.databinding.DialogShopDetailBinding;
import com.irg.crm_admin.model.ModelShop;

import java.util.List;

public class AdapterShopList extends RecyclerView.Adapter<AdapterShopList.ViewHolder> {

    private List<ModelShop> dataModelList;
    private Context context;

    public AdapterShopList(List<ModelShop> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterShopList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_shop, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelShop dataModel = dataModelList.get(position);
        Log.e("data : ", "" + dataModel.getOwner_name());
        holder.bind(dataModel);
        Button btnApprove = holder.itemRowBinding.getRoot().findViewById(R.id.btnApprove);

        if (dataModel.getIsActive().equals("0")) {
            btnApprove.setText("APPROVE");
        } else if (dataModel.getIsActive().equals("1")) {
            holder.itemRowBinding.getRoot().findViewById(R.id.btnApprove).setVisibility(View.GONE);
        } else if (dataModel.getIsActive().equals("2")) {
            btnApprove.setText("ACTIVATE");
            holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setVisibility(View.GONE);
            holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete).setVisibility(View.GONE);
        }

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataModel.getIsActive().equals("0")) {
                    deleteShop(dataModel.getShop_id(), "1", "approve", null);
                } else if (dataModel.getIsActive().equals("2")) {
                    deleteShop(dataModel.getShop_id(), "1", "activate", null);
                }
            }
        });

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
                        onUpdateShop(dataModel);
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

    public void onUpdateShop(ModelShop f) {
        // Config.toastShow("Update Shop", context);
        Intent intent = new Intent(context, AddShopActivity.class);
        intent.putExtra("shopDetails", f);
        intent.putExtra("shopType", "Update");
        context.startActivity(intent);
    }

    public void onDeleteProd(ModelShop f) {
        deleteShop(f.getShop_id(), "2", "remove", null);
    }

    private void onViewDetails(final ModelShop f) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogShopDetailBinding layoutBinder = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_shop_detail, null, false);
        layoutBinder.setViewModel(f);

        dialog.setContentView(layoutBinder.getRoot());
        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        Button btnApprove = dialog.findViewById(R.id.btnApprove);
        if (f.getIsActive().equals("0")) {
            btnApprove.setText("APPROVE");
        } else if (f.getIsActive().equals("1")) {
            dialog.findViewById(R.id.btnApprove).setVisibility(View.GONE);
        } else if (f.getIsActive().equals("2")) {
            btnApprove.setText("ACTIVATE");
            dialog.findViewById(R.id.btnUpdate).setVisibility(View.GONE);
            dialog.findViewById(R.id.btnRemove).setVisibility(View.GONE);
        }

        dialog.findViewById(R.id.btnApprove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.getIsActive().equals("0")) {
                    deleteShop(f.getShop_id(), "1", "approve", dialog);
                } else if (f.getIsActive().equals("2")) {
                    deleteShop(f.getShop_id(), "1", "activate", dialog);
                }
            }
        });

        dialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, AddShopActivity.class);
                intent.putExtra("shopDetails", f);
                intent.putExtra("shopType", "Update");
                context.startActivity(intent);
            }
        });

        dialog.findViewById(R.id.btnRemove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShop(f.getShop_id(), "2", "remove", dialog);
            }
        });
    }

    private void deleteShop(final String shopId, final String updateValue, String title, final Dialog dialog1) {

        String msg = "Do you want to " + title + " this shop?";
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(msg);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ApiOperation.deleteShop(context, shopId, updateValue, dialog1);
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