package com.csows.sweep.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity

class ConnectionDetector {

    /* public static boolean isConnectedToInernet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (manager != null) {
			NetworkInfo infos[] = manager.getAllNetworkInfo();
			if (infos != null) {
				for (int i = 0; i < infos.length; i++) {
					if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
*/
    companion object{
        fun isConnectedToInernet(mCtx: Context): Boolean {
            val manager: ConnectivityManager =
                (mCtx as AppCompatActivity).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (manager != null) {
                val infos: Array<out NetworkInfo> = manager.allNetworkInfo
                if (infos != null) {
                    for (s in infos.indices) {
                        if (infos[s].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }
            return false
        }
    }


}