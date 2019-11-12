package com.irgsol.irg_crm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.models.ModelHistoryList;
import java.util.ArrayList;
import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyViewHolder> {

    List<ModelHistoryList> modelHistoryListArrayList = new ArrayList<ModelHistoryList>();
    Context context;

    public AdapterHistory(Context context, List<ModelHistoryList> modelHistoryList) {
        this.context = context;
        this.modelHistoryListArrayList = modelHistoryList;
    }

    @Override
    public AdapterHistory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history, parent, false);
        AdapterHistory.MyViewHolder myViewHolder = new AdapterHistory.MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(AdapterHistory.MyViewHolder holder, final int position) {

        final ModelHistoryList modelHistoryList = modelHistoryListArrayList.get(position);


        int imgUrl = modelHistoryList.getShopImg();
        int imageResource = context.getResources().getIdentifier("" + imgUrl, null, context.getPackageName());

        Drawable res = context.getResources().getDrawable(imageResource);
        holder.ivImg.setImageDrawable(res);

        // 0== Pending, 1= Delivered, 2= cancel
        int IsPaymentColor=0, IsOrderColor=0;
        int orderStatus = Integer.parseInt(modelHistoryList.getOrderStatus());
        String IsOrder = "", IsPayment = "";
        if (orderStatus == 0) {
            IsOrder = "Pending";
            IsOrderColor = Color.YELLOW;
        } else if (orderStatus == 1) {
            IsOrder = "Delivered";
            IsOrderColor = Color.GREEN;
        } else if (orderStatus == 2) {
            IsOrder = "Cancel";
            IsOrderColor = Color.RED;
        }

        int paymentStatus = Integer.parseInt(modelHistoryList.getPaymentStatus());

        if (orderStatus == 0) {
            IsPayment = "Pending";
            IsPaymentColor = Color.YELLOW;
        } else if (orderStatus == 1) {
            IsPayment = "Paid";
            IsPaymentColor = Color.GREEN;
        } else if (orderStatus == 2) {
            IsPayment = "Cancel";
            IsPaymentColor = Color.RED;
        }

        holder.tvShopname.setText("" + modelHistoryList.getShopName());
        holder.tvOwnername.setText("" + modelHistoryList.getOwnerName());
        holder.tvOrderDate.setText("" + modelHistoryList.getOrderDate());
        holder.tvTotalItem.setText("" + modelHistoryList.getTotalItem());
        holder.tvOrderStatus.setText("" + IsOrder);
        holder.tvOrderStatus.setTextColor(IsPaymentColor);
        holder.tvAmt.setText("" + modelHistoryList.getTotalPrice());
        holder.tvPaymentStatus.setText("" + IsPayment);
        holder.tvPaymentStatus.setTextColor(IsOrderColor);

       /* holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open cart dialog
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gotoActivity(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return modelHistoryListArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TextView tvShopname, tvOwnername, tvOrderDate, tvTotalItem, tvOrderStatus, tvAmt, tvPaymentStatus;
        androidx.appcompat.widget.AppCompatButton btnAdd;
        ImageView ivImg;

        MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            tvShopname = itemView.findViewById(R.id.tvShopname);
            tvOwnername = itemView.findViewById(R.id.tvOwnername);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvTotalItem = itemView.findViewById(R.id.tvTotalItem);

            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvAmt = itemView.findViewById(R.id.tvAmt);
            tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus);
            ivImg = itemView.findViewById(R.id.ivImg);
        }
    }
}

