package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.irgsol.irg_crm.MyDB.Database;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterCheckout;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.models.ModelProduct;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public LinearLayout llEmptyView;
    public TextView tvTotalAmt;
    public static androidx.appcompat.widget.AppCompatButton btnNext;
    String shopId = "";
    Database myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initView();
    }

    private void initView() {
        context = CheckoutActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Checkout",context );
        shopId = SharedPref.getSharedPreferences(context, "shopId", "");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        llEmptyView = findViewById(R.id.llEmptyView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        btnNext = findViewById(R.id.btnNext);

        myDB = Database.getInstance(this);
        setupCartList();
        showTotalAmt();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupCartList();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PlaceOrderActivity.class));
            }
        });
    }

    private void setupCartList() {
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        final List<ModelProduct> getTotalAmt= myDB.cartItemsDao().getCartItem(shopId);

        final AdapterCheckout mAdapter = new AdapterCheckout(context, getTotalAmt);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterCheckout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final ModelProduct obj, int position) {

               AlertDialog.Builder dialog = new AlertDialog.Builder(context);
               dialog.setTitle("Do you want to delete item");
               dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       myDB.cartItemsDao().deleteCartItems(obj);

                       showTotalAmt();
                       getTotalAmt.clear();

                       final List<ModelProduct> getTotalAmt= myDB.cartItemsDao().getCartItem(shopId);

                       final AdapterCheckout mAdapter = new AdapterCheckout(context, getTotalAmt);
                       recyclerView.setVisibility(View.VISIBLE);
                       recyclerView.setItemAnimator(new DefaultItemAnimator());
                       recyclerView.setAdapter(mAdapter);
                   }
               });
               dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
                dialog.show();

            }
        });
    }

    private void showTotalAmt() {

        double totalAmt =0;
        List<ModelProduct> getTotalAmt= myDB.cartItemsDao().getCartItem(shopId);
        for(int i=0;i<getTotalAmt.size();i++){
            String amt = getTotalAmt.get(i).getProd_price();
            int qty = Integer.parseInt(getTotalAmt.get(i).getProd_qty());
            totalAmt = totalAmt+ Double.parseDouble(amt)*qty;
        }

        tvTotalAmt.setText(""+totalAmt+"  ("+getTotalAmt.size()+")");

        if(getTotalAmt.size()>0){
            btnNext.setEnabled(true);
        }else {
            btnNext.setEnabled(false);
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }

}
