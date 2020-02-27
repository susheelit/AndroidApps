package com.irg.crm_admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.adapter.AdapterOrder;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentOrderHistoryBinding;
import com.irg.crm_admin.model.ModelOrderHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    FragmentOrderHistoryBinding binding;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false);
        View view = binding.getRoot();
        getOrderList();
        return view;
    }

    private void getOrderList() {

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/adOrderHistory.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Config.alertBox(response, context);

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelOrderHistory> modelOrderHistoryList= new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("jsonArray", ""+jsonArray.length());
                        Log.e("shopList ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            ModelOrderHistory modelOrderHistory = new ModelOrderHistory(

                                    jsonObject1.getString("order_id"),
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("shop_id"),
                                    jsonObject1.getString("order_date"),
                                    jsonObject1.getString("order_status"),
                                    jsonObject1.getString("total_item"),
                                    jsonObject1.getString("prod_id"),
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
                                    jsonObject1.getString("isProdActive")

                            );
                            modelOrderHistoryList.add(modelOrderHistory);
                        }

                        AdapterOrder adapter = new AdapterOrder(modelOrderHistoryList, getContext());
                        binding.setAdapter(adapter);

                    } else {
                        Config.toastShow(jsonObject.getString("Message"), getContext());
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

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}