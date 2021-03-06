package com.irg.crm_admin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.adapter.AdapterProduct;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentMyProductBinding;
import com.irg.crm_admin.model.ModelProduct;
import com.irg.crm_admin.viewModel.ClickEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyProductFragment extends Fragment {

    static FragmentMyProductBinding binding;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_my_product, container, false);
        View view = binding.getRoot();
        ClickEvent handlers = new ClickEvent(getContext());
        binding.setHandlers(handlers);
        //here data must be an instance of the class MarsDataProvider
       // binding.setMarsdata(data);
        getProductList(view.getContext());
        return view;
    }

    public static void getProductList(final Context context) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/adminProductList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelProduct> productLists = new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("jsonArray", "" + jsonArray.length());
                        Log.e("productList ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            ModelProduct modelProduct = new ModelProduct(
                                    jsonObject1.getString("prod_id"),
                                    jsonObject1.getString("prod_name"),
                                    jsonObject1.getString("prod_img"),
                                    jsonObject1.getString("prod_mrp"),
                                    jsonObject1.getString("prod_price"),
                                    jsonObject1.getString("prod_qty"),
                                    jsonObject1.getString("prod_type"),
                                    jsonObject1.getString("total_sticks"),
                                    jsonObject1.getString("prod_weight"),
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

                        AdapterProduct adapter = new AdapterProduct(productLists, context);
                        binding.setAdapter(adapter);
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
}