package com.ceo.elc.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by BBh on 17-Jan-2020.
 */

public class BaseActivity extends AppCompatActivity {


    private ProgressDialog mProgressDialog;
    protected void showProgressDialog(String title, String message){
        if(mProgressDialog == null){
           /* mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");*/

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message); // Setting Message
            mProgressDialog.setTitle(title); // Setting Title
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            mProgressDialog.show(); // Display Progress Dialog
            mProgressDialog.setCancelable(false);
           // mProgressDialog.setIcon(R.mipmap.app_icon);


        }
        mProgressDialog.show();
    }
    protected void dismisProgressDialog(){
        mProgressDialog.dismiss();
    }

    protected boolean checkNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;
    }

    public void doPostRequest(String base_url,String soap_action,String soap_object_request,final int returnCode)
    {


        final OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Konstant.SOAP_MEDIA_TYPE, soap_object_request);

        final Request request = new Request.Builder()
                .url(base_url)
                .post(body)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("soapaction", soap_action)
                .addHeader("cache-control", "no-cache")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                onFailedRequest(mMessage, returnCode);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e("Success Response", mMessage);
                onFinishRequest(mMessage, returnCode);
            }
        });
    }

    protected void onFinishRequest(String response, int returnCode){
        // override in child class
    }

    protected void onFailedRequest( String errorMsg,int returnCode){
        // override in child class
    }

    public void setSetting(String key, String value) {
        SharedPreferences settings = getSharedPreferences("CEOELC", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        // Commit the edits!
        editor.commit();
    }

    public String getSetting(String key, String def) {
        SharedPreferences settings = getSharedPreferences("CEOELC", 0);
        return settings.getString(key, def);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeStatusBarColor(Activity activity, int color)
    {
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity, color));
    }
}
