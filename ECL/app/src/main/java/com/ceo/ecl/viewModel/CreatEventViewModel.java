package com.ceo.ecl.viewModel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.InverseMethod;

import com.ceo.ecl.BR;
import com.ceo.ecl.R;
import com.ceo.ecl.common.Config;
import com.ceo.ecl.model.CreateEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreatEventViewModel extends BaseObservable {

    private CreateEvent createEvent;
    private String successMessage = "Event Created";
    private String errorMessage = "Failed to create event";
    private String alertDateMessage = "Please selecet event date and time.";
   // private String alertTimeMessage = "Please select event time.";
    Context context;

    public CreatEventViewModel(Context context) {
        this.context = context;
        createEvent = new CreateEvent("", "");
    }

    @Bindable
    private String toastMessage = null;
    @Bindable
    private String alertMessage = null;

    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

   /* private void setAltDateMessage(String alertMessage){
        this.alertMessage = alertMessage;
        notifyPropertyChanged(BR.alertMessage);
    }*/

   /* private void setAltTimeMessage(){
        this.alertMessage = alertMessage;
        notifyPropertyChanged(BR.alertMessage);
    }*/

    public void setDesc(String description) {
        createEvent.setDecription(description);
        notifyPropertyChanged(BR.desc);
    }

   public void setEventDate(String eventDate) {
        createEvent.setEventDate(eventDate);
        notifyPropertyChanged(BR.eventDate);
    }

    @Bindable
    public String getDesc(){
        return createEvent.getDecription();
    }

    @Bindable
    public String getEventDate(){
        return createEvent.getEventDate();
    }

    public void onEventClicked() {
        // open date and time picker
       /* if (isInputDataValid())
            setToastMessage(successMessage);
            //context.startActivity(new Intent(context, EmployeesListActivity.class));
        else
            setToastMessage(errorMessage);*/
    }

    public void onSubmitClicked() {
        if (isInputDataValid())
            setToastMessage(successMessage);
            //context.startActivity(new Intent(context, EmployeesListActivity.class));
        else
            setToastMessage(alertMessage);
    }

    public boolean isInputDataValid() {
        if(!TextUtils.isEmpty((getEventDate())) && !TextUtils.isEmpty((getDesc()))){
            return true;
        }else {
            return false;
        }
       // return !TextUtils.isEmpty((getEventDate()));
    }

    @InverseMethod("stringToDate")
    public static String dateToString(EditText view, long oldValue,
                                      long value) {
        // Converts long to String.
        String cc = String.valueOf(value);
        Log.e("cc", cc);
        return cc;
    }

    public static long stringToDate(EditText view, String oldValue,
                                    String value) {
        // Converts String to long.
        Log.e("cc 1", value);
        return Long.parseLong(value);
    }




}
