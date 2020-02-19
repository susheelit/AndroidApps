package com.aob.aobsalesman.activities.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.aob.aobsalesman.BuildConfig;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MyHttpEntity;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class ActivitySalesLead extends AppCompatActivity {

    Uri photoURI;
    androidx.appcompat.widget.AppCompatEditText company_name, contact_person, designation, email_id, city, state, pin_code, order_details, gst_no, pan_no, payment_details, contact_number, street_address;

    String string_company_name, string_contact_person, string_designation, string_email_id, string_city, string_state, string_pin_code, string_order_details, string_gst_no, string_pan_no, string_payment_Details, string_contact_number, string_street_address;

    private ProgressDialog progressDialog;
    String imageEncoded;
    List<String> imagesEncodedList;

    private int PICK_IMAGE_REQUEST = 100;
    private int PICK_CAMERA_REQUEST = 200;
    private int PICK_AUDIO_REQUEST = 300;
    private int PICK_IMAGES_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    int checkway1 = 0;

    String base64_pan = "";
    String base64_gst = "";
    String base64_sales_image = "";
    String base64__chk = "";
    String base64__multiimages = "";

    String base64_sales_recording = "";

    private Uri filePath, filePath_audio;
    String selectedPath = "";

    TextView tv_pan, tv_gst, tv_sales_image, tv_sales_recording, multiimage, cancelchk;

    File audioFile;

    LinearLayout b2b_linear;

    @SuppressLint("HardwareIds")
    String android_id;

    String send1 = "";
    String send2 = "";

    String project_id = "";

    long mLastClickTime40 = 0;
    long mLastClickTime41 = 0;
    long mLastClickTime42 = 0;
    long mLastClickTime43 = 0;
    long mLastClickTime44 = 0;
    long mLastClickTime45 = 0;
    long mLastClickTime46 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_lead);

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }

        initObj();
        initviews();
        initFunctionality();

        try {

            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
        }
    }

    private void initObj() {
        Intent intent = getIntent();
        send1 = intent.getExtras().getString("send1");
        send2 = intent.getExtras().getString("send2");
        project_id = intent.getExtras().getString("project_id");

        Log.e("Aobsales", "yo " + send1);
        Log.e("Aobsales", "yo " + send2);
        Log.e("Aobsales", "yo " + project_id);

    }

    private void initviews() {
        b2b_linear = findViewById(R.id.b2b_linear);

        if (!TextUtils.isEmpty(send1)) {
            if (TextUtils.isEmpty(send2)) {
                if (send1.equalsIgnoreCase("B2C")) {
                    b2b_linear.setVisibility(View.GONE);
                }
            }
        }

        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pan_no = findViewById(R.id.pan_no);
        company_name = findViewById(R.id.company_name);
        contact_person = findViewById(R.id.contact_person);
        designation = findViewById(R.id.designation);
        email_id = findViewById(R.id.email_id);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pin_code = findViewById(R.id.pin_code);
        order_details = findViewById(R.id.order_details);
        gst_no = findViewById(R.id.gst_no);
        payment_details = findViewById(R.id.payment_details);
        contact_number = findViewById(R.id.contact_number);
        street_address = findViewById(R.id.street_address);

        tv_pan = findViewById(R.id.tv_pan);
        tv_gst = findViewById(R.id.tv_gst);
        tv_sales_image = findViewById(R.id.tv_sales_image);

        tv_sales_recording = findViewById(R.id.tv_sales_recording);
        multiimage = findViewById(R.id.tv_sales_imagess);
        cancelchk = findViewById(R.id.tv_sales_cancelchk);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initFunctionality() {

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }

        findViewById(R.id.kyc_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime40 < 1000) {
                    return;
                }
                mLastClickTime40 = SystemClock.elapsedRealtime();

                validation();
                hideKeyboard(ActivitySalesLead.this);
            }
        });

        findViewById(R.id.upload_gst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime41 < 1000) {
                    return;
                }

                mLastClickTime41 = SystemClock.elapsedRealtime();

                checkway1 = 1;
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
                hideKeyboard(ActivitySalesLead.this);
            }
        });


        findViewById(R.id.upload_pan1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SystemClock.elapsedRealtime() - mLastClickTime42 < 1000) {
                    return;
                }
                mLastClickTime42 = SystemClock.elapsedRealtime();

                checkway1 = 2;
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
                hideKeyboard(ActivitySalesLead.this);
            }
        });

        findViewById(R.id.upload_sales_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SystemClock.elapsedRealtime() - mLastClickTime43 < 1000) {
                    return;
                }
                mLastClickTime43 = SystemClock.elapsedRealtime();

                checkway1 = 3;
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
                hideKeyboard(ActivitySalesLead.this);
            }
        });


        findViewById(R.id.upload_sales_recording).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime44 < 1000) {
                    return;
                }
                mLastClickTime44 = SystemClock.elapsedRealtime();

                checkway1 = 4;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    openGalleryAudio();
                } else {
                    requestStoragePermission();
                }

                hideKeyboard(ActivitySalesLead.this);

            }
        });

        findViewById(R.id.upload_sales_imagess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime45 < 1000) {
                    return;
                }
                mLastClickTime45 = SystemClock.elapsedRealtime();

                checkway1 = 5;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showFileChooserImages();
                       // showDialog();
                    } else {
                        showFileChooserImages();
                        // Implement this feature without material design
                    }

                } else {
                    requestStoragePermission();
                }
                hideKeyboard(ActivitySalesLead.this);
            }
        });
        findViewById(R.id.upload_sals_cancelchk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime46 < 1000) {
                    return;
                }
                mLastClickTime46 = SystemClock.elapsedRealtime();

                checkway1 = 6;
                Boolean check = checkReadExternalPermission();
                if (check == true) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showDialog();
                    } else {
                        showFileChooserImages();
                        // Implement this feature without material design
                    }

                } else {
                    requestStoragePermission();
                }
                hideKeyboard(ActivitySalesLead.this);
            }
        });

       /* int  notificationId = (int) System.currentTimeMillis();
        Context mContext = ActivitySalesLead.this;
        Intent resultIntent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 *//* Request code *//*, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "aob");
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBuilder.setContentTitle("Upload")
                .setOngoing(true)
                .setContentText("in progress")
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        mBuilder.setSound(null);
        mBuilder.setVibrate(new long[]{0L});
        mBuilder.build().flags |= Notification.FLAG_ONGOING_EVENT;
        mBuilder.setWhen(System.currentTimeMillis());
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("aob", "test", importance);
            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            mBuilder.setChannelId("aob");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        mBuilder.setProgress(100, 0, false);
        mNotificationManager.notify(notificationId, mBuilder.build());*/
    }

    private void validation() {

        // Reset errors.
        pan_no.setError(null);
        company_name.setError(null);
        contact_person.setError(null);
        designation.setError(null);
        email_id.setError(null);
        city.setError(null);
        state.setError(null);
        pin_code.setError(null);
        order_details.setError(null);
        gst_no.setError(null);
        payment_details.setError(null);
        street_address.setError(null);

        tv_gst.setError(null);
        tv_pan.setError(null);

        // Store values at the time of the login attempt.
        string_pan_no = pan_no.getText().toString();
        string_company_name = company_name.getText().toString();
        string_contact_person = contact_person.getText().toString();
        string_designation = designation.getText().toString();
        string_email_id = email_id.getText().toString();
        string_city = city.getText().toString();
        string_state = state.getText().toString();
        string_pin_code = pin_code.getText().toString();
        string_order_details = order_details.getText().toString();
        string_gst_no = gst_no.getText().toString();
        string_payment_Details = payment_details.getText().toString();
        string_contact_number = contact_number.getText().toString();
        string_street_address = street_address.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

      /*  if (TextUtils.isEmpty(tv_canceled_cheque.getText().toString())) {
            tv_canceled_cheque.setError("");
            focusView = tv_canceled_cheque;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(string_payment_Details)) {
            payment_details.setError("Please enter payment details");
            focusView = payment_details;
            cancel = true;
        }

        if (TextUtils.isEmpty(tv_sales_image.getText().toString())) {
            tv_sales_image.setError("");
            focusView = tv_sales_image;
            cancel = true;
        }

        if (b2b_linear.getVisibility() == View.VISIBLE) {

            if (TextUtils.isEmpty(tv_pan.getText().toString())) {
                tv_pan.setError("");
                focusView = tv_pan;
                cancel = true;
            }

            if (!isPANValid(string_pan_no)) {
                pan_no.setError("Please enter 10 digit PAN no.");
                focusView = pan_no;
                cancel = true;
            }

            if (TextUtils.isEmpty(tv_gst.getText().toString())) {
                tv_gst.setError("");
                focusView = tv_gst;
                cancel = true;
            }

            if (!isGSTValid(string_gst_no)) {
                gst_no.setError("Please enter 15 digit GST no.");
                focusView = gst_no;
                cancel = true;
            }

        }

        if (TextUtils.isEmpty(string_order_details)) {
            order_details.setError("Please enter order details");
            focusView = order_details;
            cancel = true;
        }

        if (!isPinValid(string_pin_code)) {
            pin_code.setError("Please enter 6 digit PIN code");
            focusView = pin_code;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_state)) {
            state.setError("Please enter state");
            focusView = state;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_city)) {
            city.setError("Please enter city");
            focusView = city;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_street_address)) {
            street_address.setError("Please enter street address");
            focusView = street_address;
            cancel = true;
        }


        if (!isEmailValid(string_email_id)) {
            email_id.setError("Please enter email");
            focusView = email_id;
            cancel = true;
        }

        if (!isMobileValid(string_contact_number)) {
            contact_number.setError("Please enter contact number");
            focusView = contact_number;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_designation)) {
            designation.setError("Please enter designation");
            focusView = designation;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_contact_person)) {
            contact_person.setError("Please enter contact person's name");
            focusView = designation;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_company_name)) {
            company_name.setError("Please enter company's name");
            focusView = company_name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if (InternetConnection.checkInternetConnectivity()) {

                progressDialog = new ProgressDialog(this);

                jsonParseForLogin();

            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivitySalesLead.this);
                alertDialog.setMessage("Retry with internet connection.");
                alertDialog.setCancelable(false);
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

        /*if (audioFile != null) {*/
        UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(ActivitySalesLead.this);
        uploadAsyncTask.execute();


        /*}else {

            ((TextView)findViewById(R.id.kyc_submit)).setEnabled(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://aobindia.in/app/sales/v1_0/salesman_add_sales.php?",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("aobsales", "reesponse " + response.toString());

                            progressDialog.dismiss();
                            ((TextView) findViewById(R.id.kyc_submit)).setEnabled(true);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("status");
                                if (success.equals("0")) {

                                    Toast.makeText(ActivitySalesLead.this, "Your Sale has been submitted", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (success.equals("1")) {
                                    Toast.makeText(ActivitySalesLead.this, "Submission failed", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ((TextView) findViewById(R.id.kyc_submit)).setEnabled(true);
                    progressDialog.dismiss();
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivitySalesLead.this);
                    alertDialog.setMessage("Oops Something went wrong");
                    alertDialog.setCancelable(false);
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
                    params.put("sl_name", string_company_name);
                    params.put("sl_contact_person", string_contact_person);
                    params.put("sl_designation", string_designation);
                    params.put("sl_contact_no", string_contact_number);
                    params.put("sl_email_id", string_email_id);
                    params.put("sl_address", string_street_address);
                    params.put("sl_city", string_city);
                    params.put("sl_state", string_state);
                    params.put("sl_pincode", string_pin_code);
                    params.put("sl_order_detail", string_order_details);
                    params.put("sl_payment_detail", string_payment_Details);
                    params.put("sl_gst_no", string_gst_no);
                    params.put("sl_pan_no", string_pan_no);
                    params.put("sl_gst_upload", base64_gst);
                    params.put("sl_pan_upload", base64_pan);
                    params.put("sl_payment_image", base64_sales_image);

                    if (!TextUtils.isEmpty(project_id)) {
                        params.put("project_id", project_id);
                    }

                    params.put("android_id", android_id);

                    Log.e("aobsales", "params " + params.toString());

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(ActivitySalesLead.this).addToRequestQueue(stringRequest);
        }*/
    }


    //method to show file chooser
    private void showFileChooserImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_IMAGE_REQUEST);
    }

    private void showFileChooserImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGES_REQUEST);
    }

    /*private void showFileChooserPDF() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_PDF_REQUEST);
    }*/

    //handling the image chooser activity result
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            if (checkway1 == 1) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_gst = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_gst.setText(name);
                }
            } else if (checkway1 == 2) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_pan = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_pan.setText(name);
                }

            } else if (checkway1 == 3) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_sales_image = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_sales_image.setText(name);
                }

            } else if (checkway1 == 6) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64__chk = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    cancelchk.setText(name);
                }

            }

        } else if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");


            if(checkway1 == 1)
            {
                base64_gst = convertBitmapToBase64String(imageBitmap);
                if(!TextUtils.isEmpty(base64_gst)) {
                    tv_gst.setText("Selected From Camera");
                }
            }
            else if(checkway1 == 2)
            {
                base64_pan = convertBitmapToBase64String(imageBitmap);
                if(!TextUtils.isEmpty(base64_pan)) {
                    tv_pan.setText("Selected From Camera");
                }
            }
            else if(checkway1 == 3)
            {
                base64_sales_image = convertBitmapToBase64String(imageBitmap);
                if(!TextUtils.isEmpty(base64_sales_image)) {
                    tv_sales_image.setText("Selected From Camera");
                }
            }

    */
            File cachePath = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");
            //  filePath = Uri.parse(photoURI);
            filePath = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    cachePath);
            if (checkway1 == 1) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_gst = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_gst.setText(name);
                }
            } else if (checkway1 == 2) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_pan = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_pan.setText(name);
                }

            } else if (checkway1 == 3) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64_sales_image = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    tv_sales_image.setText(name);
                }

            }
        } else if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK) {
            filePath_audio = data.getData();
            String name = getFileName(filePath_audio);

            selectedPath = getPath(this, filePath_audio);
            Log.e("aobsales", "selected path " + selectedPath);

            byte[] audioBytes;
            try {

                audioFile = new File(selectedPath);

            } catch (Exception e) {
                Log.e("aobsales", e.toString());
            }

            tv_sales_recording.setText(name);

        }
        else if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK
                && null != data) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imagesEncodedList = new ArrayList<String>();
            Bitmap bitmap = null;
            if (data.getData() != null) {

                filePath = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                } catch (Exception e) {
                }
                if (bitmap != null) {
                    base64__multiimages = convertBitmapToBase64String(bitmap);
                    String name = getFileName(filePath);
                    multiimage.setText(name);
                }

                // Get the cursor
                Cursor cursor = getContentResolver().query(filePath,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageEncoded = cursor.getString(columnIndex);
                cursor.close();

            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        filePath = item.getUri();
                        mArrayUri.add(filePath);
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(filePath, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();
                    }

                    StringBuilder builder = new StringBuilder();
                    for (Uri details : mArrayUri) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), details);
                        } catch (Exception e) {
                        }
                        if (bitmap != null) {
                            base64__multiimages += convertBitmapToBase64String(bitmap);
                            builder.append(getFileName(details) + "\n");

                        }
                    }

                    multiimage.setText(builder.toString());

                    Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                }
            }
        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
        Log.v("LOG_TAG", "Selected Images" + base64__multiimages);

    }

    /*public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName1(contentUri);
        if (!TextUtils.isEmpty(fileName)) {

            File copyFile = new File(TEMP_DIR_PATH + File.separator + fileName);
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();

            //return copyFile.getAbsolutePath();
        }
        return null;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static String getFileName1(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

   /* public static String getFileNameByUri(Context context, Uri uri)
    {
        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        }
        else if (uri.getScheme().compareTo("file")==0)
        {
            fileName = filePathUri.getLastPathSegment().toString();
        }
        else
        {
            fileName = fileName+"_"+filePathUri.getLastPathSegment();
        }
        return fileName;
    }*/

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
                if (checkway1 == 4) {
                    openGalleryAudio();
                } else {
                    if (Build.VERSION.SDK_INT >= 24) {
                        // Call some material design APIs here
                        showDialog();
                    } else {
                        showFileChooserImage();
                        // Implement this feature without material design
                    }
                }

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
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
        try {
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

        } catch (Exception e) {

            Log.e("aobsales", "error is " + e.toString());
        }
        return result;
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

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");

        photoURI = FileProvider.getUriForFile(this, getContext().getApplicationContext().getPackageName() + ".fileprovider", file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, PICK_CAMERA_REQUEST);
    }


    public void openGalleryAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Audio "), PICK_AUDIO_REQUEST);
    }

    private String convertBitmapToBase64String(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("base64String", imageString);
        return imageString;
    }

    private boolean isMobileValid(String Mobile) {
        return Mobile.length() == 10;
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    private boolean isGSTValid(String gst) {
        return gst.length() == 15;
    }

    private boolean isPinValid(String pincode) {
        return pincode.length() == 6;
    }

    private boolean isPANValid(String pan) {
        return pan.length() == 10;
    }

   /* private class Upload extends AsyncTask {

        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivitySalesLead.this);
            progressDialog.setTitle("Processing");
            progressDialog.setMessage("Uploading Audio File");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                HashMap<String, String> data = new HashMap<>();
                data.put("uploadedfile", selectedPath);
                data.put("name", "uploadedfile");
                HttpMultipartUpload.upload(new URL("http://sachinverma.co.in/services/upload_multiple_images/upload_audio.php"),
                        new File(selectedPath), "uploadedfile", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }*/

    private class UploadAsyncTask extends AsyncTask<Void, Integer, String> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog;

        private UploadAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            String responseString = null;

            try {
                HttpPost httpPost = new HttpPost(getResources().getString(R.string.hostName) + "/app/sales/v1_0/salesman_add_sales.php?email=" + ShareprefreancesUtility.getInstance().getString("email", ""));
               /* List<NameValuePair> params1 = new ArrayList<NameValuePair>(1);
                params1.add(new BasicNameValuePair("email", ShareprefreancesUtility.getInstance().getString("email", "")));
                httpPost.setEntity(new UrlEncodedFormEntity(params1, "UTF-8"));
*/
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                if (audioFile != null) {
                    multipartEntityBuilder.addPart("sl_sales_recording", new FileBody(audioFile));
                }
                multipartEntityBuilder.addTextBody("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                multipartEntityBuilder.addTextBody("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                multipartEntityBuilder.addTextBody("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                multipartEntityBuilder.addTextBody("sl_name", string_company_name);
                multipartEntityBuilder.addTextBody("sl_contact_person", string_contact_person);
                multipartEntityBuilder.addTextBody("sl_designation", string_designation);
                multipartEntityBuilder.addTextBody("sl_contact_no", string_contact_number);
                multipartEntityBuilder.addTextBody("sl_email_id", string_email_id);
                multipartEntityBuilder.addTextBody("sl_address", string_street_address);
                multipartEntityBuilder.addTextBody("sl_city", string_city);
                multipartEntityBuilder.addTextBody("sl_state", string_state);
                multipartEntityBuilder.addTextBody("sl_pincode", string_pin_code);
                multipartEntityBuilder.addTextBody("sl_order_detail", string_order_details);
                multipartEntityBuilder.addTextBody("sl_payment_detail", string_payment_Details);
                multipartEntityBuilder.addTextBody("sl_gst_no", string_gst_no);
                multipartEntityBuilder.addTextBody("sl_pan_no", string_pan_no);
                multipartEntityBuilder.addTextBody("sl_gst_upload", base64_gst);
                multipartEntityBuilder.addTextBody("sl_pan_upload", base64_pan);
                multipartEntityBuilder.addTextBody("sl_payment_image", base64_sales_image);

                if (!TextUtils.isEmpty(project_id)) {
                    multipartEntityBuilder.addTextBody("project_id", project_id);
                }

                multipartEntityBuilder.addTextBody("android_id", android_id);
                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(httpEntity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
                e.printStackTrace();
                Log.e("aobsales", e.getMessage());
                this.exception = e;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPreExecute() {
            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
            this.progressDialog.setTitle("Uploading..");

        }

        @Override
        protected void onPostExecute(String result) {

            String result1 = "" + result;

            this.progressDialog.dismiss();

            if (result1.isEmpty() || result1 == null || result1.equalsIgnoreCase("null")) {

                Toast.makeText(ActivitySalesLead.this, "Submission failed", Toast.LENGTH_SHORT).show();
            } else {


                try {
                    JSONObject jsonObject = new JSONObject(result1);
                    String success = jsonObject.getString("status");
                    //  Toast.makeText(ActivitySalesLead.this, success, Toast.LENGTH_SHORT).show();
                    if (success.equals("0")) {

                        Toast.makeText(ActivitySalesLead.this, "Your Sale has been submitted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (success.equals("1")) {
                        Toast.makeText(ActivitySalesLead.this, "Submission failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress(progress[0]);
        }
    }


}
