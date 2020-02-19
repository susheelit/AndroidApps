package com.e.salestracker.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.Toast;

import com.e.salestracker.R;

public class Config {

    public static String getProjectName(){
        String projectNames = ShareprefreancesUtility.getInstance().getString("project_name");

        String projectlist[]= projectNames.trim().split(",");
        String projectName="";

        for(int i=0;i<projectlist.length;i++) {
            if( projectlist[i].compareToIgnoreCase("PM Cona")==0) {
                projectName = projectlist[i];
                break;
            }
        }
        return projectName;
    }

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }

    public static ProgressDialog createProgressDialog(Context context ) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        // dialog.setMessage(Message);
        return dialog;
    }

}
