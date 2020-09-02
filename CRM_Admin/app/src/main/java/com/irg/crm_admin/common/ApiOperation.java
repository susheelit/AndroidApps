package com.irg.crm_admin.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.fragment.AreaFragment;
import com.irg.crm_admin.fragment.CityFragment;
import com.irg.crm_admin.fragment.DistrictFragment;
import com.irg.crm_admin.fragment.MyProductFragment;
import com.irg.crm_admin.fragment.MySalesmanFragment;
import com.irg.crm_admin.fragment.ShopListFragment;
import com.irg.crm_admin.fragment.StateFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiOperation {

    /*delete shop*/
    public static void deleteShop(final Context context, final String shopId, final String updateValue, final Dialog dialog) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);

        String urlApi = Config.baseUrl + "removeShop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), context);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        ShopListFragment.getShopList(context);
                        // refreshShoplist(context);
                    } else {
                        Config.alertBox(jsonObject.getString("Message"), context);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("shop_id", shopId);
                params.put("isActive", updateValue);
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

    public static void deleteProduct(final Context context, final String prodId, final String updateValue, final Dialog dialog1) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);

        String urlApi = Config.baseUrl + "removeProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), context);
                        if (dialog1 != null) {
                            dialog1.dismiss();
                        }
                        MyProductFragment.getProductList(context);
                        // refreshShoplist(context);
                    } else {
                        Config.alertBox(jsonObject.getString("Message"), context);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("prod_id", prodId);
                params.put("isActive", updateValue);
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

    public static void updateArea(final Context context, final String id, final String name, final Dialog dialog1, final int areaType) {
        // 1= state, 2= district, 3= city, 4= area
        String url = "";
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);

        String urlApi = Config.baseUrl + "updateArea.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), context);
                        if (dialog1 != null) {
                            dialog1.dismiss();
                        }

                        if (areaType == 1) {
                            StateFragment.getStateList(context);
                        } else if (areaType == 2) {
                            DistrictFragment.getDistList(context);
                        } else if (areaType == 3) {
                            CityFragment.getCityList(context);
                        } else if (areaType == 4) {
                            AreaFragment.getAreaList(context);
                        }
                    } else {
                        Config.alertBox(jsonObject.getString("Message"), context);
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
                Log.e("Update area error ", "params value is " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("areaType", "" + areaType);
                params.put("_id", id);
                params.put("_name", name);
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

    public static void deleteServiceArea(String id, int updateValue, final Context context, final int areaType) {
        // 1= state, 2= district, 3= city, 4= area
        String url = "";
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);

        final Map<String, String> params = new HashMap<String, String>();

        if (areaType == 1) {
            url = "deleteState.php";
            params.put("state_id", id);
            params.put("isActive", "" + updateValue);
        } else if (areaType == 2) {
            url = "deleteDist.php";
            params.put("dist_id", id);
            params.put("isActive", "" + updateValue);

        } else if (areaType == 3) {
            url = "deleteCity.php";
            params.put("city_id", id);
            params.put("isActive", "" + updateValue);
        } else if (areaType == 4) {
            url = "deleteArea.php";
            params.put("area_id", id);
            params.put("isActive", "" + updateValue);
        }

        String urlApi = Config.baseUrl + "deleteServiceArea.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), context);

                        if (areaType == 1) {
                            StateFragment.getStateList(context);
                        } else if (areaType == 2) {
                            DistrictFragment.getDistList(context);
                        } else if (areaType == 3) {
                            CityFragment.getCityList(context);
                        } else if (areaType == 4) {
                            AreaFragment.getAreaList(context);
                        }

                    } else {
                        Config.alertBox(jsonObject.getString("Message"), context);
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
                Log.e("Update area error ", "params value is " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
               /* Map<String, String> params = new HashMap<String, String>();
                params.put("prod_id",prodId);
                params.put("isActive", updateValue);*/
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

    public static void deleteUser(final Context context, final String userId, final String updateValue, final Dialog dialog1) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String urlApi = Config.baseUrl + "removeUser.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        Config.toastShow(jsonObject.getString("Message"), context);
                        if (dialog1 != null) {
                            dialog1.dismiss();
                        }
                        MySalesmanFragment.getSalesmanList(context);
                    } else {
                        Config.alertBox(jsonObject.getString("Message"), context);
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
                Log.e("add user data error ", "params value is " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("reg_id", userId);
                params.put("isActive", updateValue);
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
