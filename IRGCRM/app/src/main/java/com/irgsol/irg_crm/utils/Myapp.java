package com.irgsol.irg_crm.utils;

import android.app.Application;
import android.content.Context;

public class Myapp extends Application {
    public   static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
