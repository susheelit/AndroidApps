package com.aob.aobsalesman.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.LeadFragement;

public class LeadActivity extends AppCompatActivity implements View.OnClickListener {

    long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initToolbar();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.add(R.id.main_lead,new LeadFragement(),"frag_lead");
        fragmentTransaction.commit();

        findViewById(R.id.mysales).setOnClickListener(this);
        findViewById(R.id.myearning).setOnClickListener(this);
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}


    }
    private void initToolbar() {
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mysales:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(LeadActivity.this,MySaleActivity.class));
                finish();
                break;
            case R.id.myearning:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(LeadActivity.this,TransactionActivity.class));
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        ((TextView)findViewById(R.id.main_name)).setText("My Leads");
        super.onBackPressed();
    }
}
