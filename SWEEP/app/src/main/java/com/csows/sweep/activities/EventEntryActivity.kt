package com.csows.sweep.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.csows.sweep.R
import com.csows.sweep.controller.ApiClient
import com.csows.sweep.utils.*
import com.csows.sweep.utils.CustomProgressDialog.Companion.closeDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_event_entry.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EventEntryActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    companion object {
        private lateinit var mCtx: Context
        private val TAG = EventEntryActivity::class.java.simpleName
        private lateinit var userPreferences: UserPreferences
        private var processDialog: Dialog? = null

        private var day = 0
        private var month: Int = 0
        private var year: Int = 0
        private var hour: Int = 0
        private var minute: Int = 0
        private var myDay = 0
        private var myMonth: Int = 0
        private var myYear: Int = 0
        private var myHour: Int = 0
        private var myMinute: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_entry)
        initView()
    }

    private fun initView() {
        this.also { mCtx = it }
        userPreferences = UserPreferences(mCtx)
        toolbarWithBackAndTitle(toolbar, mCtx, "Create Event")
        btnSubmit.setOnClickListener(this)
        etDate.setOnClickListener(this)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            etDate -> {
                openDateDialog()
            }

            btnSubmit -> validate()
        }

    }

    private fun openTimeDialog() {
        val timePickerDialog = TimePickerDialog(
            mCtx, this@EventEntryActivity, hour, minute, false
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        val datetime = Calendar.getInstance()
        datetime[Calendar.HOUR_OF_DAY] = hourOfDay
        datetime[Calendar.MINUTE] = minute
        var amPm = ""
        if (datetime.get(Calendar.AM_PM) == Calendar.AM) {
            amPm = "AM"
        } else if (datetime.get(Calendar.AM_PM) == Calendar.PM) {
            amPm = "PM"
        }

        val selDate = "$myDay/$myMonth/$myYear"
        val selTime = "$myHour/$myMinute/00 $amPm"
        etDate.setText("$selDate $selTime")
    }

    private fun openDateDialog() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(mCtx, this@EventEntryActivity, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()

        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        openTimeDialog()
    }

    private fun validate() {

        val eventName = etEventName.text.toString().trim()
        val eventDate = etDate.text.toString().trim()
        val remark = etRemark.text.toString().trim()

        if (TextUtils.isEmpty(eventName)) {
            showSnackbar(findViewById(R.id.parent), "Enter Event Name", mCtx)
            return
        }

        if (TextUtils.isEmpty(eventDate)) {
            showSnackbar(findViewById(R.id.parent), "Select Event Date", mCtx)
            return
        }

        if(ConnectionDetector.isConnectedToInernet(mCtx)){
            sendEvent(eventName, eventDate, remark)
        }else{
            showSnackbar(findViewById(R.id.parent), resources.getString(R.string.no_internet), mCtx)
        }

    }

    private fun sendEvent(eventName: String, eventDate: String, remark: String) {
        processDialog = CustomProgressDialog.showDialog(mCtx, "Creating Event... ")
        val call = userPreferences.getOfficerMobile()?.let {
            ApiClient.getClient().insertEvent(eventName, eventDate, remark,
                it
            )
        }

        call?.enqueue(object:Callback<String?>{
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                processDialog?.let { closeDialog(it) }
                if(response.isSuccessful){
                    Log.e(TAG, "response " + Gson().toJson(response.body()))
                    //response  Success
                  
                }else{
                    showSnackbar(findViewById(R.id.parent), resources.getString(R.string.went_wrong), mCtx)
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                processDialog?.let { closeDialog(it) }
                showSnackbar(findViewById(R.id.parent), resources.getString(R.string.went_wrong), mCtx)
            }
        })

    }

}