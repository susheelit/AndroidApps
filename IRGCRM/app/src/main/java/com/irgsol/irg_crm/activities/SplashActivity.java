package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.SharedPref;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Context context;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        context = SplashActivity.this;
        progressBar = findViewById(R.id.progressBar);
       // attemptLogin();
        openDasboard();
    }

    private void openDasboard() {

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 3000);*/

       LoginActivity.attemptLogin(context, SharedPref.getUserID(context), SharedPref.getPassword(context));


    }
}
