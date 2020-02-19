package com.aob.aobsalesman.activities.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.MyAnalytics;
import com.aob.aobsalesman.activities.SendMail;
import com.aob.aobsalesman.activities.model.DataBaseRecord;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MyDatabase;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;
import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    androidx.appcompat.widget.AppCompatEditText password;
    AutoCompleteTextView email;
    String Email,Password;
    String emailPattern="[a-zA-Z]+@[a-z]+\\.+[a-z]+";
    boolean aBoolean=false;
    @SuppressLint("HardwareIds")
    String android_id;
    MyDatabase database;
    private ProgressDialog progressDialog;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        (findViewById(R.id.login)).setOnClickListener(this);
        (findViewById(R.id.sign_up)).setOnClickListener(this);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        getInit();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
         formattedDate = df.format(c);

         database=new MyDatabase(this);
         ArrayList<String> AllData = new ArrayList<>();
          AllData=database.getAllRecord();
          if (AllData!=null) {
              if (AllData.size()>0){

              ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                      android.R.layout.simple_dropdown_item_1line, AllData);
                  ((AutoCompleteTextView)findViewById(R.id.email_id)).setAdapter(adapter);
              }
          }

          try {

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"2");
            MyAnalytics.getInstance(this).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        }catch (Exception e){}

        ((TextView)findViewById(R.id.privacy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aobindia.com/privacy-policy/"));
                    startActivity(browserIntent);
                }catch (Exception e){}
            }
        });
        ((TextView)findViewById(R.id.webside)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aobindia.com/"));
                    startActivity(browserIntent);
                }catch (Exception e){}
            }
        });

        ((TextView)findViewById(R.id.forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent(LoginActivity.this, ForgotPasswordVerification.class);
                    startActivity(intent);
                }catch (Exception e){}
            }
        });
    }
    public void getInit() {

        final SharedPreferences preferences =
                getSharedPreferences("earning", MODE_PRIVATE);
      if (!preferences.getBoolean("earning_dialog", false)) {

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            dialog.setContentView(R.layout.dialog_event);
            dialog.setCancelable(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            ( dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences =
                            getSharedPreferences("earning", MODE_PRIVATE);
                    preferences.edit()
                            .putBoolean("earning_dialog",true).apply();
                    dialog.dismiss();
                }
            });
            ( dialog.findViewById(R.id.add)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences =
                            getSharedPreferences("earning", MODE_PRIVATE);
                    preferences.edit()
                            .putBoolean("earning_dialog",true).apply();
                    startActivity(new Intent(LoginActivity.this,NewRegistrationActivity.class));
                    dialog.cancel();
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);
      }
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                   //sendEmail();
                validation();

                break;
            case R.id.sign_up:
                startActivity(new Intent(this,NewRegistrationActivity.class));
        }
        hideKeyboard(this);
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
    private void sendEmail() {
        //Getting content for email
        String email = "dhruv.aob@gmail.com";
        String subject = "mail";
        String message = "test";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
    private void validation() {
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        Email = email.getText().toString();
        Password = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if ( !isEmailValid(Email)) {
            email.setError("Please enter registered email id");
            focusView = email;
            cancel = true;
        }

        if (TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            password.setError("Please enter valid Password");
            focusView = password;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{

            if (InternetConnection.checkInternetConnectivity()) {
                jsonParseForLogin();

            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
                alertDialog.setMessage("Check your internet connection");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }
    }
    private void jsonParseForLogin(){

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/sign_in_validate_new.php?",
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
                                    ShareprefreancesUtility.getInstance().saveString("mobile",jsonObject1.getString("phone"));
                                    ShareprefreancesUtility.getInstance().saveString("userlogin","online");

                                    DataBaseRecord dataBaseRecord = new DataBaseRecord(jsonObject1.getString("email"),
                                            jsonObject1.getString("name"),
                                            true, formattedDate);
                                    database.addRecord(dataBaseRecord);

                                    email.setText("");
                                    password.setText("");
                                    sendFirebaseToken();
                                    //Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                  //  startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                 //   finish();
                                }
                            }
                            else if(success.equalsIgnoreCase("2")){
                                progressDialog.dismiss();
                                  Bundle bundle=new Bundle();
                                  bundle.putString("Email",email.getText().toString());
                                  bundle.putString("Password",password.getText().toString());
                                  Intent intent=new Intent(LoginActivity.this, VerificationActivity.class);
                                  intent.putExtras(bundle);
                                  startActivity(intent);

                            }
                            else if(success.equalsIgnoreCase("3")){
                                progressDialog.dismiss();
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                                alertDialog.setMessage("Please enter correct Password to continue.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }

                            else {
                                progressDialog.dismiss();
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                                alertDialog.setMessage("Please enter correct Email_Id to continue.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
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
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
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

                params.put("email",Email);
                params.put("password",Password);
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
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/firebase_notification_salesman_login.php?phone_id="+android_id+"&email="+Email+"&token="+ShareprefreancesUtility.getInstance().getString("token"),
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");


                            if (success.equalsIgnoreCase("0")) {

                                  //  Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                   startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                }
                                else {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                                alertDialog.setMessage("This email is not registered with us, kindly register.");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }
                            } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
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
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
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
    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
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

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
