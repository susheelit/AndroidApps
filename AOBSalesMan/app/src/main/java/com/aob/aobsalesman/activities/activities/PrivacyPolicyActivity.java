package com.aob.aobsalesman.activities.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private WebView wb;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        initToolbar();
        if (InternetConnection.checkInternetConnectivity()) {
            wb = findViewById(R.id.webView1);
            wb.getSettings().setJavaScriptEnabled(true);
            wb.getSettings().setLoadWithOverviewMode(true);
            wb.getSettings().setUseWideViewPort(true);
            wb.setWebViewClient(new HelloWebViewClient());
            wb.loadUrl("https://aobindia.com/privacy-policy");
        } else {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Retry with Internet connection");
            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        finish();
                    }
                    return true;
                }
            });
            alertDialog.show();
        }

    }

    private void initToolbar() {
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

}
