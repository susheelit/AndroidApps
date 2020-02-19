package com.aob.aobsalesman.activities.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class VerificationActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @SuppressLint("HardwareIds") String android_id;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        progressDialog = new ProgressDialog(this);
        android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ( findViewById(R.id.Continue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varification();
            }
        });
        ((androidx.appcompat.widget.AppCompatButton) findViewById(R.id.resend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });
        ((TextInputEditText) findViewById(R.id.EmailId)).setText(getIntent().getExtras().getString("Email"));
        sendOtp();
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}
    }
    private void varification() {
        if (((TextInputEditText)findViewById(R.id.code)).getText().toString().isEmpty()) {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
            alertDialog.setMessage("Please enter correct verification code");
            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        } else {

            ((TextView)findViewById(R.id.Continue)).setEnabled(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.hostName)+"/app/sales/v1_0/otp_verification.php?",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            ( findViewById(R.id.Continue)).setEnabled(true);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("status");
                                if (success.equals("0")){
                                 //   Toast.makeText(VerificationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                   jsonParseForLogin();
                                } else {
                                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                                    alertDialog.setMessage("Please enter correct verification code");
                                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();

                                }

                            } catch (JSONException e) {
                                Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ((TextView) findViewById(R.id.Continue)).setEnabled(true);
                    progressDialog.dismiss();
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                    alertDialog.setMessage("Something went wrong!");
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

                    params.put("email", getIntent().getExtras().getString("Email"));
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
        ((androidx.appcompat.widget.AppCompatButton) findViewById(R.id.resend)).setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/otp_send.php?",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ((androidx.appcompat.widget.AppCompatButton) findViewById(R.id.resend)).setEnabled(true);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("mail");
                            if (success.equals("0")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                                alertDialog.setMessage("Check your Email");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                              //  Toast.makeText(VerificationActivity.this, "Check your Email", Toast.LENGTH_SHORT).show();
                            }else{
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                                alertDialog.setMessage("Resend code");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                              //  Toast.makeText(VerificationActivity.this, "Resend code", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((androidx.appcompat.widget.AppCompatButton) findViewById(R.id.resend)).setEnabled(true);
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
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

                params.put("email", ((TextInputEditText) findViewById(R.id.EmailId)).getText().toString());
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

                                    sendFirebaseToken();
                                }
                            } else {
                                progressDialog.dismiss();
                                Intent intent=new Intent(VerificationActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
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
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
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
    private void sendFirebaseToken(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/firebase_notification_salesman_login.php?phone_id="+android_id+"&email="+getIntent().getExtras().getString("Email")+"&token="+ShareprefreancesUtility.getInstance().getString("token"),
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");


                            if (success.equalsIgnoreCase("0")) {

                             //   Toast.makeText(VerificationActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(VerificationActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else {
                                Intent intent=new Intent(VerificationActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {

                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                            alertDialog.setMessage("Retry with Internet connection");
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VerificationActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
