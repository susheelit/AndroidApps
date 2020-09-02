package com.irgsol.irg_crm.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irgsol.irg_crm.MyDB.CartItems;
import com.irgsol.irg_crm.MyDB.Database;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterProductList;
import com.irgsol.irg_crm.adapters.AdapterShopList;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.models.ModelProduct;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.utils.MySingleton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductListActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static LinearLayout llEmptyView;
    private float count = 1;
    public static TextView tvTotalAmt;
    public static Button btnNext;
    SwipeRefreshLayout swipeRefreshLayout;
    Database myDB;
    String shopId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        initView();
    }

    private void initView() {
        context = ProductListActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Product", context);
        shopId = SharedPref.getShopID(context);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        llEmptyView = findViewById(R.id.llEmptyView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, CheckoutActivity.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showTotalAmt();
                getProductList();
            }
        });

        myDB = Database.getInstance(this);
        getProductList();
        showTotalAmt();
    }

    private void getProductList() {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/productList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelProduct>productLists= new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("jsonArray", ""+jsonArray.length());
                        Log.e("productList ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            ModelProduct modelProduct = new ModelProduct(
                                    SharedPref.getShopID(context),
                             jsonObject1.getInt("prod_id"),
                             jsonObject1.getString("prod_name"),
                             jsonObject1.getString("prod_img"),
                             jsonObject1.getString("prod_mrp"),
                             jsonObject1.getString("prod_price"),
                             jsonObject1.getString("prod_qty"),
                             jsonObject1.getString("prod_type"),
                             jsonObject1.getString("total_sticks"),
                             jsonObject1.getString("prod_color"),
                             jsonObject1.getString("prod_sent"),
                             jsonObject1.getString("prod_company"),
                             jsonObject1.getString("prod_brand"),
                             jsonObject1.getString("prod_code"),
                             jsonObject1.getString("prod_offer"),
                             jsonObject1.getString("prod_instock"),
                             jsonObject1.getString("prod_desc"),
                             jsonObject1.getString("isActive"));

                            productLists.add(modelProduct);
                        }
                        AdapterProductList adapter = new AdapterProductList(context, productLists);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    } else {
                        Config.toastShow(jsonObject.getString("Message"), context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shopList ", error.toString());
                progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public void addItemDialog(final ModelProduct modelProduct) {

        // custom dialog
        final TextView item_name, userInput, unit, rate, totalAmt;
        Button btnOk, cancel;

        final Dialog dialog = new Dialog(context);

        // dialog.setTitle("Title...");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);

        item_name = dialog.findViewById(R.id.item_name);
        userInput = dialog.findViewById(R.id.userInput);
        unit = dialog.findViewById(R.id.unit);
        rate = dialog.findViewById(R.id.rate);
        totalAmt = dialog.findViewById(R.id.totalAmt);
        btnOk = dialog.findViewById(R.id.ok);
        cancel = dialog.findViewById(R.id.cancel);

        item_name.setText(modelProduct.getProd_name());
        // unit.setText(modelProductList.getQty());
        rate.setText(modelProduct.getProd_price());

        // ontext change qty
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count1, int after) {
                //  Toast.makeText(context, "beforeTextChanged", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count1) {

               if (!s.equals("") || !s.toString().isEmpty() ) {
                  // if (s.toString().trim().length()>0) {

                   if (s.toString().compareTo(".") == 0) {
                       userInput.setText("" + 0);
                       s = "0";
                   }

                    String qty = userInput.getText().toString();
                    String amt = modelProduct.getProd_price().toString();
                    totalAmt.setText("" + totalAmt(qty, amt));
                } else {
                    count = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Toast.makeText(context, "afterTextChanged", Toast.LENGTH_LONG).show();
            }
        });

        // if androidx.appcompat.widget.AppCompatButton is clicked, close the custom dialog
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userInput.getText().toString().trim().equals("0") || userInput.getText().toString().trim().isEmpty()){
                    Config.alertBox("Please enter Qty", context);
                }else {
                    ModelProduct modelProduct1 = new ModelProduct(modelProduct.getShopId(), modelProduct.getProd_id(),
                            modelProduct.getProd_name(), modelProduct.getProd_img(), modelProduct.getProd_mrp(),
                            modelProduct.getProd_price(), userInput.getText().toString(), modelProduct.getProd_type(),
                            modelProduct.getTotal_sticks(),modelProduct.getProd_color(),modelProduct.getProd_sent(),
                            modelProduct.getProd_company(), modelProduct.getProd_brand(),modelProduct.getProd_code(),
                            modelProduct.getProd_offer(), modelProduct.getProd_instock(), modelProduct.getProd_desc(),
                            modelProduct.getIsProdActive());

                    myDB.cartItemsDao().insertCartItem(modelProduct1);
                    showTotalAmt();
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void showTotalAmt() {
        String shopId = SharedPref.getShopID(context);
        double totalAmt = 0;
        List<ModelProduct> getTotalAmt = myDB.cartItemsDao().getCartItem(shopId);
        for (int i = 0; i < getTotalAmt.size(); i++) {
            String amt = getTotalAmt.get(i).getProd_price();

            String qty1 = getTotalAmt.get(i).getProd_qty();
            if(qty1.isEmpty())
                qty1="0";
            int qty = Integer.parseInt(qty1);

            totalAmt = totalAmt + Double.parseDouble(amt) * qty;
        }

        tvTotalAmt.setText("" + totalAmt + "  (" + getTotalAmt.size() + ")");

        if (getTotalAmt.size() > 0) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    public double totalAmt(String qty1, String amt1) {
     if(qty1.isEmpty())
       qty1="0";

        double qty = Double.parseDouble(qty1);
        double amt, total;
        amt = Double.parseDouble(amt1);
        total = qty * amt;
        total = Double.parseDouble(new DecimalFormat("##.##").format(total));
        return total;
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

    @Override
    protected void onRestart() {
        super.onRestart();
        showTotalAmt();
    }

}
