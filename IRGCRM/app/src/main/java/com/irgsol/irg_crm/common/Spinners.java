package com.irgsol.irg_crm.common;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.irgsol.irg_crm.R;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by hdi on 30/10/17.
 */

public class Spinners {

    static String selectedItem = "";
    public static void spinnerItems(Context context, Spinner spinner, String listItem[]){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public static void showSpinnerList(final Context context, String listItems[], String title, final EditText editText){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.app_logo);
        builderSingle.setTitle("Select "+title);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(listItems);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem = arrayAdapter.getItem(which);
                editText.setText(selectedItem);

            }
        });

        builderSingle.show();
    }

}
