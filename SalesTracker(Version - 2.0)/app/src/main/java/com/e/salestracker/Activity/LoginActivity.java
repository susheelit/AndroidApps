package com.e.salestracker.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

private Button login;
private TextInputEditText user_id;
private TextInputEditText password;
private ProgressDialog progressDialog;
private String User_Id,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        progressDialog=new ProgressDialog(this);
        Tools.setSystemBarColor(this);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }
    private void initView(){
        user_id=findViewById(R.id.userId);
        password=findViewById(R.id.password);
    }
    private void attemptLogin() {

        // Reset errors.
        user_id.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        User_Id = user_id.getText().toString();
        Password = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            password.setError("Please fill valid password");
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(User_Id)) {
            user_id.setError("Please enter UserId");
            focusView = user_id;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if (InternetConnection.checkInternetConnectivity()){
                jsonParseForLogin();
            }
            else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
                alertDialog.setMessage("Retry with internet connection.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }

     /*  Intent i = new Intent(this,DSRDetailActivityNew.class);
        startActivity(i);*/
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 1;
    }

    private void jsonParseForLogin(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                getResources().getString(R.string.host_name)+"/sales_tracker/agent_logins.php?agent_id="
                        + (user_id.getText()).toString().trim()
                        +"&agent_password="+ (password.getText()).toString().trim(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.e("Login response", ""+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("status");

                    if (success.equalsIgnoreCase("ok")) {
                        JSONArray jsonArray1=jsonObject.getJSONArray("role");

                        String user=jsonArray1.getString(0);
                        ShareprefreancesUtility.getInstance().saveString("role",""+user);

                        ShareprefreancesUtility.getInstance().saveString("user",user);

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                       for (int i=0;i<jsonArray.length();i++){
                           JSONObject jsonObject1=jsonArray.getJSONObject(i);
                           ShareprefreancesUtility.getInstance().saveString("userId",jsonObject1.getString("agent_id"));
                           ShareprefreancesUtility.getInstance().saveString("password",password.getText().toString());
                           ShareprefreancesUtility.getInstance().saveString("name",jsonObject1.getString("agent_name"));
                           ShareprefreancesUtility.getInstance().saveString("email_id",jsonObject1.getString("email_id"));
                           ShareprefreancesUtility.getInstance().saveString("project_name",jsonObject1.getString("project_name"));
                           ShareprefreancesUtility.getInstance().saveString("time_stamp",jsonObject1.getString("time_stamp"));
                           ShareprefreancesUtility.getInstance().saveString("profile",jsonObject1.getString("profile"));
                           ShareprefreancesUtility.getInstance().saveString("mobile",jsonObject1.getString("mobile_no"));
                           ShareprefreancesUtility.getInstance().saveString("userlogin","online");

                           user_id.setText("");
                           password.setText("");
                           Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                           if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Admin")){
                               startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                               //startActivity(new Intent(LoginActivity.this, LocationMainActivity.class));
                               finish();
                           }else if(ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("client")){
                               startActivity(new Intent(LoginActivity.this, LocationMainActivity.class));
                               finish();
                           }else {
                               startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                               finish();
                           }
                       }
                    } else {

                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                        alertDialog.setMessage("Please enter valid Login details");
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

                progressDialog.dismiss();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
