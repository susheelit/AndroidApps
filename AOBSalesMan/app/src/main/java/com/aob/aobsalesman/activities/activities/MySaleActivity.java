package com.aob.aobsalesman.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.MySaleFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MySaleActivity extends AppCompatActivity implements View.OnClickListener {
    long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sale);

        initToolbar();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.add(R.id.main_lead,new MySaleFragment(),"mysale_frag");
        fragmentTransaction.commit();

        findViewById(R.id.mylead).setOnClickListener(this);
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
            case R.id.mylead:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(MySaleActivity.this,LeadActivity.class));
                finish();
                break;

            case R.id.myearning:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(MySaleActivity.this,TransactionActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ((TextView)findViewById(R.id.main_name)).setText("My Sales");
        super.onBackPressed();
    }
}
