package com.csows.sweep.utils

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class Permission {

    companion object {
        val TAG = "Permission"
        private val CAMERA_PERMISSION_REQUEST = 10
        private val STORAGE_PERMISSION_REQUEST = 20

        /*Take camera permission*/
        fun cameraPermission(context: Context): Boolean {
            return (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            )
        }

        fun requestCameraPermission(context: Context) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as AppCompatActivity,
                    Manifest.permission.CAMERA
                )
            ) {

                ActivityCompat.requestPermissions(
                    context, arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST
                )

               /* val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage("Permission access is required to display the preview.")
                builder.setCancelable(false)
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        context, arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_REQUEST
                    )
                })

                builder.show()*/

            } else {
                showSettingsDialog(context)
                //  ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
            }
        }

        /*Take storage permission*/
        fun storagePermission(context: Context): Boolean {
            return (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
           // return false
        }

        fun requestStoragePermission(context: Context) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (context as AppCompatActivity?)!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                && ActivityCompat.shouldShowRequestPermissionRationale(
                    (context as AppCompatActivity?)!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

            ) {

                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ), STORAGE_PERMISSION_REQUEST)


                /*val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage("Permission access is required to read the file.")
                builder.setCancelable(false)
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        ), STORAGE_PERMISSION_REQUEST) })

                builder.show()*/

            } else {
                showSettingsDialog(context)
                  //ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), STORAGE_PERMISSION_REQUEST)
            }
        }


        /* --------------------- Common -------------------------------*/
        private fun showSettingsDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Need Permission")
            builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
            builder.setPositiveButton(
                "GOTO SETTINGS",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    openSetting(context)
                })

            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            builder.show()

        }

        private fun openSetting(context: Context) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            (context as AppCompatActivity).startActivityForResult(intent, 101)
        }

    }
}