package com.e.salestracker.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
private AlertDialog alertDialog;
    private String currentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkVersionCode();
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
        },2000);
    }
    class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()  + "&hl=en")
                        .timeout(30000)
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

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (!currentVersion.equalsIgnoreCase(onlineVersion)) {
                    //show anything
                    String currentversion=currentVersion;

                    showUpdateDialog(onlineVersion,currentversion);
                /*}else if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("")){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();*/
                } else  if ((ShareprefreancesUtility.getInstance().getString("userlogin")).equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Admin")){
                    startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                    //startActivity(new Intent(SplashActivity.this, LocationMainActivity.class));
                    finish();
                } else if( ShareprefreancesUtility.getInstance().getString("role").compareToIgnoreCase("client") == 0){
                        startActivity(new Intent(SplashActivity.this, LocationMainActivity.class));
                        finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }
            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }

    private void showUpdateDialog(String onlineVersion, String currentversion) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.update_dialog);
        TextView update=dialog.findViewById(R.id.update);
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

/*
    @RequiresApi(28)
    private static class OnUnhandledKeyEventListenerWrapper implements View.OnUnhandledKeyEventListener {
        private ViewCompat.OnUnhandledKeyEventListenerCompat mCompatListener;

        OnUnhandledKeyEventListenerWrapper(ViewCompat.OnUnhandledKeyEventListenerCompat listener) {
            this.mCompatListener = listener;
        }

        public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
            return this.mCompatListener.onUnhandledKeyEvent(v, event);
        }
    }*/

}
