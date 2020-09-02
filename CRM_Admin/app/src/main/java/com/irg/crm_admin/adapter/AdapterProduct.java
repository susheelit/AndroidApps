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
import com.irg.crm_admin.activity.AddProductActivity;
import com.irg.crm_admin.common.ApiOperation;
import com.irg.crm_admin.databinding.DialogProductDetailBinding;
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

        Button btnDelete = holder.itemRowBinding.getRoot().findViewById(R.id.btnDelete);
        if (dataModel.getIsProdActive().equals("2")) {
            btnDelete.setText("ACTIVATE");
            holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setVisibility(View.GONE);
        }else {
            btnDelete.setText("DELETE");
            holder.itemRowBinding.getRoot().findViewById(R.id.btnUpdate).setVisibility(View.VISIBLE);
        }

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
                       // onDeleteProd(dataModel);
                        if (dataModel.getIsProdActive().equals("1")) {
                            deleteProd(""+dataModel.getProd_id(), "2", "delete", null);
                        } else if (dataModel.getIsProdActive().equals("2")) {
                            deleteProd(""+dataModel.getProd_id(), "1", "activate", null);
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
            itemRowBinding.setVariable(BR.product, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public void onUpdateProd(ModelProduct f) {
        Intent intent = new Intent(context, AddProductActivity.class);
        intent.putExtra("productDetails", f);
        intent.putExtra("prodType", "Update");
        context.startActivity(intent);
    }

   /* public void onDeleteProd(ModelProduct f) {
        Config.toastShow("Delete Product", context);
    }*/

    private void onViewProduct(final ModelProduct f) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogProductDetailBinding layoutBinder = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_product_detail, null, false);
        layoutBinder.setViewModel(f);

        dialog.setContentView(layoutBinder.getRoot());
        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
       if (f.getIsProdActive().equals("2")) {
            btnDelete.setText("ACTIVATE");
            dialog.findViewById(R.id.btnUpdate).setVisibility(View.GONE);
            // btnDelete.setVisibility(View.GONE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.getIsProdActive().equals("1")) {
                    deleteProd(""+f.getProd_id(), "2", "delete", dialog);
                } else if (f.getIsProdActive().equals("2")) {
                    deleteProd(""+f.getProd_id(), "1", "activate", dialog);
                }
            }
        });

        dialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onUpdateProd(f);
            }
        });
    }

    private void deleteProd(final String prodId, final String updateValue, String title, final Dialog dialog1) {

        String msg = "Do you want to " + title + " this product?";
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(msg);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ApiOperation.deleteProduct(context, prodId, updateValue, dialog1);
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