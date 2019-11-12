package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.common.OprActivity;

public class AddShopActivity_2 extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btnSubmit;
    Context context;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_2);
        initView();
    }

    private void initView() {
        context = AddShopActivity_2.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Shop Address",context );
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.toastShow("Shop Added !!", context);

                OprActivity.finishAllOpenNewActivity(context, new DasboardActivity());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }
}
