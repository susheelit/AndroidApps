package com.ceodelhi.filesystemapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.ceodelhi.filesystemapp.model.ModelOfficer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 1/24/2017.
 */

public class UserPreferences {

    private static String One = "Update";
    private static String Two = "Exceed";
    private static String ZERO = "Error";

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    Gson gson;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "CEODELHI";

    // All Shared Preferences Keys
    private static String Officers = "Officers";
    private static String MOBILE = "MOBILE";
    private static String NAME = "NAME";
    private static final String OTP = "OTP";
    private static final String LOGIN = "LOGIN";
    public static final String token = "token";
    public static final String isTokenSend = "false";
    public static final String newFileNoti = "newFileNotification";
    public static final String newLetterNoti = "newLetterNotification";
    public static final String updateStatus = "0";

    public UserPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    public Boolean getLogin() {
        return pref.getBoolean(LOGIN, false);
    }

    public void setLogin(Boolean login) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(LOGIN, login);
        editor.commit();
    }

    public ArrayList<ModelOfficer> getOfficers() {
        String json = pref.getString(Officers, "");
        Type type = new TypeToken<List<ModelOfficer>>() {
        }.getType();
        ArrayList<ModelOfficer> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    public void setOfficers(ArrayList<ModelOfficer> officers) {
        SharedPreferences.Editor editor = pref.edit();
        String json = gson.toJson(officers);
        editor.putString(Officers, json);
        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public void setMobile(String Mobile) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MOBILE, Mobile);
        editor.commit();
    }

    public void setOTP(String otp) {
        editor.putString(OTP, otp);
        editor.commit();
    }

    public String getOTP() {
        return pref.getString(OTP, null);
    }

    public void setIsTokenSend(String isSend) {
        editor.putString(isTokenSend, isSend);
        editor.commit();
    }
    public String getIsTokenSend() {
        return pref.getString(isTokenSend, null);
    }

    /* manage notification view*/

    // file
    public void setFileNotification(String newFile){
        editor.putString(newFileNoti, newFile);
        editor.commit();
    }
    public String getFileNotification(){
        return pref.getString(newFileNoti, null);
    }

    // Letter
    public void setLetterNotification(String newLetter){
        editor.putString(newLetterNoti, newLetter);
        editor.commit();
    }
    public String getLetterNotification(){
        return pref.getString(newLetterNoti, null);
    }

    // check wether file or letter status is change
    // status 0 not change, 1 change
    public void setLetterorFileStatus(String updateStatus){
        editor.putString(updateStatus, updateStatus);
        editor.commit();
    }
    public String getLetterorFileStatus(){
        return pref.getString(updateStatus, null);
    }


}
