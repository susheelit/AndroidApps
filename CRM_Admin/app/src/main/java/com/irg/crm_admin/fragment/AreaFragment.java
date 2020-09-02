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
import com.irg.crm_admin.adapter.AdapterArea;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.databinding.FragmentAreaBinding;
import com.irg.crm_admin.model.ModelServiceArea;
import com.irg.crm_admin.viewModel.ClickEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AreaFragment extends Fragment {
    public AreaFragment() { }

    static FragmentAreaBinding binding;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_area, container, false);
        View view = binding.getRoot();
        ClickEvent handlers = new ClickEvent(getContext());
        binding.setHandlers(handlers);
        getAreaList(view.getContext());
        return view;
    }

   public static void getAreaList(final Context context) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/areaList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        List<ModelServiceArea> modelAreaLists= new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Log.e("jsonArray", ""+jsonArray.length());
                        Log.e("area List ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            ModelServiceArea modelServiceArea = new ModelServiceArea(
                                    jsonObject1.getString("state_name"),
                                    jsonObject1.getString("state_id"),
                                    jsonObject1.getString("dist_name"),
                                    jsonObject1.getString("dist_id"),
                                    jsonObject1.getString("city_name"),
                                    jsonObject1.getString("city_id"),
                                    jsonObject1.getString("area_name"),
                                    jsonObject1.getString("area_id"),
                                    jsonObject1.getString("zone_name"),
                                    jsonObject1.getString("isActive"));
                            modelAreaLists.add(modelServiceArea);
                        }
                        AdapterArea adapter = new AdapterArea(modelAreaLists, context);
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
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}