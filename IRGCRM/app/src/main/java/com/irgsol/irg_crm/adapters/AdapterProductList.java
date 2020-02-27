package com.irgsol.irg_crm.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.irgsol.irg_crm.MyDB.CartItems;
import com.irgsol.irg_crm.MyDB.Database;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.activities.ProductListActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.models.ModelProduct;
import com.irgsol.irg_crm.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.MyViewHolder> {

    List<ModelProduct> modelProductList = new ArrayList<ModelProduct>();
    Context context;
    private ProductListActivity mAct;

    public AdapterProductList(Context context, List<ModelProduct> modelProductList) {
        this.context = context;
        this.modelProductList = modelProductList;
        this.mAct = (ProductListActivity)context;
    }

    @Override
    public AdapterProductList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product, parent, false);
        AdapterProductList.MyViewHolder myViewHolder = new AdapterProductList.MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(AdapterProductList.MyViewHolder holder, final int position) {

        final ModelProduct modelProduct = modelProductList.get(position);



        holder.ivImg.setImageDrawable(context.getDrawable(R.drawable.app_logo));
        // String imgUrl = modelSubService.getSubCategoryImage();
        // OprImage.setImageWithUrl(imgUrl, holder.ivServiceImg, context);

        holder.tvProductNam.setText(modelProduct.getProd_name());
        holder.tvPrice.setText(""+modelProduct.getProd_price());
        holder.tvQty.setText(""+modelProduct.getProd_qty());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open cart dialog
                Database myDb = Database.getInstance(context);
                String shopId = SharedPref.getSharedPreferences(context, "shopId","");
                List<ModelProduct>modelProducts1 = myDb.cartItemsDao().checkCartItem(modelProduct.getProd_id(), shopId);

                if(modelProducts1.size()>0){
                    Config.alertBox("Item is already added ", context);
                }else {
                    mAct.addItemDialog(modelProduct);
                }
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemDetail(modelProduct, context);
            }
        });
    }

    private void showItemDetail(ModelProduct modelProductList, Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_detail_dialog);

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return modelProductList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TextView tvProductNam, tvPrice, tvQty;
        androidx.appcompat.widget.AppCompatButton btnAdd;
        ImageView ivImg;
       //ImageButton btnAdd;

        MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            tvProductNam = itemView.findViewById(R.id.tvProductNam);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQty = itemView.findViewById(R.id.tvQty);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            ivImg = itemView.findViewById(R.id.ivImg);
        }
    }


}
