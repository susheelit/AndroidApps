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
import com.irg.crm_admin.adapter.AdapterSalesman;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentMySalesmanBinding;
import com.irg.crm_admin.model.ModelSalesman;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MySalesmanFragment extends Fragment {

    FragmentMySalesmanBinding binding;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_salesman, container, false);
        View view = binding.getRoot();
        getSalesmanList();
        return view;
    }

    private void getSalesmanList() {

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/adUserList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Config.alertBox(response, context);

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelSalesman> modelShopLists= new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("jsonArray", ""+jsonArray.length());
                        Log.e("salesman List ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            ModelSalesman modelSalesman = new ModelSalesman(

                                    jsonObject1.getString("reg_id"),
                                    jsonObject1.getString("area_id"),
                                    jsonObject1.getString("user_name"),
                                    jsonObject1.getString("mobile_no"),
                                    jsonObject1.getString("email_id"),
                                    jsonObject1.getString("user_image"),
                                    jsonObject1.getString("password"),
                                    jsonObject1.getString("isUserActive")
                            );
                            modelShopLists.add(modelSalesman);
                        }

                        AdapterSalesman adapter = new AdapterSalesman(modelShopLists, getContext());
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
                Log.e("salesman List ", error.toString());
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