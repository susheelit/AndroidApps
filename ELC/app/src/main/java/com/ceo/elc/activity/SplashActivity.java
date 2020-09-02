package com.ceo.elc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.ceo.elc.R;
import com.ceo.elc.common.BaseActivity;
import com.ceo.elc.common.Konstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

public class SplashActivity extends BaseActivity {

    private AlertDialog alertDialog;
    private String currentVersion;
    private Context mContext;
    private String clubID, webResponse;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setProgress(0);
        if (checkNetwork()) {
            checkVersionCode();
        } else {
            Konstant.toastShow(mContext, getResources().getString(R.string.no_internet));
        }

    }

    private void checkVersionCode() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                new GetVersionCode().execute();
            }
        }, 500);
    }

    class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName() + "&hl=en")
                        .timeout(1000) // time out in 3 second
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);

            clubID = getSetting("ClubID", "");
            if (!TextUtils.isEmpty(clubID)) {
                new LoginUser().execute();
            } else {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            // Uncomment when app live
          /* if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (!currentVersion.equalsIgnoreCase(onlineVersion)) {
                    //show anything
                    String currentversion = currentVersion;
                    showUpdateDialog(onlineVersion, currentversion);
                } else {
                    // check whether user is login of not
                    clubID = getSetting("ClubID", "");
                    if(!TextUtils.isEmpty(clubID)){
                        new  loginUser().execute();
                    }else {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
            }*/
        }
    }

    private void showUpdateDialog(String onlineVersion, String currentversion) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.update_dialog);
        TextView update = dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                }
                return true;
            }
        });
        dialog.show();
    }


    /*Attempt to login user*/
    public class LoginUser extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            webResponse = "";
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_LOGIN);

                // to start service
                System.out.println("ClubID " + clubID);
                request.addProperty("ClubID", "" + clubID);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_LOGIN, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("result", "" + result);

            if (!result.equalsIgnoreCase("No Record found") &&
                    !result.equalsIgnoreCase("99")) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (mContext != null/* && !isFinishing()*/) {
                            //  progressDialog.dismiss();
                            saveData(result);
                        }
                    }
                }, 500);
            } else {
                Konstant.toastShow(mContext, "Something went wrong!!");
                // progressDialog.dismiss();
            }
        }
    }

    public void saveData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            setSetting("ClubID", jsonObject.getString("ClubID"));
            setSetting("DISTRICT_NAME", jsonObject.getString("DISTRICT_NAME"));
            setSetting("DIST_NO", jsonObject.getString("DIST_NO"));
            setSetting("AC_NAME", jsonObject.getString("AC_NAME"));
            setSetting("AC_NO", jsonObject.getString("AC_NO"));
            setSetting("PS_NO", jsonObject.getString("PS_NO"));
            setSetting("ELCType", jsonObject.getString("ELCType"));
            setSetting("NameOfInstitute", jsonObject.getString("NameOfInstitute"));
            setSetting("NameOfNodalPerson", jsonObject.getString("NameOfNodalPerson"));
            setSetting("AddressOfInstitute", jsonObject.getString("AddressOfInstitute"));
            setSetting("Pincode", jsonObject.getString("Pincode"));
            setSetting("ClubNo", jsonObject.getString("ClubNo"));
            setSetting("EntryDate", jsonObject.getString("EntryDate"));

            Intent intent = new Intent(mContext, CreateEventActivity.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}