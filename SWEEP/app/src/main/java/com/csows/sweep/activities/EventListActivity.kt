package com.csows.sweep.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.csows.sweep.R
import com.csows.sweep.adapters.EventListAdapter
import com.csows.sweep.controller.ApiClient
import com.csows.sweep.models.EventDetail
import com.csows.sweep.models.EventList
import com.csows.sweep.utils.ConnectionDetector
import com.csows.sweep.utils.CustomProgressDialog.Companion.closeDialog
import com.csows.sweep.utils.finishActivity
import com.csows.sweep.utils.showSnackbar
import com.csows.sweep.utils.toolbarWithBackAndTitle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventListActivity : AppCompatActivity() {

    companion object{
        lateinit var mCtx:Context
        private val TAG = EventEntryActivity::class.java.simpleName
        private var processDialog: Dialog? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        initView()
    }

    private fun initView() {
        this.also { mCtx = it }
        toolbarWithBackAndTitle(toolbar, mCtx, "Event History ")
        getEventList()
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, EventEntryActivity::class.java))
        }

        swipeRefreshLayout.setOnRefreshListener {
            getEventList()
        }
    }

    private fun getEventList() {
        if(ConnectionDetector.isConnectedToInernet(mCtx)){

            val call = ApiClient.getClient().getEventList()

            call?.enqueue(object:Callback<EventList>{
                override fun onResponse(call: Call<EventList>, response: Response<EventList>) {
                    processDialog?.let { closeDialog(it) }
                    if(swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing = false
                    }
                    if(response.isSuccessful){
                        Log.e(TAG, "response " + Gson().toJson(response.body()))

                       bindRecyclerView(response)
                    }else{
                        showSnackbar(findViewById(R.id.parent), resources.getString(R.string.went_wrong),
                            mCtx
                        )
                    }
                }

                override fun onFailure(call: Call<EventList>, t: Throwable) {
                    if(swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing = false
                    }
                    processDialog?.let { closeDialog(it) }
                    showSnackbar(findViewById(R.id.parent), resources.getString(R.string.went_wrong), mCtx)
                }
            })

        }else{
            showSnackbar(findViewById(R.id.parent), resources.getString(R.string.no_internet), mCtx)
        }

    }

    private fun bindRecyclerView(response: Response<EventList>) {
        val list : List<EventDetail> = response.body()?.EventDetails as ArrayList<EventDetail>
        if(list.isNotEmpty()){
            if(list.size == 1){
                tvFound.text = ""+list.size + " Event Found."
            }else{
                tvFound.text = ""+list.size + " Events Found."
            }
            recyclerView.layoutManager = LinearLayoutManager(mCtx)
            recyclerView.setHasFixedSize(true)
            val adapter = EventListAdapter(list)
            recyclerView.adapter = adapter
        }else{
           showSnackbar(findViewById(R.id.parent), "No Event Found", mCtx)
        }
    }

    override fun onBackPressed() {
        finishActivity(mCtx)
    }

    override fun onSupportNavigateUp(): Boolean {
        finishActivity(mCtx)
        return true
    }
}