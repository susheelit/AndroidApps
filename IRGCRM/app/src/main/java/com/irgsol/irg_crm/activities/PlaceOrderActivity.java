package com.irgsol.irg_crm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.utils.Config;

public class PlaceOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context context;
    private Button btnSubmit;
    private TextView etTotalItem,etTotalAmt;
    private androidx.appcompat.widget.AppCompatEditText  etContactPerson, etMobileNo;
    private RadioButton rbCash,rbCradit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        initView();
    }

    private void initView() {
        context = PlaceOrderActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, getResources().getString(R.string.place_order),context);
        etTotalItem= findViewById(R.id.etTotalItem);
        etContactPerson= findViewById(R.id.etContactPerson);
        etMobileNo= findViewById(R.id.etMobileNo);
        etTotalAmt = findViewById(R.id.etTotalAmt);
        rbCash= findViewById(R.id.rbCash);
        rbCradit = findViewById(R.id.rbCradit);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {

        String totalItem="", totalAmt="", contactPerson="", mobileNo="";

        totalItem = etTotalItem.getText().toString();
        totalAmt = etTotalAmt.getText().toString();
        contactPerson = etContactPerson.getText().toString();
        mobileNo = etMobileNo.getText().toString();

        if(contactPerson.isEmpty()){
            Config.alertBox("Please enter contact person name", context);
        }else if(mobileNo.isEmpty()){
            Config.alertBox("Please enter mobile no", context);
        }else if(mobileNo.length() !=10){
            Config.alertBox("Please enter 10 digit mobile no ", context);
        }else {
            Intent intent = new Intent(context, WelcomeActivity.class);
            intent.putExtra("contactPerson", contactPerson);
            intent.putExtra("mobileNo", mobileNo);
            startActivity(intent);
        }

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
