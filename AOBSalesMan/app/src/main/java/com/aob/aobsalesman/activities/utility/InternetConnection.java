package com.aob.aobsalesman.activities.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aob.aobsalesman.activities.utility.Myapp;

public class InternetConnection {
    private static boolean connected=false;
    public static boolean checkInternetConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Myapp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }
}
