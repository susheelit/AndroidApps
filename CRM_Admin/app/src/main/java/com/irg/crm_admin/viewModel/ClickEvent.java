package com.irg.crm_admin.viewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.irg.crm_admin.activity.AddProductActivity;
import com.irg.crm_admin.activity.AddSalesmanActivity;
import com.irg.crm_admin.activity.AddShopActivity;
import com.irg.crm_admin.fragment.MyProductFragment;

public class ClickEvent{

    String TAG = "ClickEvent";
    private Context mContext;

    public ClickEvent(Context context) {
        this.mContext = context;
    }

    public void onClickAddNewShop(View view){
       mContext = view.getContext();
        Intent intent = new Intent(mContext, AddShopActivity.class);
        intent.putExtra("shopType", "Add Shop");
        mContext.startActivity(intent);


    }

    public void onClickAddProduct(View view){
        mContext = view.getContext();
        Intent intent = new Intent(mContext, AddProductActivity.class);
        intent.putExtra("prodType", "Add Poduct");
        mContext.startActivity(intent);
    }

    public void onClickAddSalesman(View view){
        mContext = view.getContext();
        Intent intent = new Intent(mContext, AddSalesmanActivity.class);
        intent.putExtra("userType", "Add Salesman");
        mContext.startActivity(intent);
    }

   public void onRefreshProd(View view){
        Log.e(TAG, "onRefreshProd");
       MyProductFragment.getProductList(view.getContext());
    }
}
