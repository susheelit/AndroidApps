package com.aob.aobsalesman.activities;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MyAnalytics {
    private static MyAnalytics mInstance;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static Context mContext;

    private MyAnalytics(Context context){
        // Specify the application context
        mContext = context;
        // Get the request queue
        mFirebaseAnalytics = getFirebaseAnalytics();
    }

    public static synchronized MyAnalytics getInstance(Context context){
        // If Instance is null then initialize new Instance
        if(mInstance == null){
            mInstance = new MyAnalytics(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }

    public FirebaseAnalytics getFirebaseAnalytics(){
        // If RequestQueue is null the initialize new RequestQueue
        if(mFirebaseAnalytics == null){
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        }

        // Return FirebaseAnalytics
        return mFirebaseAnalytics;
    }

}
