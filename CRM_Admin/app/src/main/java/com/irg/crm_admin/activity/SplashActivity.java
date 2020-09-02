package com.irg.crm_admin.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.irg.crm_admin.R;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.common.SharedPref;

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
        //progressBar = findViewById(R.id.progressBar);
        openDasboard();
    }

    private void openDasboard() {

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 1000);*/

        if (!TextUtils.isEmpty(SharedPref.getMobileNo(context)) && !TextUtils.isEmpty(SharedPref.getPassword(context))) {
            LoginActivity.attemptLogin(context, SharedPref.getMobileNo(context), SharedPref.getPassword(context));
        } else {
            OprActivity.finishAllOpenNewActivity(context, new LoginActivity());
        }
    }
}
