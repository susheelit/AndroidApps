package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;

public class MyKYCActivity extends AppCompatActivity {

    TextView pan_p, aadhaar_p, beneficiary_p, bank_account_number_p, bank_name_p, bank_branch_p, ifsc_p;
    private long mLastClickTime4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kyc);
        initToolbar();
        initViews();
        setFunctionality();
        setValueAfterRegsitration();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My KYC");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews() {

        pan_p = findViewById(R.id.pan_p);
        aadhaar_p = findViewById(R.id.aadhaar_p);
        beneficiary_p = findViewById(R.id.beneficiary_p);
        bank_account_number_p = findViewById(R.id.bank_account_number_p);
        bank_name_p = findViewById(R.id.bank_name_p);
        bank_branch_p = findViewById(R.id.bank_branch_p);
        ifsc_p = findViewById(R.id.ifsc_p);
    }
    private void setFunctionality() {

        ( findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime4 < 1000) {
                    return;
                }
                mLastClickTime4 = SystemClock.elapsedRealtime();
                startActivity(new Intent(MyKYCActivity.this, KYCActivity.class));
                finish();
            }
        });

    }
    public void setValueAfterRegsitration() {

        try {
            if (!ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("pending")) {

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("pan_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("pan_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("pan_number", "") != null
                ) {

                    pan_p.setText(ShareprefreancesUtility.getInstance().getString("pan_number", ""));

                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_account_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_account_number", "") != null
                ) {

                    bank_account_number_p.setText(ShareprefreancesUtility.getInstance().getString("bank_account_number", ""));
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""))
                        && !ShareprefreancesUtility.getInstance().getString("aadhaar_number", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("aadhaar_number", "") != null
                ) {

                    aadhaar_p.setText(ShareprefreancesUtility.getInstance().getString("aadhaar_number", ""));

                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("beneficiary_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("beneficiary_name", "") != null
                ) {

                    beneficiary_p.setText(ShareprefreancesUtility.getInstance().getString("beneficiary_name", ""));
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("branch", ""))
                        && !ShareprefreancesUtility.getInstance().getString("branch", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("branch", "") != null
                ) {

                    bank_branch_p.setText(ShareprefreancesUtility.getInstance().getString("branch", ""));
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("bank_name", ""))
                        && !ShareprefreancesUtility.getInstance().getString("bank_name", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("bank_name", "") != null
                ) {

                    bank_name_p.setText(ShareprefreancesUtility.getInstance().getString("bank_name", ""));
                }

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""))
                        && !ShareprefreancesUtility.getInstance().getString("ifsc_code", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("ifsc_code", "") != null
                ) {

                    ifsc_p.setText(ShareprefreancesUtility.getInstance().getString("ifsc_code", ""));
                }
            }

        }catch (Exception e){}
    }

}