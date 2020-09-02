package com.ceo.elc.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ceo.elc.R;
import com.ceo.elc.activity.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.MediaType;

public class Konstant {

     public static int otpMessage = 1234 + new Random().nextInt(8765);
     public static String messageBody = "Your one time password(OTP) is: " + otpMessage;
     public static String OTPSENDER = "CEODEL";

    public static final MediaType SOAP_MEDIA_TYPE = MediaType.parse("text/xml");

    public static final String NAMESPACE = "http://tempuri.org/";

    public static final String BASE_URL = "http://ceodelhi.gov.in/DelhiElection/Service.asmx";

    /*SOAP ACTION*/
    public static final String SOAP_ACTION_PSLIST = NAMESPACE + "GetPSNO";
    public static final String METHOD_PSLIST = "GetPSNO";

    public static final String SOAP_ACTION_REGISTRATION = NAMESPACE + "InsertedData";
    public static final String METHOD_REGISTRATION = "InsertedData";

    public static final String SOAP_ACTION_LOGIN = NAMESPACE + "ELCLogin";
    public static final String METHOD_LOGIN = "ELCLogin";

    public static final String SOAP_ACTION_EVENTLIST = NAMESPACE + "GetEventList";
    public static final String METHOD_EVENTLIST = "GetEventList";

    public static final String SOAP_ACTION_InsertEvent = NAMESPACE + "Inserted_EventManagement";
    public static final String METHOD_InsertEvent = "Inserted_EventManagement";

    public static final String SOAP_ACTION_UPDATED_EVENTMANAGEMENT = NAMESPACE + "Updated_EventManagement";
    public static final String METHOD_UPDATED_EVENTMANAGEMENT = "Updated_EventManagement";

    public static final String SOAP_ACTION_GETMOBILECHECK = NAMESPACE + "GetMobileCheck";
    public static final String METHOD_GETMOBILECHECK = "GetMobileCheck";

    public static final String SOAP_ACTION_FNCSMS_MULTIPLE = NAMESPACE + "fncSMS_Multiple";
    public static final String METHOD_FNCSMS_MULTIPLE = "fncSMS_Multiple";

    /*unique methods*/
    public static void altDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Information");
        alert.show();
    }

    public static void altDialogFinish(Activity context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Information");
        alert.show();
    }

    /*Registration success*/
    public static void altRegDialog(Context context, String clubID) {

        String msg = "Congratulation, You successfully registered with " + context.getResources().getString(R.string.electoral_literacy_club)
                + ". Your Club ID is :" + clubID + ". Remember this club id for login";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("clubID", clubID);
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                        dialog.dismiss();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Information");
        alert.show();
    }

    public static void toastShow(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static void setUpToolbar(Toolbar toolbar, Context context) {
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setTitle("");
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((AppCompatActivity)context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
