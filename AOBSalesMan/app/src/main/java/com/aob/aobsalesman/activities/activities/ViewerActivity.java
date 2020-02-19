package com.aob.aobsalesman.activities.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.aob.aobsalesman.R;
import com.google.android.youtube.player.YouTubeBaseActivity;

public class ViewerActivity extends YouTubeBaseActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        ImageView back_image = findViewById(R.id.back_image);
        ((TextView)findViewById(R.id.main_name)).setText(getIntent().getExtras().getString("header"));
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
        ImageView download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getExtras().getString("url"))));
            }
        });

       final ProgressBar progress = findViewById(R.id.progress);

        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                return false;
            }
            @Override
            public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(final WebView view, final String url) {
                progress.setVisibility(View.GONE);
                findViewById(R.id.progressbar).setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
            @Override public void onReceivedError(WebView view, WebResourceRequest request,
                                                  WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(ViewerActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onReceivedHttpError(
                    WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                recreate();
                Log.e("ReceivedHttpError", errorResponse.toString());
                Toast.makeText(ViewerActivity.this, "ReceivedHttpError:"+errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                           SslError error) {
                Toast.makeText(ViewerActivity.this, "ReceivedSslError", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("url",getIntent().getExtras().getString("url"));
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ getIntent().getExtras().getString("url"));
        //webView.loadUrl("https://www.google.com/");

        Log.e("final url","https://docs.google.com/gview?embedded=true&url="+ getIntent().getExtras().getString("url"));

    }
}
