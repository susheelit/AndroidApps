package com.ceodelhi.filesystemapp.utility;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;


public class ShowDialog {

    private Context context;
    private SharedPreferences prefs;
    private static AlertDialog internetFailureDialog;


    public static void showAlertDialog(Context ctx, String Message, boolean status) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
//        builder.setTitle("Queue Management System");
        builder.setCancelable(false);
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (!status) {
            builder.setIcon(android.R.drawable.ic_dialog_alert);
        } else {
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }

        builder.show();
    }

    public static void showAlertDialogFinish(final Activity activity, String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        builder.setCancelable(false);
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
        builder.show();
    }


//	public static void showInternetFailurDialog(final Activity activity,
//			final Context context, String title, String message, Boolean status) {
//		internetFailureDialog = new AlertDialog.Builder(context).create();
//		internetFailureDialog.setTitle(title);
//		internetFailureDialog.setMessage(message);
//		internetFailureDialog.setIcon((status) ? R.drawable.success
//				: R.drawable.fail);
//		internetFailureDialog.setButton(AlertDialog.BUTTON_POSITIVE,
//				"Open Settings", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = new Intent(
//								android.provider.Settings.ACTION_SETTINGS);
//						context.startActivity(intent);
//					}
//				});
//		internetFailureDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
//				"Close App", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						activity.finish();
//					}
//				});
//		internetFailureDialog.setCancelable(false);
//		internetFailureDialog.setCanceledOnTouchOutside(false);
//		internetFailureDialog.show();
//	}

//	public void logoutDialog() {
//
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				context);
//		alertDialogBuilder.setTitle(R.string.app_name);
//		alertDialogBuilder
//				.setMessage("Do you want to Logout ?")
//				.setCancelable(false)
//				.setPositiveButton("Cancel",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								dialog.cancel();
//							}
//						})
//				.setNegativeButton("Confirm",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								// System.out.println("login type "+
//								// UserContext.LOGIN_TYPE);
//
//								if (mGoogleApiClient.isConnected()) {
//									Plus.AccountApi
//											.clearDefaultAccount(mGoogleApiClient);
//									mGoogleApiClient.disconnect();
//								}
//								ParsePush.unsubscribeInBackground("Id"
//										+ UserContext.USER_ID);
//								ParseInstallation.getCurrentInstallation().saveEventually();
//
//								prefs = context.getSharedPreferences(
//										"food_app", Context.MODE_PRIVATE);
//								prefs.edit().clear().commit();
//
//								TwistApplication.userIsLogin = false;
//
//								context.startActivity(new Intent(context,
//										MainActivity.class)
//										.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//							}
//						});
//		AlertDialog alertDialog = alertDialogBuilder.create();
//		alertDialog.show();
//	}

}
