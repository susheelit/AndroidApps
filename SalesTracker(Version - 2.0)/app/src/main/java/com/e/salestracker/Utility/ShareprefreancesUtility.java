package com.e.salestracker.Utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class ShareprefreancesUtility extends Application {
   public static String PREFERENCE_NAME ="SalesTracker";
   public static ShareprefreancesUtility shareprefreancesUtility;
     SharedPreferences sharedPreferences;

   private ShareprefreancesUtility(Context context){
       PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName() ;
       sharedPreferences =context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
   }

   public static ShareprefreancesUtility getInstance() {
       if (shareprefreancesUtility==null) {
           shareprefreancesUtility = new ShareprefreancesUtility(Myapp.getContext());
       }
       return shareprefreancesUtility;
   }

   public void saveString(String key,String value){
       SharedPreferences.Editor editor=sharedPreferences.edit();
       editor.putString(key,value);
       editor.commit();
       }

   public String getString(String key,String devalue){
       return sharedPreferences.getString(key,devalue);
       }

   public String getString(String key){

       return  sharedPreferences.getString(key,"");
     }

}
