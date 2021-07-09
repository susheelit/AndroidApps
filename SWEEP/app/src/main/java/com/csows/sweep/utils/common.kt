package com.csows.sweep.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.finishAfterTransition
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

fun toolbarWithBackAndTitle(toolbar: Toolbar?, context: Context, title: String?) {

    context.let {
        (context as AppCompatActivity).setSupportActionBar(toolbar)
        context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context.supportActionBar?.title = title
    }
}

fun btnEnable(view: View) {
    view.alpha = 1.0f
    view.isEnabled = true
}

fun btnDisable(view: View) {
    view.alpha = 0.5f
    view.isEnabled = false
}

fun getfileExtension(fileName: String): String {
    return fileName.substring(fileName.lastIndexOf("."))
}

fun generateRandomNo(): Int {
    return 1234 + Random.nextInt(8756)
}

fun showSnackbar(parentId: View, msg: String, context: Context) {
    val snack = Snackbar.make(parentId, msg, Snackbar.LENGTH_LONG)
        .setAction("OK") {

        }.setActionTextColor(context.resources.getColor(android.R.color.holo_blue_dark))
    snack.show()
}

fun finishActivity(context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        finishAfterTransition(context as Activity)
    }else{
        (context as Activity).finish()
    }
}
