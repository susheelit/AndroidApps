package com.irgsol.irg_crm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.irgsol.irg_crm.MyDB.Database;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterProductList;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.models.ModelPlaceOrder;
import com.irgsol.irg_crm.models.ModelProduct;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context context;
    private Button btnGetDues, btnSubmit;
    private TextView etTotalItem, etTotalAmt, etDueAmt;
    private EditText etContactPerson, etMobileNo, etTotalPayableAmt, etDiscountAmt;
    private RadioButton rbCash, rbCradit;
    private double totalPayableAmt = 0, newDuesAmt = 0;
    Database myDB;
    private String payment_mode = "";
    private boolean isDuesGet = false; // check whether dues get or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        initView();
    }

    private void initView() {
        context = PlaceOrderActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, getResources().getString(R.string.place_order), context);
        etTotalItem = findViewById(R.id.etTotalItem);
        etContactPerson = findViewById(R.id.etContactPerson);
        etMobileNo = findViewById(R.id.etMobileNo);
        etTotalAmt = findViewById(R.id.etTotalAmt);
        etDueAmt = findViewById(R.id.etDueAmt);
        etDiscountAmt = findViewById(R.id.etDiscountAmt);
        rbCash = findViewById(R.id.rbCash);
        rbCradit = findViewById(R.id.rbCradit);
        etTotalPayableAmt = findViewById(R.id.etTotalPayableAmt);
        myDB = Database.getInstance(this);
        //setDetails();
        getDuesAmt();
        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetDues = findViewById(R.id.btnGetDues);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        btnGetDues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDuesAmt();
            }
        });

        etDiscountAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //if (!s.equals("") || !s.toString().isEmpty()) {
                if (s.toString().trim().length() > 0) {

                    if (s.toString().compareTo(".") == 0) {
                        etDiscountAmt.setText("" + 0);
                        s = "0";
                    }

                    String totalAmtStr = etTotalAmt.getText().toString();
                    String dueAmtStr = etDueAmt.getText().toString();
                    String disAmtStr = etDiscountAmt.getText().toString();

                    if (!TextUtils.isEmpty(totalAmtStr) && !TextUtils.isEmpty(dueAmtStr) && !TextUtils.isEmpty(disAmtStr)) {
                        double totalAmt = Double.parseDouble(totalAmtStr);
                        double dueAmt = Double.parseDouble(dueAmtStr);
                        double disAmt = Double.parseDouble(disAmtStr);
                        double payableAmt = (totalAmt + dueAmt) - disAmt;
                        etTotalPayableAmt.setText("" + payableAmt);
                    }

                } else {
                    count = 0;
                    String totalAmtStr = etTotalAmt.getText().toString();
                    String dueAmtStr = etDueAmt.getText().toString();
                    if (!TextUtils.isEmpty(totalAmtStr) && !TextUtils.isEmpty(dueAmtStr)) {
                        double totalAmt = Double.parseDouble(totalAmtStr);
                        double dueAmt = Double.parseDouble(dueAmtStr);
                        double payableAmt = (totalAmt + dueAmt);
                        etTotalPayableAmt.setText("" + payableAmt);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setDetails(String dueAmtStr) {
        etContactPerson.setText("" + SharedPref.getOwnerName(context));
        etMobileNo.setText("" + SharedPref.getOwnerMobile(context));

        double totalAmt = 0;
        List<ModelProduct> getTotalAmt = myDB.cartItemsDao().getCartItem(SharedPref.getShopID(context));
        for (int i = 0; i < getTotalAmt.size(); i++) {
            String amt = getTotalAmt.get(i).getProd_price();
            int qty = Integer.parseInt(getTotalAmt.get(i).getProd_qty());
            totalAmt = totalAmt + Double.parseDouble(amt) * qty;
        }

        etTotalItem.setText("" + getTotalAmt.size());
        etTotalAmt.setText("" + totalAmt);
        etDueAmt.setText("" + dueAmtStr);

        if (!TextUtils.isEmpty(dueAmtStr)) {
            double dueAmt = Double.parseDouble(dueAmtStr);
            totalPayableAmt = totalAmt + dueAmt;
            etTotalPayableAmt.setText("" + totalPayableAmt);
        } else {
            totalPayableAmt = totalAmt;
            etTotalPayableAmt.setText("" + totalPayableAmt);
        }

    }

    private void validate() {

        String contactPerson = etContactPerson.getText().toString();
        String mobileNo = etMobileNo.getText().toString();

        if (contactPerson.isEmpty()) {
            Config.alertBox("Please enter contact person name", context);
        } else if (mobileNo.isEmpty()) {
            Config.alertBox("Please enter mobile no", context);
        } else if (mobileNo.length() != 10) {
            Config.alertBox("Please enter 10 digit mobile no ", context);
        } else {
            double totalPayableAmt1 = 0;
            totalPayableAmt1 = totalPayableAmt;
            if (isDuesGet) {
                String disPriceStr = etDiscountAmt.getText().toString();
                if (!TextUtils.isEmpty(disPriceStr)) {
                    double disPrice = Double.parseDouble(disPriceStr);
                    totalPayableAmt1 = totalPayableAmt - disPrice;
                }
                double payableAmt = 0;
                String payableAmtStr = etTotalPayableAmt.getText().toString();
                if (!TextUtils.isEmpty(payableAmtStr)) {
                    payableAmt = Double.parseDouble(payableAmtStr);
                }

                if (rbCash.isChecked()) {
                    payment_mode = "CASH";
                    if (payableAmt >= 1 && payableAmt <= totalPayableAmt) {
                        if (payableAmt == totalPayableAmt1) {
                            newDuesAmt = 0.00;
                            alertPlaceOrder("All dues will be cleared.");
                        } else {
                            newDuesAmt = totalPayableAmt1 - payableAmt;
                            alertPlaceOrder("Rs." + newDuesAmt + " will be dues.");
                        }
                    } else {
                        Config.alertBox("Please enter correct payable amount", context);
                    }
                } else if (rbCradit.isChecked()) {
                    payment_mode = "CREDIT";
                    if (payableAmt >= 0 && payableAmt <= totalPayableAmt) {
                        newDuesAmt = totalPayableAmt1 - payableAmt;
                        alertPlaceOrder("Rs." + newDuesAmt + " will be dues.");
                    } else {
                        Config.alertBox("Please enter correct payable amount", context);
                    }
                }

            } else {
                Config.toastShow("Unable to get dues amount. Please try again.", context);
            }

        }

    }

  /*  private void validate1() {

        String totalItem = "", totalAmt = "", contactPerson = "", mobileNo = "";

        totalItem = etTotalItem.getText().toString();
        totalAmt = etTotalAmt.getText().toString();
        contactPerson = etContactPerson.getText().toString();
        mobileNo = etMobileNo.getText().toString();

        if (contactPerson.isEmpty()) {
            Config.alertBox("Please enter contact person name", context);
        } else if (mobileNo.isEmpty()) {
            Config.alertBox("Please enter mobile no", context);
        } else if (mobileNo.length() != 10) {
            Config.alertBox("Please enter 10 digit mobile no ", context);
        } else {
           *//* Intent intent = new Intent(context, WelcomeActivity.class);
            intent.putExtra("contactPerson", contactPerson);
            intent.putExtra("mobileNo", mobileNo);
            startActivity(intent);*//*
            if (isDuesGet) {
                String disPriceStr = etDiscountAmt.getText().toString();
                String totalPayableAmtStr = etTotalPayableAmt.getText().toString();
                if (!totalPayableAmtStr.isEmpty()) {
                    double PayableAmt = Double.parseDouble(totalPayableAmtStr);
                    double disPrice = 0;
                    if (!TextUtils.isEmpty(disPriceStr)) {
                        disPrice = Double.parseDouble(disPriceStr);
                        totalPayableAmt = totalPayableAmt - disPrice;
                    }

                    if (rbCash.isChecked()) {
                        if (totalPayableAmt == PayableAmt) {
                            newDuesAmt = 0.00;
                            alertPlaceOrder("All dues will be cleared.");
                        } else {
                            newDuesAmt = totalPayableAmt - PayableAmt;
                            alertPlaceOrder("Rs." + newDuesAmt + " will be dues.");
                        }
                    } else if (rbCradit.isChecked()) {
                        String duesAmtStr = etDueAmt.getText().toString();
                        if (!TextUtils.isEmpty(duesAmtStr)) {
                            newDuesAmt = totalPayableAmt + Double.parseDouble(duesAmtStr);
                            totalPayableAmt = 0.0;
                            alertPlaceOrder("Rs." + newDuesAmt + " will be dues.");
                        }

                    } else {
                        Config.alertBox("Please choose payment mode.", context);
                    }
                }
            } else {
                Config.toastShow("Unable to get dues amount. Please try again.", context);
            }
        }

    }*/

    private void alertPlaceOrder(String msg) {
        AlertDialog.Builder altDialog = new AlertDialog.Builder(this);
        altDialog.setTitle("Information");
        altDialog.setMessage(msg);
        altDialog.setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                placeOrderDetails();
            }
        });

        altDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        altDialog.create();
        altDialog.show();
    }

    private void placeOrderDetails() {

        List<ModelProduct> cartItems = myDB.cartItemsDao().getCartItem(SharedPref.getShopID(context));
        int totalItem = cartItems.size();
        if (totalItem > 0) {
            String totalCartAmtStr = etTotalAmt.getText().toString();
            String disAmtStr = etDiscountAmt.getText().toString();

            //  if (!TextUtils.isEmpty(totalCartAmtStr)/* && !TextUtils.isEmpty(duesAmtStr)*/) {
            double totalCartAmt = Double.parseDouble(totalCartAmtStr);

            String contactPerson = etContactPerson.getText().toString();
            String mobileNo = etMobileNo.getText().toString();
            String paidAmt = etTotalPayableAmt.getText().toString();
            try {
                placeOrder(cartItems, totalItem, totalCartAmt, totalPayableAmt, disAmtStr, paidAmt, newDuesAmt, contactPerson, mobileNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
/*
            } else {
                Config.toastShow("Unable to get cart amount.", context);
            }*/

        } else {
            Config.toastShow("Oops! Your cart is empty", context);
        }

    }

    private void placeOrder(List<ModelProduct> cartItems, int totalItem, double totalCartAmt, double totalPayableAmt, String disAmtStr, String paidAmt, double newDuesAmt, String contactPerson, String mobileNo) throws JSONException {

        // -----order details -------
        String regID = SharedPref.getRegId(context);
        String shopID = SharedPref.getShopID(context);
        String order_status = "1";

        ModelPlaceOrder orderDetailsObj = new ModelPlaceOrder(regID, shopID, order_status);


        // ---- payment detais ----
        //payment_id;
        //order_no;
        String txn_no = regID + shopID + System.currentTimeMillis();
        String totalAmt = "" + totalCartAmt;
        String disPrice = disAmtStr;
        String payment_mode1 = payment_mode;
        String payment_status = "1";
        String dueAmt = "" + newDuesAmt;
        String paidAmt1 = paidAmt;
        //timestamp

        ModelPlaceOrder paymentDetailsObj = new ModelPlaceOrder(txn_no, totalAmt, disPrice, payment_mode1, payment_status, dueAmt, paidAmt1);


        // ---- items detais -----
        //item_id;
        //order_no;
        // String product_id;
        // String qty;
        // String totalPrice;
        //String isActive = "1";
        //  Gson gson = new Gson();
        // String ddd = gson.toJson(cartItems);

        List<ModelPlaceOrder> itemList = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            int product_id = cartItems.get(i).getProd_id();
            String qtyStr = cartItems.get(i).getProd_qty();
            String prodPriceStr = cartItems.get(i).getProd_price();
            double prodPrice = 0;
            if (!TextUtils.isEmpty(qtyStr) && !TextUtils.isEmpty(prodPriceStr)) {
                prodPrice = Integer.parseInt(qtyStr) * Double.parseDouble(prodPriceStr);
                ModelPlaceOrder modelPlaceOrder = new ModelPlaceOrder("" + product_id, qtyStr, "" + prodPrice, "1");
                itemList.add(modelPlaceOrder);
            }

            JSONObject orderDetailsJsonObj = new JSONObject();
            orderDetailsJsonObj.put("OrderDetails", orderDetailsObj);
            orderDetailsJsonObj.put("PaymentDetails", paymentDetailsObj);
            orderDetailsJsonObj.put("ItemDetails", itemList);


            Log.e("finalobject ddd 1 ", orderDetailsJsonObj.toString());

            attempPlaceOrder(orderDetailsJsonObj);

        }

    }

    private void attempPlaceOrder(final JSONObject orderDetailsJsonObj) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/placeOrder.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("place_order ", response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    Log.e("resultCode", resultCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("place order ", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("OrderDetails", orderDetailsJsonObj.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void getDuesAmt() {
        etDiscountAmt.setText("");
        isDuesGet = false;
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/getDueAmt.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String dueAmtStr = jsonObject1.getString("dueAmt");
                        setDetails(dueAmtStr);
                        isDuesGet = true;
                        // dues get success
                        Log.e("productList ", response);
                    } else {
                        // unable to get dues
                        isDuesGet = false;
                        btnGetDues.setVisibility(View.VISIBLE);
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
                Log.e("dues amt ", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("shop_id", SharedPref.getShopID(context));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
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
