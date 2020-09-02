package com.irg.crm_admin.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static String preferences = "pref_irg_crm";

    public static void putSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        String defvalue = sharedPreferences.getString(key, value);
        return defvalue;
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void putRegId(Context context, String reg_id) {
        putSharedPreferences(context, "reg_id", reg_id);
    }

    public static String getRegId(Context context) {
        return getSharedPreferences(context, "reg_id", "");
    }


    public static void putUserRole(Context context, String user_name) {
        putSharedPreferences(context, "user_role", user_name);
    }

    public static String getUserRole(Context context) {
        return getSharedPreferences(context, "user_role", "");
    }

    public static void putUserName(Context context, String user_name) {
        putSharedPreferences(context, "user_name", user_name);
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context, "user_name", "");
    }

    public static void putPassword(Context context, String password) {
        putSharedPreferences(context, "Password", password);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context, "Password", "");
    }

    public static void putUserID(Context context, String userID) {
        putSharedPreferences(context, "UserID", userID);
    }

    public static String getUserID(Context context) {
        return getSharedPreferences(context, "UserID", "");
    }

    public static void putMobileNo(Context context, String mobileNo) {
        putSharedPreferences(context, "MobileNo", mobileNo);
    }

    public static String getMobileNo(Context context) {
        return getSharedPreferences(context, "MobileNo", "");
    }

    public static void putAltmobileno(Context context, String alt_mobile_no) {
        putSharedPreferences(context, "alt_mobile_no", alt_mobile_no);
    }

    public static String getAltmobileno(Context context) {
        return getSharedPreferences(context, "alt_mobile_no", "");
    }


    public static void putEmailId(Context context, String email_id) {
        putSharedPreferences(context, "email_id", email_id);
    }

    public static String getEmailId(Context context) {
        return getSharedPreferences(context, "email_id", "");
    }


    public static void putUserImage(Context context, String user_image ) {
        putSharedPreferences(context, "user_image", user_image);
    }

    public static String getUserImage(Context context) {
        return getSharedPreferences(context, "user_image", "");
    }

}
