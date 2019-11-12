package com.irgsol.irg_crm.common;

import android.content.Context;
import android.content.Intent;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.activities.LoginActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by hdi on 25/10/17.
 */

public class OprActivity {

    public static void finishAllOpenNewActivity(Context context, AppCompatActivity activity) {
        // clear all and open new Activity
        Intent intent = new Intent(context, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
  //      ((AppCompatActivity) context).overridePendingTransition(R.anim.right_in1, R.anim.left_out1);
    }

    public static void openActivity(Context context, AppCompatActivity activity) {
        Intent intent = new Intent(context, activity.getClass());
        context.startActivity(intent);
//        ((AppCompatActivity) context).overridePendingTransition(R.anim.right_in1, R.anim.left_out1);
    }


    public static void finishActivity(Context context) {
        ((AppCompatActivity) context).finish();
       // ((AppCompatActivity) context).overridePendingTransition(R.anim.right_in1, R.anim.left_out1);
    }


    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public static void setUpToolbar(Toolbar toolbar, Context context) {
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setTitle(context.getResources().getString(R.string.app_name));
    }

    public static void setUpToolbarWithTitle(Toolbar toolbar, String title,Context context) {
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setTitle(title);
    }

    public static void setUpToolbarWithoutBack(Toolbar toolbar, Context context) {
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
       // ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setTitle(context.getResources().getString(R.string.app_name));
    }

    /*public static void replaceFragment(Fragment frag, Context context) {
        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction ft = manager.beginTransaction();
            Fragment currentFrag = manager.findFragmentById(R.id.content_frame);
            ft.setCustomAnimations(R.anim.right_in1, R.anim.left_out1);
            ft.replace(R.id.content_frame, frag).addToBackStack(null).commit();
        }
    }*/

  public static void logoutUser(Context context){
        String userName = SharedPref.getMobileNo(context);
        String password = SharedPref.getPassword(context);
       // GetApi_Result.userLogOut(context, userName, password, true);
      Intent intent = new Intent(context, LoginActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      context.startActivity(intent);
    }

   /*   public static void dismissProgressDialog() {

        if ( CallPostApi.progressDialog!=null && CallPostApi.progressDialog.isShowing() ){
            CallPostApi.progressDialog.dismiss();
        }
    }*/
}
