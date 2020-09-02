package com.irg.crm_admin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.irg.crm_admin.R;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.common.MySingleton;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.common.SharedPref;
import com.irg.crm_admin.databinding.ActivityLoginBinding;
import com.irg.crm_admin.model.ModelRegistration;
import com.irg.crm_admin.viewModel.RegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private RegistrationViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<ModelRegistration>() {
            @Override
            public void onChanged(@Nullable ModelRegistration loginUser) {

                if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getMobileNo())){
                    binding.etMobile.setError("Enter Mobile no");
                    binding.etMobile.requestFocus();
                }else if(Objects.requireNonNull(loginUser).getMobileNo().length() !=10){
                    binding.etMobile.setError("Enter valid Mobile no");
                    binding.etMobile.requestFocus();
                }else if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())){
                    binding.etPassword.setError("Enter password");
                    binding.etPassword.requestFocus();
                }else if(!(Objects.requireNonNull(loginUser).getPassword().length() >5
                        && Objects.requireNonNull(loginUser).getPassword().length() <11 )){
                    binding.etPassword.setError("Password length should be between 5-10 characters.");
                    binding.etPassword.requestFocus();
                }else {
                    //Config.toastShow("Login Success",LoginActivity.this);
                     attemptLogin(LoginActivity.this, loginUser.getMobileNo(), loginUser.getPassword() );
                }
            }
        });

    }

    public static void attemptLogin(final Context context, String userId, String password) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", true, false);
        String apiUrl= Config.baseUrl+"userLogin.php?mobile_no="+userId+"&password="+password;
        // String apiUrl = "http://192.168.0.105/irg_crm/api/userLogin.php?&email_id=test%40test.com&password=12345";

        Log.e("apiUrl", apiUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                progressDialog.dismiss();
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
                        SharedPref.putUserRole(context, jsonObject1.getString("user_role"));
                        SharedPref.putEmailId(context, jsonObject1.getString("email_id"));
                        SharedPref.putUserImage(context, jsonObject1.getString("user_image"));
                        SharedPref.putPassword(context, jsonObject1.getString("password"));

                        OprActivity.finishAllOpenNewActivity(context, new MainActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response", error.toString());
                progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}