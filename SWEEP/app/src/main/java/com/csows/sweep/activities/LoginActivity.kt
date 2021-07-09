package com.csows.sweep.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.csows.sweep.R
import com.csows.sweep.controller.ApiClient
import com.csows.sweep.models.UserList
import com.csows.sweep.utils.ConnectionDetector
import com.csows.sweep.utils.CustomProgressDialog
import com.csows.sweep.utils.CustomProgressDialog.Companion.closeDialog
import com.csows.sweep.utils.ShowDialog.Companion.alertError
import com.csows.sweep.utils.ShowDialog.Companion.toastShow
import com.csows.sweep.utils.UserPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var mCtx: Context
    lateinit var userPreferences: UserPreferences
    val TAG = "LoginActivity"
    private var processDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        this.also { mCtx = it }

        btnSubmit.setOnClickListener {
            val mobileNo = etMobile.text.toString().trim()
            if (!TextUtils.isEmpty(mobileNo)) {
                if (mobileNo.length == 10) {
                    getLogin(mobileNo, true, mCtx)
                } else {
                    alertError("Please enter 10 digit mobile no", mCtx)
                }

            } else {
                alertError("Please enter mobile no", mCtx)

            }
        }

    }

    fun getLogin(mobileNo: String, isShowDialog: Boolean, mCtx1: Context) {
        if (ConnectionDetector.isConnectedToInernet(mCtx1)) {
            if (isShowDialog) {
                processDialog = CustomProgressDialog.showDialog(mCtx1, "Logging in... ")
            }
            val call = ApiClient.getClient().getLoginDetails(mobileNo)
            call?.enqueue(object : Callback<UserList?> {
                override fun onResponse(call: Call<UserList?>, response: Response<UserList?>) {

                    if (isShowDialog) {
                        processDialog?.let { closeDialog(it) }
                    }
                    if (response.isSuccessful) {

                        Log.e(TAG, "response " + Gson().toJson(response.body()))
                        Log.e(TAG, "response 123 " + response.body()!!)
                        //var responseList: User? = User()
                        var responseList = response.body()
                        if (null != responseList) {
                            setDetails(responseList, mCtx1)
                        } else {
                            toastShow(resources.getString(R.string.went_wrong), mCtx1)
                        }
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                        toastShow(resources.getString(R.string.went_wrong), mCtx1)
                    }
                }

                override fun onFailure(call: Call<UserList?>, t: Throwable) {
                    Log.e(TAG, "onFailure " + t.message)
                    toastShow(resources.getString(R.string.went_wrong), mCtx1)
                    if (isShowDialog) {
                        processDialog?.let { closeDialog(it) }
                    }
                }
            })

        } else {
            toastShow(resources.getString(R.string.no_internet), mCtx1)
        }
    }

    private fun setDetails(responseList: UserList, mCtx1: Context) {
        userPreferences = UserPreferences(mCtx1)
        val userList = responseList.LoginDetails
        if (userList != null && userList.isNotEmpty()) {
            val user = userList[0]
            userPreferences.setLogin(true)
            userPreferences.setBranchName(user.BranchName)
            userPreferences.setID(user.ID)
            userPreferences.setOfficerMobile(user.OfficerMobile)
            userPreferences.setOfficerName(user.OfficerName)
            mCtx1.startActivity(Intent(mCtx1, HomeActivity::class.java))
            (mCtx1 as AppCompatActivity).finish()
        }

    }
}