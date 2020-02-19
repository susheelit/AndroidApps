package com.e.salestracker.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Tools;

public class DSRReportActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsrreport);
        Tools.setSystemBarColor(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle("Filter");

                findViewById(R.id.DSR_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.DSR_name).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                findViewById(R.id.DSR_project).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.visit).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.DSR_report_name).setVisibility(View.VISIBLE);
                findViewById(R.id.DSR_report_project).setVisibility(View.GONE);
                findViewById(R.id.Visit_summary).setVisibility(View.GONE);
            }
        });

                findViewById(R.id.DSR_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.DSR_name).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.DSR_project).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                findViewById(R.id.visit).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.DSR_report_name).setVisibility(View.GONE);
                findViewById(R.id.DSR_report_project).setVisibility(View.VISIBLE);
                findViewById(R.id.Visit_summary).setVisibility(View.GONE);

            }
        });

                findViewById(R.id.visit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.DSR_name).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.DSR_project).setBackgroundColor(getResources().getColor(R.color.colorlabel));
                findViewById(R.id.visit).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                findViewById(R.id.DSR_report_name).setVisibility(View.GONE);
                findViewById(R.id.DSR_report_project).setVisibility(View.GONE);
                findViewById(R.id.Visit_summary).setVisibility(View.VISIBLE);
            }
        });

                findViewById(R.id.Apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog =new ProgressDialog(DSRReportActivity.this);
                progressDialog.setMessage("Filtering...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        finish();
                    }
                },1000);

            }
        });
    }
}
