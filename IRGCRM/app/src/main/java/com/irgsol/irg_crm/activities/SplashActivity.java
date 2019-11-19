package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import com.irgsol.irg_crm.R;

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

   /* private void attemptLogin() {
        if (Config.isOnline(context)) {
            String memberId = SharedPref.getMemberID(context);
            String password = SharedPref.getPassword(context);
            SharedPref.putIsShow(context, "0");
            if (!TextUtils.isEmpty(memberId) && !TextUtils.isEmpty(password)) {
                // implement login with memberId
                LoginActivity.attemptLogin(context, SharedPref.getMemberID(context), SharedPref.getPassword(context), false);
            } else {
                String mobileNo = SharedPref.getMobileNo(context);
                if (!TextUtils.isEmpty(mobileNo)) {
                    RegistrationActivity.getRegistrationResult(mobileNo, false, context);

                } else {
                    OprActivity.openActivity(context, new LoginActivity());
                }
            }
        } else {
            alertNoInternet();
        }

    }

    private void alertNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage("Problem with internet connection. Try again");
        builder.setPositiveandroidx.appcompat.widget.AppCompatButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                attemptLogin();
            }
        });

        builder.setNegativeandroidx.appcompat.widget.AppCompatButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        builder.create();
        builder.show();
    }*/
}
