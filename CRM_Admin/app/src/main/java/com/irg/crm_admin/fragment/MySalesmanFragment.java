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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.adapter.AdapterSalesman;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentMySalesmanBinding;
import com.irg.crm_admin.model.ModelSalesman;
import com.irg.crm_admin.viewModel.ClickEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySalesmanFragment extends Fragment {

    static FragmentMySalesmanBinding binding;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_salesman, container, false);
        View view = binding.getRoot();
        ClickEvent handlers = new ClickEvent(getContext());
        binding.setHandlers(handlers);
        getSalesmanList(view.getContext());
        return view;
    }

    public static void getSalesmanList(final Context context) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/SalesmanList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelSalesman> modelShopLists= new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("salesman List ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            ModelSalesman modelSalesman = new ModelSalesman(
                            jsonObject1.getString("reg_id"),
                            jsonObject1.getString("user_name"),
                            jsonObject1.getString("user_role"),
                            jsonObject1.getString("mobile_no"),
                            jsonObject1.getString("email_id"),
                            jsonObject1.getString("address"),
                            jsonObject1.getString("user_image"),
                            jsonObject1.getString("password"),"",
                            jsonObject1.getString("isActive"),
                            jsonObject1.getString("registeredOn"),
                            jsonObject1.getString("area_id"),
                            jsonObject1.getString("state_id"),
                            jsonObject1.getString("dist_id"),
                            jsonObject1.getString("city_id"),
                            jsonObject1.getString("state_name"),
                            jsonObject1.getString("dist_name"),
                            jsonObject1.getString("city_name"),
                            jsonObject1.getString("area_name"),
                            jsonObject1.getString("zone_name") );

                            modelShopLists.add(modelSalesman);
                        }
                        AdapterSalesman adapter = new AdapterSalesman(modelShopLists, context);
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
                Log.e("salesman List ", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_role", "Salesman");
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

}