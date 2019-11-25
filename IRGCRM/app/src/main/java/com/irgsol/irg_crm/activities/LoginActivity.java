package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.models.ModelUser;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.utils.MySingleton;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import web_api.APIClient;
import web_api.APIInterface;

public class LoginActivity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btnLogin;
    Context context;
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
            attemptLogin(userId, password);
        }
    }

    private void attemptLogin(String userId, String password) {

        // String apiUrl= Config.baseUrl+"userLogin.php?email_id="+userId+"&password="+password;
        String apiUrl = "http://192.168.0.105/irg_crm/api/userLogin.php?&email_id=test%40test.com&password=12345";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);



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
