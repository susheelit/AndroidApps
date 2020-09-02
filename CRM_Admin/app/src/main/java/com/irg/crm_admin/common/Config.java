package com.irg.crm_admin.common;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irg.crm_admin.activity.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {


/*

    public static void altShow(Context context, String msg) {
        AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
        altDialog.setTitle("Information");
        altDialog.setMessage(msg);
        altDialog.setPositiveButton(msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        altDialog.create();
        altDialog.show();
    }

    public static void toastShow(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void altLogout(Context context) {
        AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
        altDialog.setTitle("Information");
        altDialog.setMessage("Are you sure want to logout?");
        altDialog.setCancelable(false);
        altDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        altDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        altDialog.create();
        altDialog.show();
    }
}
*/

    //public static final String baseUrl="http://10.128.140.43/irg_crm/api/";
    public static final String baseUrl="http://192.168.0.106/irg_crm/api/";

    public static boolean internetStatus = false;

    public static int currentapiVersion = Build.VERSION.SDK_INT;

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static ProgressDialog mProgressDialog = null;

    public static void showDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public static void hideDialog(Context context) {

        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    /*public static String authCode(){
        long randomNumber = (long) ((Math.random() * 9000000) + 1000000);
        return String.valueOf(randomNumber);
    }*/

    public static void closeKeyboard(Context context) {
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // check internet connection
    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            internetStatus = true;
            return true;
        } else {
            internetStatus = false;
        }
        return internetStatus;
    }

    public static void toastShow(String s, Context c) {
        Toast toast = Toast.makeText(c, s, Toast.LENGTH_LONG);
        TextView v = toast.getView().findViewById(android.R.id.message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        v.setTextSize(16);
        toast.show();
    }

    public static void alertBox(String s, Context c) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(c);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create();
        builder.show();
    }


    public static void alertClose(String s, final Context c) {

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(s);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                OprActivity.finishActivity(c);
            }
        });

        builder.create();
        builder.show();
    }

    public static void logoutUser(Context context) {
        SharedPref.clearSharedPreferences(context);
        OprActivity.finishAllOpenNewActivity(context, new LoginActivity());

    }

    public static void rateAppOnMarket(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Config.toastShow("unable to find market app", context);
        }
    }


    public static String getTimeAgo(String dateTime) {
        return DateUtils.getRelativeTimeSpanString(getDateInMillis(dateTime), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }

    public static long getDateInMillis(String srcDate) {

        //07/04/2018 05:52:22

        //SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            //Log.e("Exception while parsing date. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /*   public static String getJson(Object obj){
           Gson gson = new Gson();
           return gson.toJson(obj);

       }
   */
    public static String twoDecimal(String amt) {

        if (amt.compareTo("") != 0) {

            double result = new BigDecimal(Double.parseDouble(amt)).
                    setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            return new DecimalFormat("##.##").format(result);//+".00";
        } else {
            return "";
        }

    }


   /* public static double stringToDouble(String amt){

        double amount=0;
        if(amt.compareTo("")!=0) {

            amount = new BigDecimal(Double.parseDouble(amt)).
                    setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return amount;
    }*/

   /* public static int doubleToInt(String amt){
            int amount =0;
            if(amt.compareTo("")!=0) {

                double sss = Double.parseDouble(amt);
                amount =  (int)sss;

            }else {
                amount = 0;
             }

             Log.e("doubleToInt ", "hdi "+amount);
        return amount;
    }*/


    /*
     */

   /* public static void photoDialog(Context context) {

        final TextView tvCamera, tvGallery;
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_photo_diag);

        dialog.setTitle("Select...");

        tvCamera = dialog.findViewById(R.id.tvCamera);
        tvGallery = dialog.findViewById(R.id.tvGallery);

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();

    }*/

    public static void setImage(Context context, ImageView imageView, String url) {

        Glide.with(context).load(url).into(imageView);
    }

    public static void setCircularBitmap(Context context, ImageView imageView, Bitmap bitmap){

        Glide.with(context)
                .asBitmap()
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    public static void setCircularImage(Context context, ImageView imageView, Uri uri){

        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    public static String convertBitmapToBase64String(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //  Log.d("base64String", imageString);
        return imageString;
    }

}
