package com.csows.sweep.utils

import android.R
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ShowDialog {

    companion object {

        fun toastShow(msg: String, mCtx: Context) {
            Toast.makeText(mCtx, msg, Toast.LENGTH_LONG).show()
        }

        fun alertError(msg: String, mCtx: Context) {
            var builder: AlertDialog.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = Builder(mCtx, R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
            } else {
                builder = AlertDialog.Builder(mCtx)
            }
            builder.setCancelable(false)
            builder.setMessage(msg)
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.show()

        }
    }


}