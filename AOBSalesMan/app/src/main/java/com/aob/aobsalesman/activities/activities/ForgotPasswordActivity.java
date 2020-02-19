package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    String email = "";
    String otpcode = "";

    EditText new_pwd;
    EditText confirm_pwd;

    ProgressDialog progressDialog;
    @SuppressLint("HardwareIds") String android_id;

    long mLastClickTime40 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initObj();
        initViews();
        initFunctionality();
    }

    private void initObj()
    {
       email = getIntent().getStringExtra("emailid");
       otpcode = getIntent().getStringExtra("otpcode");
    }

    private void initViews()
    {

        new_pwd = findViewById(R.id.new_pwd);
        confirm_pwd = findViewById(R.id.confirm_pwd);

    }

    private void initFunctionality()
    {

        try {
            android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }catch (Exception e){}

        ((TextView)findViewById(R.id.setpwd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime40 < 1000) {
                    return;
                }
                mLastClickTime40 = SystemClock.elapsedRealtime();

                    validation();

            }
        });

    }

    private void validation()
    {

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(confirm_pwd.getText().toString()) || !isPasswordValid(confirm_pwd.getText().toString())) {
            confirm_pwd.setError("Please enter valid Password");
            focusView = confirm_pwd;
            cancel = true;
        }

        if (TextUtils.isEmpty(new_pwd.getText().toString()) || !isPasswordValid(new_pwd.getText().toString())) {
            new_pwd.setError("Please enter valid Password");
            focusView = new_pwd;
            cancel = true;
        }




        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{

            if(new_pwd.getText().toString().equalsIgnoreCase(confirm_pwd.getText().toString())) {

                if (InternetConnection.checkInternetConnectivity()) {
                    jsonParseForLogin();

                } else {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
                    alertDialog.setMessage("Retry with Internet connection");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
            else
            {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
                alertDialog.setMessage("Passwords do not match");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
               // Toast.makeText(ForgotPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

            }
        }

    }



    private void jsonParseForLogin(){

        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/forget_password_set_new_password.php?",
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");


                            if (success.equalsIgnoreCase("0")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordActivity.this);
                                alertDialog.setMessage("Password changed Successfully. Please login using new password.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                             //   Toast.makeText(ForgotPasswordActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                            }
                            else {

                                Toast.makeText(ForgotPasswordActivity.this, "Please try again", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordActivity.this);
                            alertDialog.setMessage("Please check your Internet connection");
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email",email);
                params.put("new_password",new_pwd.getText().toString());
                params.put("otp",otpcode);
                params.put("unique_id",android_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
    }


}
