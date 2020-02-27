package com.irgsol.irg_crm.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.utils.MySingleton;
import com.irgsol.irg_crm.utils.UserPermission;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class AddShopActivity_1 extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit, btnUpload;//, btnCancel;
    private ImageView ivImage;
    private EditText etShopName, etOwner, etEmail, etMobile, etLandline, etAddress, etCity, etDistrict, etState, etPincode;

    Context context;
    Toolbar toolbar;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLARY_REQUEST = 1000;
    Bitmap imageBitmap = null;

    Map<String, String> shopDetail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_1);
        initView();
    }

    private void initView() {
        context = AddShopActivity_1.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Basic Information", context);

        ivImage = findViewById(R.id.ivImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUpload = findViewById(R.id.btnUpload);
        etShopName = findViewById(R.id.etShopName);
        etOwner = findViewById(R.id.etOwner);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etLandline = findViewById(R.id.etLandline);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etDistrict = findViewById(R.id.etDistrict);
        etState = findViewById(R.id.etState);
        etPincode = findViewById(R.id.etPincode);

        shopDetail = new HashMap<>();

        btnUpload.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        etState.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSubmit:
              //  Config.toastShow("Shop Added", context);
                validate();
                break;
           case R.id.etState:
               showStateDialog();
                break;
            case R.id.btnUpload:
                // photoDialog(context);
                openCamera();
                break;
        }
    }

    private void showStateDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
       // builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select state:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
       arrayAdapter.addAll(getResources().getStringArray(R.array.states_list));

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String stateName = arrayAdapter.getItem(which);
                etState.setText(stateName);

            }
        });
        builderSingle.show();
    }

    private void validate() {

        etShopName.setError(null);
        etOwner.setError(null);
        etEmail.setError(null);
        etMobile.setError(null);
        etAddress.setError(null);
        etCity.setError(null);
        etDistrict.setError(null);
        etState.setError(null);
        etPincode.setError(null);

        boolean isCancel = false;
        View focusView = null;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String shopName = etShopName.getText().toString().trim();
        String owner = etOwner.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String landline = etLandline.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String pincode = etPincode.getText().toString().trim();

        if (imageBitmap == null) {
            Config.alertBox("Please upload shop image", context);
            isCancel = true;
        }

        if (shopName.isEmpty()) {
            etShopName.setError("Enter shop name");
            focusView = etShopName;
            isCancel = true;
        }

        if (owner.isEmpty()) {
            etOwner.setError("Enter shop owner name");
            focusView = etOwner;
            isCancel = true;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter owner email id");
            focusView = etEmail;
            isCancel = true;
        }else if(!email.matches(emailPattern)){
            etEmail.setError("Enter valid email id");
            focusView = etEmail;
            isCancel = true;
        }

        if (mobile.isEmpty()) {
            etMobile.setError("Enter owner mobile no");
            focusView = etMobile;
            isCancel = true;
        } else if (mobile.length() != 10) {
            etMobile.setError("Enter 10 digit mobile no");
            focusView = etMobile;
            isCancel = true;
        }

        if (address.isEmpty()) {
            etAddress.setError("Enter Shop address");
            focusView = etAddress;
            isCancel = true;
        }

        if (city.isEmpty()) {
            etCity.setError("Enter city name");
            focusView = etCity;
            isCancel = true;
        }

        if (district.isEmpty()) {
            etDistrict.setError("Enter district name");
            focusView = etDistrict;
            isCancel = true;
        }

        if (state.isEmpty()) {
            etState.setError("Select state");
            focusView = etState;
            isCancel = true;
        }

        if (pincode.isEmpty()) {
            etPincode.setError("Enter pincode");
            focusView = etPincode;
            isCancel = true;

        } else if (pincode.length() != 6) {
            etPincode.setError("Enter 6 digit pincode");
            focusView = etPincode;
            isCancel = true;
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            //add shop
           String imageString = Config.convertBitmapToBase64String(imageBitmap);

            shopDetail.put("shopImage", "");
            shopDetail.put("shopName", shopName);
            shopDetail.put("owner", owner);
            shopDetail.put("email", email);
            shopDetail.put("mobile", mobile);
            shopDetail.put("landline", landline);
            shopDetail.put("address", address);
            shopDetail.put("city", city);
            shopDetail.put("district", district);
            shopDetail.put("state", state);
            shopDetail.put("pincode", pincode);

            attemptAddShop(shopDetail);

        }

    }

    private void attemptAddShop(final Map<String, String> shopDetail) {

        //Config.toastShow("Shop Added", context);
        String urlApi = Config.baseUrl+"addShop.php";
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if(resultCode.equalsIgnoreCase("1")){
                        Config.toastShow(jsonObject.getString("Message"), context);
                        OprActivity.finishAllOpenNewActivity(context, new DasboardNewActivity());
                    }else {
                        Config.alertBox(jsonObject.getString("Message"), context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("add shop data error ","params value is "+ error.toString());
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.putAll(shopDetail);
                Log.e("add shop data ","params value is "+params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /*private void photoDialog(Context context) {

        final TextView tvCamera, tvGallery;
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_photo_diag);

        dialog.setTitle("Select...");

        tvCamera = dialog.findViewById(R.id.tvCamera);
        tvGallery = dialog.findViewById(R.id.tvGallery);

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openCamera();
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openGallery();
            }
        });

        dialog.show();

    }*/

    /*private void openGallery() {
        if (UserPermission.getGallaryPermission(AddShopActivity_1.this)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, GALLARY_REQUEST);
        }

    }*/

    private void openCamera() {
        if (UserPermission.getCameraPermission(AddShopActivity_1.this)) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            Config.setCircularBitmap(context, ivImage, imageBitmap);
        }

        if (requestCode == GALLARY_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            Config.setCircularImage(context, ivImage, selectedImage);
        }
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
