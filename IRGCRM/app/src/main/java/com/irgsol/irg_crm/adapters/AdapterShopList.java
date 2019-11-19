package com.irgsol.irg_crm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.activities.ProductListActivity;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.models.ModelShopList;
import com.irgsol.irg_crm.utils.Config;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterShopList extends RecyclerView.Adapter<AdapterShopList.MyViewHolder> {

    List<ModelShopList> modelShopListArrayList = new ArrayList<ModelShopList>();
    Context context;

    public AdapterShopList(Context context, List<ModelShopList> ModelShopList) {
        this.context = context;
        this.modelShopListArrayList = ModelShopList;
    }

    @Override
    public AdapterShopList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shop, parent, false);
        AdapterShopList.MyViewHolder myViewHolder = new AdapterShopList.MyViewHolder(view);
        return myViewHolder;
    }

  //  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(AdapterShopList.MyViewHolder holder, final int position) {

        ModelShopList modelShopList = modelShopListArrayList.get(position);

        final int shopImg = modelShopList.getShopImg();
        final String shopName = modelShopList.getShopName();
        final String shopId = modelShopList.getShopId();
        final String shopOwnerName = modelShopList.getShopOwnerName();

        holder.ivImg.setImageDrawable(context.getDrawable(R.drawable.app_logo));
        // String imgUrl = modelSubService.getSubCategoryImage();
        // OprImage.setImageWithUrl(imgUrl, holder.ivServiceImg, context);

        holder.tvOwnerNam.setText(shopOwnerName);
        holder.tvShoopNam.setText(shopName);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gotoActivity(cosition);
                SharedPref.putSharedPreferences(context, "shopId", ""+shopId);
                OprActivity.openActivity(context, new ProductListActivity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelShopListArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TextView tvShoopNam, tvOwnerNam;
        ImageView ivImg;

        MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            tvShoopNam = itemView.findViewById(R.id.tvShoopNam);
            tvOwnerNam = itemView.findViewById(R.id.tvOwnerNam);
            ivImg = itemView.findViewById(R.id.ivImg);
        }
    }

}
