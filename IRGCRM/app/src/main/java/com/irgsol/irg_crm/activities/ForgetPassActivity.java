package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.common.OprActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassActivity extends AppCompatActivity {

    Context context;
    private androidx.appcompat.widget.AppCompatButton btnSubmit;
    private EditText etMembetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initView();
    }

    private void initView() {
        context = ForgetPassActivity.this;
        etMembetId = findViewById(R.id.etMembetId);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    private void validate() {
        String memberId ="";
        memberId = etMembetId.getText().toString();
        if(!TextUtils.isEmpty(memberId)){
            attemptForgetPassword(memberId);
        }else {
            Config.alertBox("Please Enter Member ID", context);
        }
    }

    private void attemptForgetPassword(String memberId) {

        Map<String, String> mapData= new HashMap<>();
        mapData.put("MemberID", memberId);
        List<Map<String, String>>arrayList = new ArrayList<>();
        arrayList.add(mapData);

       /* ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.ForgetPass(arrayList).enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {

                if(response.isSuccessful()){
                    Config.toastShow(response.body().get(0).get("Message"), context);
                    if(response.body().get(0).get("Result").compareTo("1")==0){
                        OprActivity.finishAllOpenNewActivity(context, new LoginActivity());
                    }

                }else {
                    Config.toastShow(getResources().getString(R.string.went_wrong),context);
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {
                Config.toastShow(getResources().getString(R.string.went_wrong),context);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishAllOpenNewActivity(context, new LoginActivity());
    }

    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishAllOpenNewActivity(context, new LoginActivity());
        return true;
    }
}
