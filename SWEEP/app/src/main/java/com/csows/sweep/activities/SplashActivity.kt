package com.csows.sweep.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.csows.sweep.R
import com.csows.sweep.utils.ConnectionDetector
import com.csows.sweep.utils.UserPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mCtx:Context
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        initView()

    }

    private fun initView() {
        this.also { mCtx = it }
        userPreferences = UserPreferences(mCtx)
        progressbar.visibility = View.VISIBLE
        if (ConnectionDetector.isConnectedToInernet(mCtx)) {

            val loginActivity = LoginActivity()
            if (userPreferences.getLogin()) {
                if (!TextUtils.isEmpty(userPreferences.getOfficerMobile())) {
                    loginActivity.getLogin(userPreferences.getOfficerMobile()!!, false, mCtx)
                } else {
                    startActivity(Intent(mCtx, LoginActivity::class.java))
                    finish()
                }
            }else{
                startActivity(Intent(mCtx, LoginActivity::class.java))
                finish()
            }

        }else{
            showSnakbar()
        }

    }


    private fun showSnakbar() {
        progressbar.visibility = View.GONE
        Snackbar.make(findViewById(R.id.parent),
            resources.getString(R.string.no_internet),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Retry") {
                initView()
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_blue_dark))
            .show()
    }
}