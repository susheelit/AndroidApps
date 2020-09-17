package com.ceodelhi.taskmanagement.utility;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

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
    private static String Officer = "Officer";
    private static String Developer = "Developer";
    private static String MOBILE = "MOBILE";
    private static String Post = "Post";
    private static String NAME = "NAME";
    private static final String OTP = "OTP";
    private static final String LOGIN = "LOGIN";

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

   /* public ArrayList<ModelOfficer> getOfficers() {
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
    }*/

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public void setMobile(String Mobile) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MOBILE, Mobile);
        editor.commit();
    }

    public String getPost() {
        return pref.getString(Post, "");
    }

    public void setPost(String Mobile) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Post, Mobile);
        editor.commit();
    }

    public void setOTP(String otp) {
        editor.putString(OTP, otp);
        editor.commit();
    }

    public String getOTP() {
        return pref.getString(OTP, null);
    }

}
