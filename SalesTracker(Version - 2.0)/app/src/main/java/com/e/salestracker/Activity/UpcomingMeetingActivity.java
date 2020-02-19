package com.e.salestracker.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.AdapterUpcomingMeeting;
import com.e.salestracker.Modal.UpcomingMeetingModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class UpcomingMeetingActivity extends AppCompatActivity {

    private TextView tv_no_data_lead;
    private RecyclerView recyclerView;
    private AdapterUpcomingMeeting mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ProgressDialog progressDialog;
    List<UpcomingMeetingModal> items = new ArrayList<>();
    SwipeRefreshLayout swiperefresh_myleads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_meeting);
        progressDialog = new ProgressDialog(this);
        initToolbar();
        initComponent();
        swiperefresh_myleads.setRefreshing(true);
        swiperefresh_myleads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initComponent();
            }
        });

    }

    private void initToolbar() {
        tv_no_data_lead = findViewById(R.id.tv_no_data_lead);
        swiperefresh_myleads = findViewById(R.id.swiperefresh_myleads);
        TextView title = findViewById(R.id.main_name);
        TextView date = findViewById(R.id.date);
        ImageView back = findViewById(R.id.back_image);
        title.setText("Upcoming Meeting");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initComponent() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        if(InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = null;
            try {
                stringRequest = new StringRequest(Request.Method.POST,
                        getResources().getString(R.string.host_name)+"/sales_tracker/upcoming_meeting_json_app.php?agent_id="+ShareprefreancesUtility.getInstance().getString("userId"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                swiperefresh_myleads.setRefreshing(false);
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if (jsonObject.getString("status").equalsIgnoreCase("ok")){
                                        JSONArray jsonArray=jsonObject.getJSONArray("Data");
                                        if (items!=null)
                                            items.clear();

                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                            UpcomingMeetingModal upcomingMeetingModal = new UpcomingMeetingModal(
                                                    jsonObject1.getString("agent_id"),
                                                    jsonObject1.getString("client_name"),
                                                    jsonObject1.getString("next_meeting"),
                                                    jsonObject1.getString("visit_type"),
                                                    jsonObject1.getString("user_location"),
                                                    jsonObject1.getString("description"),
                                                    jsonObject1.getString("project_name_form"),
                                                    jsonObject1.getString("last_visited"));

                                            items.add(upcomingMeetingModal);
                                        }
                                        mAdapter = new AdapterUpcomingMeeting(UpcomingMeetingActivity.this, items);
                                        recyclerView.setAdapter(mAdapter);

                                        if(!items.isEmpty()) {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            tv_no_data_lead.setVisibility(View.INVISIBLE);

                                        }else
                                        {
                                            recyclerView.setVisibility(View.INVISIBLE);
                                            tv_no_data_lead.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else {
                                        Toast.makeText(UpcomingMeetingActivity.this,jsonObject.getString("status") , Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(UpcomingMeetingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swiperefresh_myleads.setRefreshing(false);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(UpcomingMeetingActivity.this);
                        builder1.setMessage("Check your internet connection");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Retry",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        initComponent();
                                        dialog.cancel();
                                    }
                                });
                        builder1.setNegativeButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(UpcomingMeetingActivity.this).addToRequestQueue(stringRequest);
        } else {
            swiperefresh_myleads.setRefreshing(false);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(UpcomingMeetingActivity.this);
            builder1.setMessage("Retry with Internet connection");
            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            initComponent();
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton(
                    "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

}
