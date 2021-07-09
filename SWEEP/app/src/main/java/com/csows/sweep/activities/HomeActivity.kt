package com.csows.sweep.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.csows.sweep.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mCtx: Context
    val TAG = HomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    private fun initView() {
        this.also { mCtx = it }
        etActivityCalender.setOnClickListener(this)
        btnSveepActivity.setOnClickListener(this)
        btnResource.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            etActivityCalender -> {
                startActivity(Intent(mCtx, EventListActivity::class.java))
            }

            btnSveepActivity -> {

            }

            btnResource -> {
                startActivity(Intent(mCtx, ResourceEntryActivity::class.java))
            }

        }
    }
}