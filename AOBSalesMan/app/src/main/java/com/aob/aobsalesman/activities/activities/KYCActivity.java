package com.aob.aobsalesman.activities.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.BuildConfig;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class KYCActivity extends AppCompatActivity {

    Uri photoURI;
    androidx.appcompat.widget.AppCompatEditText pan_no, aadhar_no, bank_account_number, bank_account_name, bank_branch, ifsc_code, bank_name;
    TextInputLayout pan_no_ti, aadhar_no_ti, bank_account_number_ti, bank_account_name_ti, bank_branch_ti, ifsc_code_ti;

    String string_pan_no, string_aadhar_no, string_bank_account_number, string_bank_account_name, string_bank_branch, string_ifsc_code, string_bank_name;
    private ProgressDialog progressDialog;

    //Pdf request code
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_CAMERA_REQUEST = 2;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    int checkway = 0;

    String base64_pan = "";
    String base64_aadhaar = "";
    String base64_cheque = "";

    @SuppressLint("HardwareIds")
    String android_id;

    //Uri to store the image uri
    private Uri filePath;

    long mLastClickTime40 = 0;
    long mLastClickTime41 = 0;
    long mLastClickTime42 = 0;
    long mLastClickTime43 = 0;

    TextView tv_pan, tv_aadhaar, tv_canceled_cheque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }


        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        (findViewById(R.id.kyc_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime40 < 1000) {
                    return;
                }
                mLastClickTime40 = SystemClock.elapsedRealtime();

                validation();
            }
        });


        (findViewById(R.id.upload_pan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime41 < 1000) {
                    return;
                }
                mLastClickTime41 = SystemClock.elapsedRealtime();

                checkway = 1;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showDialog();
                    } else {
                        showFileChooserImage();
                        // Implement this feature without material design
                    }
                } else {
                    requestStoragePermission();
                }
            }
        });


        (findViewById(R.id.upload_aadhaar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime42 < 1000) {
                    return;
                }
                mLastClickTime42 = SystemClock.elapsedRealtime();

                checkway = 2;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showDialog();
                    } else {
                        showFileChooserImage();
                        // Implement this feature without material design
                    }
                } else {
                    requestStoragePermission();
                }
            }
        });


        findViewById(R.id.upload_canceled_cheque).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime43 < 1000) {
                    return;
                }
                mLastClickTime43 = SystemClock.elapsedRealtime();

                checkway = 3;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showDialog();
                    } else {
                        showFileChooserImage();
                        // Implement this feature without material design
                    }
                } else {
                    requestStoragePermission();
                }
            }
        });

        tv_aadhaar = findViewById(R.id.tv_aadhaar);
        tv_pan = findViewById(R.id.tv_pan);
        tv_canceled_cheque = findViewById(R.id.tv_canceled_cheque);
        pan_no = findViewById(R.id.pan_no);
        aadhar_no = findViewById(R.id.aadhar_no);
        bank_account_number = findViewById(R.id.bank_account_number);
        bank_account_name = findViewById(R.id.bank_account_name);
        bank_branch = findViewById(R.id.bank_branch);
        ifsc_code = findViewById(R.id.ifsc_code);
        bank_name = findViewById(R.id.bank_name);

        setValueAfterRegsitration();

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.diolog_layout);
        dialog.findViewById(R.id.sel_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooserImage();
                dialog.dismiss();

            }
        });

        dialog.findViewById(R.id.sel_pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void validation() {
        pan_no = findViewById(R.id.pan_no);
        aadhar_no = findViewById(R.id.aadhar_no);
        bank_account_number = findViewById(R.id.bank_account_number);
        bank_account_name = findViewById(R.id.bank_account_name);
        bank_branch = findViewById(R.id.bank_branch);
        ifsc_code = findViewById(R.id.ifsc_code);
        bank_name = findViewById(R.id.bank_name);

        // Reset errors.
        pan_no.setError(null);
        aadhar_no.setError(null);
        bank_account_number.setError(null);
        bank_account_name.setError(null);
        bank_branch.setError(null);
        ifsc_code.setError(null);
        bank_name.setError(null);

        tv_canceled_cheque.setError(null);
        tv_aadhaar.setError(null);
        tv_pan.setError(null);

        // Store values at the time of the login attempt.
        string_pan_no = pan_no.getText().toString();
        string_aadhar_no = aadhar_no.getText().toString();
        string_bank_account_number = bank_account_number.getText().toString();
        string_bank_account_name = bank_account_name.getText().toString();
        string_bank_name = bank_name.getText().toString();
        string_bank_branch = bank_branch.getText().toString();
        string_ifsc_code = ifsc_code.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (TextUtils.isEmpty(tv_canceled_cheque.getText().toString())) {
            tv_canceled_cheque.setError("");
            focusView = tv_canceled_cheque;
            cancel = true;
        }


        if (TextUtils.isEmpty(string_ifsc_code)) {
            ifsc_code.setError("Please enter 11 digit IFSC code");
            focusView = ifsc_code;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_ifsc_code)) {
            ifsc_code.setError("Please enter 11 digit IFSC code");
            focusView = ifsc_code;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_bank_branch)) {
            bank_branch.setError("Please enter your bank branch");
            focusView = bank_branch;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_bank_name)) {
            bank_name.setError("Please enter your bank name");
            focusView = bank_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_bank_account_number)) {
            bank_account_number.setError("Please enter bank account number");
            focusView = bank_account_number;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_bank_account_name)) {
            bank_account_name.setError("Please enter your beneficiary name");
            focusView = bank_account_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(tv_aadhaar.getText().toString())) {
            tv_aadhaar.setError("");
            focusView = tv_aadhaar;
            cancel = true;
        }

        if (!isAadhaarValid(string_aadhar_no)) {
            aadhar_no.setError("Please enter 12 digit aadhar number");
            focusView = aadhar_no;
            cancel = true;
        }

        if (TextUtils.isEmpty(tv_pan.getText().toString())) {
            tv_pan.setError("");
            focusView = tv_pan;
            cancel = true;
        }

        if (!isPANValid(string_pan_no)) {
            pan_no.setError("Please enter 10 digit PAN");
            focusView = pan_no;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if (InternetConnection.checkInternetConnectivity()) {

                progressDialog = new ProgressDialog(this);

                try {

                    android_id = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                } catch (Exception e) {
                }

                jsonParseForLogin();
            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(KYCActivity.this);
                alertDialog.setMessage("Retry with Internet connection.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }
    }

    private void jsonParseForLogin() {
        findViewById(R.id.kyc_submit).setEnabled(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName) + "/app/sales/v1_0/kyc_api.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales", "reesponse " + response);
                        progressDialog.dismiss();
                        findViewById(R.id.kyc_submit).setEnabled(true);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            if (success.equals("0")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    ShareprefreancesUtility.getInstance().saveString("kyc_status", jsonObject1.getString("kyc_status"));
                                    ShareprefreancesUtility.getInstance().saveString("pan_number", jsonObject1.getString("pan_number"));
                                    ShareprefreancesUtility.getInstance().saveString("aadhaar_number", jsonObject1.getString("aadhaar_number"));
                                    ShareprefreancesUtility.getInstance().saveString("beneficiary_name", jsonObject1.getString("beneficiary_name"));
                                    ShareprefreancesUtility.getInstance().saveString("bank_account_number", jsonObject1.getString("bank_account_number"));
                                    ShareprefreancesUtility.getInstance().saveString("bank_name", jsonObject1.getString("bank_name"));
                                    ShareprefreancesUtility.getInstance().saveString("branch", jsonObject1.getString("branch"));
                                    ShareprefreancesUtility.getInstance().saveString("ifsc_code", jsonObject1.getString("ifsc_code"));
                                }

                                Toast.makeText(KYCActivity.this, "KYC Submitted Successfully", Toast.LENGTH_SHORT).show();
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(KYCActivity.this);
                                alertDialog.setMessage("Please wait for 24 hours. Till then browse through our projects.");
                                alertDialog.setCancelable(false);
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent intent = new Intent(KYCActivity.this, ProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                    }
                                });
                                alertDialog.show();

                            } else if (success.equals("1")) {
                                Toast.makeText(KYCActivity.this, "KYC Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.kyc_submit).setEnabled(true);
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(KYCActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();

                Log.e("aobsales", "errot " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                params.put("pan_number", string_pan_no);
                params.put("aadhaar_number", string_aadhar_no);
                params.put("bank_account_number", string_bank_account_number);
                params.put("beneficiary_name", string_bank_account_name);
                params.put("branch", string_bank_branch);
                params.put("bank_name", string_bank_name);
                params.put("ifsc_code", string_ifsc_code);
                params.put("upload_pan", base64_pan);
                params.put("upload_aadhaar", base64_aadhaar);
                params.put("upload_canceled_cheque", base64_cheque);
                params.put("android_id", android_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(KYCActivity.this).addToRequestQueue(stringRequest);

    }

    //method to show file chooser
    private void showFileChooserImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_IMAGE_REQUEST);
    }

    /*private void showFileChooserPDF() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_PDF_REQUEST);
    }*/

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            filePath = data.getData();
            if (checkway == 1) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                   /* Drawable d = new BitmapDrawable(getResources(), bitmap);
                    mImageView.setImageDrawable(d);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    base64_pan = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_pan.setText(name);
                }
            } else if (checkway == 2) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_aadhaar = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_aadhaar.setText(name);
                }

            } else if (checkway == 3) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_cheque = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_canceled_cheque.setText(name);
                }

            }
        } else if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK) {
           /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");*/
           /* Bitmap bitmap = null;
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                   *//* Drawable d = new BitmapDrawable(getResources(), bitmap);
                    mImageView.setImageDrawable(d);*//*
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(checkway == 1)
            {

                base64_pan = convertBitmapToBase64String(imageBitmap);
                if(!TextUtils.isEmpty(base64_pan)) {
                    tv_pan.setText("Selected From Camera");
                }
            }
            else if(checkway == 2)
            {
                base64_aadhaar = convertBitmapToBase64String(imageBitmap);
                Log.e("aobsales","aob "+base64_aadhaar);
                if(!TextUtils.isEmpty(base64_aadhaar)) {
                    tv_aadhaar.setText("Selected From Camera");
                }
            }
            else if(checkway == 3)
            {
                base64_cheque = convertBitmapToBase64String(imageBitmap);
                if(!TextUtils.isEmpty(base64_cheque)) {
                    tv_canceled_cheque.setText("Selected From Camera");
                }
            }
*/
            File cachePath = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");
            if (Build.VERSION.SDK_INT <= 23) {
                // Do some stuff
                photoURI = Uri.fromFile(cachePath);
            } else {
                // Do some stuff
                filePath = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        cachePath);
            }

            //  filePath = Uri.parse(photoURI);

            if (checkway == 1) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                   /* Drawable d = new BitmapDrawable(getResources(), bitmap);
                    mImageView.setImageDrawable(d);*/
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    base64_pan = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_pan.setText(name);
                }
            } else if (checkway == 2) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_aadhaar = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_aadhaar.setText(name);
                }

            } else if (checkway == 3) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_cheque = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_canceled_cheque.setText(name);
                }

            }
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast

                if (Build.VERSION.SDK_INT >= 24) {
                    // Call some material design APIs here
                    showDialog();
                } else {
                    showFileChooserImage();
                    // Implement this feature without material design
                }
            } else {

            }
        }
    }

    private boolean checkReadExternalPermission() {
        String permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

   /* private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.diolog_layout);
        dialog.findViewById(R.id.sel_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showFileChooserImage();
                dialog.dismiss();

            }
        });
      *//*  dialog.findViewById(R.id.sel_pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                captureImage();
                dialog.dismiss();

            }
        });
        dialog.show();*//*
    }*/

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Do some stuff
        photoURI = FileProvider.getUriForFile(this, getContext().getApplicationContext().getPackageName() + ".fileprovider", file);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, PICK_CAMERA_REQUEST);
    }

    private String convertBitmapToBase64String(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(byteArray, Base64.CRLF);
        Log.d("base64String", imageString);
        return imageString;
    }


    public void setValueAfterRegsitration() {

        try {

            if (!ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("pending")) {

                Log.e("aobsales", "test " + ShareprefreancesUtility.getInstance().getString("pan_number", ""));

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("pan_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("pan_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("pan_number", "") != null
                ) {

                    string_pan_no = ShareprefreancesUtility.getInstance().getString("pan_number", "");
                    pan_no.setText(ShareprefreancesUtility.getInstance().getString("pan_number", ""));
                    pan_no.setEnabled(false);
                    tv_pan.setText("Submitted");
                    findViewById(R.id.upload_pan).setEnabled(false);

                    tv_canceled_cheque.setText("Submitted");
                    findViewById(R.id.upload_canceled_cheque).setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_account_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_account_number", "") != null
                ) {

                    string_bank_account_number = ShareprefreancesUtility.getInstance().getString("bank_account_number", "");
                    bank_account_number.setText(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""));
                    bank_account_number.setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("aadhaar_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("aadhaar_number", "") != null
                ) {

                    string_aadhar_no = ShareprefreancesUtility.getInstance().getString("aadhaar_number", "");
                    aadhar_no.setText(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""));
                    aadhar_no.setEnabled(false);

                    tv_aadhaar.setText("Submitted");
                    findViewById(R.id.upload_aadhaar).setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("beneficiary_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("beneficiary_name", "") != null
                ) {

                    string_bank_account_name = ShareprefreancesUtility.getInstance().getString("beneficiary_name", "");
                    bank_account_name.setText(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""));
                    bank_account_name.setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("branch", ""))
                        && !ShareprefreancesUtility.getInstance().getString("branch", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("branch", "") != null
                ) {

                    string_bank_branch = ShareprefreancesUtility.getInstance().getString("branch", "");
                    bank_branch.setText(ShareprefreancesUtility.getInstance().getString("branch", ""));
                    bank_branch.setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_name", "") != null
                ) {

                    string_bank_name = ShareprefreancesUtility.getInstance().getString("bank_name", "");
                    bank_name.setText(ShareprefreancesUtility.getInstance().getString("bank_name", ""));
                    bank_name.setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""))
                        && !ShareprefreancesUtility.getInstance().getString("ifsc_code", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("ifsc_code", "") != null
                ) {

                    string_ifsc_code = ShareprefreancesUtility.getInstance().getString("ifsc_code", "");
                    ifsc_code.setText(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""));
                    ifsc_code.setEnabled(false);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("kyc_status", ""))
                        && !ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("kyc_status", "") != null
                ) {

                    if (ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("complete")) {

                        ((TextView) findViewById(R.id.kyc_submit)).setText("Approval Pending");
                        findViewById(R.id.kyc_submit).setEnabled(false);

                    } else if (ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("approved")) {

                        ((TextView) findViewById(R.id.kyc_submit)).setText("Approved");
                        findViewById(R.id.kyc_submit).setEnabled(false);

                    }


                }
            }
            if (ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("Rejected")) {

                Log.e("aobsales", "test " + ShareprefreancesUtility.getInstance().getString("pan_number", ""));

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("pan_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("pan_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("pan_number", "") != null
                ) {

                    string_pan_no = ShareprefreancesUtility.getInstance().getString("pan_number", "");
                    pan_no.setText(ShareprefreancesUtility.getInstance().getString("pan_number", ""));
                    pan_no.setEnabled(true);
                    tv_pan.setText("");
                    findViewById(R.id.upload_pan).setEnabled(true);

                    tv_canceled_cheque.setText("");
                    findViewById(R.id.upload_canceled_cheque).setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_account_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_account_number", "") != null
                ) {

                    string_bank_account_number = ShareprefreancesUtility.getInstance().getString("bank_account_number", "");
                    bank_account_number.setText(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""));
                    bank_account_number.setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("aadhaar_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("aadhaar_number", "") != null
                ) {

                    string_aadhar_no = ShareprefreancesUtility.getInstance().getString("aadhaar_number", "");
                    aadhar_no.setText(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""));
                    aadhar_no.setEnabled(true);

                    tv_aadhaar.setText("");
                    findViewById(R.id.upload_aadhaar).setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("beneficiary_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("beneficiary_name", "") != null
                ) {

                    string_bank_account_name = ShareprefreancesUtility.getInstance().getString("beneficiary_name", "");
                    bank_account_name.setText(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""));
                    bank_account_name.setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("branch", ""))
                        && !ShareprefreancesUtility.getInstance().getString("branch", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("branch", "") != null
                ) {

                    string_bank_branch = ShareprefreancesUtility.getInstance().getString("branch", "");
                    bank_branch.setText(ShareprefreancesUtility.getInstance().getString("branch", ""));
                    bank_branch.setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_name", "") != null
                ) {

                    string_bank_name = ShareprefreancesUtility.getInstance().getString("bank_name", "");
                    bank_name.setText(ShareprefreancesUtility.getInstance().getString("bank_name", ""));
                    bank_name.setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""))
                        && !ShareprefreancesUtility.getInstance().getString("ifsc_code", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("ifsc_code", "") != null
                ) {

                    string_ifsc_code = ShareprefreancesUtility.getInstance().getString("ifsc_code", "");
                    ifsc_code.setText(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""));
                    ifsc_code.setEnabled(true);
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("kyc_status", ""))
                        && !ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("kyc_status", "") != null
                ) {

                    if (ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("complete")) {

                        ((TextView) findViewById(R.id.kyc_submit)).setText("Approval Pending");
                        findViewById(R.id.kyc_submit).setEnabled(true);

                    } else if (ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("approved")) {

                        ((TextView) findViewById(R.id.kyc_submit)).setText("Approved");
                        findViewById(R.id.kyc_submit).setEnabled(true);

                    }


                }
            }

        } catch (Exception e) {
        }

    }

    private boolean isAadhaarValid(String pincode) {
        return pincode.length() == 12;
    }

    private boolean isPANValid(String pan) {
        return pan.length() == 10;
    }

}