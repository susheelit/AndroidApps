package com.irgsol.irg_crm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.irgsol.irg_crm.MyDB.CartItems;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.models.ModelProduct;

import java.util.ArrayList;
import java.util.List;

public class AdapterCheckout extends RecyclerView.Adapter<AdapterCheckout.MyViewHolder> {

    List<ModelProduct> modelCartItemsArrayList = new ArrayList<ModelProduct>();
    private OnItemClickListener mOnItemClickListener;
    Context context;


    public AdapterCheckout(Context context, List<ModelProduct> modelCartItemsArrayList) {
        this.context = context;
        this.modelCartItemsArrayList = modelCartItemsArrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ModelProduct obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public AdapterCheckout.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_checkout, parent, false);
        AdapterCheckout.MyViewHolder myViewHolder = new AdapterCheckout.MyViewHolder(view);
        return myViewHolder;
    }

   @SuppressLint("NewApi")
   @Override
    public void onBindViewHolder(AdapterCheckout.MyViewHolder holder, final int position) {

        final ModelProduct modelCartItems = modelCartItemsArrayList.get(position);

        final String pName = modelCartItems.getProd_name();
        final String price = modelCartItems.getProd_price();
        final String qty = modelCartItems.getProd_qty();
        //final int imgUrl = modelCartItems.getProd_img();

        holder.ivImg.setImageDrawable(context.getDrawable(R.drawable.app_logo));
        // String imgUrl = modelSubService.getSubCategoryImage();
        // OprImage.setImageWithUrl(imgUrl, holder.ivServiceImg, context);

        holder.tvProductNam.setText(pName);
        holder.tvPrice.setText(""+price);
        holder.tvQty.setText(""+qty);
        double totalAmt = Integer.parseInt(qty)*Double.parseDouble(price);
        holder.tvTotalAmt.setText(""+totalAmt);

       holder.btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (mOnItemClickListener != null) {
                   mOnItemClickListener.onItemClick(view, modelCartItemsArrayList.get(position), position);
               }
           }
       });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gotoActivity(position);
              //  mListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelCartItemsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TextView tvProductNam, tvPrice, tvQty, tvTotalAmt;
        androidx.appcompat.widget.AppCompatButton btnDelete;
        ImageView ivImg;

        MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            tvProductNam = itemView.findViewById(R.id.tvProductNam);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvTotalAmt = itemView.findViewById(R.id.tvTotalAmt);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            ivImg = itemView.findViewById(R.id.ivImg);
        }
    }


}
