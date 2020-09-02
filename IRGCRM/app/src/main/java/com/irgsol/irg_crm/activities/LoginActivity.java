package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.utils.MySingleton;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btnLogin;
    static Context context;
    private EditText etUserId, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        context = LoginActivity.this;
        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OprActivity.finishAllOpenNewActivity(context,new DasboardActivity());
                validate();
            }
        });
    }

    private void validate() {
        String userId = "", password = "";
        userId = etUserId.getText().toString();
        password = etPassword.getText().toString();

        if (userId.compareTo("") == 0) {
            Config.alertBox("Please enter user name", context);
        } else if (password.compareTo("") == 0) {
            Config.alertBox("Please enter password", context);
        } else {
            attemptLogin(context, userId, password);
        }
    }

    public static void attemptLogin( final Context context, String userId, String password) {

         String apiUrl= Config.baseUrl+"userLogin.php?mobile_no="+userId+"&password="+password;
       // String apiUrl = "http://192.168.0.105/irg_crm/api/userLogin.php?&email_id=test%40test.com&password=12345";

        Log.e("apiUrl", apiUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String resultCode = jsonObject.getString("ResultCode");
                    Config.toastShow(jsonObject.getString("Message"), context);
                    if (resultCode.equalsIgnoreCase("1")){
                        JSONArray jsonArray= jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        SharedPref.putRegId(context, jsonObject1.getString("reg_id"));
                        SharedPref.putUserName(context, jsonObject1.getString("user_name"));
                        SharedPref.putMobileNo(context, jsonObject1.getString("mobile_no"));
                        SharedPref.putAltmobileno(context, jsonObject1.getString("alt_mobile_no"));
                        SharedPref.putEmailId(context, jsonObject1.getString("email_id"));
                        SharedPref.putUserImage(context, jsonObject1.getString("user_image"));
                        SharedPref.putPassword(context, jsonObject1.getString("password"));

                        OprActivity.finishAllOpenNewActivity(context, new DasboardNewActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response", error.toString());

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
