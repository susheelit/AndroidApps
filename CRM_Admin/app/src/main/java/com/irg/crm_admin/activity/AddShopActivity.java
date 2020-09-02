package com.irg.crm_admin.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.common.SharedPref;
import com.irg.crm_admin.databinding.ActivityAddShopBinding;
import com.irg.crm_admin.fragment.ShopListFragment;
import com.irg.crm_admin.model.ModelShop;
import com.irg.crm_admin.viewModel.AddShopViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddShopActivity extends AppCompatActivity {

    private AddShopViewModel addShopViewModel;
    private ActivityAddShopBinding binding;
    private static Context mContext;
    private static final int PERMISSION_REQUEST = 0;
    String encodedImg, picturePath, imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addShopViewModel = ViewModelProviders.of(this).get(AddShopViewModel.class);
        binding = DataBindingUtil.setContentView(AddShopActivity.this, R.layout.activity_add_shop);
        binding.setLifecycleOwner(this);

        binding.setViewModel(addShopViewModel);

        addShopViewModel.getUser(this).observe(this, new Observer<ModelShop>() {
            @Override
            public void onChanged(@Nullable ModelShop modelShop) {

                /*if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getMobileNo())){
                    binding.etMobile.setError("Enter Mobile no");
                    binding.etMobile.requestFocus();
                }else if(Objects.requireNonNull(loginUser).getMobileNo().length() !=10){
                    binding.etMobile.setError("Enter valid Mobile no");
                    binding.etMobile.requestFocus();
                }else if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())){
                    binding.etPassword.setError("Enter password");
                    binding.etPassword.requestFocus();
                }else if(!(Objects.requireNonNull(loginUser).getPassword().length() >5
                        && Objects.requireNonNull(loginUser).getPassword().length() <11 )){
                    binding.etPassword.setError("Password length should be between 5-10 characters.");
                    binding.etPassword.requestFocus();
                }else {
                    //Config.toastShow("Login Success",LoginActivity.this);
                    attemptLogin(AddShopActivity.this, loginUser.getMobileNo(), loginUser.getPassword() );
                }*/
                addShop(modelShop);

            }
        });
    }

    private void addShop(final ModelShop modelShop) {
         mContext = AddShopActivity.this;
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Please wait...", true, false);
        String url = "";

        final String shopType = getIntent().getStringExtra("shopType");
        if(shopType.equalsIgnoreCase("Update")){
            url = "updateShop.php";
        }else {
            url = "addShop.php";
        }

        String urlApi = Config.baseUrl +url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response ", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("ResultCode");
                    if (resultCode.equalsIgnoreCase("1")) {
                        finish();
                        ShopListFragment.getShopList(mContext);
                        //ApiOperation.refreshShoplist(mContext);
                    }
                    Config.alertBox(jsonObject.getString("Message"), mContext);

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

                if(shopType.equalsIgnoreCase("Update")){
                    params.put("shop_id", modelShop.getShop_id());
                }
                params.put("addBy", SharedPref.getUserID(mContext));
                params.put("shop_img",encodedImg);
                params.put("shop_name",modelShop.getShop_name());
                params.put("owner_name",modelShop.getOwner_name());
                params.put("owner_email_id",modelShop.getOwner_email_id());
                params.put("owner_mobile", modelShop.getOwner_mobile());
                params.put("owner_landline_no",modelShop.getOwner_landline_no());
                params.put("address",modelShop.getAddress());
                params.put("area_id", modelShop.getArea_id());
                params.put("city_id",modelShop.getCity_id());
                params.put("dist_id",modelShop.getDist_id());
                params.put("state_id",modelShop.getState_id());
                params.put("pincode",modelShop.getPincode());
                params.put("isActive","1");
                Log.e("add Shop data ", "params value is " + params);
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

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*select image*/
    public static void showPreview(View view) {
        mContext = view.getContext();
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview

            selectImage();
        } else {
            // Permission is missing and must be requested.
            requestPermission();
        }
    }

    public static void requestPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(((AppCompatActivity) mContext), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(((AppCompatActivity) mContext), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(((AppCompatActivity) mContext), android.Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setMessage("Permission access is required to display the preview.");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            ActivityCompat.requestPermissions(((AppCompatActivity) mContext),
                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                                    PERMISSION_REQUEST);
                            // finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        } else {

            Toast.makeText(mContext, "Permission is not available. Requesting permission.", Toast.LENGTH_SHORT).show();

            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(((AppCompatActivity) mContext), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST) {
            // Request for camera permission.
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(mContext, "permission granted.", Toast.LENGTH_SHORT).show();

                selectImage();
            } else {
                // Permission request was denied.

                Toast.makeText(mContext, "permission request was denied.", Toast.LENGTH_SHORT).show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    public static void selectImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((AppCompatActivity) mContext).startActivityForResult(intent, 1);

        /*final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ((AppCompatActivity) mContext).startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((AppCompatActivity) mContext).startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                //  thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                picturePath = destination.getPath();
                imageName = destination.getName();

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // mUploadProfile.setImageBitmap(thumbnail);
                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
                byte[] picByteArray = getByteArray(scaled);

                encodedImg = Base64.encodeToString(picByteArray, Base64.DEFAULT);
                //  ivLeftImage.setImageBitmap(scaled);
                //  ((ImageView) findViewById(R.id.ivImage)).setImageBitmap(scaled);
                binding.ivImage.setImageBitmap(scaled);
                Log.e("encodedImg", encodedImg);
                Log.e("Image Name", imageName);

                // imgName.setText(imageName);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (requestCode == 2) {
            try {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};


                Cursor c = (mContext).getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                File destination = new File(picturePath);

                imageName = destination.getName();
                c.close();
                //  Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Bitmap thumbnail = getBitmap(picturePath);

                Log.e("path gallarey", picturePath + "");
                Log.e("Bitmap", thumbnail + "");

                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);

                byte[] picByteArray = getByteArray(scaled);

                encodedImg = Base64.encodeToString(picByteArray, Base64.DEFAULT);
                //   ((ImageView) findViewById(R.id.ivImage)).setImageBitmap(scaled);
                binding.ivImage.setImageBitmap(scaled);
                Log.e("encodedImg", encodedImg);
                Log.e("Image Name", imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    protected byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {

            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}

