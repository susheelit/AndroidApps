package com.ceo.ecl.viewModel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.ceo.ecl.BR;
import com.ceo.ecl.activity.CreateEventActivity;
import com.ceo.ecl.model.Login;

public class LoginViewModel extends BaseObservable {

    private Login login;
    private String successMessage = "Login was successful";
    private String errorMessage = "Institution ID is invalid";
    Context context;

    @Bindable
    private String toastMessage = null;

    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public void setInstID(String instID) {
        login.setInstituteID(instID);
        notifyPropertyChanged(BR.instID);
    }

    @Bindable
    public String getInstID() {
        return login.getInstituteID();
    }

    public LoginViewModel(Context context) {
        this.context = context;
        login = new Login("");
    }

    public void onLoginClicked() {
        if (isInputDataValid()) {
            setToastMessage(successMessage);
            context.startActivity(new Intent(context, CreateEventActivity.class));
            ((AppCompatActivity)context).finish();
        } else {
            setToastMessage(errorMessage);
        }
    }

    public boolean isInputDataValid() {

        if (!TextUtils.isEmpty(getInstID())) {
            return true;
        } else {
            return false;  // change to false later
        }
    }

}
