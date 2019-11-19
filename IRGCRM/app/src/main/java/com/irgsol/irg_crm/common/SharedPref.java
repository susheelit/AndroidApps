package com.irgsol.irg_crm.common;

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

    public static void putMemberName(Context context, String memberID) {
        putSharedPreferences(context, "MemberName", memberID);
    }

    public static String getMemberName(Context context) {
        return getSharedPreferences(context, "MemberName", "");
    }

    public static void putMemberID(Context context, String memberID) {
        putSharedPreferences(context, "MemberID", memberID);
    }

    public static void putPassword(Context context, String password) {
        putSharedPreferences(context, "Password", password);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context, "Password", "");
    }


    public static String getMemberID(Context context) {
        return getSharedPreferences(context, "MemberID", "");
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

    public static void putIsShow(Context context, String isAllService) {
        putSharedPreferences(context, "ServiceList", isAllService);
    }

    public static String getIsShow(Context context) {
        return getSharedPreferences(context, "ServiceList", "");
    }


    public static void putMemberPackageID(Context context, String MemberPackageID) {
        putSharedPreferences(context, "MemberPackageID", MemberPackageID);
    }

    public static String getMemberPackageID(Context context) {
        return getSharedPreferences(context, "MemberPackageID", "");
    }

    public static void putreferralcode(Context context, String referralcode) {
        putSharedPreferences(context, "referralcode", referralcode);
    }

    public static String getreferralcode(Context context) {
        return getSharedPreferences(context, "referralcode", "");
    }


    public static void putAddService(Context context, String isAdd ) {
        putSharedPreferences(context, "AddService", isAdd);
    }

    public static String getAddService(Context context) {
        return getSharedPreferences(context, "AddService", "");
    }

}
