package com.aob.aobsalesman.activities.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.aob.aobsalesman.activities.MyAnalytics;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;
import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class NewRegistrationActivity extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatEditText name,address,email,referral_code, password,p;
    String Name,Email,Password,P,Referral_email;
    String emailPattern="[a-zA-Z]+@[a-z]+\\.+[a-z]+";
    boolean aBoolean=false;
    boolean verify=false;
    @SuppressLint("HardwareIds") String android_id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);
        referral_code = findViewById(R.id.referral_code);

        progressDialog=new ProgressDialog(this);
        findViewById(R.id.new_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"3");
            MyAnalytics.getInstance(this).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        }catch (Exception e){}

        findViewById(R.id.terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aobindia.com/terms-and-conditions/"));
                    startActivity(browserIntent);
                }catch (Exception e){}
            }
        });
    }
    private void verifying() {
        if (InternetConnection.checkInternetConnectivity()) {
            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            if (referral_code.getText().toString().isEmpty()){
                //referral_email.setError("Please enter referral No");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                jsonParseForLogin();
            }else {
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        getResources().getString(R.string.hostName) + "/app/sales/v1_0/refer.php?refer_phone_id="+android_id+"&refer_code_name="+referral_code.getText().toString()+"&register_email="+Email,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                Log.e("aobsales", "new reg " + response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("status");
                                    if (success.equals("0")) {
                                        verify = true;
                                      jsonParseForLogin();
                                        referral_code.setFocusable(false);
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NewRegistrationActivity.this);
                                        alertDialog.setMessage("Something went wrong!");
                                        alertDialog.setCancelable(false);
                                        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();
                                    }


                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NewRegistrationActivity.this);
                        alertDialog.setMessage("Something went wrong!");
                        alertDialog.setCancelable(false);
                        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }) ;

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getInstance(NewRegistrationActivity.this).addToRequestQueue(stringRequest);
            }

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

    @SuppressLint("HardwareIds")
    private void validation() {
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        p = findViewById(R.id.p);

        // Reset errors.
        email.setError(null);
        password.setError(null);
        name.setError(null);
        p.setError(null);

        // Store values at the time of the login attempt.
        Email = email.getText().toString();
        Password = password.getText().toString();
        Name = name.getText().toString();
        P = p.getText().toString();
        P = p.getText().toString();
        Referral_email = referral_code.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            password.setError("Please enter valid Password");
            focusView = password;
            cancel = true;
        }

        if ( !isEmailValid(Email)) {
            email.setError("Please enter valid Email Id");
            focusView = email;
            cancel = true;
        }

        if (TextUtils.isEmpty(P)) {
            p.setError("Please enter valid Mobile No.");
            focusView = p;
            cancel = true;
        }
        else if (!isMobileValid(P)){
            p.setError("Please enter valid Mobile No.");
            focusView = p;
            cancel = true;
        }

        if (TextUtils.isEmpty(Name)) {
            name.setError("Please enter your Name");
            focusView = name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{

            if (InternetConnection.checkInternetConnectivity()) {

                android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                //jsonParseForLogin();
                verifying();

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
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
    }
    private boolean isMobileValid(String Mobile) {
        return Mobile.length() == 10;
    }
    public boolean isEmailValid(String email)
    {
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

    private void jsonParseForLogin(){
        findViewById(R.id.new_register).setEnabled(false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/registration_api.php?",
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        findViewById(R.id.new_register).setEnabled(true);

                        Log.e("aobsales","new reg "+response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if (success.equals("0")){
                                getData();
                            }
                            else if (success.equals("1")){
                                Toast.makeText(NewRegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                            else if (success.equals("3")){
                                getData();
                            }
                            else {

                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NewRegistrationActivity.this);
                                alertDialog.setMessage("You have already registered. Please login.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.new_register).setEnabled(true);
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NewRegistrationActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setCancelable(false);
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
                params.put("name",Name);
                params.put("password",Password);
                params.put("email",Email);
                if (verify){
                    params.put("referral_code",referral_code.getText().toString());
                }
                params.put("phone",P);
                params.put("unique_id", android_id);
                params.put("registration_status","incomplete");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(NewRegistrationActivity.this).addToRequestQueue(stringRequest);
    }

    private void getData() {
        Bundle bundle=new Bundle();
        bundle.putString("Name",Name);
        bundle.putString("Email",Email);
        bundle.putString("Password",Password);
        bundle.putString("Mobile",P);
        bundle.putString("android_id",android_id);
        bundle.putString("registration_status","incomplete");

        Intent intent=new Intent(NewRegistrationActivity.this, VerificationActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

}
