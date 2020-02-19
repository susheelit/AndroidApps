package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordVerification extends AppCompatActivity {

    private ProgressDialog progressDialog;
    @SuppressLint("HardwareIds") String android_id;

    TextInputEditText EmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_verification);

        EmailId = findViewById(R.id.EmailId);

        progressDialog = new ProgressDialog(this);
        try {
            android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }catch (Exception e){}

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}


        findViewById(R.id.Continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varification();
            }
        });

        findViewById(R.id.send_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(EmailId.getText().toString().trim()))
                {
                    sendOtp();
                }else
                {
                    Toast.makeText(ForgotPasswordVerification.this, "Please enter correct Email ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(EmailId.getText().toString().trim()))
                {
                    sendOtp();
                }else
                {
                    Toast.makeText(ForgotPasswordVerification.this, "Please enter correct Email ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("email", ""))
                && !ShareprefreancesUtility.getInstance().getString("email", "").equalsIgnoreCase("null")
                && ShareprefreancesUtility.getInstance().getString("email", "") != null
        ) {

            EmailId.setText(ShareprefreancesUtility.getInstance().getString("email", ""));

        }

    }

    private void varification() {
        if (((TextInputEditText)findViewById(R.id.code)).getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter verification code", Toast.LENGTH_SHORT).show();
        } else {

            findViewById(R.id.Continue).setEnabled(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.hostName)+"/app/sales/v1_0/forget_password_otp_verification.php?",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            findViewById(R.id.Continue).setEnabled(true);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Log.e("aobsales","verification response "+response);

                                String success = jsonObject.getString("status");


                                if (success.equals("2")){
                                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                    alertDialog.setMessage("Invalid email id");
                                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                 //   Toast.makeText(ForgotPasswordVerification.this, "Invalid email id", Toast.LENGTH_SHORT).show();
                                    //jsonParseForLogin();
                                } else if (success.equals("3")){
                                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                    alertDialog.setMessage("Please enter correct verification code");
                                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                   // Toast.makeText(ForgotPasswordVerification.this, "Please enter correct verification code", Toast.LENGTH_SHORT).show();
                                }
                                else if (success.equals("0")){
                                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                    alertDialog.setMessage("OTP Verification Successful");
                                    alertDialog.setCancelable(false);
                                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            Intent intent=new Intent(ForgotPasswordVerification.this, ForgotPasswordActivity.class);
                                            intent.putExtra("emailid",EmailId.getText().toString().trim());
                                            intent.putExtra("otpcode",((TextInputEditText)findViewById(R.id.code)).getText().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                   // Toast.makeText(ForgotPasswordVerification.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                Toast.makeText(ForgotPasswordVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    findViewById(R.id.Continue).setEnabled(true);
                    progressDialog.dismiss();
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                    alertDialog.setMessage("Retry with Internet connection");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", EmailId.getText().toString());
                    params.put("unique_id", android_id);
                    params.put("otp", ((TextInputEditText) findViewById(R.id.code)).getText().toString());

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }
    }

    private void sendOtp() {
        findViewById(R.id.resend).setEnabled(false);
        findViewById(R.id.send_code).setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/forget_password_otp_send.php?",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.resend).setEnabled(true);
                        findViewById(R.id.send_code).setEnabled(true);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("mail");
                            if (success.equals("0")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                alertDialog.setMessage("OTP has been sent to your registered email Id.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                               // Toast.makeText(ForgotPasswordVerification.this, "OTP has been sent to your registered email", Toast.LENGTH_LONG).show();
                            } else if (success.equals("1")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                alertDialog.setMessage("Please Try Again");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                               // Toast.makeText(ForgotPasswordVerification.this, "Please Try Again", Toast.LENGTH_LONG).show();
                            }
                            else if (success.equals("2")){
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                                alertDialog.setMessage("Please enter your registered email Id.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                //Toast.makeText(ForgotPasswordVerification.this, "Please enter your registered email id", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                            alertDialog.setMessage("Something went wrong.");
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
                findViewById(R.id.resend).setEnabled(true);
                findViewById(R.id.send_code).setEnabled(true);
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", EmailId.getText().toString().trim());
                params.put("unique_id", android_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void jsonParseForLogin(){

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/sign_in_validate.php?",
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equalsIgnoreCase("0")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    ShareprefreancesUtility.getInstance().saveString("name",jsonObject1.getString("name"));
                                    ShareprefreancesUtility.getInstance().saveString("password",jsonObject1.getString("password"));
                                    ShareprefreancesUtility.getInstance().saveString("email",jsonObject1.getString("email"));
                                    ShareprefreancesUtility.getInstance().saveString("address",jsonObject1.getString("address"));
                                    ShareprefreancesUtility.getInstance().saveString("highest_qualification",jsonObject1.getString("highest_qualification"));
                                    ShareprefreancesUtility.getInstance().saveString("experience_type",jsonObject1.getString("experience_type"));
                                    ShareprefreancesUtility.getInstance().saveString("experience_in_sale",jsonObject1.getString("experience_in_sale"));
                                    ShareprefreancesUtility.getInstance().saveString("vehicle_mode",jsonObject1.getString("vehicle_mode"));
                                    ShareprefreancesUtility.getInstance().saveString("what_sold",jsonObject1.getString("what_sold"));
                                    ShareprefreancesUtility.getInstance().saveString("area_of_interest",jsonObject1.getString("area_of_interest"));
                                    ShareprefreancesUtility.getInstance().saveString("monthly_income",jsonObject1.getString("monthly_income"));
                                    ShareprefreancesUtility.getInstance().saveString("unique_id",jsonObject1.getString("unique_id"));
                                    ShareprefreancesUtility.getInstance().saveString("userlogin","online");

                                    Intent intent=new Intent(ForgotPasswordVerification.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }

                            else {

                                Intent intent=new Intent(ForgotPasswordVerification.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                            alertDialog.setMessage("Retry with Internet connection");
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
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ForgotPasswordVerification.this);
                alertDialog.setMessage("Something went wrong!");
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

                params.put("email",getIntent().getExtras().getString("Email"));
                params.put("password",getIntent().getExtras().getString("Password"));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }
}
