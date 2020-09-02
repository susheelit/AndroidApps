package com.irg.crm_admin.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.irg.crm_admin.R;
import com.irg.crm_admin.databinding.ActivityAddServiceAreaBinding;
import com.irg.crm_admin.model.ModelServiceArea;
import com.irg.crm_admin.viewModel.ServiceAreaViewModel;

public class AddServiceAreaActivity extends AppCompatActivity  {
    ActivityAddServiceAreaBinding binding;
    private ServiceAreaViewModel serviceAreaViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceAreaViewModel = ViewModelProviders.of(this).get(ServiceAreaViewModel.class);
        binding = DataBindingUtil.setContentView(AddServiceAreaActivity.this, R.layout.activity_add_service_area);
        binding.setLifecycleOwner(this);
        binding.setViewModel(serviceAreaViewModel);

        setUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    private void setUp() {

        serviceAreaViewModel.getUser(AddServiceAreaActivity.this).observe(AddServiceAreaActivity.this, new Observer<ModelServiceArea>() {
            @Override
            public void onChanged(@Nullable ModelServiceArea modelProduct) {

               // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //getSupportActionBar().setTitle("test");

                /*if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getMobileNo())){
                    binding.etMobile.setError("Enter Mobile no");
                    binding.etMobile.requestFocus();
                }else if(Objects.requireNonNull(loginUser).getMobileNo().length() !=10){
                    binding.etMobile.setError("Enter valid Mobile no");
                    binding.etMobile.requestFocus();
                }else if(TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())){
                    binding.etPassword.setError("Enter password");
                    binding.etPassword.requestFocus();
                }else if(!(Objects.requireNonNull(loginUser).getPassword().length() >5
                        && Objects.requireNonNull(loginUser).getPassword().length() <11 )){
                    binding.etPassword.setError("Password length should be between 5-10 characters.");
                    binding.etPassword.requestFocus();
                }else {
                    //Config.toastShow("Login Success",LoginActivity.this);
                    attemptLogin(AddShopActivity.this, loginUser.getMobileNo(), loginUser.getPassword() );
                }*/
                //addProduct(modelProduct);

            }
        });

    }

}
