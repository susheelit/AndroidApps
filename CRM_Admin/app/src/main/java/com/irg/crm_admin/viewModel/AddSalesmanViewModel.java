package com.irg.crm_admin.viewModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.activity.AddSalesmanActivity;
import com.irg.crm_admin.adapter.AreaListAdapter;
import com.irg.crm_admin.adapter.CityListAdapter;
import com.irg.crm_admin.adapter.DistrictListAdapter;
import com.irg.crm_admin.adapter.StateListAdapter;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.model.ModelArea;
import com.irg.crm_admin.model.ModelCity;
import com.irg.crm_admin.model.ModelDistrict;
import com.irg.crm_admin.model.ModelSalesman;
import com.irg.crm_admin.model.ModelState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddSalesmanViewModel extends ViewModel {

    ArrayList<ModelState> stateList;
    ArrayList<ModelDistrict> distList;
    ArrayList<ModelCity> cityList;
    ArrayList<ModelArea> areaList;
    String selItem = "";

    public MutableLiveData<String> regId = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> mobileNo = new MutableLiveData<>();
    public MutableLiveData<String> userPhoto = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> conPassword = new MutableLiveData<>();
    public MutableLiveData<String> userRole = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> stateName = new MutableLiveData<>();
    public MutableLiveData<String> districtName = new MutableLiveData<>();
    public MutableLiveData<String> cityName = new MutableLiveData<>();
    public MutableLiveData<String> areaName = new MutableLiveData<>();
    public MutableLiveData<String> zoneName = new MutableLiveData<>();
    public MutableLiveData<String> stateId = new MutableLiveData<>();
    public MutableLiveData<String> districtId = new MutableLiveData<>();
    public MutableLiveData<String> cityId = new MutableLiveData<>();
    public MutableLiveData<String> areaId = new MutableLiveData<>();
    public MutableLiveData<String> isActive = new MutableLiveData<>();
    public MutableLiveData<String> registeredOn = new MutableLiveData<>();
    public MutableLiveData<String> mTitle = new MutableLiveData<>();

    private MutableLiveData<ModelSalesman> userMutableLiveData;

    public MutableLiveData<ModelSalesman> getUser(Context context) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            getAllAreaList(context);

            String prodType = ((AppCompatActivity) context).getIntent().getStringExtra("userType");
            mTitle.setValue(prodType);
            if (prodType.equalsIgnoreCase("Update")) {
                setDetails(context);
            }
        }
        return userMutableLiveData;
    }

    private void setDetails(Context context) {

        ModelSalesman modelSalesman = (ModelSalesman) ((AppCompatActivity) context).getIntent().getSerializableExtra("userDetails");

        if (modelSalesman != null) {
            regId.setValue(modelSalesman.getReg_id());
            userName.setValue(modelSalesman.getUser_name());
            userEmail.setValue(modelSalesman.getEmail_id());
            mobileNo.setValue(modelSalesman.getMobile_no());
            userPhoto.setValue(modelSalesman.getUser_image());
            password.setValue(modelSalesman.getPassword());
            conPassword.setValue(modelSalesman.getPassword());
            userRole.setValue(modelSalesman.getUser_role());
            address.setValue(modelSalesman.getAddress());
            stateName.setValue(modelSalesman.getState_name());
            districtName.setValue(modelSalesman.getDist_name());
            cityName.setValue(modelSalesman.getCity_name());
            areaName.setValue(modelSalesman.getArea_name());
            zoneName.setValue(modelSalesman.getZone_name());
            stateId.setValue(modelSalesman.getState_id());
            districtId.setValue(modelSalesman.getDist_id());
            cityId.setValue(modelSalesman.getCity_id());
            areaId.setValue(modelSalesman.getArea_id());
            isActive.setValue(modelSalesman.getIsActive());
            registeredOn.setValue(modelSalesman.getRegisteredOn());
        }

    }

    public void onClickBackArrow(View view) {
        Context context = ((ContextWrapper) view.getContext()).getBaseContext();
        OprActivity.finishActivity(context);
    }

    public void onClickUploadSalesmanImage(View view) {
        AddSalesmanActivity.showPreview(view);
    }

    public void onClickRegisterSalesman(View view) {
        ModelSalesman modelSalesman = new ModelSalesman(regId.getValue(), userName.getValue(), userRole.getValue(),
                mobileNo.getValue(), userEmail.getValue(), address.getValue(), userPhoto.getValue(),
                password.getValue(), password.getValue(), isActive.getValue(), registeredOn.getValue(),
                areaId.getValue(), stateId.getValue(), districtId.getValue(), cityId.getValue(), stateName.getValue(),
                districtName.getValue(), cityName.getValue(), areaName.getValue(), zoneName.getValue());
        userMutableLiveData.setValue(modelSalesman);
    }

    /*show dialog for service area*/
    public void onClickState(View view) {
        stateDialog(view);
    }

    public void onClickDistrict(View view) {
        districtDialog(view);
    }

    public void onClickCity(View view) {
        cityDialog(view);
    }

    public void onClickArea(View view) {
        areaDialog(view);
    }

    public String stateDialog(final View view) {
        selItem = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select State");
        int checkedItem = -1;

        StateListAdapter adapter = new StateListAdapter(view.getContext(), stateList);

        builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item

                stateName.setValue(stateList.get(which).getState_name());
                stateId.setValue(stateList.get(which).getState_id());
                selItem = stateList.get(which).getState_name();
                Log.e("State name 0: ", stateList.get(which).getState_name());
                Log.e("State name 00: ", " " + stateList.get(which).getState_id());
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK

                if (!selItem.isEmpty()) {
                    dialog.dismiss();
                    ((EditText) view).setText("" + selItem);
                } else {
                    Config.toastShow("Please select state", view.getContext());
                    ((EditText) view).setText("");
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        return selItem;
    }

    public String districtDialog(final View view) {
        // setup the alert builder
        selItem = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select District");
        int checkedItem = -1;

        final ArrayList<ModelDistrict> dList = new ArrayList<>();
        if (!TextUtils.isEmpty(stateId.getValue())) {
            for (int i = 0; i < distList.size(); i++) {
                if (stateId.getValue().equals(distList.get(i).getState_id())) {
                    dList.add(distList.get(i));
                }
            }
        }

        DistrictListAdapter adapter = new DistrictListAdapter(view.getContext(), dList);

        builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                districtName.setValue(dList.get(which).getDist_name());
                districtId.setValue(dList.get(which).getDist_id());
                stateId.setValue(dList.get(which).getState_id());
                selItem = dList.get(which).getDist_name();
                Log.e("Dist name 0: ", dList.get(which).getDist_name());
                Log.e("Dist name 00: ", " " + dList.get(which).getDist_id());
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK

                if (!selItem.isEmpty()) {
                    dialog.dismiss();
                    ((EditText) view).setText("" + selItem);
                } else {
                    Config.toastShow("Please select district", view.getContext());
                    ((EditText) view).setText("");
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        return selItem;
    }

    public String cityDialog(final View view) {
        selItem = "";
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select city");
        int checkedItem = -1;

        final ArrayList<ModelCity> cList = new ArrayList<>();
        if (!TextUtils.isEmpty(stateId.getValue()) && !TextUtils.isEmpty(districtId.getValue())) {
            for (int i = 0; i < cityList.size(); i++) {
                if (stateId.getValue().equals(cityList.get(i).getState_id())
                        && districtId.getValue().equals(cityList.get(i).getDist_id())) {
                    cList.add(cityList.get(i));
                }
            }
        }

        CityListAdapter adapter = new CityListAdapter(view.getContext(), cList);

        builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                cityName.setValue(cList.get(which).getCity_name());
                cityId.setValue(cList.get(which).getCity_id());
                districtId.setValue(cList.get(which).getDist_id());
                stateId.setValue(cList.get(which).getState_id());
                selItem = cList.get(which).getCity_name();
                Log.e("City name 0: ", cList.get(which).getCity_name());
                Log.e("City name 00: ", " " + cList.get(which).getCity_id());
                Log.e("City name 000: ", " " + cList.get(which).getDist_id());
                Log.e("City name 000: ", " " + cList.get(which).getState_id());

            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK;

                if (!selItem.isEmpty()) {
                    //  dialog.dismiss();
                    ((EditText) view).setText("" + selItem);
                } else {
                    //  Config.toastShow("Please select city", view.getContext());
                    ((EditText) view).setText("");
                }

            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        return selItem;
    }

    public String areaDialog(final View view) {
        selItem = "";
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select area");
        int checkedItem = -1;

        final ArrayList<ModelArea> arList = new ArrayList<>();
        if (!TextUtils.isEmpty(stateId.getValue())
                && !TextUtils.isEmpty(districtId.getValue())
                && !TextUtils.isEmpty(cityId.getValue())) {
            for (int i = 0; i < areaList.size(); i++) {
                if (stateId.getValue().equals(areaList.get(i).getState_id())
                        && districtId.getValue().equals(areaList.get(i).getDist_id())
                        && cityId.getValue().equals(areaList.get(i).getCity_id())) {
                    arList.add(areaList.get(i));
                }
            }
        }

        AreaListAdapter adapter = new AreaListAdapter(view.getContext(), arList);

        builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                areaId.setValue(arList.get(which).getArea_id());
                cityId.setValue(arList.get(which).getCity_id());
                districtId.setValue(arList.get(which).getDist_id());
                stateId.setValue(arList.get(which).getState_id());
                selItem = arList.get(which).getArea_name();
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK;

                if (!selItem.isEmpty()) {
                    //  dialog.dismiss();
                    ((EditText) view).setText("" + selItem);
                } else {
                    //  Config.toastShow("Please select city", view.getContext());
                    ((EditText) view).setText("");
                }

            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        return selItem;
    }

    // get All area list from server
    private void getAllAreaList(final Context context) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl = Config.baseUrl + "/allAreaList.php";

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("response:", response);
                    // get State list
                    String state = jsonObject.getString("State");

                    JSONObject stateObject = new JSONObject(state);
                    String stateResult = stateObject.getString("ResultCode");
                    if (stateResult.equals("1")) {
                        JSONArray stateArray = stateObject.getJSONArray("StateList");
                        stateList = new ArrayList<>();
                        for (int i = 0; i < stateArray.length(); i++) {
                            JSONObject jsonObject1 = stateArray.getJSONObject(i);
                            ModelState modelState = new ModelState(jsonObject1.getString("state_id"),
                                    jsonObject1.getString("state_name"));
                            stateList.add(modelState);
                        }

                    } else {
                        stateList.clear();
                    }

                    // get District list
                    String district = jsonObject.getString("District");

                    JSONObject districtObject = new JSONObject(district);
                    String districtResult = districtObject.getString("ResultCode");
                    if (districtResult.equals("1")) {
                        JSONArray distArray = districtObject.getJSONArray("DistrictList");
                        distList = new ArrayList<>();
                        for (int i = 0; i < distArray.length(); i++) {
                            JSONObject jsonObject1 = distArray.getJSONObject(i);
                            ModelDistrict modelDistrict = new ModelDistrict(jsonObject1.getString("dist_id"),
                                    jsonObject1.getString("dist_name"), jsonObject1.getString("state_id"));
                            distList.add(modelDistrict);
                        }

                    } else {
                        distList.clear();
                    }

                    // get city list
                    String city = jsonObject.getString("City");

                    JSONObject cityObject = new JSONObject(city);
                    String cityResult = cityObject.getString("ResultCode");
                    if (cityResult.equals("1")) {
                        JSONArray cityArray = cityObject.getJSONArray("CityList");
                        cityList = new ArrayList<>();
                        for (int i = 0; i < cityArray.length(); i++) {
                            JSONObject jsonObject1 = cityArray.getJSONObject(i);
                            ModelCity modelCity = new ModelCity(jsonObject1.getString("city_id"),
                                    jsonObject1.getString("city_name"), jsonObject1.getString("state_id"),
                                    jsonObject1.getString("dist_id"));
                            cityList.add(modelCity);
                        }

                    } else {
                        cityList.clear();
                    }

                    // get Area list
                    String area = jsonObject.getString("Area");

                    JSONObject areaObject = new JSONObject(area);
                    String areaResult = areaObject.getString("ResultCode");
                    if (areaResult.equals("1")) {
                        JSONArray areaArray = areaObject.getJSONArray("AreaList");
                        areaList = new ArrayList<>();
                        for (int i = 0; i < areaArray.length(); i++) {
                            JSONObject jsonObject1 = areaArray.getJSONObject(i);
                            ModelArea modelArea = new ModelArea(jsonObject1.getString("area_id"),
                                    jsonObject1.getString("area_name"), jsonObject1.getString("zone_name"),
                                    jsonObject1.getString("state_id"), jsonObject1.getString("dist_id"),
                                    jsonObject1.getString("city_id"), jsonObject1.getString("isActive"));
                            areaList.add(modelArea);
                        }

                    } else {
                        areaList.clear();
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
