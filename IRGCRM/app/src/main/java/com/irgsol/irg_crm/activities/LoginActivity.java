package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.utils.Config;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btnLogin;
    Context context;
    private EditText etUserId, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        context = LoginActivity.this;
        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OprActivity.finishAllOpenNewActivity(context,new DasboardActivity());
                validate();
            }
        });
    }

    private void validate() {
        String userId="", password="";
        userId = etUserId.getText().toString();
        password = etPassword.getText().toString();

        if(userId.compareTo("")==0){
            Config.alertBox("Please enter user name", context);
        }else if(password.compareTo("")==0){
            Config.alertBox("Please enter password", context);
        }else {
          //  attemptLogin(userId, password);
        }
    }

    /*private void attemptLogin(String userId, String password) {

        Call<UserList> call2 = apiInterface.doGetUserList("2");
        call2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;
                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList) {
                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
