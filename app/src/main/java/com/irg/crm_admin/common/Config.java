package com.irg.crm_admin.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class Config {

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
