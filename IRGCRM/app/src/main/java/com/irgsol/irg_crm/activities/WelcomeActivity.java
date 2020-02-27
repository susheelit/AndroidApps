package com.irgsol.irg_crm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;

public class WelcomeActivity extends AppCompatActivity {

    Context context;
    Button btnContinue;
    TextView tvOrderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        context = WelcomeActivity.this;
        btnContinue = findViewById(R.id.btnContinue);
        tvOrderNo = findViewById(R.id.tvOrderNo);

        tvOrderNo.setText("Your order no. is : "+120);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DasboardNewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, DasboardNewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
