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
import android.util.Log;
import android.view.View;
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
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class ActivityRegisterLead extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatEditText company_name,contact_person,designation, email_id,city,state,pin_code,instructions, street_address,contact_number;

    String string_company_name,string_contact_person,string_designation, string_email_id,string_city,string_state, string_pin_code, string_street_address, string_instructions, string_contact_number;

    private ProgressDialog progressDialog;

    @SuppressLint("HardwareIds")
    String android_id;

    String project_id = "";

    long mLastClickTime30 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lead);

        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}

        initObj();
        initviews();
        initFunctionality();

    }

    private void initObj() {
        Intent intent = getIntent();
        project_id = intent.getExtras().getString("project_id");

        Log.e("Aobsales","yo "+project_id);

    }

    private void initviews()
    {

        company_name = findViewById(R.id.company_name);
        contact_person = findViewById(R.id.contact_person);
        designation = findViewById(R.id.designation);
        email_id = findViewById(R.id.email_id);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pin_code = findViewById(R.id.pin_code);
        instructions = findViewById(R.id.instructions);
        contact_number = findViewById(R.id.contact_number);
        street_address = findViewById(R.id.street_address);

    }

    private void initFunctionality()
    {

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}

        ((TextView)findViewById(R.id.kyc_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime30 < 1000) {
                    return;
                }
                mLastClickTime30 = SystemClock.elapsedRealtime();

                validation();
            }
        });

    }

    private void validation() {

        // Reset errors.
        company_name.setError(null);
        contact_person.setError(null);
        designation.setError(null);
        email_id.setError(null);
        city.setError(null);
        state.setError(null);
        pin_code.setError(null);
        instructions.setError(null);
        street_address.setError(null);
        contact_number.setError(null);

        // Store values at the time of the login attempt.
        string_company_name = company_name.getText().toString();
        string_contact_person = contact_person.getText().toString();
        string_designation  = designation.getText().toString();
        string_email_id = email_id.getText().toString();
        string_city = city.getText().toString();
        string_state = state.getText().toString();
        string_pin_code = pin_code.getText().toString();
        string_instructions = instructions.getText().toString();
        string_street_address = street_address.getText().toString();
        string_contact_number = contact_number.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

      /*  if (TextUtils.isEmpty(tv_canceled_cheque.getText().toString())) {
            tv_canceled_cheque.setError("");
            focusView = tv_canceled_cheque;
            cancel = true;
        }*/

        /*if (TextUtils.isEmpty(string_instructions)) {
            instructions.setError("Please enter order the instructions");
            focusView = instructions;
            cancel = true;
        }*/

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
            city.setError("Please enter state");
            focusView = city;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_street_address)) {
            street_address.setError("Please enter street address");
            focusView = street_address;
            cancel = true;
        }

        if (!isEmailValid(string_email_id)) {
            email_id.setError("Please enter correct email");
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
            contact_person.setError("Please enter contact person");
            focusView = designation;
            cancel = true;
        }

        if (TextUtils.isEmpty(string_company_name)) {
            company_name.setError("Please enter company name");
            focusView = company_name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{

            if (InternetConnection.checkInternetConnectivity()) {

                progressDialog=new ProgressDialog(this);

                try {

                    android_id = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                } catch (Exception e) {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivityRegisterLead.this);
                    alertDialog.setMessage(e.getMessage());
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }

                jsonParseForLogin();
            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivityRegisterLead.this);
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

        if(matcher.matches())
            return true;
        else
            return false;
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


    private void jsonParseForLogin(){
        ((TextView)findViewById(R.id.kyc_submit)).setEnabled(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/salesman_add_lead.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","reesponse "+response.toString());

                        progressDialog.dismiss();
                        ((TextView) findViewById(R.id.kyc_submit)).setEnabled(true);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if (success.equals("0")){

                                Toast.makeText(ActivityRegisterLead.this, "Your Lead has been submitted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if (success.equals("1")){
                                Toast.makeText(ActivityRegisterLead.this, "Submission failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivityRegisterLead.this);
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.setCancelable(false);
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
                ((TextView)findViewById(R.id.kyc_submit)).setEnabled(true);
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ActivityRegisterLead.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();

                Log.e("aobsales","errot "+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                params.put("sl_name",string_company_name);
                params.put("sl_contact_person",string_contact_person);
                params.put("sl_designation",string_designation);
                params.put("sl_contact_no",string_contact_number);
                params.put("sl_email_id", string_email_id);
                params.put("sl_address", string_street_address);
                params.put("sl_city",string_city);
                params.put("sl_state", string_state);
                params.put("sl_pincode", string_pin_code);
                params.put("sl_instructions", string_instructions);

                if(!TextUtils.isEmpty(project_id)) {
                    params.put("project_id", project_id);
                }

                params.put("android_id", android_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(ActivityRegisterLead.this).addToRequestQueue(stringRequest);

    }

}
