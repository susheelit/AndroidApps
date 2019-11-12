package com.irgsol.irg_crm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;

public class ContactUsActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    private WebView webView;
  //  private ProgressBar progressBar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initView();
    }

    private void initView() {
        context = ContactUsActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithoutBack(toolbar, context);
        webView = findViewById(R.id.webView);
     //   progressBar = findViewById(R.id.progressBar);
       // setDetails();
        setdetails1();
    }

    private void setdetails1() {
        progressDialog = ProgressDialog.show(context, "", "Loading...", true, true);

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
       // String url = ApiClient.BASE_URL+"/contact.html";
        webView.loadUrl("https://www.cfcs.co.in/contactus.htm");
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
           // progressBar.setVisibility(View.VISIBLE);
           view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

           /// progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

    }

    private void setDetails() {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //String url = ApiClient.BASE_URL+"/contact.html";
        //webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }

    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }
}
