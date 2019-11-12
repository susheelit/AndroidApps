package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.common.SharedPref;
import com.irgsol.irg_crm.utils.Config;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangePassActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context context;
    private androidx.appcompat.widget.AppCompatButton btnChangePass;
    private EditText etOldPass, etNewPass, etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initView();
    }

    private void initView() {
        context = ChangePassActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, getResources().getString(R.string.change_password),context);
        etOldPass= findViewById(R.id.etOldPass);
        etNewPass= findViewById(R.id.etNewPass);
        etConfirmPass= findViewById(R.id.etConfirmPass);

        btnChangePass = findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        String savedPass = "", oldPass = "", newPass = "", conPass = "";

        savedPass = SharedPref.getPassword(context);
        oldPass = etOldPass.getText().toString().trim();
        newPass = etNewPass.getText().toString().trim();
        conPass = etConfirmPass.getText().toString().trim();

        etOldPass.setError(null);
        etNewPass.setError(null);
        etConfirmPass.setError(null);

        boolean cancel = false;


        if (TextUtils.isEmpty(oldPass)) {
            cancel = true;
            etOldPass.setError("Enter Old Password");
            return;
        }

        if (savedPass.compareTo(oldPass) != 0) {
            cancel = true;
            Config.alertBox("Wrong Old Password", context);
            return;
        }

        if (TextUtils.isEmpty(newPass)) {
            cancel = true;
            etNewPass.setError("Enter New Password");
            return;
        }

        if (TextUtils.isEmpty(conPass)) {
            cancel = true;
            etConfirmPass.setError("Enter Confirm Password");
            return;
        }

        if (newPass.compareTo(conPass) != 0) {
            cancel = true;
            Config.alertBox("New Password and Confirm Password does not match", context);
        }

        if (!cancel) {
            //attemptChangePass(context, oldPass, newPass);

        }

    }

   /* private void attemptChangePass(Context context, String oldPass, String newPass) {

        Map<String, String> mapData = new HashMap<>();
        mapData.put("MemberID", SharedPref.getMemberID(context));
        mapData.put("OldPassword", oldPass);
        mapData.put("NewPassword", newPass);
        List<Map<String, String>> arrayList = new ArrayList<>();
        arrayList.add(mapData);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.ChangePassword(arrayList).enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {

                if(response.isSuccessful()){
                    Config.toastShow(response.body().get(0).get("Message"), context);
                    if(response.body().get(0).get("Result").compareTo("1")==0){
                        Config.logoutUser(context);
                    }
                }else {
                    Config.toastShow(getResources().getString(R.string.went_wrong), context);
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {
                Config.toastShow(getResources().getString(R.string.went_wrong), context);
            }
        });


    }*/


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
