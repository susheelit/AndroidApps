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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.adapter.CityListAdapter;
import com.irg.crm_admin.adapter.DistrictListAdapter;
import com.irg.crm_admin.adapter.StateListAdapter;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.model.ModelCity;
import com.irg.crm_admin.model.ModelDistrict;
import com.irg.crm_admin.model.ModelServiceArea;
import com.irg.crm_admin.model.ModelState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceAreaViewModel extends ViewModel {

    static int flagAreaType = 0;
    String selItem = "";
    ArrayList<ModelState> stateList;
    ArrayList<ModelDistrict> distList;
    ArrayList<ModelCity> cityList;

    public MutableLiveData<String> stateName = new MutableLiveData<>();
    public MutableLiveData<String> stateId = new MutableLiveData<>();
    public MutableLiveData<String> districtName = new MutableLiveData<>();
    public MutableLiveData<String> districtId = new MutableLiveData<>();
    public MutableLiveData<String> cityName = new MutableLiveData<>();
    public MutableLiveData<String> cityId = new MutableLiveData<>();
    public MutableLiveData<String> areaname = new MutableLiveData<>();
    public MutableLiveData<String> zoneName = new MutableLiveData<>();

    public MutableLiveData<Integer> btnAddNewState = new MutableLiveData<>();
    public MutableLiveData<Integer> btnAddNewDistrict = new MutableLiveData<>();
    public MutableLiveData<Integer> btnAddNewCity = new MutableLiveData<>();
    public MutableLiveData<Integer> btnAddNewArea = new MutableLiveData<>();
    public MutableLiveData<Boolean> spState = new MutableLiveData<>();
    public MutableLiveData<Boolean> spDist = new MutableLiveData<>();
    public MutableLiveData<Boolean> etDistrict = new MutableLiveData<>();
    public MutableLiveData<Boolean> tblDist = new MutableLiveData<>();
    // public MutableLiveData<Boolean> spCity = new MutableLiveData<>();
    public MutableLiveData<Boolean> tblCity = new MutableLiveData<>();
    public MutableLiveData<Boolean> llArea = new MutableLiveData<>();
    public MutableLiveData<Boolean> btnSubmit = new MutableLiveData<>();

    public MutableLiveData<String> mTitle = new MutableLiveData<>();

    private MutableLiveData<ModelServiceArea> areaMutableLiveData;

    public MutableLiveData<ModelServiceArea> getUser(Context context) {

        if (areaMutableLiveData == null) {
            areaMutableLiveData = new MutableLiveData<>();
            mTitle.setValue("Add Service Area");
            getAllAreaList(context);
        }
        return areaMutableLiveData;
    }

    public void onClickBackArrow(View view){
       Context context =  ((ContextWrapper)view.getContext()).getBaseContext();
        OprActivity.finishActivity(context);
    }

    // 0 means visible , 8 means gone
    /*AddNewState*/
    public void onClickAddNewState(View view) {
        Log.e("onClickAddNewState", "onClickAddNewState");
        // view.findViewById(R.id.btnAddNewState).setClickable(false);
        view.setClickable(false);
        btnAddNewState.setValue(0);
        btnAddNewDistrict.setValue(8);
        btnAddNewCity.setValue(8);
        btnAddNewArea.setValue(8);

        spState.setValue(true);
        spDist.setValue(false);
        etDistrict.setValue(false);
        tblDist.setValue(false);
        tblCity.setValue(false);
        llArea.setValue(false);
        btnSubmit.setValue(true);
        flagAreaType = 1;
        Config.toastShow("onClickAddNewState", view.getContext());
    }

    /*AddNewDistrict*/
    public void onClickAddNewDistrict(View view) {
        view.setClickable(false);
        btnAddNewState.setValue(8);
        btnAddNewDistrict.setValue(0);
        btnAddNewCity.setValue(8);
        btnAddNewArea.setValue(8);

        spState.setValue(true);
        spDist.setValue(false);
        etDistrict.setValue(true);
        tblDist.setValue(true);
        tblCity.setValue(false);
        llArea.setValue(false);

        btnSubmit.setValue(true);
        flagAreaType = 2;
        Config.toastShow("onClickAddNewDistrict", view.getContext());
    }

    /*AddNewCity*/
    public void onClickAddNewCity(View view) {
        view.setClickable(false);
        btnAddNewState.setValue(8);
        btnAddNewDistrict.setValue(8);
        btnAddNewCity.setValue(0);
        btnAddNewArea.setValue(8);

        spState.setValue(true);
        spDist.setValue(true);
        etDistrict.setValue(false);
        tblDist.setValue(false);
        tblCity.setValue(true);
        llArea.setValue(false);
        btnSubmit.setValue(true);
        flagAreaType = 3;
        Config.toastShow("onClickAddNewCity", view.getContext());
    }

    /*AddNewArea*/
    public void onClickAddNewArea(View view) {
        view.setClickable(false);
        btnAddNewState.setValue(8);
        btnAddNewDistrict.setValue(8);
        btnAddNewCity.setValue(8);
        btnAddNewArea.setValue(0);

        spState.setValue(true);
        spDist.setValue(true);
        etDistrict.setValue(false);
        tblDist.setValue(false);
        tblCity.setValue(false);
        llArea.setValue(true);
        btnSubmit.setValue(true);
        flagAreaType = 4;
        Config.toastShow("onClickAddNewArea", view.getContext());
    }

    public void onClickAddServiceArea(View view) {
        String url = "";
        final Map<String, String> params = new HashMap<String, String>();
        params.clear();
        if (flagAreaType == 1) {
            // Config.toastShow("Add State", view.getContext());
            if (!TextUtils.isEmpty(stateName.getValue())) {
                params.put("state_name", stateName.getValue());
                params.put("isActive", "1");
                url = "addState.php";
                addServiceArea(view, params, url);
            } else {
                Config.alertBox("Select state", view.getContext());
            }
        } else if (flagAreaType == 2) {
            // Config.toastShow("Add District", view.getContext());
            if (!TextUtils.isEmpty(stateName.getValue())) {
                if (!TextUtils.isEmpty(districtName.getValue())) {
                    params.put("state_id", stateId.getValue());
                    params.put("dist_name", districtName.getValue());
                    params.put("isActive", "1");
                    url = "addDistrict.php";
                    addServiceArea(view, params, url);
                } else {
                    Config.alertBox("Enter district name", view.getContext());
                }

            } else {
                Config.alertBox("Select state", view.getContext());
            }
        } else if (flagAreaType == 3) {
            //Config.toastShow("Add City", view.getContext());
            if (!TextUtils.isEmpty(stateName.getValue())) {
                if (!TextUtils.isEmpty(districtName.getValue())) {
                    if (!TextUtils.isEmpty(cityName.getValue())) {
                        params.put("state_id", stateId.getValue());
                        params.put("dist_id", districtId.getValue());
                        params.put("city_name", cityName.getValue());
                        params.put("isActive", "1");
                        url = "addCity.php";
                        addServiceArea(view, params, url);
                    } else {
                        Config.alertBox("Enter city name", view.getContext());
                    }
                } else {
                    Config.alertBox("Select district", view.getContext());
                }
            } else {
                Config.alertBox("Select State", view.getContext());
            }
        } else if (flagAreaType == 4) {
            // Config.toastShow("Add Area", view.getContext());
            if (!TextUtils.isEmpty(stateName.getValue())) {
                if (!TextUtils.isEmpty(districtName.getValue())) {
                    if (!TextUtils.isEmpty(cityName.getValue())) {
                        if (!TextUtils.isEmpty(zoneName.getValue())) {
                            if (!TextUtils.isEmpty(areaname.getValue())) {
                                params.put("state_id", stateId.getValue());
                                params.put("dist_id", districtId.getValue());
                                params.put("city_id", cityId.getValue());
                                params.put("zone_name", zoneName.getValue());
                                params.put("area_name", areaname.getValue());
                                params.put("isActive", "1");
                                url = "addArea.php";
                                addServiceArea(view, params, url);
                            } else {
                                Config.alertBox("Enter area name", view.getContext());
                            }
                        } else {
                            Config.alertBox("Enter zone name", view.getContext());
                        }
                    } else {
                        Config.alertBox("Select city", view.getContext());
                    }
                } else {
                    Config.alertBox("Select district", view.getContext());
                }
            } else {
                Config.alertBox("Select State", view.getContext());
            }
        }
    }

    private void addServiceArea(final View view, final Map<String, String> params, String url) {

        final ProgressDialog progressDialog = ProgressDialog.show(view.getContext(), "", "Please wait...", true, false);

        String urlApi = Config.baseUrl + url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), view.getContext());
                      //  OprActivity.finishAllOpenNewActivity(view.getContext(), new LoginActivity());
                    } else {
                        Config.alertBox(jsonObject.getString("Message"), view.getContext());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Log.e("add shop data error ", "params value is " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.e("Add Service ", "params value is " + params);
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

        MySingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    /* show state list dialog*/
    public void onClickState(View view) {
        if (flagAreaType == 1) {
            String stateList[] = view.getContext().getResources().getStringArray(R.array.states_list);
            String selItem = addStateDialog(view, stateList);
            Log.e("selItem ", selItem);
        } else {
            // get area list from server
            //   getAllAreaList(view);
            stateDialog(view);
        }

    }

    /* show District list dialog*/
    public void onClickDistrict(View view) {
        //  getAllAreaList(view);
        districtDialog(view);

    }

    /* show City list dialog*/
    public void onClickCity(View view) {
        cityDialog(view);
    }

    public String addStateDialog(final View view, final String[] listItem) {
        selItem = "";
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Choose an service area");
        int checkedItem = -1;
        builder.setSingleChoiceItems(listItem, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item

                selItem = listItem[which];
                Log.e("State List 0: ", selItem);
                Log.e("State name 00: ", " "+which);
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                Log.e("State List 1: ", selItem);
                Log.e("State name 11: ", " "+which);

                if (!selItem.isEmpty()) {
                    dialog.dismiss();
                    ((EditText) view).setText(""+selItem);
                } else {
                    Config.toastShow("Please select an area", view.getContext());
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

    public String stateDialog(final View view) {
        // setup the alert builder
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
                Log.e("State name 00: ", " "+stateList.get(which).getState_id());
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

        final ArrayList<ModelDistrict>dList = new ArrayList<>();
        if(!TextUtils.isEmpty(stateId.getValue())){
            for(int i=0; i<distList.size();i++){
                if(stateId.getValue().equals(distList.get(i).getState_id())){
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
                Log.e("Dist name 00: ", " "+dList.get(which).getDist_id());
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

        final ArrayList<ModelCity>cList = new ArrayList<>();
        if(!TextUtils.isEmpty(stateId.getValue()) && !TextUtils.isEmpty(districtId.getValue())){
            for(int i=0; i<cityList.size();i++){
                if(stateId.getValue().equals(cityList.get(i).getState_id())
                && districtId.getValue().equals(cityList.get(i).getDist_id())){
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
                Log.e("City name 00: ", " "+cList.get(which).getCity_id());
                Log.e("City name 000: ", " "+cList.get(which).getDist_id());
                Log.e("City name 000: ", " "+cList.get(which).getState_id());

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
                    //stateDialog(view);
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
