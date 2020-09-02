package com.irg.crm_admin.viewModel;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irg.crm_admin.activity.LoginActivity;
import com.irg.crm_admin.activity.RegistrationActivity;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.model.ModelRegistration;

public class RegistrationViewModel extends ViewModel {

    public MutableLiveData<String> areaId = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> mobileNo = new MutableLiveData<>();
    public MutableLiveData<String> userPhoto = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> conPassword = new MutableLiveData<>();
    public MutableLiveData<String> userRole = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
   // public MutableLiveData<String> stateName = new MutableLiveData<>();
   // public MutableLiveData<String> districtName = new MutableLiveData<>();
    //public MutableLiveData<String> cityName = new MutableLiveData<>();
   // public MutableLiveData<String> areaName = new MutableLiveData<>();

    public MutableLiveData<String> mTitle = new MutableLiveData<>();

    private MutableLiveData<ModelRegistration> userMutableLiveData;

    public MutableLiveData<ModelRegistration> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClickBackArrow(View view){
        Context context =  ((ContextWrapper)view.getContext()).getBaseContext();
        OprActivity.finishActivity(context);
    }

    /*Login*/
    public void onClickLogin(View view) {
        ModelRegistration loginUser = new ModelRegistration(mobileNo.getValue(), password.getValue());
        userMutableLiveData.setValue(loginUser);

    }

    public void onClickNoAcc(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    /*Registration*/
    public void onClickUploadImage(View view) {
       // ModelRegistration loginUser = new ModelRegistration(mobileNo.getValue(), password.getValue());
       // userMutableLiveData.setValue(loginUser);

        RegistrationActivity.showPreview(view);
    }

    public void onClickRegistration(View view) {
        ModelRegistration loginUser = new ModelRegistration(areaId.getValue(),userName.getValue(), userEmail.getValue(), mobileNo.getValue(),
                userPhoto.getValue(), password.getValue(), conPassword.getValue(), userRole.getValue(), address.getValue());
        userMutableLiveData.setValue(loginUser);

    }

    public void onClickAlreadyRegistered(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        view.getContext().startActivity(intent);
        ((AppCompatActivity) view.getContext()).finish();
    }

}