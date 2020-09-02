package com.ceodelhi.filesystemapp.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.utility.UserPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Random;

import static com.ceodelhi.filesystemapp.utility.MyConstants.NAMESPACE;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialogFinish;

public class LoginActivity extends AppCompatActivity {

    private EditText et_mobile;
    private TextView submitbtn;
    private String mobile;
    private UserPreferences userPreferences;
    private int otpCode;
    private String messageBody;
    private ArrayList<Integer> otps;
    private ProgressDialog progressDialog;
    private String webResponse2 = "";
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
        userPreferences = new UserPreferences(LoginActivity.this);
        otps = new ArrayList<>();
        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);

        if (ConnectionDetector.isConnectedToInernet(LoginActivity.this)) {
            checkLogin();
        } else {
            showAlertDialog(LoginActivity.this, "Please check your internet connection", false);
        }


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidekeyBoard();
                if (ConnectionDetector.isConnectedToInernet(LoginActivity.this)) {
                    if ((et_mobile.getText().toString().equals(""))) {
                        showAlertDialog(LoginActivity.this, "Please enter your mobile number", false);
                    } else if ((et_mobile.getText().toString().equals(""))
                            || (et_mobile.getText().toString().length() < 10)) {
                        showAlertDialog(LoginActivity.this, "Please enter your correct mobile number", false);
                    } else {
                        mobile = et_mobile.getText().toString();
                        if (mobile.equalsIgnoreCase(getResources().getString(R.string.mob_no))) {
                            new SendOTP().execute();
                        } else {
                            showAlertDialog(LoginActivity.this, "You aren't authorised person to access this mobile app !", false);
                        }
                    }
                } else {
                    showAlertDialog(LoginActivity.this, "Please check your internet connection", false);
                }
            }
        });

    }


    private class SendOTP extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
            createOTP();
            hidekeyBoard();
        }

        @Override
        protected String doInBackground(String... params) {
            userPreferences.setOTP("" + otpCode);
            System.out.println("MyConstants.otpMessage " + otpCode);
            otps.add(otpCode);
            try {
                SoapObject request = new SoapObject(NAMESPACE, MyConstants.METHOD_SMS);
                request.addProperty("strMobileNos", mobile);
                request.addProperty("strMessage", messageBody);
                request.addProperty("strSenderID", MyConstants.OTPSENDER);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL1);

                androidHttpTransport.call(MyConstants.SOAP_ACTION_SMS, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse2 = response.toString();
                System.out.println("test response " + response.toString());
            } catch (Exception e) {
                System.out.println("test Exception " + e.getMessage());
            }
            return webResponse2;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("OK")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (this != null && !isFinishing()) {
                            showDialogOtp();
                            progressDialog.dismiss();
                        }
                    }
                }, 6000);
            } else {
//                Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                showAlertDialogFinish(LoginActivity.this, getString(R.string.error));
                progressDialog.dismiss();
            }

        }
    }

    private void showDialogOtp() {
        AlertDialog.Builder alert;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            alert = new AlertDialog.Builder(LoginActivity.this);
        }
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View confirmDialog = li.inflate(R.layout.confirm_otp, null);
        alert.setView(confirmDialog);
        final EditText editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        alert.setPositiveButton(android.R.string.ok, null);
//        alert.setNegativeButton(android.R.string.cancel, null);
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setNegativeButton("Resend",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
                    }
                });
        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextConfirmOtp.getText().toString().equals("")) {
                    if ((userPreferences.getOTP().equalsIgnoreCase
                            (editTextConfirmOtp.getText().toString())) ||
                            (otps.contains(Integer.parseInt(editTextConfirmOtp.getText().toString())))) {
                        userPreferences.setMobile(mobile);
                        userPreferences.setLogin(true);
                        hidekeyBoard();
                        alertDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Mobile number validated", Toast.LENGTH_LONG).show();
                        et_mobile.clearFocus();
                        getToken();
                       // openMainPage();
                    } else {
                        showAlertDialog(LoginActivity.this, "OTP is invalid", false);
                        editTextConfirmOtp.setText("");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "OTP not entered", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                new SendOTP().execute();
            }
        });
    }

    private void checkLogin() {
        if (userPreferences.getLogin()) {
            if (ConnectionDetector.isConnectedToInernet(LoginActivity.this)) {
               // openMainPage();
               String isTokenSend =   userPreferences.getIsTokenSend();
                if(isTokenSend != null &&  isTokenSend.equalsIgnoreCase("true")){
                    openMainPage();
                }else {
                    getToken();
                }

            } else {
                Snackbar.make(submitbtn, "Please check your internet connection", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void openMainPage() {
        // set 1 to check file letter status
       userPreferences.setLetterorFileStatus("1");

        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    private String createOTP() {
        otpCode = 1234 + new Random().nextInt(8765);
        messageBody = "Your one time password(OTP) is: " + otpCode;
        return messageBody;
    }

    protected void hidekeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getToken() {
        try {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("FileApp", "getInstanceId failed", task.getException());
                                return;
                            }
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                          //  userPreferences.setToken(token);
                            String android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                            Log.d("Tag123 android_id", android_id);
                            Log.d("Tag123", token);
                            getTokenandDeviceIdTosendServer(android_id, token);

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTokenandDeviceIdTosendServer(String android_id, String token) {

        if (ConnectionDetector.isConnectedToInernet(LoginActivity.this)) {
            new sendTokenAndDeviceIdToServer().execute(android_id, token);
        } else {
            Snackbar.make(submitbtn, "Please check your internet connection", Snackbar.LENGTH_LONG).show();
        }

    }

    private class sendTokenAndDeviceIdToServer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, MyConstants.METHOD_SEND_TOKEN_DEVICE_ID);
                request.addProperty("DeviceID", params[0]);
                request.addProperty("DeviceToken", params[1]);
                request.addProperty("MobileNo", mobile);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL);

                androidHttpTransport.call(MyConstants.SOAP_ACTION_SEND_TOKEN_DEVICE_ID, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse2 = response.toString();
                System.out.println("test response " + response.toString());

            } catch (Exception e) {
                System.out.println("test Exception " + e.getMessage());
            }
            return webResponse2;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("1")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (this != null && !isFinishing()) {
                           // showDialogOtp();
                            userPreferences.setIsTokenSend("true");
                            openMainPage();
                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
            } else {
                showAlertDialogFinish(LoginActivity.this, getString(R.string.error));
                progressDialog.dismiss();
            }
        }
    }

}
