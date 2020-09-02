package com.irg.crm_admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.irg.crm_admin.adapter.AdapterShopList;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentShopListBinding;
import com.irg.crm_admin.model.ModelShop;
import com.irg.crm_admin.viewModel.ClickEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopListFragment extends Fragment {

   static FragmentShopListBinding binding;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_list, container, false);
        View view = binding.getRoot();
        ClickEvent handlers = new ClickEvent(getContext());
        binding.setHandlers(handlers);
        getShopList(getContext());
        return view;
    }

    public static void getShopList(final Context context) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "adminShopList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelShop> modelShopLists1 = new ArrayList<>();
                        modelShopLists1.clear();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            ModelShop modelShop = new ModelShop(jsonObject1.getString("addBy"),
                                    jsonObject1.getString("shop_id"), jsonObject1.getString("shop_img"),
                                    jsonObject1.getString("shop_name"), jsonObject1.getString("owner_name"),
                                    jsonObject1.getString("owner_email_id"), jsonObject1.getString("owner_mobile"),
                                    jsonObject1.getString("owner_landline_no"), jsonObject1.getString("address"),
                                    jsonObject1.getString("city_name"), jsonObject1.getString("dist_name"),
                                    jsonObject1.getString("state_name"), jsonObject1.getString("pincode"),
                                    jsonObject1.getString("isActive"), jsonObject1.getString("area_id"),
                                    jsonObject1.getString("state_id"), jsonObject1.getString("dist_id"),
                                    jsonObject1.getString("city_id"), jsonObject1.getString("area_name"),
                                    jsonObject1.getString("addOn"));

                            modelShopLists1.add(modelShop);
                        }
                        AdapterShopList adapter = new AdapterShopList(modelShopLists1, context);
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