package com.aob.aobsalesman.activities.utility;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class Myapp extends Application {
    public   static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

}
